package com.thesisderik.appthesis.persistence.simplegraph.entities;

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
public class PlainRelation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME",  nullable = false, length = 200)
	private String NAME;

}
