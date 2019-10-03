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
	    @UniqueConstraint(columnNames = {"FK_NODE","FK_FEATURE"})
	}, name = "GRAPH_REL_NODE_FEATURE") 
public class NodeFeatureRelation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @JoinColumn(name = "FK_NODE", nullable = false)
    @ManyToOne
	private PlainNode node;

    @JoinColumn(name = "FK_FEATURE", nullable = false)
    @ManyToOne
	private PlainFeature feature;
	
	@Column(name = "VALUE",  nullable = false, length = 200)
	private String value;

}
