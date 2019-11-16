package com.thesisderik.appthesis.layout;

import com.thesisderik.appthesis.layout.LayoutManager.Layouts;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainFeature;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainGroup;

public class LayoutItem {
	
	boolean enabled;
	
	PlainGroup group;
	
	Integer layer;
	
	boolean filter;
	PlainFeature feature;
	String value0;
	String value1;
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public PlainGroup getGroup() {
		return group;
	}
	public void setGroup(PlainGroup group) {
		this.group = group;
	}
	public Integer getLayer() {
		return layer;
	}
	public void setLayer(Integer layer) {
		this.layer = layer;
	}
	public boolean isFilter() {
		return filter;
	}
	public void setFilter(boolean filter) {
		this.filter = filter;
	}
	public PlainFeature getFeature() {
		return feature;
	}
	public void setFeature(PlainFeature feature) {
		this.feature = feature;
	}
	public String getValue0() {
		return value0;
	}
	public void setValue0(String value0) {
		this.value0 = value0;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((feature == null) ? 0 : feature.hashCode());
		result = prime * result + (filter ? 1231 : 1237);
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((layer == null) ? 0 : layer.hashCode());
		result = prime * result + ((value0 == null) ? 0 : value0.hashCode());
		result = prime * result + ((value1 == null) ? 0 : value1.hashCode());
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
		LayoutItem other = (LayoutItem) obj;
		if (enabled != other.enabled)
			return false;
		if (feature == null) {
			if (other.feature != null)
				return false;
		} else if (!feature.equals(other.feature))
			return false;
		if (filter != other.filter)
			return false;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (layer == null) {
			if (other.layer != null)
				return false;
		} else if (!layer.equals(other.layer))
			return false;
		if (value0 == null) {
			if (other.value0 != null)
				return false;
		} else if (!value0.equals(other.value0))
			return false;
		if (value1 == null) {
			if (other.value1 != null)
				return false;
		} else if (!value1.equals(other.value1))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LayoutItem [enabled=" + enabled + ", group=" + group + ", layer=" + layer + ", filter=" + filter
				+ ", feature=" + feature + ", value0=" + value0 + ", value1=" + value1 + "]";
	}
	
	
	

}
