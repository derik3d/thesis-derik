package com.thesisderik.appthesis.viz;

import java.awt.Color;

import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainFeature;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainGroup;
import com.thesisderik.appthesis.viz.DataMapperUtils.Mappers;

public class ColorDataMapper {

	private Color bottom;
	private Color top;

	private boolean enabled;

	private boolean normalize;
	
	private boolean useGroup;
	
	private PlainFeature bindingPlainFeature;
	
	private PlainGroup bindingPlainGroup;

	private DataMapperUtils.Mappers mapper;

	public Color getBottom() {
		return bottom;
	}

	public void setBottom(Color bottom) {
		this.bottom = bottom;
	}

	public Color getTop() {
		return top;
	}

	public void setTop(Color top) {
		this.top = top;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isNormalize() {
		return normalize;
	}

	public void setNormalize(boolean normalize) {
		this.normalize = normalize;
	}

	public boolean isUseGroup() {
		return useGroup;
	}

	public void setUseGroup(boolean useGroup) {
		this.useGroup = useGroup;
	}

	public PlainFeature getBindingPlainFeature() {
		return bindingPlainFeature;
	}

	public void setBindingPlainFeature(PlainFeature bindingPlainFeature) {
		this.bindingPlainFeature = bindingPlainFeature;
	}

	public PlainGroup getBindingPlainGroup() {
		return bindingPlainGroup;
	}

	public void setBindingPlainGroup(PlainGroup bindingPlainGroup) {
		this.bindingPlainGroup = bindingPlainGroup;
	}

	public DataMapperUtils.Mappers getMapper() {
		return mapper;
	}

	public void setMapper(DataMapperUtils.Mappers mapper) {
		this.mapper = mapper;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bindingPlainFeature == null) ? 0 : bindingPlainFeature.hashCode());
		result = prime * result + ((bindingPlainGroup == null) ? 0 : bindingPlainGroup.hashCode());
		result = prime * result + ((bottom == null) ? 0 : bottom.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((mapper == null) ? 0 : mapper.hashCode());
		result = prime * result + (normalize ? 1231 : 1237);
		result = prime * result + ((top == null) ? 0 : top.hashCode());
		result = prime * result + (useGroup ? 1231 : 1237);
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
		ColorDataMapper other = (ColorDataMapper) obj;
		if (bindingPlainFeature == null) {
			if (other.bindingPlainFeature != null)
				return false;
		} else if (!bindingPlainFeature.equals(other.bindingPlainFeature))
			return false;
		if (bindingPlainGroup == null) {
			if (other.bindingPlainGroup != null)
				return false;
		} else if (!bindingPlainGroup.equals(other.bindingPlainGroup))
			return false;
		if (bottom == null) {
			if (other.bottom != null)
				return false;
		} else if (!bottom.equals(other.bottom))
			return false;
		if (enabled != other.enabled)
			return false;
		if (mapper != other.mapper)
			return false;
		if (normalize != other.normalize)
			return false;
		if (top == null) {
			if (other.top != null)
				return false;
		} else if (!top.equals(other.top))
			return false;
		if (useGroup != other.useGroup)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ColorDataMapper [bottom=" + bottom + ", top=" + top + ", enabled=" + enabled + ", normalize="
				+ normalize + ", useGroup=" + useGroup + ", bindingPlainFeature=" + bindingPlainFeature
				+ ", bindingPlainGroup=" + bindingPlainGroup + ", mapper=" + mapper + "]";
	}

	

}
