package com.thesisderik.appthesis.rawgraphparser.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "pathway")
public class GraphKGML {

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "org")
	private String org;

	@XmlAttribute(name = "number")
	private String number;
	
	@XmlElement(name = "entry")
	private List<KGMLEntry> entries;

	@XmlElement(name = "relation")
	private List<KGMLRelation> relations;

	@XmlElement(name = "reaction")
	private List<KGMLReaction> reactions;
	
	
	
	@Override
	public String toString() {
		return "GraphKGML [name=" + name + ", org=" + org + ", number=" + number + ", entries=" + entries
				+ ", relations=" + relations + ", reactions=" + reactions + "]";
	}

	
	public String getName() {
		return name;
	}


	@XmlTransient
	public void setName(String name) {
		this.name = name;
	}


	public String getOrg() {
		return org;
	}


	@XmlTransient
	public void setOrg(String org) {
		this.org = org;
	}


	public String getNumber() {
		return number;
	}


	@XmlTransient
	public void setNumber(String number) {
		this.number = number;
	}


	public List<KGMLEntry> getEntries() {
		return entries;
	}

	@XmlTransient
	public void setEntries(List<KGMLEntry> entries) {
		this.entries = entries;
	}


	public List<KGMLRelation> getRelations() {
		return relations;
	}

	@XmlTransient
	public void setRelations(List<KGMLRelation> relations) {
		this.relations = relations;
	}


	public List<KGMLReaction> getReactions() {
		return reactions;
	}

	@XmlTransient
	public void setReactions(List<KGMLReaction> reactions) {
		this.reactions = reactions;
	}



	public static class KGMLEntry{

		@XmlAttribute(name = "id")
		int id;
		@XmlAttribute(name = "name")
		String name;
		@XmlAttribute(name = "type")
		String type;	
		@XmlAttribute(name = "reaction")
		String reaction;
		@XmlAttribute(name = "link")
		String link;
		

		@XmlElement(name = "graphics")
		EntryGraphics graphics;
		
		@Override
		public String toString() {
			
			return String.format("kgmlentry %s %s %s %s %s", id, name, type, reaction, link);
		}

		public int getId() {
			return id;
		}

		@XmlTransient
		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		@XmlTransient
		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		@XmlTransient
		public void setType(String type) {
			this.type = type;
		}

		public String getReaction() {
			return reaction;
		}

		@XmlTransient
		public void setReaction(String reaction) {
			this.reaction = reaction;
		}

		public String getLink() {
			return link;
		}

		@XmlTransient
		public void setLink(String link) {
			this.link = link;
		}

		public EntryGraphics getGraphics() {
			return graphics;
		}

		@XmlTransient
		public void setGraphics(EntryGraphics graphics) {
			this.graphics = graphics;
		}
				
		
	}
	
	public static class KGMLRelation{

		@XmlAttribute(name = "entry1")
		int entry1;
		@XmlAttribute(name = "entry2")
		int entry2;
		@XmlAttribute(name = "type")
		String type;	

		@XmlElement(name = "subtype")
		RelationSubtype subtype;
		
		@Override
		public String toString() {
			
			return String.format("kgmlrelation %s %s %s %s", entry1, entry2, type, subtype);
		}

		public int getEntry1() {
			return entry1;
		}

		@XmlTransient
		public void setEntry1(int entry1) {
			this.entry1 = entry1;
		}

		public int getEntry2() {
			return entry2;
		}

		@XmlTransient
		public void setEntry2(int entry2) {
			this.entry2 = entry2;
		}

		public String getType() {
			return type;
		}

		@XmlTransient
		public void setType(String type) {
			this.type = type;
		}

		public RelationSubtype getSubtype() {
			return subtype;
		}

		@XmlTransient
		public void setSubtype(RelationSubtype subtype) {
			this.subtype = subtype;
		}
		
	}
	
	public static class KGMLReaction{

		@XmlAttribute(name = "id")
		int id;
		@XmlAttribute(name = "name")
		String name;
		@XmlAttribute(name = "type")
		String type;	
		

		@XmlElement(name = "substrate")
		List<ReactionSubstrate> substrates;

		@XmlElement(name = "product")
		List<ReactionProduct> products;
		
		
		@Override
		public String toString() {
			
			return String.format("kgmlreation %s %s %s", id, name, type);
		}


		public int getId() {
			return id;
		}


		@XmlTransient
		public void setId(int id) {
			this.id = id;
		}


		public String getName() {
			return name;
		}


		@XmlTransient
		public void setName(String name) {
			this.name = name;
		}


		public String getType() {
			return type;
		}


		@XmlTransient
		public void setType(String type) {
			this.type = type;
		}


		public List<ReactionSubstrate> getSubstrates() {
			return substrates;
		}


		@XmlTransient
		public void setSubstrates(List<ReactionSubstrate> substrates) {
			this.substrates = substrates;
		}


		public List<ReactionProduct> getProducts() {
			return products;
		}


		@XmlTransient
		public void setProducts(List<ReactionProduct> products) {
			this.products = products;
		}
		
	}
	
	public static class EntryGraphics{
		
		@XmlAttribute(name = "name")
		String name;
		@XmlAttribute(name = "fgcolor")
		String fgcolor;	
		@XmlAttribute(name = "bgcolor")
		String bgcolor;
		@XmlAttribute(name = "type")
		String type;	
		@XmlAttribute(name = "x")
		String x;	
		@XmlAttribute(name = "y")
		String y;	
		@XmlAttribute(name = "width")
		String width;	
		@XmlAttribute(name = "height")
		String height;
		@XmlAttribute(name = "link")
		String link;
		
		@Override
		public String toString() {
			return "EntryGraphics [name=" + name + ", fgcolor=" + fgcolor + ", bgcolor=" + bgcolor + ", type=" + type
					+ ", x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", link=" + link + "]";
		}

		public String getName() {
			return name;
		}

		@XmlTransient
		public void setName(String name) {
			this.name = name;
		}

		public String getFgcolor() {
			return fgcolor;
		}

		@XmlTransient
		public void setFgcolor(String fgcolor) {
			this.fgcolor = fgcolor;
		}

		public String getBgcolor() {
			return bgcolor;
		}

		@XmlTransient
		public void setBgcolor(String bgcolor) {
			this.bgcolor = bgcolor;
		}

		public String getType() {
			return type;
		}

		@XmlTransient
		public void setType(String type) {
			this.type = type;
		}

		public String getX() {
			return x;
		}

		@XmlTransient
		public void setX(String x) {
			this.x = x;
		}

		public String getY() {
			return y;
		}

		@XmlTransient
		public void setY(String y) {
			this.y = y;
		}

		public String getWidth() {
			return width;
		}

		@XmlTransient
		public void setWidth(String width) {
			this.width = width;
		}

		public String getHeight() {
			return height;
		}

		@XmlTransient
		public void setHeight(String height) {
			this.height = height;
		}

		public String getLink() {
			return link;
		}

		@XmlTransient
		public void setLink(String link) {
			this.link = link;
		}
		
	}


	
	public static class RelationSubtype{
		
		@XmlAttribute(name = "name")
		String name;
		@XmlAttribute(name = "value")
		int value;
		
		@Override
		public String toString() {
			return "RelationSubtype [name=" + name + ", value=" + value + "]";
		}

		public String getName() {
			return name;
		}

		@XmlTransient
		public void setName(String name) {
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		@XmlTransient
		public void setValue(int value) {
			this.value = value;
		}	
		
	}

	
	public static class ReactionSubstrate{

		@XmlAttribute(name = "id")
		int id;
		@XmlAttribute(name = "name")
		String name;
		
		@Override
		public String toString() {
			return "RelationSubtype [id=" + id + ", name=" + name + "]";
		}

		public int getId() {
			return id;
		}

		@XmlTransient
		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		@XmlTransient
		public void setName(String name) {
			this.name = name;
		}	
		
	}

	
	public static class ReactionProduct{

		@XmlAttribute(name = "id")
		int id;
		@XmlAttribute(name = "name")
		String name;
		
		@Override
		public String toString() {
			return "RelationSubtype [id=" + id + ", name=" + name + "]";
		}

		public int getId() {
			return id;
		}

		@XmlTransient
		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		@XmlTransient
		public void setName(String name) {
			this.name = name;
		}	
		
	}

}
