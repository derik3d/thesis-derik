package com.thesisderik.appthesis.persistence.simplegraph.datastructure;

import java.util.ArrayList;

public class GroupFileDataStructure {
	
	String fileName;
	ArrayList<String> nodes;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public ArrayList<String> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<String> nodes) {
		this.nodes = nodes;
	}
	
}
