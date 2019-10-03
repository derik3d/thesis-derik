package com.thesisderik.appthesis.persistence.simplegraph.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={
	    @UniqueConstraint(columnNames = {"FK_GROUP","FK_NODE"})
	}, name = "GRAPH_REL_NODE_GROUP")
public class NodeGroupRelation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @JoinColumn(name = "FK_GROUP", nullable = false)
    @ManyToOne
	private PlainGroup group;
	
    @JoinColumn(name = "FK_NODE", nullable = false)
    @ManyToOne
	private PlainNode node;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlainGroup getGroup() {
		return group;
	}

	public void setGroup(PlainGroup group) {
		this.group = group;
	}

	public PlainNode getNode() {
		return node;
	}

	public void setNode(PlainNode node) {
		this.node = node;
	}

}
