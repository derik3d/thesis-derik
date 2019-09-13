package com.thesisderik.appthesis.idscrawler.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseSMILESPubchem extends ResponseData {
	
	@JsonProperty("PropertyTable")
	private SMILESPropertyTable propertyTable;
	
	public SMILESPropertyTable getPropertyTable() {
		return propertyTable;
	}

	public void setPropertyTable(SMILESPropertyTable propertyTable) {
		this.propertyTable = propertyTable;
		this.query = (propertyTable.getProperties().stream().findFirst().get().getCid());
		this.result = (propertyTable.getProperties().stream().findFirst().get().getCanonicalSmiles());
	}

	public static class SMILESPropertyTable{
		
		@JsonProperty("Properties")
		private List<Property> properties;

		public List<Property> getProperties() {
			return properties;
		}

		public void setProperties(List<Property> properties) {
			this.properties = properties;
		}
		
	}
	
	public static class Property {
		
		@JsonProperty("CID")
		private String cid;
		@JsonProperty("CanonicalSMILES")
		private String canonicalSmiles;
		public String getCid() {
			return cid;
		}
		public void setCid(String cid) {
			this.cid = cid;
		}
		public String getCanonicalSmiles() {
			return canonicalSmiles;
		}
		public void setCanonicalSmiles(String canonicalSmiles) {
			this.canonicalSmiles = canonicalSmiles;
		}
		
		
		
	}

}
