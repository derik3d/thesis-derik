package com.thesisderik.appthesis.interfaces;

import java.util.ArrayList;

import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentRequestFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;

public interface IAnalysisService {
	
	public ArrayList<String> getServices();

	public void processData(ExperimentRequestFileDataStructure data);
	
	public boolean integrateFeaturesFile(ArrayList<String> file);

	public void setExperimentDataIntegrator(IExperimentDataIntegrator results);
	
}
