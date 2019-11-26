package com.thesisderik.appthesis.layout;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.vecmath.Point2d;

import com.thesisderik.appthesis.viz.EdgeViz;
import com.thesisderik.appthesis.viz.NodeViz;
import com.thesisderik.appthesis.viz.VizGraphFormat;

import edu.uci.ics.jung.algorithms.layout.AggregateLayout;
import edu.uci.ics.jung.algorithms.layout.BalloonLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
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
	

	
	public static void layoutGraph(
			VizGraphFormat graphSent, Map<Integer,Layouts> layerLayouts ,
			Map<Integer, Set<String>> nodeLayers) {
		

		System.out.println();
		System.out.println(nodeLayers);
		System.out.println();
		System.out.println(layerLayouts);
		System.out.println();
		


		
		//get a general map with all vertices and edges
		Graph<String,String> generalGraph = buildGeneralGraph(graphSent);
		
		//make graphs with the graph vertices by group
		Map<Integer,Graph<String,String>> graphsBuilt = buildGraphs(nodeLayers,graphSent.getEdges());
		
		//build an layout integrator
		DynamicLayoutIntegrator<String,String> dli = new DynamicLayoutIntegrator<>();
		
		//order the groups
		TreeMap<Integer,Graph<String,String>> graphsBuiltOrdered = new TreeMap<>(graphsBuilt);
		
		

		//add layout for all nodes, at the end
		DynamicSpring<String,String> dlgen = new DynamicSpring<>(generalGraph);

		//dlgen.ignoreForceCalcualtionsNodes = new HashSet<String>();
		dlgen.clusters = new ArrayList<>();
		
		//add layout for subgraphs
		for(int currLayer: graphsBuiltOrdered.navigableKeySet()) {
			Graph<String,String> currGraph = graphsBuiltOrdered.get(currLayer);
			dli.addDynamicLayoutToStack(getDinamicLayout(layerLayouts.get(currLayer),currGraph),1);
			//dlgen.ignoreForceCalcualtionsNodes.addAll(currGraph.getVertices());
			dlgen.clusters.add(new HashSet<>(currGraph.getVertices()));
			
		}
		

		dli.addDynamicLayoutToStack(dlgen,1);
		
		

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("LINK START");
		dli.execute();


		
		//integrate result
		applyCoordinatesDynamic(graphSent.getNodes(), dli);
		
		
		
	
	}
	
	static DynamicLayout<String,String> getDinamicLayout(Layouts layout, Graph<String,String> graph){
		

		switch(layout) {
		case SPRING:
			 DynamicSpring<String, String> springLayout = new DynamicSpring<String,String>(graph);


			 //springLayout.targetEdgeLength = 50;
			 //springLayout.edgeLengthMultiplier = 3;
				
			 //springLayout.desiredVertexSeparation = 10;
			 //springLayout.repellingMultiplier = 1; // 0 0.01 1 10
				
			 springLayout.concentricDistance = 10;
			 springLayout.concentricMultiplier =0; //0 1 10 100 500 1000
			 
			 return springLayout;
		case CIRCLE:
			DynamicSpring<String, String> circleLayout = new DynamicSpring<String,String>(graph);
			circleLayout.concentricDistance = 10;
			circleLayout.concentricMultiplier = 100;
			circleLayout.repellingMultiplier = 100;
			return circleLayout;
		default: 
			 DynamicSpring<String, String> springLayoutgen = new DynamicSpring<String,String>(graph);
			 return springLayoutgen;
		}
		
	}
	


	private static void applyCoordinatesDynamic(ArrayList<NodeViz> nodes, DynamicLayoutIntegrator<String, String> dli) {

		
		//fill the node positions with the general agregate layout data
		for(NodeViz nodeViz : nodes) {
			Point2d extracted = dli.dataForNode(nodeViz.getId());
			nodeViz.setX(extracted.x);
			nodeViz.setY(extracted.y);
		}
		
		
	}
	
	/*

	
	public static void layoutGraphold(
			VizGraphFormat graphSent, Map<Integer,
			Integer> hierarchy ,
			Map<Integer,Layouts> layerLayouts ,
			Map<Integer, Set<String>> nodeLayers) {
		

		System.out.println();
		System.out.println(hierarchy);
		System.out.println();
		System.out.println(nodeLayers);
		System.out.println();
		

		

		
		//get a general map with all vertices and edges
		Graph<String,String> generalGraph = buildGeneralGraph(graphSent);
		
		SpringLayout<String,String> generalLayout = new SpringLayout<>(generalGraph);
		generalLayout.setSize(new Dimension(500,500)); 
		AggregateLayout<String,String> aggregateGeneral =  new AggregateLayout<String,String>(generalLayout);


		
		//make graphs with the graph vertices by group
		Map<Integer,Graph<String,String>> graphsBuilt = buildGraphs(nodeLayers,graphSent.getEdges());
		
		//get layouts for the different layers on the hierarchy
		Map<Integer,Layout<String,String>> layouts = getLayoutsForLayers(hierarchy, graphsBuilt, layerLayouts);
		




	
		applyCoordinates(graphSent.getNodes(), aggregateGeneral);
		
		
		
	
	}
	
	
	public static void layoutGraphUnused(
			VizGraphFormat graphSent, Map<Integer,
			Integer> hierarchy ,
			Map<Integer,Layouts> layerLayouts ,
			Map<Integer, Set<String>> nodeLayers) {
		

		System.out.println();
		System.out.println(hierarchy);
		System.out.println();
		System.out.println(nodeLayers);
		System.out.println();
		
		
		Graph<String,String> generalGraph = new SparseMultigraph<>();
		Layout<String,String> generalLayout = new SpringLayout<>(generalGraph);
		
		
		generalLayout.setSize(new Dimension(50,50)); 
		
		
		AggregateLayout<String,String> aggregateGeneral =  new AggregateLayout<String,String>(generalLayout);

		
		//for(NodeViz nodeViz : graphSent.getNodes()) {
		//	generalGraph.addVertex(nodeViz.getId());
		//}
		
		//make graphs with the graph vertices by group
		Map<Integer,Graph<String,String>> graphsBuilt = buildGraphs(nodeLayers);
		
		//get layouts for the different layers on the hierarchy
		Map<Integer,Layout<String,String>> layouts = getLayoutsForLayers(hierarchy, graphsBuilt, layerLayouts);

		
		
		//fill the edges on the subgraphs
		for(EdgeViz edgeViz : graphSent.getEdges()) {
			 
			generalGraph.addEdge(edgeViz.getId(), edgeViz.getSource(), edgeViz.getTarget());
			 
			//if a graphs contains both vertices of a edge, add the edge
			 for(Graph<String,String> aGraph : graphsBuilt.values()) {
				 if(aGraph.containsVertex(edgeViz.getSource()) && aGraph.containsVertex(edgeViz.getTarget()))
					 aGraph.addEdge(edgeViz.getId(), edgeViz.getSource(), edgeViz.getTarget());
			 }
		}
		
		
		
		hierarchyOperation(hierarchy , aggregateGeneral, layouts, layerLayouts, graphsBuilt);
		
		
		applyCoordinates(graphSent.getNodes(), aggregateGeneral);
		

	}
	
	*/

	
	private static Graph<String, String> buildGeneralGraph(VizGraphFormat graphSent) {
		Graph<String,String> generalGraph = new SparseMultigraph<>();

		for(EdgeViz edgeViz : graphSent.getEdges()) {
			generalGraph.addEdge(edgeViz.getId(), edgeViz.getSource(), edgeViz.getTarget()); 
		}

		for(NodeViz nodeViz : graphSent.getNodes()) {
				generalGraph.addVertex(nodeViz.getId());
		}
		
		return generalGraph;
	}

	
	private static Map<Integer,Layout<String,String>> getLayoutsForLayers(
			Map<Integer, Integer> hierarchy,
			Map<Integer, Graph<String, String>> graphsBuilt,
			Map<Integer,Layouts> layerLayouts
			) {

		
		//where to store the layouts for the different hierarchy indexes
		Map<Integer,Layout<String,String>> layouts = new HashMap<>();
		
		//get all the different values from the hierarchy
		Set<Integer> existingLayers = new HashSet<>();
		//add the keys and the values of the hierarchy
		existingLayers.addAll(hierarchy.keySet());
		existingLayers.addAll(hierarchy.values());
		
		//build the layouts for the hierarchy indexes
		for(Integer currLayer : existingLayers) {
			//build a layout for a key with a graph, the graph or the layout could not exist
			layouts.put(currLayer, buildLayout(layerLayouts.get( currLayer), graphsBuilt.get(currLayer)));
		}
		
		return layouts;
		
	}


	private static void applyCoordinates(ArrayList<NodeViz> nodes, AggregateLayout<String, String> aggregateGeneral) {

		
		//fill the node positions with the general agregate layout data
		for(NodeViz nodeViz : nodes) {
			Point2D extracted = aggregateGeneral.apply(nodeViz.getId());
			nodeViz.setX(extracted.getX());
			nodeViz.setY(extracted.getY());
		}
		
		
	}




	public static Map<Integer,Graph<String,String>> buildGraphs(
			Map<Integer, Set<String>> nodeLayers,
			ArrayList<EdgeViz> edges
			) {


		//where to store the mini graphs built
		Map<Integer,Graph<String,String>> graphsBuilt = new HashMap<>();
		
		//natural ordered sub groups of nodes
		TreeMap<Integer,Set<String>> orderedNodesGroups = new TreeMap<>();
		orderedNodesGroups.putAll(nodeLayers);
		
		//for every group of nodes, starting by the lower number key
		for(Map.Entry<Integer, Set<String>> currGroup :orderedNodesGroups.entrySet()) {
			//create a graph
			Graph<String,String> builtGraph = new SparseMultigraph<>();
			//and add the corresponding vertices for the given group
			for(String vertexName: currGroup.getValue()) {
				builtGraph.addVertex(vertexName);
			}
			//store the graph with the key of the group
			graphsBuilt.put(currGroup.getKey(), builtGraph);
		}
		

		//fill the edges on the subgraphs
		for(EdgeViz edgeViz : edges) {			 
			//if a graphs contains both vertices of a edge, add the edge
			 for(Graph<String,String> aGraph : graphsBuilt.values()) {
				 if(aGraph.containsVertex(edgeViz.getSource()) && aGraph.containsVertex(edgeViz.getTarget()))
					 aGraph.addEdge(edgeViz.getId(), edgeViz.getSource(), edgeViz.getTarget());
			 }
		}
		
		
		return graphsBuilt;
		
	}
	
	
	
	private static void hierarchyOperation(
			Map<Integer, Integer> hierarchy,
			AggregateLayout<String, String> topMerger,
			Map<Integer,Layout<String,String>> availableLayouts,
			Map<Integer,Layouts> layerLayouts,
			Map<Integer,Graph<String,String>> graphsExisting
			) {
		
		


		System.out.println();
		System.out.println(hierarchy);
		System.out.println();
		System.out.println(topMerger);
		System.out.println();
		System.out.println(availableLayouts);
		System.out.println();
		System.out.println(layerLayouts);
		System.out.println();
		
		
		//if hierarchy doesnt have values finish
		if(hierarchy.size()<1)return;
		
		//obtain the current highest value of the childs in the hierarchy
		int max = hierarchy.keySet().stream().max(Integer::compare).get();
		
		Predicate<Integer> passTheIndexes = num -> {
			return num>max;
		}; 
		
		//get a set with the parents that surpass the current high value of the children
		Set<Integer> passed = hierarchy.values().stream().filter(passTheIndexes).collect(Collectors.toSet());
		
		//list of children of the "passed" parrents
		Set<Integer> keysToRemove = new HashSet<>();
		
		//traverse the hierachy and find the children related to the parents "passed"
		for( Map.Entry<Integer, Integer> entry : hierarchy.entrySet()){
			
			//add the children keys to the list
			if(passed.contains(entry.getValue())) {
				keysToRemove.add(entry.getKey());
			}
			 
		}
		
		//create a new list with to later remove the surpassed parents children
		Map<Integer, Integer> remaining = new HashMap<>();
		remaining.putAll(hierarchy);
		
		for(Integer val : keysToRemove) {
			remaining.remove(val);
		}
		
		
		//process every parent that passed the top children values
		for(int passedValue: passed) {
			
			//if there is a graph for that value, add it to the top layout merger
			if(graphsExisting.keySet().contains(passedValue)) {
				
				Point2D center = new Point2D.Double();
				center.setLocation(50, 50);
				
				//add to the upper layout, the layer corresponding to that graph
				topMerger.put(availableLayouts.get(passedValue), center);
				
				
			//if not, it could be a layout layer, so go deeper
			}else {

				//get a layout for the layer, in the case there were a predef layout method
				Layout<String,String> layout;
				if(layerLayouts.containsKey(passedValue)){
					layout = buildLayout(layerLayouts.get(passedValue), null);
				}else{
					layout = buildLayout( Layouts.SPRING , null);
				}

				//craate an agregate layout merger and add the layer
				AggregateLayout<String,String> layoutAggregate =  new AggregateLayout<String,String>(layout);
				layoutAggregate.setSize(new Dimension(100,100));
				
				Point2D center = new Point2D.Double();
				center.setLocation(50, 50);
				
				//put the new agg layout on the top merger layout
				topMerger.put(layoutAggregate, center);

				//call again the funciton with the unused children and with this new merger layer as top
				hierarchyOperation(remaining,layoutAggregate,availableLayouts, layerLayouts, graphsExisting);
				
			}
			
		}
		
		
	}


	private static Layout<String, String> buildLayout(Layouts layout, Graph<String,String> g) {
		
		if(g == null) {
			g = new SparseMultigraph<>();
		}
		
		if(layout == null)
			layout = Layouts.SPRING;
		
		switch(layout) {
		case SPRING:
			 SpringLayout<String, String> springLayout = new SpringLayout<String,String>(g);
			 springLayout.setRepulsionRange(2000);
			 return springLayout;
		case CIRCLE:
			 CircleLayout<String, String> circleLayout = new CircleLayout<String,String>(g);
			 //circleLayout.setRadius(10);
			 circleLayout.setSize(new Dimension(30,30));
			 return circleLayout;
		default: return null;
		}
		
	}
	

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
