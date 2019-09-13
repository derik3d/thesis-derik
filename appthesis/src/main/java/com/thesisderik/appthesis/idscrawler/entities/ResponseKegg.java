package com.thesisderik.appthesis.idscrawler.entities;

public class ResponseKegg extends ResponseData{

	String cpd;
	String pubchem;
	
	public String getCpd() {
		return cpd;
	}
	public void setCpd(String cpd) {
		this.cpd = cpd;
		this.query = cpd;
	}
	public String getPubchem() {
		return pubchem;
	}
	public void setPubchem(String pubchem) {
		this.pubchem = pubchem;
		this.result = pubchem;
	}

}
