package com.thesisderik.appthesis.persistence.simplegraph.datastructure;

import java.util.ArrayList;

public class ExperimentRequestFileDataStructure {
	
	
	String fileName;
	
	//names  ->>> name / class / data1 / data2
	ArrayList<String> firstRow;
	
	//row
	//cols ->->->-> name / class / data / data2
	ArrayList<ArrayList<String>> dataRows;

}
