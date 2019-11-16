package com.thesisderik.appthesis.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.thesisderik.appthesis.interfaces.IAnalysisService;
import com.thesisderik.appthesis.interfaces.IGraphBuilder;
import com.thesisderik.appthesis.interfaces.IGraphManagerService;
import com.thesisderik.appthesis.interfaces.INamesIntegrator;
import com.thesisderik.appthesis.interfaces.ISimpleGraphManager;
import com.thesisderik.appthesis.interfaces.IStorageService;
import com.thesisderik.appthesis.persistence.graph.entities.Graph;
import com.thesisderik.appthesis.persistence.graph.entities.GraphNode;
import com.thesisderik.appthesis.persistence.graph.entities.GraphNode.NType;
import com.thesisderik.appthesis.persistence.graph.entities.GraphNodeRelation;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;


public class GraphManagerService implements IGraphManagerService{

	
	@Autowired
	INamesIntegrator iNamesIntegrator;
	
	@Autowired
	IGraphBuilder iGraphBuilder;
	

	@Autowired
	ISimpleGraphManager iSimpleGraphManager;
	

	@Autowired
	IAnalysisService iAnalisysService;	

	
	@Autowired
	IStorageService iStorageService;
	

	@Override
	public void processSbml(InputStream inputStream){
		
		
		Graph gp;
		gp = iGraphBuilder.loadSbml(inputStream);
		loadTagsOnDBSbml(gp);
		persistOnDBSbml(gp);
		
		
	}

	public void loadTagsOnDBSbml(Graph g){
		//foreach node process with inamesintegrator and save normalized name on graph
		
		for(GraphNode gn : g.getNodes()) {

			if(NType.COMPOUND.equals(gn.getnType()))
				gn.setName(processNameSbml(gn.getFullName()));
			
		}
		
		iGraphBuilder.automateReactionNaming(g);
		
	}
	
	public String processNameSbml(String name) {
		
		String oriname = name;
		
		Optional<PubchemIdentifier> processSbmlIdentifier = iNamesIntegrator.processSbmlIdentifier(name,false);
		if(processSbmlIdentifier.isPresent()) {
			return processSbmlIdentifier.get().getName();
		}
		
		
		
		name= name.substring(2).trim();
		
		if(name.substring(name.length()-2).equals("_c") ||
				name.substring(name.length()-2).equals("_e")||
				name.substring(name.length()-2).equals("_b")||
				name.substring(name.length()-2).equals("_l")||
				name.substring(name.length()-2).equals("_m")||
				name.substring(name.length()-2).equals("_r")||
				name.substring(name.length()-2).equals("_g")||
				name.substring(name.length()-2).equals("_x")||
				name.substring(name.length()-2).equals("_L")||
				name.substring(name.length()-2).equals("_U")||
				name.substring(name.length()-2).equals("_n")
				)
			name = name.substring(0,name.length()-2);
			
		processSbmlIdentifier = iNamesIntegrator.processSbmlIdentifier(name,false);
		if(processSbmlIdentifier.isPresent()) {
			return processSbmlIdentifier.get().getName();
		}
		
		
		
		name = name.replaceFirst("_", "__");
		
		
		processSbmlIdentifier = iNamesIntegrator.processSbmlIdentifier(name,true);
		if(processSbmlIdentifier.isPresent()) {
			return processSbmlIdentifier.get().getName();
		}
		else {
			return "BAD_"+oriname;
		}
		
	}
	
	private void persistOnDBSbml(Graph gp) {
		
		persistOnDB(gp);
		
	}
	
	
	

	@Override
	public void processKgml(InputStream inputStream) {
		
		Graph gp;
		gp = iGraphBuilder.loadKgml(inputStream);
		loadTagsOnDBKgml(gp);
		persistOnDBKgml(gp);
		
	}

	public void loadTagsOnDBKgml(Graph g){
		//foreach node process with inamesintegrator and save normalized name on graph
		
		for(GraphNode gn : g.getNodes()) {
			if(NType.COMPOUND.equals(gn.getnType()))
				gn.setName(processNameKgml(gn.getFullName()));
		}

		iGraphBuilder.automateReactionNaming(g);

		
	}
	
	public String processNameKgml(String name) {
		
		String oriname = name;

		
		name= name.replace("cpd:", "").trim();
		
		Optional<PubchemIdentifier> processKgmlIdentifier = iNamesIntegrator.processKgmlIdentifier(name,true);
		if(processKgmlIdentifier.isPresent()) {
			return processKgmlIdentifier.get().getName();
		}else {
			return "BAD"+oriname;
		}
		
	}
	
	private void persistOnDBKgml(Graph gp) {
		
		persistOnDB(gp);
		
	}
	
	private void persistOnDB(Graph gp) {
		
		Iterator<GraphNodeRelation> iterator = gp.getNodeRelations().iterator();
		
		while(iterator.hasNext()) {
			
			
			GraphNodeRelation relation = iterator.next();
			
			
			String reactionNode = relation.getSource().getName();
			
			if(reactionNode.length()>10)
				reactionNode = reactionNode.substring(0, 10);
			
			String compoundNode = relation.getTarget().getName();
			
			if(compoundNode.length()>10)
				compoundNode = compoundNode.substring(0, 10);

			iSimpleGraphManager.doNode(reactionNode);
			iSimpleGraphManager.createFeature("METABOLITE_TYPE","REACTION",reactionNode);

			iSimpleGraphManager.doNode(compoundNode);
			iSimpleGraphManager.createFeature("METABOLITE_TYPE","COMPOUND",compoundNode);
			
			iSimpleGraphManager.createRelation("METABOLITE_"+gp.getName(), reactionNode, compoundNode);
			
			iSimpleGraphManager.createGroupRel(gp.getName(),reactionNode);
			iSimpleGraphManager.createGroupRel(gp.getName(),compoundNode);
			

			iSimpleGraphManager.createGroupRel("GP_REACTION_NODE",reactionNode);
			iSimpleGraphManager.createGroupRel("GP_COMPOUND_NODE",compoundNode);

			
		}
		
		
	}
	
	

}
