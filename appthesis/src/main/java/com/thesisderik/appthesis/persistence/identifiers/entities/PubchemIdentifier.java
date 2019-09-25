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
	
}
