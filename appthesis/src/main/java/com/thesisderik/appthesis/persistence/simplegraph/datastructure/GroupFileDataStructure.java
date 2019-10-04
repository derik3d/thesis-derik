package com.thesisderik.appthesis.persistence.simplegraph.datastructure;

import java.util.ArrayList;

public class GroupFileDataStructure {
	
	@Override
	public String toString() {
		return "GroupFileDataStructure [fileName=" + fileName + ", nodes=" + nodes + "]";
	}
	//without extension
	//becomes group name
	String fileName;
	
	//string names of the nodes
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
