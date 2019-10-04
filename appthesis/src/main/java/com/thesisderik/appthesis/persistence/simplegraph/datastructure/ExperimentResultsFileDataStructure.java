package com.thesisderik.appthesis.persistence.simplegraph.datastructure;

import java.util.ArrayList;

public class ExperimentResultsFileDataStructure {
	
	@Override
	public String toString() {
		return "ExperimentResultsFileDataStructure [fileName=" + fileName + ", firstRow=" + firstRow + ", dataRows="
				+ dataRows + "]";
	}

	//new feature name + number according to the dimension
	//new group name for all the nodes involved
	String fileName;
	
	//titlesrow ->>>>>> nodenames, propertyname, propertyname
	ArrayList<String> firstRow;

	//rows
	//cols =>>>> name / data1 / data2
	ArrayList<ArrayList<String>> dataRows;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public ArrayList<ArrayList<String>> getDataRows() {
		return dataRows;
	}

	public void setDataRows(ArrayList<ArrayList<String>> dataRows) {
		this.dataRows = dataRows;
	}

	public ArrayList<String> getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(ArrayList<String> firstRow) {
		this.firstRow = firstRow;
	}
	
	
	
	
}
