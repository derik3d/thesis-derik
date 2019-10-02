package com.thesisderik.appthesis.persistence.graph.entities;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.thesisderik.appthesis.persistence.graph.entities.GraphNode.NType;

public class GraphNodeRelation {

	//reaction node
	GraphNode source;
	//substrate or product node target
	GraphNode target;
	//reaction group
	RGroup rGroup;
	
	public enum RGroup {
		REACTANT, PRODUCT
	}
	
	public GraphNodeRelation() {
		super();
	}
	
	@Override
	public int hashCode() {
     return new HashCodeBuilder(71,33).
       append(source).
       append(target).
       toHashCode();
     }
	
	@Override
	public boolean equals(Object obj){
		   if (obj == null) { return false; }
		   if (obj == this) { return true; }
		   if (obj.getClass() != getClass()) {
		     return false;
		   }
		   GraphNodeRelation rhs = (GraphNodeRelation) obj;
		return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(source, rhs.source)
                .append(target, rhs.target)
                .append(rGroup, rhs.rGroup)
                .isEquals();
	}
	
	
	@Override
	public String toString() {
		return "GraphNodeRelation [source=" + source + ", target=" + target + ", rGroup=" + rGroup + "]";
	}



	public GraphNodeRelation(GraphNode source, GraphNode target, RGroup rGroup) {
		super();
		this.source = source;
		this.target = target;
		this.rGroup = rGroup;
	}

	public RGroup getrGroup() {
		return rGroup;
	}

	public void setrGroup(RGroup rGroup) {
		this.rGroup = rGroup;
	}

	public GraphNode getSource() {
		return source;
	}

	public void setSource(GraphNode source) {
		if(!NType.REACTION.equals(source.getnType()))
			throw new RuntimeException("bad_node_type_not_reaction");
			
		this.source = source;
	}

	public GraphNode getTarget() {
		return target;
	}

	public void setTarget(GraphNode target) {
		if(!NType.COMPOUND.equals(target.getnType()))
			throw new RuntimeException("bad_node_type_not_reaction");
		this.target = target;
	}
}
