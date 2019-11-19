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

import edu.uci.ics.jung.graph.Graph;

public class DynamicLayout<N,E> {
	
	
	
	
	double desiredEdgeLength = 100;
	double stepSize = 0.01;
	
	
	
	Graph<N,E> graph;
	
	public DynamicLayout(Graph<N,E> graph ){
		this.graph = graph;
	}
	
	public Collection<N> getNodes(){
		return graph.getVertices();
	}

	public void execute(Map<N, Point2D> nodes) {
		
		final Map<N, ArrayList<Point2D>> newVertexValues = new HashMap<>();
		
		System.out.println(nodes);
		
		Random r = new Random();

		
		Consumer<E> dynamicProcess = edge -> {
			
			if(r.nextDouble()>0.5)
				return;
			
			Collection<N> incidentVerticesPrev = graph.getIncidentVertices(edge);
			ArrayList<N> incidentVertices = new ArrayList<>(incidentVerticesPrev);
						
			ArrayList<Point2D> verticesPositions = new ArrayList<>();
						
			incidentVertices.forEach(v -> verticesPositions.add(nodes.get(v)));
						
			//distance
			double distance = verticesPositions.get(0).distance(verticesPositions.get(1));
			
			//linear force desired (inverted)
			double forceDesired;
			if(distance!=0) {
				forceDesired = desiredEdgeLength / distance  ;
			}
			else {
				forceDesired = desiredEdgeLength*desiredEdgeLength;
			}
				
			
			
			//direction vector
			Point2D directionNormalized = new Point2D.Double() ;
			directionNormalized.setLocation(
					( verticesPositions.get(0).getX() - verticesPositions.get(1).getX() ) / distance,
					( verticesPositions.get(0).getY() - verticesPositions.get(1).getY() ) / distance
					);
			
			
			if(forceDesired<1) {
				directionNormalized.setLocation(-directionNormalized.getX(), -directionNormalized.getY());
				forceDesired = 1/forceDesired;
			}
			
			for(int i = 0; i<2; i++) {
			
				N vertex = incidentVertices.get(i);
				
				//invert the direction for the other vertex
				if(i==1) {
					directionNormalized.setLocation(
							-directionNormalized.getX(),
							-directionNormalized.getY());
				}
				
				//list of deltas
				ArrayList<Point2D> deltasVertexList;
				
				//get or create the list
				synchronized(newVertexValues) {
					if(!((deltasVertexList = newVertexValues.get(vertex)) instanceof Object)) {
						deltasVertexList = new ArrayList<>();
						newVertexValues.put(vertex, deltasVertexList);
					}
				}
				
				//calculate new delta
				Point2D deltaCalculated = new Point2D.Double();
				deltaCalculated.setLocation(
						directionNormalized.getX()*forceDesired*stepSize,
						directionNormalized.getY()*forceDesired*stepSize
						);
			
				//add to the delta list the new delta
				synchronized(deltasVertexList) {
					deltasVertexList.add(deltaCalculated);
				}
				
			};
			
			
		};
		
		graph.getEdges().stream().forEach(dynamicProcess);
		
		System.out.println("afsadffadaf");
		
		BiConsumer<N, ArrayList<Point2D>> summarize = (vname, deltas) -> {

			if(deltas instanceof Object && deltas.size()>0) {
			
				BinaryOperator<Point2D> sum = (a,b) -> {
					a.setLocation(a.getX() + b.getX(), a.getY() + b.getY());
					return a;
				};
				
				Point2D resultPoint = deltas.stream().reduce(sum).get();
				resultPoint.setLocation(resultPoint.getX() / deltas.size(), resultPoint.getY() / deltas.size());
				nodes.put(vname, resultPoint);
			}
			
		};
		
		newVertexValues.forEach(summarize);
		
	}
}
