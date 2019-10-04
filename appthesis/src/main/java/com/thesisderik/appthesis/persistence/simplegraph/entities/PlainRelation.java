package com.thesisderik.appthesis.persistence.simplegraph.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
	name = "GRAPH_PLAIN_RELATIONS"
)
public class PlainRelation implements Comparable<PlainRelation>{
	
	@Override
	public String toString() {
		return "PlainRelation [id=" + id + ", name=" + name + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME", unique=true,  nullable = false, length = 200)
	private String name;
	
	

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PlainRelation)) {
			return false;
		}
		PlainRelation other = (PlainRelation) obj;
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
	public int compareTo(PlainRelation o) {
		return this.getName().compareTo(o.getName());
	}

}
