package com.thesisderik.appthesis.persistence.simplegraph.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "GRAPH_EXPERIMENTS" )
public class Experiment {
	
	@Id
	private String title;
	
	@Column(name = "DESCRIPTION",  nullable = false)
	private String description;
	
	
	

}
