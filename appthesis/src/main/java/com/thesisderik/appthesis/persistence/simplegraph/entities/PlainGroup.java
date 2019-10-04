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
	name = "GRAPH_PLAIN_GROUPS"
)
public class PlainGroup implements Comparable<PlainGroup>{
	
	@Override
	public String toString() {
		return "PlainGroup [id=" + id + ", name=" + name + ", plainExperiments=" + plainExperiments + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME", unique=true, nullable = false, length = 200)
	private String name;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "plainGroups", cascade = CascadeType.ALL)	
	private Set<PlainExperiment> plainExperiments = new HashSet<>();
	
	


    public void addPlainExperiment(PlainExperiment plainExperiment) {
    	plainExperiments.add(plainExperiment);
    	plainExperiment.addPlainGroup(this);
    }
    
    public void removePlainExperiment(PlainExperiment plainExperiment) {
    	plainExperiments.remove(plainExperiment);
    	plainExperiment.removePlainGroup(this);
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
		if (!(obj instanceof PlainGroup)) {
			return false;
		}
		PlainGroup other = (PlainGroup) obj;
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
	public int compareTo(PlainGroup o) {

		return this.getName().compareTo(o.getName());
	}

}
