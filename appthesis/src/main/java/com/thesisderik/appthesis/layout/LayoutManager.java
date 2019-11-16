package com.thesisderik.appthesis.layout;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.thesisderik.appthesis.viz.EdgeViz;
import com.thesisderik.appthesis.viz.NodeViz;
import com.thesisderik.appthesis.viz.VizGraphFormat;

import edu.uci.ics.jung.algorithms.layout.AggregateLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class LayoutManager {
	
	
	public enum Layouts {
		DEFAULT("def spring layout"),
		SPRING("spring layout"),
		CIRCLE("concentric");
		
		private String description;

		private Layouts(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
		
	}
	
	
	public static void layoutGraph(VizGraphFormat graphSent, Map<Integer,Integer> hierarchy ,
			Map<Integer,Layouts> layerLayouts ,
			Map<Integer, Set<String>> nodeLayers) {
		

		System.out.println();
		System.out.println(hierarchy);
		System.out.println();
		System.out.println(nodeLayers);
		System.out.println();
		

		
		Map<Integer,Graph<String,String>> graphsBuilt = new HashMap<>();
		
		TreeMap<Integer,Set<String>> orderedNodesGroups = new TreeMap<>();
		orderedNodesGroups.putAll(nodeLayers);
		
		for(Map.Entry<Integer, Set<String>> currGroup :orderedNodesGroups.entrySet()) {
			Graph<String,String> builtGraph = new SparseMultigraph<>();
			for(String vertexName: currGroup.getValue()) {
				builtGraph.addVertex(vertexName);
			}
			graphsBuilt.put(currGroup.getKey(), builtGraph);
		}
		
		Graph<String,String> generalGraph = new SparseMultigraph<>();
		Layout<String,String> generalLayout = new SpringLayout<>(generalGraph);
		generalLayout.setSize(new Dimension(20,20)); 
		AggregateLayout<String,String> aggregateGeneral =  new AggregateLayout<String,String>(generalLayout);

		
		for(NodeViz nodeViz : graphSent.getNodes()) {
			generalGraph.addVertex(nodeViz.getId());
		}
		
		
		for(EdgeViz edgeViz : graphSent.getEdges()) {
			 
			generalGraph.addEdge(edgeViz.getId(), edgeViz.getSource(), edgeViz.getTarget());
			 
			 for(Graph<String,String> aGraph : graphsBuilt.values()) {
				 if(aGraph.containsVertex(edgeViz.getSource()) && aGraph.containsVertex(edgeViz.getTarget()))
					 aGraph.addEdge(edgeViz.getId(), edgeViz.getSource(), edgeViz.getTarget());
			 }
		}
		
		
		Map<Integer,Layout<String,String>> layouts = new HashMap<>();
		
		for(Map.Entry<Integer, Set<String>> currGroup :nodeLayers.entrySet()) {
			layouts.put(currGroup.getKey(), buildLayout(layerLayouts.get( currGroup.getKey()), graphsBuilt.get(currGroup.getKey())));
		}
		
		
		
		//add more aggregate accord to the hierarchy
		//or add all the subgraphs to the aggregate general as a easyfix
		
		

		for(NodeViz nodeViz : graphSent.getNodes()) {
			Point2D extracted = aggregateGeneral.apply(nodeViz.getId());
			nodeViz.setX(extracted.getX());
			nodeViz.setY(extracted.getY());
		}
		
		
		
		
		
		
		
		
		
		/*
		Graph<String, String> g = new SparseMultigraph<String, String>();
		Graph<String, String> g2 = new SparseMultigraph<String, String>();
		Graph<String, String> gg = new SparseMultigraph<String, String>();

		int i=0;
		for(NodeViz nodeViz : graphSent.getNodes()) {
			
			if(i>3)
				g.addVertex(nodeViz.getId());
			else
				g2.addVertex(nodeViz.getId());
			
			gg.addVertex(nodeViz.getId());


		}
		
		for(EdgeViz edgeViz : graphSent.getEdges()) {
			 g.addEdge(edgeViz.getId(), edgeViz.getSource(), edgeViz.getTarget());
		}
		
		for(EdgeViz edgeViz : graphSent.getEdges()) {
			 g2.addEdge(edgeViz.getId(), edgeViz.getSource(), edgeViz.getTarget());
		}

		SpringLayout<String, String> layout = new SpringLayout<String,String>(g);
		layout.setSize(new Dimension(20,20)); 
		
		SpringLayout<String, String> layout2 = new SpringLayout<String,String>(g2);
		layout2.setSize(new Dimension(20,20)); 
		
		CircleLayout<String, String> laygen = new CircleLayout<String,String>(gg);
		laygen.setSize(new Dimension(40,40)); 
		
		AggregateLayout<String,String> layoutg =  new AggregateLayout<String,String>(laygen);
		layoutg.setSize(new Dimension(100,100));
		
		
		Point2D p2g = new Point2D.Double();
		p2g.setLocation(0, 0);
		
		
		Point2D pg = new Point2D.Double();
		p2g.setLocation(50, 50);
		
		layoutg.put(layout, p2g);
		layoutg.put(layout2, p2g);
		

	
		for(NodeViz nodeViz : graphSent.getNodes()) {
			nodeViz.setX(layoutg.getX(nodeViz.getId()));
			nodeViz.setY(layoutg.getY(nodeViz.getId()));
		}
*

		for(NodeViz nodeViz : graphSent.getNodes()) {
			Point2D extracted = layoutg.apply(nodeViz.getId());
			nodeViz.setX(extracted.getX());
			nodeViz.setY(extracted.getY());
		}
		
		*/

	}


	private static Layout<String, String> buildLayout(Layouts layouts, Graph<String,String> g) {
		
		switch(layouts) {
		case SPRING:
			return new SpringLayout<String,String>(g);
		case CIRCLE:
			return new CircleLayout<String,String>(g);
		default: return null;
		}
		
	}
	

}
