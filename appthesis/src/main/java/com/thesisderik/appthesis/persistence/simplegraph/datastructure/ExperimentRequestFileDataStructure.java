package com.thesisderik.appthesis.persistence.simplegraph.datastructure;

import java.util.ArrayList;

public class ExperimentRequestFileDataStructure {
	
	
	@Override
	public String toString() {
		return "ExperimentRequestFileDataStructure [fileName=" + fileName + ", firstRow=" + firstRow + ", dataRows="
				+ dataRows + "]";
	}

	String fileName;
	
	//names  ->>> nodename / classname / data1name / data2name
	ArrayList<String> firstRow;
	
	//row
	//cols ->->->-> name / class / data / data2
	ArrayList<ArrayList<String>> dataRows;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ArrayList<String> getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(ArrayList<String> firstRow) {
		this.firstRow = firstRow;
	}

	public ArrayList<ArrayList<String>> getDataRows() {
		return dataRows;
	}

	public void setDataRows(ArrayList<ArrayList<String>> dataRows) {
		this.dataRows = dataRows;
	}
	
	

}
