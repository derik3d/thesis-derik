package com.thesisderik.appthesis.entities.graphentities;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class GraphNode {

	//node name trimmed for better processing
	String name;
	
	//real name without trimming
	String fullName;
	
	//long written name
	String longName;
	
	//node type
	NType nType;
	
	public enum NType {
		//if the node corresponds to a compound, reaction or other thing like a outside connection or input
		COMPOUND, REACTION, OTHER
	}

	public GraphNode() {
		super();
	}
	
	public GraphNode(String name, String fullName, NType nType) {
		super();
		this.name = name;
		this.fullName = fullName;
		this.nType = nType;
	}

	@Override
	public int hashCode() {
     return new HashCodeBuilder(51,33).
       append(name).
       append(fullName).
       append(longName).
       append(nType).
       toHashCode();
     }
	
	@Override
	public boolean equals(Object obj){
		   if (obj == null) { return false; }
		   if (obj == this) { return true; }
		   if (obj.getClass() != getClass()) {
		     return false;
		   }
		   GraphNode rhs = (GraphNode) obj;
		return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(name, rhs.name)
                .append(fullName, rhs.fullName)
                .append(longName, rhs.longName)
                .append(nType, rhs.nType)
                .isEquals();
	}

	@Override
	public String toString() {
		return "\n GraphNode [name=" + name + ", fullName=" + fullName + ", longName=" + longName + ", nType=" + nType
				+ "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public NType getnType() {
		return nType;
	}

	public void setnType(NType nType) {
		this.nType = nType;
	}
	
	
	
}
