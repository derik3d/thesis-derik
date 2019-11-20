package com.thesisderik.appthesis.layout;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

import javax.vecmath.Point2d;

import edu.uci.ics.jung.graph.Graph;

public abstract class DynamicLayout<N,E> {
	
	Graph<N,E> graph;
	
	public DynamicLayout(Graph<N,E> graph ){
		this.graph = graph;
	}
	
	public Collection<N> getNodes(){
		return graph.getVertices();
	}

	public abstract void execute(Map<N, Point2d> nodes);
}
