package com.thesisderik.appthesis.viz;

import java.util.Set;

import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainGroup;

public class QueryVizFormat {
	
	//groups to show
	Set<PlainGroup> groups;

	public Set<PlainGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<PlainGroup> groups) {
		this.groups = groups;
	}
	
	

}
