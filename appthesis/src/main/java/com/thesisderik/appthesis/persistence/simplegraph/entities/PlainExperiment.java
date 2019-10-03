package com.thesisderik.appthesis.persistence.simplegraph.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name = "GRAPH_EXPERIMENTS" )
public class PlainExperiment {
	
	@Id
	private Long id;
	
	@Column(name = "TITLE",  nullable = false, unique = true)
	private String title;
	
	@Column(name = "DESCRIPTION",  nullable = false)
	private String description;

	@ManyToMany
	private Set<PlainGroup> groups;
	
	@ManyToMany
	private Set<PlainFeature> features;
	
	@ManyToOne
	private PlainTask task;
	
	@Column(name = "TASK_DESCRIPTION_COMMAND",  nullable = false)
	private String taskDescriptionCommand;
	
	@Column(name = "FEATURE_NAME_OVERRIDE",  nullable = false)
	private String featureNameOverride;

}
