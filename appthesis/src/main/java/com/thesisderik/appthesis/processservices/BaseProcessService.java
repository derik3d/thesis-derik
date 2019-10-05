package com.thesisderik.appthesis.processservices;

import java.util.ArrayList;

public abstract class BaseProcessService implements IProcessService{


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
