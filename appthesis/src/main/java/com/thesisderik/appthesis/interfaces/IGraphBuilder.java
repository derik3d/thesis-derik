package com.thesisderik.appthesis.interfaces;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.thesisderik.appthesis.persistence.graph.entities.Graph;

@Service
public interface IGraphBuilder {

	public Graph loadKgml(InputStream inputStream);
	public Graph loadSbml(InputStream inputStream);
	public void automateReactionNaming(Graph g);

}
