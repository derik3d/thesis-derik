package com.thesisderik.appthesis.processservices;

import java.util.ArrayList;
import java.util.Arrays;

import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;

public abstract class BaseProcessService implements IProcessService{



	public static ExperimentResultsFileDataStructure fileToStructure(ArrayList<String> file) {
		


		ExperimentResultsFileDataStructure erde = new ExperimentResultsFileDataStructure();
		
		
		erde.setFileName(file.get(0));
		
		boolean firstRow=true;
		
		ArrayList<ArrayList<String>> tempCont = new ArrayList<>();
		
		for(String row : file.get(1).split("\n")) {
			
			if(firstRow) {
				if(row.length()>2) {
					erde.setFirstRow(new ArrayList<>(Arrays.asList( row.split(",")) ));
					firstRow=false;
				}
			}else{
			
	
				if(row.length()>2) {
					tempCont.add(new ArrayList<>(Arrays.asList( row.split(",")) ));
				}
			
			}
			
		}
		
		erde.setDataRows(tempCont);
		
		return erde;
		
	}
	
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
	
	public static double mapAroundOne(double media, double minor, double mayor, double value) {
		
		//lower
		if(value<mayor) {
			return map(minor, media, value);
		}else {
			return 1+map(media, mayor, value);	
		}
	}
	
	
	public static double map(double floor, double top, double value) {
		
		return (value-floor)/(top-floor);
		
	}
	
}
