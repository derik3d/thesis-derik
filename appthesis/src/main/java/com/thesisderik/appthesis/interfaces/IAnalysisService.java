package com.thesisderik.appthesis.interfaces;

import java.util.ArrayList;

import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentRequestFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;

public interface IAnalysisService {
	
	public ArrayList<String> getServices();

	public void processData(ExperimentRequestFileDataStructure data);
	
	//name first string, content second string
	public boolean integrateFeaturesFile(ArrayList<String> file);
	
	public boolean integrateFeaturesFile(String filename, String csvUrl);

	public void setExperimentDataIntegrator(IExperimentDataIntegrator results);
	
}
