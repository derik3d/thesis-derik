package com.thesisderik.appthesis.persistence.graph.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class Graph {

	
	
	//unique id
	Integer id;
	
	//graph name
	String name;
	
	//graph type
	GType gtype;
	
	//nodes that make up this graph
	List<GraphNode> nodes = new ArrayList();
	
	//relations between the nodes, from to, in the case is a directed graph
	Set<GraphNodeRelation> nodeRelations = new HashSet();
	
	public enum GType {
		//kgml = from KEGG
		//sbml = from BIGG
		KGML, SBML
	}
	
	public Graph() {
		super();
	}
	
	public Graph(String name, GType gtype, List<GraphNode> nodes, Set<GraphNodeRelation> nodeRelations) {
		super();
		this.name = name;
		this.gtype = gtype;
		this.nodes = nodes;
		this.nodeRelations = nodeRelations;
	}
	
	public int hashCode() {
	     return new HashCodeBuilder(27, 37).
	       append(name).
	       append(gtype).
	       append(nodes).
	       append(nodeRelations).
	       toHashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		   if (obj == null) { return false; }
		   if (obj == this) { return true; }
		   if (obj.getClass() != getClass()) {
		     return false;
		   }
		   Graph rhs = (Graph) obj;
		return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(name, rhs.name)
                .append(gtype, rhs.gtype)
                .append(nodes, rhs.nodes)
                .append(nodeRelations, rhs.nodeRelations)
                .isEquals();
	}
	
	
	@Override
	public String toString() {
		return "Graph [id=" + id + ", name=" + name + ", gtype=" + gtype + ", nodes=" + nodes + ", nodeRelations="
				+ nodeRelations + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GType getGtype() {
		return gtype;
	}

	public void setGtype(GType gtype) {
		this.gtype = gtype;
	}

	public List<GraphNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<GraphNode> nodes) {
		this.nodes = nodes;
	}

	public Set<GraphNodeRelation> getNodeRelations() {
		return nodeRelations;
	}

	public void setNodeRelations(Set<GraphNodeRelation> nodeRelations) {
		this.nodeRelations = nodeRelations;
	}
}
