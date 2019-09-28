package com.thesisderik.appthesis.persistence.identifiers.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
	name = "ERRORS"
)
public class ErrorFound {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME", unique = true, nullable = false, length = 100)
	private String name;

	@Column(name = "TYPE", unique = true, nullable = false, length = 100)
	private String type;

	

	public ErrorFound(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

	public ErrorFound() {
		super();
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ErrorFound [id=" + id + ", name=" + name + ", type=" + type + "]";
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
