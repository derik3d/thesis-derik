package com.thesisderik.appthesis.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.thesisderik.appthesis.interfaces.IAnalisysService;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentRequestFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;

@Service
public class AnalysisService implements IAnalisysService {



	
	public static ArrayList<ArrayList<String>>transposeMatrix(ArrayList<ArrayList<String>> results){
		
		ArrayList<ArrayList<String>> resp = new ArrayList<>();
		
		for(int i = 0; i<results.get(0).size(); i++) {
			resp.add(new ArrayList<String>());
		}
		

		for(int i = 0; i<results.get(0).size(); i++) {
			for(int j = 0; j < results.size(); j++) {
				resp.get(i).add(results.get(j).get(i));
			}
		}
		
		return resp;
	}
	
	
	
	@Override
	public ExperimentResultsFileDataStructure processData(ExperimentRequestFileDataStructure data) {
		
		
		//preparing data for analysis
		ArrayList<ArrayList<String>> dataForInstances = data.deleteFirstCols(2);
		ArrayList<String> featureNames = new ArrayList<String>( data.getFirstRow().subList(2, data.getFirstRow().size()));
		
		
		StatisticsService service = new StatisticsService( dataForInstances , featureNames);		
		
		
		ArrayList<String> resultsTags = StatisticsService.resultsTags;
		ArrayList<ArrayList<String>> resultsToSave = service.result;
		
		
		
		ExperimentResultsFileDataStructure response = new ExperimentResultsFileDataStructure();
		response.setFileName(data.getFileName());

		
		response.setFirstRow(new ArrayList<>());
		response.getFirstRow().add("names");
		response.getFirstRow().addAll(resultsTags);
		
		response.setDataRows(new ArrayList<>());
		
		for(int i = 0; i< data.getDataRows().size(); i++) {
			
			ArrayList<String> myResultRow = new ArrayList<>();
			myResultRow.add(data.getDataOfRow(i).get(0));
			myResultRow.addAll(resultsToSave.get(i));
			
			response.getDataRows().add(myResultRow);
			
		}
		
		return response;
	}
	

	public static class StatisticsService{
		
		public static ArrayList<String> resultsTags = new ArrayList<>( Arrays.asList("C_mayor_bool_first","C_menor_bool_first","V_media_first_mapped") );
		
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		
		
		public StatisticsService(ArrayList<ArrayList<String>> dataForEveryInstance,ArrayList<String> featureNames) {
			
			
			int instancesFeaturesLength = dataForEveryInstance.get(0).size();
			
			
			ArrayList<ArrayList<String>> dataForEveryFeature = AnalysisService.transposeMatrix(dataForEveryInstance);
			
			
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
		
		
		public double mapAroundOne(double media, double minor, double mayor, double value) {
			
			//lower
			if(value<mayor) {
				return map(minor, media, value);
			}else {
				return 1+map(media, mayor, value);	
			}
		}
		
		
		public double map(double floor, double top, double value) {
			
			return (value-floor)/(top-floor);
			
		}
		
	}
	
	
	
	

}
