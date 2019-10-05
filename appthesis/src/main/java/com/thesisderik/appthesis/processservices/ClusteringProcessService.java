package com.thesisderik.appthesis.processservices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClusteringProcessService extends BaseProcessService{
	
	public static final String serviceName = "Clustering";
	
	public static ArrayList<String> resultsTags = new ArrayList<>( Arrays.asList("ClusterClass") );
	
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
			
			res.add(getSampleCluster(dataForEveryInstance.get(i)));
			
			result.add(res);
		}
		
		
	}
	
	
	String getSampleCluster(ArrayList<String> point) {
		
		return "test_cluster_4point_"+ point.stream().reduce("", (a,b)->{return a+" "+b;} );
		
	}
	
}
