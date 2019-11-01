package com.thesisderik.appthesis.processservices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class MachineLearningProcessService extends BaseProcessService{
	
	@Autowired
	RestTemplate restTemplate;
	
	public static final String serviceName = "ML";
	
	public static ArrayList<String> resultsTags = new ArrayList<>( Arrays.asList("C_mayor_bool_first","C_menor_bool_first","V_media_first_mapped") );
	
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
	
	public void setData(String args, ArrayList<ArrayList<String>> dataForEveryInstance,ArrayList<String> featureNames, String dataFileName) {

		Thread t1 = new Thread() {

			public void start() {

	    		try {
		            String url = "http://localhost:5000/train/"+dataFileName;
		            System.out.println(restTemplate.getForObject(url, String.class));
		            
		            Thread t2 = new Thread() {
		    	    	
		    	    	public void start() {

		    	    		try {
		    		            String url = "http://localhost:5000/evaluate/"+dataFileName;
		    		            System.out.println(restTemplate.getForObject(url, String.class));
		    	            }catch(Exception ex) {
		    	            	
		    	            }
		    	    	}
		    	    	
		    	    };
		    	    
		    	    t2.start();
		            
	            }catch(Exception ex) {
	            	
	            }
	    	}
	    	
	    };
	    
	    t1.start();
		
		result = null;
		
	}
	
}
