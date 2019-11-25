package com.thesisderik.appthesis.layout;

import com.thesisderik.appthesis.layout.LayoutManager.Layouts;

public class HierarchyRelation {
	
	boolean enabled;
	
	Layouts lay;
	
	Integer layerNumber;

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

	public Integer getLayerNumber() {
		return layerNumber;
	}

	public void setLayerNumber(Integer layerNumber) {
		this.layerNumber = layerNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((lay == null) ? 0 : lay.hashCode());
		result = prime * result + ((layerNumber == null) ? 0 : layerNumber.hashCode());
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
		if (lay != other.lay)
			return false;
		if (layerNumber == null) {
			if (other.layerNumber != null)
				return false;
		} else if (!layerNumber.equals(other.layerNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HierarchyRelation [enabled=" + enabled + ", lay=" + lay + ", layerNumber=" + layerNumber + "]";
	}
	
	
	
}
