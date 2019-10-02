package com.thesisderik.appthesis.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.thesisderik.appthesis.persistence.graph.entities.Graph;
import com.thesisderik.appthesis.persistence.graph.entities.GraphNode;
import com.thesisderik.appthesis.persistence.graph.entities.GraphNode.NType;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;


public class GraphManagerService implements IGraphManagerService{

	
	@Autowired
	INamesIntegrator iNamesIntegrator;
	
	@Autowired
	IGraphBuilder iGraphBuilder;
	

	@Override
	public void processSbml(String path) {
		
		Graph gp = iGraphBuilder.loadSbml(path);
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
		// persist graph on db
		
	}
	
	
	

	@Override
	public void processKgml(String path) {
		
		Graph gp = iGraphBuilder.loadKgml(path);
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
		// persist graph on db
		
	}
	
	
	

}
