package com.thesisderik.appthesis.entities.graphentities;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class GraphNodeRelation {

	//current node
	GraphNode source;
	//relation node target
	GraphNode target;
	
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
                .isEquals();
	}
	
	@Override
	public String toString() {
		return "\n GraphNodeRelation [source=" + source + ", target=" + target + "]";
	}

	public GraphNodeRelation(GraphNode source, GraphNode target) {
		super();
		this.source = source;
		this.target = target;
	}

	public GraphNode getSource() {
		return source;
	}

	public void setSource(GraphNode source) {
		this.source = source;
	}

	public GraphNode getTarget() {
		return target;
	}

	public void setTarget(GraphNode target) {
		this.target = target;
	}
}
