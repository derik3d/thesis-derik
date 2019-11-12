package com.thesisderik.appthesis.viz;

import java.util.List;
import java.util.Set;

import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainGroup;

public class QueryVizFormat {
	
	//groups to show
	Set<PlainGroup> groups;
	
	List<ColorDataMapper> mappers;

	public Set<PlainGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<PlainGroup> groups) {
		this.groups = groups;
	}

	public List<ColorDataMapper> getMappers() {
		return mappers;
	}

	public void setMappers(List<ColorDataMapper> mappers) {
		this.mappers = mappers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((mappers == null) ? 0 : mappers.hashCode());
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
		QueryVizFormat other = (QueryVizFormat) obj;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (mappers == null) {
			if (other.mappers != null)
				return false;
		} else if (!mappers.equals(other.mappers))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QueryVizFormat [groups=" + groups + ", mappers=" + mappers + "]";
	}
	
	

}
