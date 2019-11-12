package com.thesisderik.appthesis.viz;

import java.awt.Color;

import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainFeature;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainGroup;

public class ColorDataMapper {

	private Color bottom;
	private Color top;

	private boolean enable;

	private boolean normalize;
	
	private boolean useGroup;
	
	private PlainFeature bindingPlainFeature;
	
	private PlainGroup bindingPlainGroup;
	
	public String processValue(Object value) {
		String rgb = Integer.toHexString(bottom.getRGB());
		rgb = rgb.substring(2, rgb.length());
		return "#"+rgb;
		
	}

	public Color getBottom() {
		return bottom;
	}

	public void setBottom(String bottom) {
		this.bottom = Color.decode(bottom);
	}

	public Color getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = Color.decode(top);
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bindingPlainFeature == null) ? 0 : bindingPlainFeature.hashCode());
		result = prime * result + ((bindingPlainGroup == null) ? 0 : bindingPlainGroup.hashCode());
		result = prime * result + ((bottom == null) ? 0 : bottom.hashCode());
		result = prime * result + (enable ? 1231 : 1237);
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
		if (enable != other.enable)
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
		return "ColorDataMapper [bottom=" + bottom + ", top=" + top + ", enable=" + enable + ", normalize=" + normalize
				+ ", useGroup=" + useGroup + ", bindingPlainFeature=" + bindingPlainFeature + ", bindingPlainGroup="
				+ bindingPlainGroup + "]";
	}

	
	

}
