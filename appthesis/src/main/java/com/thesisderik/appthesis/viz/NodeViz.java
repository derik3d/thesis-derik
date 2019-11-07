package com.thesisderik.appthesis.viz;

public class NodeViz {
	
	private String id;
	
	private String label;
	
	private double x;
	
	private double y;
	
	private double size;
	
	private String colora0;
	
	private String colorb0;
	
	private String colorc0;
	
	private String colord0;
	
	private String colora1;
	
	private String colorb1;
	
	private String colorc1;
	
	private String colord1;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getColora0() {
		return colora0;
	}

	public void setColora0(String colora0) {
		this.colora0 = colora0;
	}

	public String getColorb0() {
		return colorb0;
	}

	public void setColorb0(String colorb0) {
		this.colorb0 = colorb0;
	}

	public String getColorc0() {
		return colorc0;
	}

	public void setColorc0(String colorc0) {
		this.colorc0 = colorc0;
	}

	public String getColord0() {
		return colord0;
	}

	public void setColord0(String colord0) {
		this.colord0 = colord0;
	}

	public String getColora1() {
		return colora1;
	}

	public void setColora1(String colora1) {
		this.colora1 = colora1;
	}

	public String getColorb1() {
		return colorb1;
	}

	public void setColorb1(String colorb1) {
		this.colorb1 = colorb1;
	}

	public String getColorc1() {
		return colorc1;
	}

	public void setColorc1(String colorc1) {
		this.colorc1 = colorc1;
	}

	public String getColord1() {
		return colord1;
	}

	public void setColord1(String colord1) {
		this.colord1 = colord1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colora0 == null) ? 0 : colora0.hashCode());
		result = prime * result + ((colora1 == null) ? 0 : colora1.hashCode());
		result = prime * result + ((colorb0 == null) ? 0 : colorb0.hashCode());
		result = prime * result + ((colorb1 == null) ? 0 : colorb1.hashCode());
		result = prime * result + ((colorc0 == null) ? 0 : colorc0.hashCode());
		result = prime * result + ((colorc1 == null) ? 0 : colorc1.hashCode());
		result = prime * result + ((colord0 == null) ? 0 : colord0.hashCode());
		result = prime * result + ((colord1 == null) ? 0 : colord1.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		long temp;
		temp = Double.doubleToLongBits(size);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		NodeViz other = (NodeViz) obj;
		if (colora0 == null) {
			if (other.colora0 != null)
				return false;
		} else if (!colora0.equals(other.colora0))
			return false;
		if (colora1 == null) {
			if (other.colora1 != null)
				return false;
		} else if (!colora1.equals(other.colora1))
			return false;
		if (colorb0 == null) {
			if (other.colorb0 != null)
				return false;
		} else if (!colorb0.equals(other.colorb0))
			return false;
		if (colorb1 == null) {
			if (other.colorb1 != null)
				return false;
		} else if (!colorb1.equals(other.colorb1))
			return false;
		if (colorc0 == null) {
			if (other.colorc0 != null)
				return false;
		} else if (!colorc0.equals(other.colorc0))
			return false;
		if (colorc1 == null) {
			if (other.colorc1 != null)
				return false;
		} else if (!colorc1.equals(other.colorc1))
			return false;
		if (colord0 == null) {
			if (other.colord0 != null)
				return false;
		} else if (!colord0.equals(other.colord0))
			return false;
		if (colord1 == null) {
			if (other.colord1 != null)
				return false;
		} else if (!colord1.equals(other.colord1))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (Double.doubleToLongBits(size) != Double.doubleToLongBits(other.size))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NodeViz [id=" + id + ", label=" + label + ", x=" + x + ", y=" + y + ", size=" + size + ", colora0="
				+ colora0 + ", colorb0=" + colorb0 + ", colorc0=" + colorc0 + ", colord0=" + colord0 + ", colora1="
				+ colora1 + ", colorb1=" + colorb1 + ", colorc1=" + colorc1 + ", colord1=" + colord1 + "]";
	}
	
	

}
