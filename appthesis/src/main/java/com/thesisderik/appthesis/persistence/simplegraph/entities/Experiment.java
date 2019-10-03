package com.thesisderik.appthesis.persistence.simplegraph.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name = "GRAPH_EXPERIMENTS" )
public class Experiment {
	
	@Id
	private String title;
	
	@Column(name = "DESCRIPTION",  nullable = false)
	private String description;

	@OneToMany
	private Set<PlainGroup> groups;
	
	@OneToMany
	private Set<PlainFeature> features;
	
	
	
	

}
