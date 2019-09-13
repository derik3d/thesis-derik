package com.thesisderik.appthesis.idscrawler.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseBigg extends ResponseData {
	
	private String bigg_id;
	private DatabaseLinks database_links;
	
	public String getBigg_id() {
		return bigg_id;
	}

	public void setBigg_id(String bigg_id) {
		this.bigg_id = bigg_id;
		this.query = bigg_id;
	}

	public DatabaseLinks getDatabase_links() {
		return database_links;
	}

	public void setDatabase_links(DatabaseLinks database_links) {
		this.database_links = database_links;
		this.result = database_links.getKEGGCompound().stream().findFirst().get().getId();
	}

	public static class DatabaseLinks{
		
		@JsonProperty("KEGG Compound")
		private List<KEGGCompound> KEGGCompound;

		public List<KEGGCompound> getKEGGCompound() {
			return KEGGCompound;
		}

		public void setKEGGCompound(List<KEGGCompound> kEGGCompound) {
			KEGGCompound = kEGGCompound;
		}
		
	}
	
	public static class KEGGCompound{
	
		private String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	
	}

}
