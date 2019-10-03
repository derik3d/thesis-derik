package com.thesisderik.appthesis.persistence.simplegraph.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(
	name = "GRAPH_PLAIN_FEATURES"
)
public class PlainFeature {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME",  nullable = false, length = 200)
	private String name;
	
	
	
	@OneToMany(mappedBy="plainFeatures")
	private Set<PlainExperiment> plainExperiments;



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Set<PlainExperiment> getPlainExperiments() {
		return plainExperiments;
	}



	public void setPlainExperiments(Set<PlainExperiment> plainExperiments) {
		this.plainExperiments = plainExperiments;
	}

}
