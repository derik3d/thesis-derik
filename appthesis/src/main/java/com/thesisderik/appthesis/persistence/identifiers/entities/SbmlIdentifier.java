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
	
}