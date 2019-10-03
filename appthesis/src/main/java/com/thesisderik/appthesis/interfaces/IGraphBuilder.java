package com.thesisderik.appthesis.interfaces;

import org.springframework.stereotype.Service;

import com.thesisderik.appthesis.persistence.graph.entities.Graph;

@Service
public interface IGraphBuilder {

	public Graph loadKgml(String path);
	public Graph loadSbml(String path);
	public void automateReactionNaming(Graph g);

}
