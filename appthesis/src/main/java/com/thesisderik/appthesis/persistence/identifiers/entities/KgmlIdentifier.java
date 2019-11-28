package com.thesisderik.appthesis.persistence.identifiers.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(
	name = "KEGG_IDENTIFIERS"
)
public class KgmlIdentifier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME", unique = true, nullable = false)
	private String name;
	
	@ManyToOne(optional = false, cascade = {CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private PubchemIdentifier target;

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
	public String toString() {
		return "KgmlIdentifier [id=" + id + ", name=" + name + ", target=" + target + "]";
	}

	public PubchemIdentifier getTarget() {
		return target;
	}

	public void setTarget(PubchemIdentifier target) {
		this.target = target;
	}

	public KgmlIdentifier( String name, PubchemIdentifier target) {
		super();
		this.id = id;
		this.name = name;
		this.target = target;
	}

	public KgmlIdentifier() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}