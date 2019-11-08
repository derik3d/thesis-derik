package com.thesisderik.appthesis.processservices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesisderik.appthesis.idscrawler.entities.ResponseData;
import com.thesisderik.appthesis.interfaces.INamesCrawlerService;

@Service
public class SmilesCrawlerProcessService extends BaseProcessService{
	
	@Autowired
	private INamesCrawlerService iNamesCrawlerService;
	
	public static final String serviceName = "SmilesCrawler";
	
	public static final ArrayList<String> resultsTags = new ArrayList<>( Arrays.asList("SMILES") );
	
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
		
		
		for(int i =0; i<dataForEveryInstance.size();i++) {
			ArrayList<String> res = new ArrayList<>();
			
			res.add(getSMILES(dataForEveryInstance.get(i).get(0)));
			
			result.add(res);
		}
		
		
	}
	
	
	String getSMILES(String name){
		
		
		ResponseData res = iNamesCrawlerService.fromPubchemIdGetSmiles(name);		
		
		System.out.println("smiles processed "+name);
		
		if(res==null)
			return "BAD_NO_DATA";
		
		return res.getResult();
	}
	
	
}
