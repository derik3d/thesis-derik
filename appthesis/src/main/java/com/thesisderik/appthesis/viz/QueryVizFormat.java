package com.thesisderik.appthesis.viz;

import java.util.List;
import java.util.Set;

import com.thesisderik.appthesis.layout.HierarchyRelation;
import com.thesisderik.appthesis.layout.LayoutItem;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainGroup;

public class QueryVizFormat {
	
	//groups to show
	Set<PlainGroup> groups;
	
	List<ColorDataMapper> mappers;
	
	List<HierarchyRelation> relations; 
	
	List<LayoutItem> layoutItems;

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

	public List<HierarchyRelation> getRelations() {
		return relations;
	}

	public void setRelations(List<HierarchyRelation> relations) {
		this.relations = relations;
	}

	public List<LayoutItem> getLayoutItems() {
		return layoutItems;
	}

	public void setLayoutItems(List<LayoutItem> layoutItems) {
		this.layoutItems = layoutItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((layoutItems == null) ? 0 : layoutItems.hashCode());
		result = prime * result + ((mappers == null) ? 0 : mappers.hashCode());
		result = prime * result + ((relations == null) ? 0 : relations.hashCode());
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
		if (layoutItems == null) {
			if (other.layoutItems != null)
				return false;
		} else if (!layoutItems.equals(other.layoutItems))
			return false;
		if (mappers == null) {
			if (other.mappers != null)
				return false;
		} else if (!mappers.equals(other.mappers))
			return false;
		if (relations == null) {
			if (other.relations != null)
				return false;
		} else if (!relations.equals(other.relations))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QueryVizFormat [groups=" + groups + ", mappers=" + mappers + ", relations=" + relations
				+ ", layoutItems=" + layoutItems + "]";
	}
	
	


}
