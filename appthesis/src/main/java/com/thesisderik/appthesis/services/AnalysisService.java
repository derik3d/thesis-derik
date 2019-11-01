package com.thesisderik.appthesis.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.thesisderik.appthesis.interfaces.IAnalysisService;
import com.thesisderik.appthesis.interfaces.IExperimentDataIntegrator;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentRequestFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;
import com.thesisderik.appthesis.processservices.BaseProcessService;
import com.thesisderik.appthesis.processservices.QSARProcessService;
import com.thesisderik.appthesis.processservices.IProcessService;
import com.thesisderik.appthesis.processservices.MachineLearningProcessService;
import com.thesisderik.appthesis.processservices.SmilesCrawlerProcessService;
import com.thesisderik.appthesis.processservices.StatisticsProcessService;

@Service
public class AnalysisService implements IAnalysisService {
	
	private IExperimentDataIntegrator iExperimentDataIntegrator;
	private ArrayList<IProcessService> suscribedServices = new ArrayList<>();
	
	@Autowired
	SmilesCrawlerProcessService smilesCrawlerProcessService;
	
	@Autowired
	MachineLearningProcessService machineLearningProcessService;
	


	
	@Override
	public boolean integrateFeaturesFile(ArrayList<String> file) {
		
		iExperimentDataIntegrator.integrateExperimentResult( BaseProcessService.fileToStructure(file) );
		
		return true;

	}
	
	
	@Override
	public boolean integrateFeaturesFile(String filename, String csvUrl) {


		ArrayList<String> data = new ArrayList<>();
				
		if(filename.contains("."))
			filename = filename.substring(0,filename.lastIndexOf("."));
		
		data.add(filename);
		
		File resource;
		try {
			
			resource = new UrlResource(csvUrl).getFile();
			
			data.add(new String(Files.readAllBytes(resource.toPath())));

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		return integrateFeaturesFile(data);

	}
	

	@Override
	public ArrayList<String> getServices() {
		
		suscribedServices.add(new StatisticsProcessService());
		suscribedServices.add(new QSARProcessService());		
		suscribedServices.add(smilesCrawlerProcessService);
		suscribedServices.add(machineLearningProcessService);
		
		return new ArrayList<>(suscribedServices.stream().map(IProcessService::getServiceName).collect(Collectors.toList()));
	}

	@Override
	public void setExperimentDataIntegrator(IExperimentDataIntegrator integrator) {
		this.iExperimentDataIntegrator = integrator;		
	}
			
	@Override
	public void processData(ExperimentRequestFileDataStructure data) {
		
		
		//preparing data for analysis
		ArrayList<ArrayList<String>> dataForInstances = data.deleteFirstCols(2);
		ArrayList<String> featureNames = new ArrayList<String>( data.getFirstRow().subList(2, data.getFirstRow().size()));
		
		String[] fullName = data.getFileName().split("_CMDSEP_");
		
		
		
		String serviceName = fullName[0];
		String serviceArgs = fullName[1];
		String name = fullName[2];
		
		IProcessService service = selectService(serviceName);
		service.setData(serviceArgs, dataForInstances , featureNames, data.getUQName());		
		
		
		if(service.getResult()==null)
			return;
		
		ArrayList<String> resultsTags = service.getFeaturesNames();
		ArrayList<ArrayList<String>> resultsToSave = service.getResult();
		
		
		
		ExperimentResultsFileDataStructure response = new ExperimentResultsFileDataStructure();
		response.setFileName(name);

		
		response.setFirstRow(new ArrayList<>());
		response.getFirstRow().add("names");
		response.getFirstRow().addAll(resultsTags);
		
		response.setDataRows(new ArrayList<>());
		
		for(int i = 0; i< data.getDataRows().size(); i++) {
			
			ArrayList<String> myResultRow = new ArrayList<>();
			myResultRow.add(data.getDataOfRow(i).get(0));
			myResultRow.addAll(resultsToSave.get(i));
			
			response.getDataRows().add(myResultRow);
			
		}
		
		iExperimentDataIntegrator.integrateExperimentResult( response );
	}
	

	public IProcessService selectService(String serviceName) {
		
		for(IProcessService ps : suscribedServices) {
			if(ps.getServiceName().equals(serviceName)) {
				return ps;
			}
		}
		return null;
		
	}
	



}
