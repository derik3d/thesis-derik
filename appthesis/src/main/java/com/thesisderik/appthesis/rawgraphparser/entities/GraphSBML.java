package com.thesisderik.appthesis.rawgraphparser.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement(name = "sbml" , namespace="http://www.sbml.org/sbml/level2")
public class GraphSBML{
	
	@XmlElement(name = "model" , namespace="http://www.sbml.org/sbml/level2")
	private SBMLModel model;


	@Override
	public String toString() {
		return "GraphSBML [model=" + model + "]";
	}
	
	public SBMLModel getModel() {
		return model;
	}


	@XmlTransient
	public void setModel(SBMLModel model) {
		this.model = model;
	}


	static public class SBMLModel {
		@XmlAttribute(name = "id")
		private String id;

		@XmlAttribute(name = "name")
		private String name;
		
		@XmlElement(name = "listOfSpecies" , namespace="http://www.sbml.org/sbml/level2")
		private SBMLListSpecies listOfSpecies;
		
	
		@XmlElement(name = "listOfReactions" , namespace="http://www.sbml.org/sbml/level2")
		private SBMLListReactions listOfReactions;

		
		@Override
		public String toString() {
			return "SBMLModel [id=" + id + ", name=" + name + ", listOfSpecies=" + listOfSpecies + ", listOfReactions="
					+ listOfReactions + "]";
		}
		
		public String getId() {
			return id;
		}

		@XmlTransient
		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		@XmlTransient
		public void setName(String name) {
			this.name = name;
		}

		public SBMLListSpecies getListOfSpecies() {
			return listOfSpecies;
		}

		@XmlTransient
		public void setListOfSpecies(SBMLListSpecies listOfSpecies) {
			this.listOfSpecies = listOfSpecies;
		}

		public SBMLListReactions getListOfReactions() {
			return listOfReactions;
		}

		@XmlTransient
		public void setListOfReactions(SBMLListReactions listOfReactions) {
			this.listOfReactions = listOfReactions;
		}
	}

	public static class SBMLListSpecies{
		@XmlElement(name = "species" , namespace="http://www.sbml.org/sbml/level2")
		private List<SBMLSpecie> species;

		@Override
		public String toString() {
			return "SBMLListSpecies [species=" + species + "]";
		}

		public List<SBMLSpecie> getSpecies() {
			return species;
		}

		@XmlTransient
		public void setSpecies(List<SBMLSpecie> species) {
			this.species = species;
		}
		
		
	}
	
	public static class SBMLListReactions{
		@XmlElement(name = "reaction" , namespace="http://www.sbml.org/sbml/level2")
		private List<SBMLReaction> entries;

		@Override
		public String toString() {
			return "SBMLListReactions [entries=" + entries + "]";
		}

		public List<SBMLReaction> getEntries() {
			return entries;
		}

		@XmlTransient
		public void setEntries(List<SBMLReaction> entries) {
			this.entries = entries;
		}
		
	}
	
	public static class SBMLSpecie{
		@XmlAttribute(name = "id")
		private String id;

		@XmlAttribute(name = "name")
		private String name;

		@Override
		public String toString() {
			return "SBMLSpecie [id=" + id + ", name=" + name + "]";
		}
		
		public String getId() {
			return id;
		}

		@XmlTransient
		public void setId(String id) {
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
	
	public static class SBMLReaction{
		@XmlAttribute(name = "id")
		private String id;
		@XmlAttribute(name = "name")
		private String name;
		
		@XmlElement(name = "listOfReactants" , namespace="http://www.sbml.org/sbml/level2")
		ReferencesList listOfReactants;
		
		@XmlElement(name = "listOfProducts" , namespace="http://www.sbml.org/sbml/level2")
		ReferencesList listOfProducts;

		@Override
		public String toString() {
			return "SBMLReaction [id=" + id + ", name=" + name + ", listOfReactants=" + listOfReactants
					+ ", listOfProducts=" + listOfProducts + "]";
		}
		
		public String getId() {
			return id;
		}

		@XmlTransient
		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		@XmlTransient
		public void setName(String name) {
			this.name = name;
		}

		public ReferencesList getListOfReactants() {
			return listOfReactants;
		}

		@XmlTransient
		public void setListOfReactants(ReferencesList listOfReactants) {
			this.listOfReactants = listOfReactants;
		}

		public ReferencesList getListOfProducts() {
			return listOfProducts;
		}

		@XmlTransient
		public void setListOfProducts(ReferencesList listOfProducts) {
			this.listOfProducts = listOfProducts;
		}

	}
	
	public static class ReferencesList{
		@XmlElement(name = "speciesReference" , namespace="http://www.sbml.org/sbml/level2")
		private List<SBMLSpeciesReference> entries;

		public List<SBMLSpeciesReference> getEntries() {
			return entries;
		}

		@XmlTransient
		public void setEntries(List<SBMLSpeciesReference> entries) {
			this.entries = entries;
		}

		@Override
		public String toString() {
			return "ReferencesList [entries=" + entries + "]";
		}
		
	}
	
	public static class SBMLSpeciesReference{
		@XmlAttribute(name = "species")
		private String specie;
		
		@Override
		public String toString() {
			return "SBMLSpeciesReference [specie=" + specie + "]";
		}

		public String getSpecies() {
			return specie;
		}

		@XmlTransient
		public void setSpecies(String species) {
			this.specie = species;
		}

		
	}
	
	
	
	
}
