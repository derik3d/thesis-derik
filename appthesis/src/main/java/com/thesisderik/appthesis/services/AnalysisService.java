package com.thesisderik.appthesis.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.thesisderik.appthesis.interfaces.IAnalysisService;
import com.thesisderik.appthesis.interfaces.IExperimentDataIntegrator;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentRequestFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;
import com.thesisderik.appthesis.processservices.BaseProcessService;
import com.thesisderik.appthesis.processservices.ClusteringProcessService;
import com.thesisderik.appthesis.processservices.IProcessService;
import com.thesisderik.appthesis.processservices.SmilesCrawlerProcessService;
import com.thesisderik.appthesis.processservices.StatisticsProcessService;

@Service
public class AnalysisService implements IAnalysisService {
	
	private IExperimentDataIntegrator iExperimentDataIntegrator;
	private ArrayList<IProcessService> suscribedServices = new ArrayList<>();
	
	
	

	@Override
	public void integrateFeaturesFile(ArrayList<String> file) {

		ExperimentResultsFileDataStructure erde = new ExperimentResultsFileDataStructure();
		
		
		erde.setFileName(file.get(0));
		
		boolean firstRow=true;
		
		ArrayList<ArrayList<String>> tempCont = new ArrayList<>();
		
		for(String row : file.get(1).split("\n")) {
			
			if(firstRow) {
				if(row.length()>2) {
					erde.setFirstRow(new ArrayList<>(Arrays.asList( row.split(",")) ));
					firstRow=false;
				}
			}else{
			
	
				if(row.length()>2) {
					tempCont.add(new ArrayList<>(Arrays.asList( row.split(",")) ));
				}
			
			}
			
		}
		
		erde.setDataRows(tempCont);

		
		System.out.println(erde);

		
		iExperimentDataIntegrator.integrateExperimentResult( erde );

	}

	@Override
	public ArrayList<String> getServices() {
		
		suscribedServices.add(new StatisticsProcessService());
		suscribedServices.add(new ClusteringProcessService());
		suscribedServices.add(new SmilesCrawlerProcessService());
		
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
		service.setData(serviceArgs, dataForInstances , featureNames);		
		
		
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
