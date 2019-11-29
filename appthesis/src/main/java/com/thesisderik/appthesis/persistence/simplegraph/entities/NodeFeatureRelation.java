package com.thesisderik.appthesis.persistence.simplegraph.entities;

import java.util.Objects;

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
public class NodeFeatureRelation implements Comparable<NodeFeatureRelation>{
	
	@Override
	public String toString() {
		return "NodeFeatureRelation [id=" + id + ", node=" + node + ", feature=" + feature + ", value=" + value + "]";
	}




	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @JoinColumn(name = "FK_NODE", nullable = false)
    @ManyToOne
	private PlainNode node;

    @JoinColumn(name = "FK_FEATURE", nullable = false)
    @ManyToOne
	private PlainFeature feature;
	
	@Column(name = "VALUE",  nullable = false)
	private String value;
	
	
	
	
	

	@Override
	public int hashCode() {
		return Objects.hash(feature, node);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof NodeFeatureRelation)) {
			return false;
		}
		NodeFeatureRelation other = (NodeFeatureRelation) obj;
		return Objects.equals(feature, other.feature) && Objects.equals(node, other.node);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlainNode getNode() {
		return node;
	}

	public void setNode(PlainNode node) {
		this.node = node;
	}

	public PlainFeature getFeature() {
		return feature;
	}

	public void setFeature(PlainFeature feature) {
		this.feature = feature;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	


	@Override
	public int compareTo(NodeFeatureRelation o) {

		return this.getId().compareTo(o.getId());
	}

}
