package com.thesisderik.appthesis.layout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

	
	boolean debugging = false;
	
	@FunctionalInterface
	interface TriFunction<A,B,C,R> {

	    R apply(A a, B b, C c);

	    default <V> TriFunction<A, B, C, V> andThen(
	                                Function<? super R, ? extends V> after) {
	        return (A a, B b, C c) -> after.apply(apply(a, b, c));
	    }
	}


	Point2d concentricCenter = new Point2d(50,50);
	N concentricNode = null;
	
	double targetEdgeLength = 50;
	double edgeLengthMultiplier = 1;
	
	double desiredVertexSeparation = 10;
	double repellingMultiplier = 1; // 0 0.01 1 10
	
	double concentricDistance = 10;
	double concentricMultiplier =0; //0 1 10 100 500 1000
	
	double stepSize = 0.05;
	
	//disable force calculations on
	public Set<N> ignoreForceCalcualtionsNodes = null;
	
	
	public Set<N> disabledForceNodesForEdges = null;
	public Set<N> disabledForceNodesForRepelling = null;
	
	//their forces dont affect other nodes
	public Set<N> draggedNodesForEdges = null;
	public Set<N> draggedNodesForRepelling = null;
	
	public Set<N> customRepellingSeparation = null;
			
	public DynamicSpring(Graph<N, E> graph) {
		super(graph);
	}
	
	double annealingReduction = 1;
	
	@Override
	public void execute(final Map<N, Point2d> nodes, double annealingReduction){
		
		Long lStartTime  = new Date().getTime();
		
		this.annealingReduction = annealingReduction;
		
		if(debugging)System.out.println();
		if(debugging)System.out.println();
		if(debugging)System.out.println(nodes);
		
		//System.out.println(graph.getVertices().size());
		
		
		final Map<N, ArrayList<Point2d>> summaryOfForces = new HashMap<>();
		ArrayList<Double> error = new ArrayList<>();
		
		
		
		
		Function<N, Map<N ,Double>> nodeDistance = node -> {
			
			Map<N, Double> distances = new HashMap<>();
			
			for(N otherNode : graph.getVertices()) {
				if(!node.equals(otherNode))
				distances.put(otherNode, getDistance(nodes.get(node),nodes.get(otherNode)));
			}
			
			return distances;
		};
		
		
		//NODES NODES
		Consumer<N> dynamicProcessNodeRepulsion = n -> {
			

			if(ignoreForceCalcualtionsNodes instanceof Object && ignoreForceCalcualtionsNodes.contains(n))
				return;
			
			//save distances to all the other nodes
			Map<N ,Double> distances = nodeDistance.apply(n);
			
			for(N otherNode : distances.keySet()) {
				
				
				double distanceToOtherNode = distances.get(otherNode);
				
				double forceDesiredRepelling = forceCalculatorRepelling(distanceToOtherNode , desiredVertexSeparation);
				
				Point2d directionNormalized = normalizeVector(nodes.get(otherNode), nodes.get(n) , distanceToOtherNode);

				
				//only if the force is repelling
				if(forceDesiredRepelling > 0) {
					
					
					//direction vector
					
					//force adder and calculator
					calculateAddAForce(directionNormalized, forceDesiredRepelling, n, summaryOfForces);
						
					
				}
				
				if(graph.findEdge(n, otherNode) instanceof Object || graph.findEdge(otherNode, n) instanceof Object ) {
					
					double forceDesiredSpring = forceCalculatorSpring(distanceToOtherNode , desiredVertexSeparation);
					

					//only atraction force
					if(forceDesiredSpring < 0) {
						
						calculateAddAForce(directionNormalized, forceDesiredSpring, n, summaryOfForces);
						
					}
					
				}
				
				
				if(concentricCenter instanceof Object) {
					
					double distanceConcentric = getDistance(nodes.get(n),concentricCenter);
						
					double forceDesiredConcentric = forceCalculatorConcentric( distanceConcentric , concentricDistance);
					

					//save error
					error.add(Math.abs(concentricDistance - distanceConcentric));
					
					
					if(forceDesiredConcentric != 0) {
						
						
						//direction vector
						Point2d directionNormalizedConcentric = normalizeVector(concentricCenter, nodes.get(n) ,distanceConcentric);
						
						//force adder and calculator
						calculateAddAForce(directionNormalizedConcentric, forceDesiredConcentric, n, summaryOfForces);
								
							
					}
					
				}
				
				
			}
			
				
		};
		
		
		
		

		
		//graph.getEdges().stream().forEach(dynamicProcessEdges);
		
		graph.getVertices().stream().forEach(dynamicProcessNodeRepulsion);
		
		//graph.getVertices().stream().forEach(dynamicProcessHoleRepulsion);
				
		
		
		
		
		for(Map.Entry<N, ArrayList<Point2d>> entry: summaryOfForces.entrySet())
			integrateForces(entry.getKey(), entry.getValue(), nodes);
		
		
		
		
		
		if(debugging)System.out.println(graph.getEdges().size());
		
		double sumError = error.stream().reduce(0.0, (a,b) -> a+b).doubleValue();

		if(debugging)System.out.println("error: " + (sumError/error.size()));
		
		long lEndTime = new Date().getTime();
		
		long difference = lEndTime - lStartTime ;
		
		if(debugging)System.out.println("duration: " + difference);


	}

	

	private Double getDistance(Point2d p0, Point2d p1) {
		return p0.distance(p1);
	}



	ArrayList<Point2d> getForcesList(N vertexName, Map<N, ArrayList<Point2d>> summaryOfForces ){
		
		ArrayList<Point2d> deltasVertexList;
		
		//get or create the list
		synchronized(summaryOfForces) {
			if(!((deltasVertexList = summaryOfForces.get(vertexName)) instanceof Object)) {
				deltasVertexList = new ArrayList<>();
				summaryOfForces.put(vertexName, deltasVertexList);
			}
		}
		
		return deltasVertexList;
	}
	
	
	Point2d normalizeVector(Point2d p0,Point2d p1, double distance){
		
		Point2d res = new Point2d();
		
		res.add(p1);
		res.sub(p0);
		res.scale(1/distance);
		
		return res;
	}
	

	
	 void addToVertexForces(Point2d forceCalculated, ArrayList<Point2d> forcesVertexList){
		//add to the delta list the new delta
		synchronized(forcesVertexList) {
			forcesVertexList.add(forceCalculated);
		}
		
	}
	 

		

	void calculateAddAForce(Point2d directionNormalized,double force,N vertex, Map<N,ArrayList<Point2d>> summaryOfForces){
		
	
		//list of forces
		ArrayList<Point2d> deltasVertexList = getForcesList(vertex, summaryOfForces);
	
	
		//calculate new force
		Point2d deltaCalculated = new Point2d();
		deltaCalculated.set(
				directionNormalized.x*force,
				directionNormalized.y*force
				);
	
		
		addToVertexForces(deltaCalculated, deltasVertexList);
				
	}
	 

	
	double forceCalculatorSpring(double distance, double desiredDistance){

		
		if(debugging)System.out.println();
		
		double deltaDistance = targetEdgeLength - distance;
		if(debugging)System.out.printf("distance %f, desiredDistance %f, deltaDistance %f ", distance, desiredDistance, deltaDistance);

		return deltaDistance*edgeLengthMultiplier*annealingReduction;
	}
	 

	
	double forceCalculatorRepelling(double distance, double desiredDistance){

		
		if(debugging)System.out.println();
		
		double deltaDistance = desiredDistance / distance * distance;
		if(debugging)System.out.printf("distance %f, desiredDistance %f, deltaDistance %f ", distance, desiredDistance, deltaDistance);

		return deltaDistance*repellingMultiplier*annealingReduction;
	}
	

	 

	
	double forceCalculatorConcentric(double distance, double desiredDistance){

		
		if(debugging)System.out.println();
		
		double deltaDistance = targetEdgeLength - distance;
		if(debugging)System.out.printf("distance %f, desiredDistance %f, deltaDistance %f ", distance, desiredDistance, deltaDistance);

		return deltaDistance*concentricMultiplier*annealingReduction;
	}


	void integrateForces(N vname, ArrayList<Point2d> forces, Map<N, Point2d> nodes){

		if(forces instanceof Object && forces.size()>0) {

			if(debugging)System.out.println();
			if(debugging)System.out.println();
			if(debugging)System.out.println();
			if(debugging)System.out.println("name "+vname);

			
			BinaryOperator<Point2d> sum = (a,b) -> {
				a.add(b);
				return a;
			};
			
			if(debugging)System.out.println();
			if(debugging)System.out.println(forces);
			
			Point2d resultPoint = forces.stream().reduce(sum).get();
			resultPoint.set(resultPoint.x , resultPoint.y );
			
			
			if(debugging)System.out.println("fuerzas "+resultPoint);

			
			resultPoint.scale(stepSize);
			resultPoint.scale(annealingReduction);
			
			
			if(debugging)System.out.println("after scale "+resultPoint);

			
			resultPoint.add(nodes.get(vname));
			

			
			if(debugging)System.out.println("punto antes "+nodes.get(vname));
			if(debugging)System.out.println("punto ahora "+resultPoint);
			if(debugging)System.out.println();
			if(debugging)System.out.println();
			
			
			nodes.put(vname, resultPoint);
		}
		
	}

	
	
	
}
