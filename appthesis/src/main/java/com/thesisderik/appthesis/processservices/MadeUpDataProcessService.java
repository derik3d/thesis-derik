package com.thesisderik.appthesis.processservices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MadeUpDataProcessService extends BaseProcessService{
	

	public String getServiceName(){
		return  "Statistics";
	}
	
	
	public ResultFormat setData(String args, ArrayList<ArrayList<String>> dataForEveryInstance,ArrayList<String> featureNames, String dataFileName) {
		

		ArrayList<String> resultsTags = new ArrayList<>(  );
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		ArrayList<Integer> ignoreList  = new ArrayList<>();
		
		for(ArrayList<String> dataOneInstance : dataForEveryInstance) {
			
			//fur now just need names and must be the only property
			String nodeName = dataOneInstance.get(0);
			
			
		}
		
		return new ResultFormat(result, resultsTags, ignoreList);
	}
	
	
}
