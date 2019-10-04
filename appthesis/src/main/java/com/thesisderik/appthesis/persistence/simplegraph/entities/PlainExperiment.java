package com.thesisderik.appthesis.persistence.simplegraph.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name = "GRAPH_EXPERIMENTS" )
public class PlainExperiment implements Comparable<PlainExperiment>{
	
	@Override
	public String toString() {
		return "PlainExperiment [id=" + id + ", title=" + title + ", description=" + description + ", plainGroups="
				+ plainGroups + ", plainFeatures=" + plainFeatures + ", task=" + task + ", taskDescriptionCommand="
				+ taskDescriptionCommand + ", featureNameOverride=" + featureNameOverride + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "TITLE",  nullable = false, unique = true)
	private String title;
	
	@Column(name = "DESCRIPTION",  nullable = false)
	private String description;

	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<PlainGroup> plainGroups = new HashSet<>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<PlainFeature> plainFeatures = new HashSet<>();
	
	@JoinColumn(name = "TASK_ID")
	@ManyToOne(fetch=FetchType.EAGER)
	private PlainTask task;
	
	@Column(name = "TASK_DESCRIPTION_COMMAND",  nullable = false)
	private String taskDescriptionCommand;
	
	@Column(name = "FEATURE_NAME_OVERRIDE",  nullable = false)
	private String featureNameOverride;

	
	public void addPlainGroup(PlainGroup plainGroup){
		plainGroups.add(plainGroup);
		plainGroup.getPlainExperiments().add(this);
	}
	
	public void removePlainGroup(PlainGroup plainGroup){
		plainGroups.remove(plainGroup);
		plainGroup.getPlainExperiments().remove(this);
	}
	
	
	public void addPlainFeature(PlainFeature plainFeature){
		plainFeatures.add(plainFeature);
		plainFeature.getPlainExperiments().add(this);
	}
	
	public void removePlainFeature(PlainFeature plainFeature){
		plainFeatures.remove(plainFeature);
		plainFeature.getPlainExperiments().remove(this);
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(featureNameOverride, id, taskDescriptionCommand, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PlainExperiment)) {
			return false;
		}
		PlainExperiment other = (PlainExperiment) obj;
		return Objects.equals(featureNameOverride, other.featureNameOverride) && Objects.equals(id, other.id)
				&& Objects.equals(taskDescriptionCommand, other.taskDescriptionCommand)
				&& Objects.equals(title, other.title);
	}

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

	@Override
	public int compareTo(PlainExperiment o) {

		return this.getTitle().compareTo(o.getTitle());
	}

}
