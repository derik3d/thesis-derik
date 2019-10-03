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
	private Set<PlainGroup> plainGroups;
	
	@ManyToMany
	private Set<PlainFeature> plainFeatures;
	
	@ManyToOne
	private PlainTask task;
	
	@Column(name = "TASK_DESCRIPTION_COMMAND",  nullable = false)
	private String taskDescriptionCommand;
	
	@Column(name = "FEATURE_NAME_OVERRIDE",  nullable = false)
	private String featureNameOverride;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<PlainGroup> getPlainGroups() {
		return plainGroups;
	}

	public void setPlainGroups(Set<PlainGroup> plainGroups) {
		this.plainGroups = plainGroups;
	}

	public Set<PlainFeature> getPlainFeatures() {
		return plainFeatures;
	}

	public void setPlainFeatures(Set<PlainFeature> plainFeatures) {
		this.plainFeatures = plainFeatures;
	}

	public PlainTask getTask() {
		return task;
	}

	public void setTask(PlainTask task) {
		this.task = task;
	}

	public String getTaskDescriptionCommand() {
		return taskDescriptionCommand;
	}

	public void setTaskDescriptionCommand(String taskDescriptionCommand) {
		this.taskDescriptionCommand = taskDescriptionCommand;
	}

	public String getFeatureNameOverride() {
		return featureNameOverride;
	}

	public void setFeatureNameOverride(String featureNameOverride) {
		this.featureNameOverride = featureNameOverride;
	}

}
