package com.thesisderik.appthesis.idscrawler.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResponseData {

	String query;
	String result;
	
	public static ResponseData responseDataBuilder(ResponseData rs){
		ResponseData res = new ResponseData();;
		res.query = rs.query;
		res.result = rs.result;
		return res;
	}

	@Override
	public String toString() {
		return "ResponseData [query=" + query + ", result=" + result + "]";
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}
