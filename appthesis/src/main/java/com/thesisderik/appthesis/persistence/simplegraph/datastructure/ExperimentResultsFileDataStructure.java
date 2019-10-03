package com.thesisderik.appthesis.persistence.simplegraph.datastructure;

import java.util.ArrayList;

public class ExperimentResultsFileDataStructure {
	
	//new feature name + number according to the dimension
	//new group name for all the nodes involved
	String fileName;
	
	//rows
	//cols =>>>> name / data1(key:value) / data2(key:value)
	ArrayList<ArrayList<String>> nodes;
	

}
