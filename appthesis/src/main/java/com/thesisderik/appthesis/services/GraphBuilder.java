package com.thesisderik.appthesis.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.util.ResourceUtils;

import com.thesisderik.appthesis.interfaces.IGraphBuilder;
import com.thesisderik.appthesis.persistence.graph.entities.Graph;
import com.thesisderik.appthesis.persistence.graph.entities.GraphNode;
import com.thesisderik.appthesis.persistence.graph.entities.GraphNodeRelation;
import com.thesisderik.appthesis.persistence.graph.entities.GraphNode.NType;
import com.thesisderik.appthesis.rawgraphparser.RawGraphParser;
import com.thesisderik.appthesis.rawgraphparser.entities.GraphKGML;
import com.thesisderik.appthesis.rawgraphparser.entities.GraphSBML;
import com.thesisderik.appthesis.rawgraphparser.entities.GraphSBML.ReferencesList;
import com.thesisderik.appthesis.rawgraphparser.entities.GraphSBML.SBMLSpeciesReference;

public class GraphBuilder implements IGraphBuilder{
	
	public Graph createGraph(GraphKGML graphKGML){
		
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
			
			for(GraphKGML.ReactionSubstrate prod : entry.getSubstrates()) {

				GraphNodeRelation gnorel = new GraphNodeRelation();
				gnorel.setSource(gnode);
				gnorel.setTarget(mappingForCompounds.get(prod.getId()));
				gnorel.setrGroup(GraphNodeRelation.RGroup.REACTANT);

				
				if(gnorel.getTarget() instanceof Object)formatted.getNodeRelations().add(gnorel);

			}
			
			for(GraphKGML.ReactionProduct prod : entry.getProducts()) {

				GraphNodeRelation gnorel = new GraphNodeRelation();
				gnorel.setSource(gnode);
				gnorel.setTarget(mappingForCompounds.get(prod.getId()));
				gnorel.setrGroup(GraphNodeRelation.RGroup.PRODUCT);

				
				if(gnorel.getTarget() instanceof Object)formatted.getNodeRelations().add(gnorel);

			}
			
			formatted.getNodes().add(gnode);
		}
		
		System.out.println(formatted);
		return formatted;
		
	}


	public Graph createGraph(GraphSBML graphSBML){
				
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
		
			if(reaction.getListOfReactants()!=null) {
			
				for(GraphSBML.SBMLSpeciesReference ref : reaction.getListOfReactants().getEntries() ) {
					
					GraphNodeRelation gnorel = new GraphNodeRelation();
					gnorel.setSource(gnode);
					gnorel.setTarget(formatted.getNodes().stream()
							.filter(x -> x.getFullName().equals(ref.getSpecies()))
					        .findFirst().orElse(null));
					gnorel.setrGroup(GraphNodeRelation.RGroup.REACTANT);
					
					if(gnorel.getTarget() instanceof Object)formatted.getNodeRelations().add(gnorel);
	
				}
				
			}
			
			
			if(reaction.getListOfProducts()!=null) {
			
				for(GraphSBML.SBMLSpeciesReference ref : reaction.getListOfProducts().getEntries() ) {
					
					GraphNodeRelation gnorel = new GraphNodeRelation();
					gnorel.setSource(gnode);
					gnorel.setTarget(formatted.getNodes().stream()
							.filter(x -> x.getFullName().equals(ref.getSpecies()))
					        .findFirst().orElse(null));
					gnorel.setrGroup(GraphNodeRelation.RGroup.PRODUCT);
					
					
					if(gnorel.getTarget() instanceof Object)formatted.getNodeRelations().add(gnorel);
	
				}
			}
			
			
		}
		
		System.out.println(formatted);
		return formatted;
		
	}


	@Override
	public Graph loadKgml(String path) {

		File file = null;
		Graph createGraph=null;
		
		try {
			
			file = ResourceUtils.getFile(path);
			
			createGraph = createGraph(RawGraphParser.readFileKGML(file));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return createGraph;
	}


	@Override
	public Graph loadSbml(String path) {


		File file = null;
		Graph createGraph=null;
		
		try {
			
			file = ResourceUtils.getFile(path);
			
			createGraph = createGraph(RawGraphParser.readFileSBML(file));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return createGraph;
		
	}


	@Override
	public void automateReactionNaming(Graph g) {

		for(GraphNodeRelation nr:  g.getNodeRelations())
		{

			nr.getSource().tempNames.add(nr.getTarget().getName()+"-"+nr.getrGroup().toString().substring(0,1));
			
		}
		

		for(GraphNode gn : g.getNodes()) {
			if(NType.REACTION.equals(gn.getnType())) {
				gn.setName(formalizeName(gn));
			}
			gn.tempNames = null;
		}
		
	}
	
	String formalizeName(GraphNode gp) {
		String result="";
		
		result=gp.tempNames.stream().collect(Collectors.joining("__"));
		
		return result;
		
	}
}
