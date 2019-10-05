package com.thesisderik.appthesis.processservices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SmilesCrawlerProcessService extends BaseProcessService{
	
	public static final String serviceName = "SmilesCrawler";
	
	public static ArrayList<String> resultsTags = new ArrayList<>( Arrays.asList("SMILES") );
	
	ArrayList<ArrayList<String>> result = new ArrayList<>();

	

	public String getServiceName(){
		return serviceName;
	}
	public ArrayList<String> getFeaturesNames(){
		return resultsTags;
	}
	public ArrayList<ArrayList<String>> getResult() {
		return result;
	}
	
	
	
	public void setData(String args, ArrayList<ArrayList<String>> dataForEveryInstance,ArrayList<String> featureNames) {
		
		
		for(int i =0; i<dataForEveryInstance.size();i++) {
			ArrayList<String> res = new ArrayList<>();
			
			res.add(getSMILES(dataForEveryInstance.get(i).get(0)));
			
			result.add(res);
		}
		
		
	}
	
	
	String getSMILES(String name){
		return "test_smiles_for"+name;
	}
	
	
}
