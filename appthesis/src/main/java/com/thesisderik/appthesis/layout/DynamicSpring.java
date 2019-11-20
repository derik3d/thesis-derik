package com.thesisderik.appthesis.layout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.vecmath.Point2d;

import edu.uci.ics.jung.graph.Graph;

public class DynamicSpring<N,E> extends DynamicLayout<N,E>{

	@FunctionalInterface
	interface TriFunction<A,B,C,R> {

	    R apply(A a, B b, C c);

	    default <V> TriFunction<A, B, C, V> andThen(
	                                Function<? super R, ? extends V> after) {
	        return (A a, B b, C c) -> after.apply(apply(a, b, c));
	    }
	}

	double desiredEdgeLength = 100;
	double desiredVertexSeparation = 50;
	double stepSize = 0.02;
	
	
	public DynamicSpring(Graph<N, E> graph) {
		super(graph);
	}
	
	public void old(Map<N, Point2d> nodes) {
		
		
		double stepSize = 0.02;
		double repulsiveBody = -1;
		double repulsiveSpring = -1;
		double desiredEdgeLength = 10;
		
		
		Map<N, ArrayList<Point2d>> forcesExperimentedBy = new HashMap<>();


		BinaryOperator<Point2d> sum = (a,b) -> {
			Point2d res = new Point2d();
			res.add(a);
			res.add(b);
			return res;
		};
		
		
		for(N vertex : graph.getVertices()) {
			
			Point2d initial = nodes.get(vertex);
			
			
			for(N otherVertex : graph.getVertices()) {
				if(vertex != otherVertex) {
					
					//VERTEX VERTEX CALCULATIONS
					
					Point2d target = nodes.get(otherVertex);
					
					Point2d calculated = new Point2d();
	
					double distance = initial.distance(target);
					
					Point2d directionNorm = new Point2d();
					directionNorm.set(target.x - initial.x , target.y - initial.y );
					directionNorm.set(directionNorm.x / distance, directionNorm.y / distance );
					
					Point2d forceExperimented = new Point2d();
					
					forceExperimented.set(directionNorm.x*repulsiveBody/(distance*distance), directionNorm.y*repulsiveBody/(distance*distance));
				
					if(forcesExperimentedBy.get(vertex) instanceof Object) {
						forcesExperimentedBy.get(vertex).add(forceExperimented);
					}
					else {
						forcesExperimentedBy.put(vertex, new ArrayList<>());
						forcesExperimentedBy.get(vertex).add(forceExperimented);
					}
					
					
					
					//EDGES CALCULATIONS
					
					
					for( E incidentEdge : graph.getIncidentEdges(vertex)) {
						
						Point2d springExperimentedForce = new Point2d();
						
						Point2d targetSpring = null;
						
						Point2d targetSpringNorm = new Point2d();
						
						for(N springTarget : graph.getIncidentVertices(incidentEdge)) {
							if(!springTarget.equals(vertex)) {
								targetSpring = nodes.get(springTarget);
								break;
							}
						}
						
						double distanceSpring = initial.distance(targetSpring);
						
						targetSpringNorm.set( targetSpring.x - initial.x , targetSpring.y - initial.y );
						targetSpringNorm.set( targetSpringNorm.x * distanceSpring , targetSpringNorm.y * distanceSpring );						
						
						springExperimentedForce.set(-repulsiveSpring *( distanceSpring - desiredEdgeLength) * targetSpringNorm.x , -repulsiveSpring *( distanceSpring - desiredEdgeLength) * targetSpringNorm.y);
						

						if(forcesExperimentedBy.get(vertex) instanceof Object) {
							forcesExperimentedBy.get(vertex).add(springExperimentedForce);
						}
						else {
							forcesExperimentedBy.put(vertex, new ArrayList<>());
							forcesExperimentedBy.get(vertex).add(springExperimentedForce);
						}
						
					}
			
				}
				
			}	
			
		}
		
		
		BiConsumer<N, ArrayList<Point2d>> processForces = (n, forces) -> {

			System.out.println();
			System.out.println();
			System.out.println(n);

			System.out.println();
			System.out.println("initial");
			System.out.println(nodes.get(n));
			
			Point2d forcesResult = forces.stream().reduce(sum).get();
			

			System.out.println("forces");
			System.out.println(forcesResult);
			
			Point2d initial = nodes.get(n);
			forcesResult.scale(stepSize);
			

			System.out.println("scaled");
			System.out.println(forcesResult);
			
			initial.add(forcesResult);
			

			System.out.println("final");
			System.out.println(nodes.get(n));
			
		};
		
		
		forcesExperimentedBy.forEach(processForces);

		
		
	}
		
	
	
	@Override
	public void execute(Map<N, Point2d> nodes){
		
		System.out.println();
		System.out.println();
		
		System.out.println(nodes);
		
		
		Random r = new Random();
		
		final Map<N, ArrayList<Point2d>> newVertexValues = new HashMap<>();

		Function<N , ArrayList<Point2d> > getDeltasList = vertexName -> {
				
				ArrayList<Point2d> deltasVertexList;
				
				//get or create the list
				synchronized(newVertexValues) {
					if(!((deltasVertexList = newVertexValues.get(vertexName)) instanceof Object)) {
						deltasVertexList = new ArrayList<>();
						newVertexValues.put(vertexName, deltasVertexList);
					}
				}
				
				return deltasVertexList;
		};
		
		TriFunction<Point2d, Point2d, Double, Point2d> normalizedVector = (p0,p1, distance) -> {
			
			if(distance==0) {
				return new Point2d((r.nextDouble()*2)-1,(r.nextDouble()*2)-1);
			}
			
			p1.set(p1.x-p0.x, p1.y-p0.y);
			p1.set(p1.x/distance, p1.y/distance);
			
			return p1;
		};
		
		BiConsumer<Point2d, ArrayList<Point2d>> addToVertexDeltas = (deltaCalculated, deltasVertexList) -> {
			//add to the delta list the new delta
			synchronized(deltasVertexList) {
				deltasVertexList.add(deltaCalculated);
			}
			
		};
		
		TriFunction<Point2d, Double, N, Object> addDeltaForce = (directionNormalized, force, vertex) -> {
			

			//list of deltas
			ArrayList<Point2d> deltasVertexList = getDeltasList.apply(vertex);
			
			
			//calculate new delta
			Point2d deltaCalculated = new Point2d();
			deltaCalculated.set(
					directionNormalized.x*force,
					directionNormalized.y*force
					);
		
			
			addToVertexDeltas.accept(deltaCalculated, deltasVertexList);
			
			return null;
			
		};
		
		BiFunction<Double, Double, Double> forceCalculator = (distance, desiredDistance) -> {

			
			System.out.println();
			
			double deltaDistance = desiredEdgeLength - distance;
			System.out.printf("distance %f, desiredDistance %f, deltaDistance %f ", distance, desiredDistance, deltaDistance);

			return deltaDistance;
		};
		
		
		
		
		
		Function<N, Map<N ,Double>> nodeDistance = node -> {
			
			Map<N, Double> distances = new HashMap<>();
			
			for(N otherNode : graph.getVertices()) {
				distances.put(otherNode, nodes.get(node).distance(nodes.get(otherNode)));
			}
			
			return distances;
		};
		
		
		
		Consumer<N> dynamicProcessNodeRepulsion = n -> {
			
			Map<N ,Double> distances = nodeDistance.apply(n);
			
			for(N otherNode : distances.keySet()) {
				
				double forceDesired = forceCalculator.apply(distances.get(otherNode), desiredVertexSeparation);
				
				//only if the force is for separation
				if(forceDesired != 0) {
					
					
					//direction vector
					Point2d directionNormalized = normalizedVector.apply(nodes.get(otherNode), nodes.get(n) , distances.get(otherNode));
					
					//force adder and calculator
					addDeltaForce.apply(directionNormalized, forceDesired, n);
						
					
				}
				
			}
				
		};
		
		
		
		

		BiConsumer<N, ArrayList<Point2d>> summarize = (vname, deltas) -> {

			if(deltas instanceof Object && deltas.size()>0) {

				System.out.println();
				System.out.println("name "+vname);

				
				BinaryOperator<Point2d> sum = (a,b) -> {
					a.add(b);
					return a;
				};
				
				System.out.println();
				System.out.println(deltas);
				
				Point2d resultPoint = deltas.stream().reduce(sum).get();
				resultPoint.set(resultPoint.x , resultPoint.y );
				
				
				System.out.println("fuerzas "+resultPoint);

				
				resultPoint.scale(stepSize);
				
				System.out.println("after scale "+resultPoint);

				
				resultPoint.add(nodes.get(vname));
				

				
				System.out.println("punto antes "+nodes.get(vname));
				System.out.println("punto ahora "+resultPoint);
				
				nodes.put(vname, resultPoint);
			}
			
		};

		Consumer<E> dynamicProcessEdges = edge -> {
			
			
			Collection<N> incidentVerticesPrev = graph.getIncidentVertices(edge);
			ArrayList<N> incidentVertices = new ArrayList<>(incidentVerticesPrev);
						
			
			//load vertices positions on a list
			ArrayList<Point2d> verticesPositions = new ArrayList<>();
			incidentVertices.forEach(v -> verticesPositions.add(nodes.get(v)));
			
			double distance = verticesPositions.get(0).distance(verticesPositions.get(1));



			//linear force desired (inverted)
			double forceDesired = forceCalculator.apply(distance, desiredEdgeLength);
			

			//direction vector
			Point2d directionNormalized = normalizedVector.apply(verticesPositions.get(1), verticesPositions.get(0), distance);
					
			
			
			for(int i = 0; i<2; i++) {
			
				N vertex = incidentVertices.get(i);
				
				
				//invert the direction for the other vertex
				if(i==1) {
					forceDesired = forceDesired*-1;
				}
				
				
				addDeltaForce.apply(directionNormalized, forceDesired, vertex);
				
				
			};
			newVertexValues.forEach(summarize);

			
		};
		
		graph.getEdges().stream().forEach(dynamicProcessEdges);
		
		//graph.getVertices().stream().forEach(dynamicProcessNodeRepulsion);
		
		newVertexValues.forEach(summarize);
		
		
		
		System.out.println(graph.getEdges().size());
	}

}
