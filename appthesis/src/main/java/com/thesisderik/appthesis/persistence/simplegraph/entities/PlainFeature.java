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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(
	name = "GRAPH_PLAIN_FEATURES"
)
public class PlainFeature implements Comparable<PlainFeature> {
	
	@Override
	public String toString() {
		return "PlainFeature [id=" + id + ", name=" + name + ", plainExperiments=" + plainExperiments + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME",  unique=true, nullable = false, length = 200)
	private String name;
	
	
	@ManyToMany(mappedBy = "plainFeatures", fetch=FetchType.EAGER)
	private Set<PlainExperiment> plainExperiments  = new HashSet<>();



    public void addPlainExperiment(PlainExperiment plainExperiment) {
    	plainExperiments.add(plainExperiment);
    	plainExperiment.addPlainFeature(this);
    }
    
    public void removePlainExperiment(PlainExperiment plainExperiment) {
    	plainExperiments.remove(plainExperiment);
    	plainExperiment.removePlainFeature(this);
    }

	
	

	@Override
	public int hashCode() {
		return Objects.hash(id, name, plainExperiments);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PlainFeature)) {
			return false;
		}
		PlainFeature other = (PlainFeature) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(plainExperiments, other.plainExperiments);
	}


	
	

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

	@Override
	public int compareTo(PlainFeature o) {

		return this.getName().compareTo(o.getName());
	}
	
	

}
