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
import com.thesisderik.appthesis.processservices.StatisticsProcessService;

@Service
public class AnalysisService implements IAnalysisService {
	
	private IExperimentDataIntegrator iExperimentDataIntegrator;
	private ArrayList<BaseProcessService> suscribedServices;

	@Override
	public ArrayList<String> getServices() {
		
		ArrayList<String> myList = new ArrayList<>();
		myList.add("SmilesCrawler");
		myList.add("Statistics");
		myList.add("Clustering");
		
		return myList;
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
		
		
		//StatisticsProcessService service = new StatisticsProcessService( data.getDataRows() , data.getFirstRow());		
		StatisticsProcessService service = new StatisticsProcessService( dataForInstances , featureNames);		
		
		
		ArrayList<String> resultsTags = service.getFeaturesNames();
		ArrayList<ArrayList<String>> resultsToSave = service.getResult();
		
		
		
		ExperimentResultsFileDataStructure response = new ExperimentResultsFileDataStructure();
		response.setFileName(data.getFileName());

		
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
	

	
	



}
