package com.thesisderik.appthesis.persistence.identifiers.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
	name = "PUBCHEM_IDENTIFIERS"
)
public class PubchemIdentifier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME", unique = true, nullable = false, length = 100)
	private String name;

	public PubchemIdentifier( String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public PubchemIdentifier() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "PubchemIdentifier [id=" + id + ", name=" + name + "]";
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
	
	
}
