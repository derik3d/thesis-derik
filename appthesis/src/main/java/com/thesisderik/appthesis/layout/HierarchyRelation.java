package com.thesisderik.appthesis.layout;

import com.thesisderik.appthesis.layout.LayoutManager.Layouts;

public class HierarchyRelation {
	
	boolean enabled;
	
	Layouts lay;
	
	Integer son;
	Integer father;
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Layouts getLay() {
		return lay;
	}
	public void setLay(Layouts lay) {
		this.lay = lay;
	}
	public Integer getSon() {
		return son;
	}
	public void setSon(Integer son) {
		this.son = son;
	}
	public Integer getFather() {
		return father;
	}
	public void setFather(Integer father) {
		this.father = father;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((father == null) ? 0 : father.hashCode());
		result = prime * result + ((lay == null) ? 0 : lay.hashCode());
		result = prime * result + ((son == null) ? 0 : son.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HierarchyRelation other = (HierarchyRelation) obj;
		if (enabled != other.enabled)
			return false;
		if (father == null) {
			if (other.father != null)
				return false;
		} else if (!father.equals(other.father))
			return false;
		if (lay != other.lay)
			return false;
		if (son == null) {
			if (other.son != null)
				return false;
		} else if (!son.equals(other.son))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "HierarchyRelation [enabled=" + enabled + ", lay=" + lay + ", son=" + son + ", father=" + father + "]";
	}
	
	
	
	
}
