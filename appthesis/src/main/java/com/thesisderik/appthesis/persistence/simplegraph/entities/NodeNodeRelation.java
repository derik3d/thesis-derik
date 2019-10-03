package com.thesisderik.appthesis.persistence.simplegraph.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={
	    @UniqueConstraint(columnNames = {"FK_RELATION","FK_NODE_A","FK_NODE_B"})
	}, name = "GRAPH_NODE_NODE_RELATION_RELATION") 
public class NodeNodeRelation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @JoinColumn(name = "FK_RELATION", nullable = false)
	private PlainRelation relation;
	
    @JoinColumn(name = "FK_NODE_A", nullable = false)
	private PlainNode nodeA;
	
    @JoinColumn(name = "FK_NODE_B", nullable = false)
	private PlainNode nodeB;

}
