package com.thesisderik.appthesis.interfaces;

import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentRequestFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;

public interface IAnalisysService {

	ExperimentResultsFileDataStructure processData(ExperimentRequestFileDataStructure data);

}
