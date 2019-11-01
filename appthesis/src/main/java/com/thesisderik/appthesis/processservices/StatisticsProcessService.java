package com.thesisderik.appthesis.processservices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StatisticsProcessService extends BaseProcessService{
	
	public static final String serviceName = "Statistics";
	
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
		
		

		
		
		int instancesFeaturesLength = dataForEveryInstance.get(0).size();
		
		
		ArrayList<ArrayList<String>> dataForEveryFeature = transposeMatrix(dataForEveryInstance);
		
		
		int featuresSamplesLength = dataForEveryFeature.get(0).size();
		
		BinaryOperator<String> strMayor = (a , b) -> { if(Double.parseDouble(a) > Double.parseDouble(b))  return a;else return b;};
		BinaryOperator<String> strMenor = (a , b) -> { if(Double.parseDouble(a) < Double.parseDouble(b))  return a;else return b;};
		BinaryOperator<String> plus = (a , b) -> { return "" + (Double.parseDouble(a) + Double.parseDouble(b)) ;};
		
		double mayor =	Double.parseDouble(dataForEveryFeature.get(0).stream().reduce("0", strMayor));
		double menor =	Double.parseDouble(dataForEveryFeature.get(0).stream().reduce("100000000", strMenor));
		
		
		
		double accumulatedFirst = Double.parseDouble(dataForEveryFeature.get(0).stream().reduce("0", plus));
		double accumulatedSecond = Double.parseDouble(dataForEveryFeature.get(1).stream().reduce("0", plus));
		
		double mediaFirst = accumulatedFirst / featuresSamplesLength;
		double mediaSecond = accumulatedSecond / featuresSamplesLength;
		
		
		for(int i = 0; i < resultsTags.size(); i++) {
			result.add(new ArrayList<String>());
		}
		

		//////////CAR0//////////
		
		Function<Double,String> mapLow = (a) -> {
			
			if(a==menor)
				return "1";
			
			return "0";
			} ;
		
		result.get(0).addAll(
					dataForEveryFeature.get(0).stream().map(Double::parseDouble).map(mapLow).collect(Collectors.toList())
				);
		
		
		//////////CAR1//////////
		
		Function<Double,String> mapHigh = (a) -> {
			
			if(a==mayor)
				return "1";
			
			return "0";
			} ;
		
		result.get(1).addAll(
					dataForEveryFeature.get(0).stream().map(Double::parseDouble).map(mapHigh).collect(Collectors.toList())
				);
		
		
		//////////CAR2//////////
		
		Function<Double,String> mapNormalize = (a) -> {
			
			
			a  = mapAroundOne(mediaFirst, menor, mayor , a);
			
			
			return "" + a;
			} ;
		
		result.get(2).addAll(
					dataForEveryFeature.get(0).stream().map(Double::parseDouble).map(mapNormalize).collect(Collectors.toList())
				);
		
		result = transposeMatrix(result);
		
		
	}
	
	
	
	
}
