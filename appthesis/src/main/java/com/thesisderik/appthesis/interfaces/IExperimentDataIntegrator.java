package com.thesisderik.appthesis.interfaces;

import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;

public interface IExperimentDataIntegrator {

	public void integrateExperimentResult(ExperimentResultsFileDataStructure expRes);
	
}
