package com.thesisderik.appthesis.layout;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Stroke;

import com.thesisderik.appthesis.viz.EdgeViz;
import com.thesisderik.appthesis.viz.NodeViz;
import com.thesisderik.appthesis.viz.VizGraphFormat;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class LayoutManager {
	
	
	public static void layoutGraph(VizGraphFormat graphSent) {
		
		
		Graph<String, String> g = new SparseMultigraph<String, String>();

		
		for(NodeViz nodeViz : graphSent.getNodes()) {
			 g.addVertex(nodeViz.getId());
		}
		
		for(EdgeViz edgeViz : graphSent.getEdges()) {
			 g.addEdge(edgeViz.getId(), edgeViz.getSource(), edgeViz.getTarget());
		}
		
		CircleLayout<String, String> layout = new CircleLayout<String,String>(g);
		layout.setSize(new Dimension(10,10)); 
		


		for(NodeViz nodeViz : graphSent.getNodes()) {
			nodeViz.setX(layout.getX(nodeViz.getId()));
			nodeViz.setY(layout.getY(nodeViz.getId()));
		}

	}
	

}
