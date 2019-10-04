package com.thesisderik.appthesis.persistence.simplegraph.entities;

import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.web.bind.annotation.Mapping;

@Entity
@Table(
	name = "GRAPH_PLAIN_TASKS"
)
public class PlainTask implements Comparable<PlainTask> {
	
	@Override
	public String toString() {
		return "PlainTask [id=" + id + ", name=" + name + ", plainExperiments=" + plainExperiments + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME", unique=true, nullable = false, length = 200)
	private String name;
	

    @OneToMany(
        mappedBy = "task",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<PlainExperiment> plainExperiments = new TreeSet<>();

    
    public void addPlainExperiment(PlainExperiment plainExperiment) {
    	plainExperiments.add(plainExperiment);
    }
    
    public void removePlainExperiment(PlainExperiment plainExperiment) {
    	plainExperiments.remove(plainExperiment);
    }

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PlainTask)) {
			return false;
		}
		PlainTask other = (PlainTask) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
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

	@Override
	public int compareTo(PlainTask o) {

		return this.getName().compareTo(o.getName());
	}



}
