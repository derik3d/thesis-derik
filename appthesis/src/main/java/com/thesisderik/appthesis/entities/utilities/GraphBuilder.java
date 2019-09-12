package com.thesisderik.appthesis.entities.utilities;

import java.util.HashMap;
import java.util.Map;

import com.thesisderik.appthesis.entities.graphentities.Graph;
import com.thesisderik.appthesis.entities.graphentities.GraphNode;
import com.thesisderik.appthesis.entities.graphentities.GraphNodeRelation;
import com.thesisderik.appthesis.graphparser.GraphKGML;
import com.thesisderik.appthesis.graphparser.GraphSBML;

public class GraphBuilder{
	
	public static Graph createGraph(GraphKGML graphKGML){
		
		Map<Integer, GraphNode> mappingForCompounds = new HashMap<>();
		
		Graph formatted = new Graph();
		
		formatted.setName(graphKGML.getName());
		formatted.setGtype(Graph.GType.KGML);
		
		for(GraphKGML.KGMLEntry entry : graphKGML.getEntries()) {
			if(entry.getType().equals("compound")) {
				GraphNode gnode = new GraphNode();
				gnode.setFullName(entry.getName());
				gnode.setnType(GraphNode.NType.COMPOUND);
				mappingForCompounds.put(entry.getId(), gnode);
				formatted.getNodes().add(gnode);
			}
		}

		
		for(GraphKGML.KGMLReaction entry : graphKGML.getReactions()) {
			GraphNode gnode = new GraphNode();
			
			gnode.setFullName(entry.getName());
			gnode.setnType(GraphNode.NType.REACTION);
			
			for(GraphKGML.ReactionProduct prod : entry.getProducts()) {

				GraphNodeRelation gnorel = new GraphNodeRelation();
				gnorel.setSource(gnode);
				gnorel.setTarget(mappingForCompounds.get(prod.getId()));
				
				if(gnorel.getTarget() instanceof Object)formatted.getNodeRelations().add(gnorel);

			}
			
			for(GraphKGML.ReactionSubstrate prod : entry.getSubstrates()) {

				GraphNodeRelation gnorel = new GraphNodeRelation();
				gnorel.setSource(gnode);
				gnorel.setTarget(mappingForCompounds.get(prod.getId()));
				
				if(gnorel.getTarget() instanceof Object)formatted.getNodeRelations().add(gnorel);

			}
			
			formatted.getNodes().add(gnode);
		}
		
		System.out.println(formatted);
		return formatted;
		
	}


	public static Graph createGraph(GraphSBML graphSBML){
				
		Graph formatted = new Graph();
		
		formatted.setName(graphSBML.getModel().getName());
		formatted.setGtype(Graph.GType.SBML);
		
		for(GraphSBML.SBMLSpecie specie : graphSBML.getModel().getListOfSpecies().getSpecies()) {
			GraphNode gnode = new GraphNode();
			gnode.setFullName(specie.getId());
			gnode.setLongName(specie.getName());
			gnode.setnType(GraphNode.NType.COMPOUND);
			formatted.getNodes().add(gnode);
			
		}
		
		for(GraphSBML.SBMLReaction reaction : graphSBML.getModel().getListOfReactions().getEntries()) {
			GraphNode gnode = new GraphNode();
			gnode.setFullName(reaction.getId());
			gnode.setLongName(reaction.getName());
			gnode.setnType(GraphNode.NType.REACTION);
			formatted.getNodes().add(gnode);
		
			for(GraphSBML.SBMLSpeciesReference ref : reaction.getListOfReactants().getEntries() ) {
				
				GraphNodeRelation gnorel = new GraphNodeRelation();
				gnorel.setSource(gnode);
				gnorel.setTarget(formatted.getNodes().stream()
						.filter(x -> x.getFullName().equals(ref.getSpecies()))
				        .findFirst().orElse(null));
				
				if(gnorel.getTarget() instanceof Object)formatted.getNodeRelations().add(gnorel);

			}
		
			for(GraphSBML.SBMLSpeciesReference ref : reaction.getListOfProducts().getEntries() ) {
				
				GraphNodeRelation gnorel = new GraphNodeRelation();
				gnorel.setSource(gnode);
				gnorel.setTarget(formatted.getNodes().stream()
						.filter(x -> x.getFullName().equals(ref.getSpecies()))
				        .findFirst().orElse(null));
				
				if(gnorel.getTarget() instanceof Object)formatted.getNodeRelations().add(gnorel);

			}
			
			
		}
		
		System.out.println(formatted);
		return formatted;
		
	}
}
