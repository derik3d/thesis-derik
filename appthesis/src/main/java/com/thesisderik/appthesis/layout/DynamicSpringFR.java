package com.thesisderik.appthesis.layout;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

import javax.vecmath.Point2d;

import edu.uci.ics.jung.graph.Graph;

public class DynamicSpringFR<N,E> extends DynamicLayout<N,E>{

	

	
	double desiredEdgeLength = 100;
	double stepSize = 0.01;
	
	
	public DynamicSpringFR(Graph<N, E> graph) {
		super(graph);
	}
	
	@Override
	public void execute(Map<N, Point2d> nodes) {
		
		final Map<N, ArrayList<Point2d>> newVertexValues = new HashMap<>();
		
		System.out.println(nodes);
		
		Random r = new Random();

		
		Consumer<E> dynamicProcess = edge -> {
			
			Collection<N> incidentVerticesPrev = graph.getIncidentVertices(edge);
			ArrayList<N> incidentVertices = new ArrayList<>(incidentVerticesPrev);
						
			//load vertices positions on a list
			ArrayList<Point2d> verticesPositions = new ArrayList<>();
			incidentVertices.forEach(v -> verticesPositions.add(nodes.get(v)));
						
			//distance
			double distance = verticesPositions.get(0).distance(verticesPositions.get(1));
			
			//linear force desired (inverted)
			double forceDesired;
			
			double deltaDistance = distance - desiredEdgeLength;
			
			if(deltaDistance == 0) {
				return;
			}else if(deltaDistance > 0) {
				forceDesired = distance / desiredEdgeLength;
			}else {
				forceDesired = - desiredEdgeLength / distance;
			}
				
			
			//direction vector
			Point2d directionNormalized = new Point2d();
			directionNormalized.set(
					( verticesPositions.get(0).x - verticesPositions.get(1).x ) / distance,
					( verticesPositions.get(0).y - verticesPositions.get(1).y ) / distance
					);
						
			
			for(int i = 0; i<2; i++) {
			
				N vertex = incidentVertices.get(i);
				
				//invert the direction for the other vertex
				if(i==1) {
					directionNormalized.set(
							-directionNormalized.x,
							-directionNormalized.y);
				}
				
				//list of deltas
				ArrayList<Point2d> deltasVertexList;
				
				//get or create the list
				synchronized(newVertexValues) {
					if(!((deltasVertexList = newVertexValues.get(vertex)) instanceof Object)) {
						deltasVertexList = new ArrayList<>();
						newVertexValues.put(vertex, deltasVertexList);
					}
				}
				
				//calculate new delta
				Point2d deltaCalculated = new Point2d();
				deltaCalculated.set(
						directionNormalized.x*forceDesired*stepSize,
						directionNormalized.y*forceDesired*stepSize
						);
			
				//add to the delta list the new delta
				synchronized(deltasVertexList) {
					deltasVertexList.add(deltaCalculated);
				}
				
			};
			
			
		};
		
		graph.getEdges().stream().forEach(dynamicProcess);
		
		
		BiConsumer<N, ArrayList<Point2d>> summarize = (vname, deltas) -> {

			if(deltas instanceof Object && deltas.size()>0) {
			
				BinaryOperator<Point2d> sum = (a,b) -> {
					a.add(b);
					return a;
				};
				
				Point2d resultPoint = deltas.stream().reduce(sum).get();
				
				resultPoint.set(resultPoint.x / deltas.size(), resultPoint.y / deltas.size());
				nodes.put(vname, resultPoint);
			}
			
		};
		
		newVertexValues.forEach(summarize);
		
	}

}

