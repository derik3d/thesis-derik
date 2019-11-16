package com.thesisderik.appthesis.layout;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

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
		SPRING("spring layout"),
		CIRCULAR("concentric");
		
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
		

	/*	
		for(NodeViz nodeViz : graphSent.getNodes()) {
			nodeViz.setX(layoutg.getX(nodeViz.getId()));
			nodeViz.setY(layoutg.getY(nodeViz.getId()));
		}
*/

		for(NodeViz nodeViz : graphSent.getNodes()) {
			Point2D extracted = layoutg.apply(nodeViz.getId());
			nodeViz.setX(extracted.getX());
			nodeViz.setY(extracted.getY());
		}
		
		

	}
	

}
