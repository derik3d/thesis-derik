package com.thesisderik.appthesis.processservices;

import java.util.ArrayList;

public interface IProcessService {
	
	public String getServiceName();
	public void setData(String args, ArrayList<ArrayList<String>> dataForEveryInstance,ArrayList<String> featureNames, String dataFileName);
	public ArrayList<String> getFeaturesNames();
	public ArrayList<ArrayList<String>> getResult();
	
	
}
