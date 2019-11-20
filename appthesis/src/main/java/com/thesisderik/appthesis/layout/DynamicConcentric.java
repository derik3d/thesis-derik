package com.thesisderik.appthesis.layout;

import java.awt.geom.Point2D;
import java.util.Map;

import javax.vecmath.Point2d;

import edu.uci.ics.jung.graph.Graph;

public class DynamicConcentric<N, E> extends DynamicLayout<N, E> {

	public DynamicConcentric(Graph<N, E> graph) {
		super(graph);
	}

	@Override
	public void execute(Map<N, Point2d> nodes, double annealing) {
		
		Point2d center;
		
	}

}
