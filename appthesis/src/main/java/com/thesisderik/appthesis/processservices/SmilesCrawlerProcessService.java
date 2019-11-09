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

	public String getServiceName(){
		return  "SmilesCrawler";
	}

	
	public ResultFormat setData(String args, ArrayList<ArrayList<String>> dataForEveryInstance,ArrayList<String> featureNames, String dataFileName) {

		ArrayList<String> resultsTags = new ArrayList<>( Arrays.asList("SMILES") );
		ArrayList<Integer> ignoreList  = new ArrayList<>();
		ArrayList<ArrayList<String>> result  = new ArrayList<>();

		
		for(int i =0; i<dataForEveryInstance.size();i++) {
			ArrayList<String> res = new ArrayList<>();

			res.add(getSMILES(ignoreList, i, dataForEveryInstance.get(i).get(0)));
			
			result.add(res);
		}
		
		return new ResultFormat(result, resultsTags, ignoreList);

	}
	
	
	String getSMILES(ArrayList<Integer> ignoreList, int instanceIndex, String name){
		
		
		ResponseData res = iNamesCrawlerService.fromPubchemIdGetSmiles(name);		
				
		if(res==null) {
			ignoreList.add(instanceIndex);
			
			
			return "BAD_NO_DATA";
		}
		

		
		return res.getResult();
	}


	
}
