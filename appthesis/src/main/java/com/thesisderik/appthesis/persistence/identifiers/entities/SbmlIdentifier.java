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
	name = "SBML_IDENTIFIERS"
)
public class SbmlIdentifier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME", unique = true, nullable = false, length = 100)
	private String name;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private KgmlIdentifier target;

	@Override
	public String toString() {
		return "SbmlIdentifier [id=" + id + ", name=" + name + ", target=" + target + "]";
	}

	public SbmlIdentifier(Long id, String name, KgmlIdentifier target) {
		super();
		this.id = id;
		this.name = name;
		this.target = target;
	}

	public SbmlIdentifier() {
		super();
		// TODO Auto-generated constructor stub
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

	public KgmlIdentifier getTarget() {
		return target;
	}

	public void setTarget(KgmlIdentifier target) {
		this.target = target;
	}
	
	
	
}