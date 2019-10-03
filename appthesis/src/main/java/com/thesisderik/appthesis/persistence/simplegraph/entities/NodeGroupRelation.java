package com.thesisderik.appthesis.persistence.simplegraph.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={
	    @UniqueConstraint(columnNames = {"FK_GROUP","FK_NODE"})
	}, name = "GRAPH_NODE_GROUP_RELATION")
public class NodeGroupRelation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @JoinColumn(name = "FK_GROUP", nullable = false)
	private PlainGroup groupName;
	
    @JoinColumn(name = "FK_NODE", nullable = false)
	private PlainNode node;

}
