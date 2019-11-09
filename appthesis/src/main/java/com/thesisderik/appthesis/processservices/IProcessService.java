package com.thesisderik.appthesis.processservices;

import java.util.ArrayList;

public interface IProcessService {
	
	public String getServiceName();
	public ResultFormat setData(String args, ArrayList<ArrayList<String>> dataForEveryInstance,
			ArrayList<String> featureNames, String dataFileName);
	
	static class ResultFormat{

		ArrayList<ArrayList<String>> resultData;
		ArrayList<String> featureNames;
		ArrayList<Integer> ignoreList;
		
		
		public ResultFormat(ArrayList<ArrayList<String>> resultData, ArrayList<String> featureNames,
				ArrayList<Integer> ignoreList) {
			super();
			this.resultData = resultData;
			this.featureNames = featureNames;
			this.ignoreList = ignoreList;
		}


		public ArrayList<ArrayList<String>> getResultData() {
			return resultData;
		}


		public ArrayList<String> getFeatureNames() {
			return featureNames;
		}


		public ArrayList<Integer> getIgnoreList() {
			return ignoreList;
		}


		@Override
		public String toString() {
			return "ResultFormat [resultData=" + resultData + ", featureNames=" + featureNames + ", ignoreList="
					+ ignoreList + "]";
		}
		
		
	}
	
}
