package com.thesisderik.appthesis.processservices;

import java.util.ArrayList;

public interface IProcessService {
	
	public String getServiceName();
	public ArrayList<String> getFeaturesNames();
	public ArrayList<ArrayList<String>> getResult();
	
}
