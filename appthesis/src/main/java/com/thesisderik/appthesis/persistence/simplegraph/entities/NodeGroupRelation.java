package com.thesisderik.appthesis.persistence.simplegraph.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class NodeGroupRelation implements Comparable<NodeGroupRelation>{
	
	@Override
	public String toString() {
		return "NodeGroupRelation [id=" + id + ", group=" + group + ", node=" + node + "]";
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @JoinColumn(name = "FK_GROUP", nullable = false)
    @ManyToOne( fetch = FetchType.EAGER )
	private PlainGroup group;
	
    @JoinColumn(name = "FK_NODE", nullable = false)
    @ManyToOne
	private PlainNode node;
    
    
    
    

	@Override
	public int hashCode() {
		return Objects.hash(group, id, node);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof NodeGroupRelation)) {
			return false;
		}
		NodeGroupRelation other = (NodeGroupRelation) obj;
		return Objects.equals(group, other.group) && Objects.equals(id, other.id) && Objects.equals(node, other.node);
	}

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


	@Override
	public int compareTo(NodeGroupRelation o) {

		return this.getId().compareTo(o.getId());
	}

	

}
