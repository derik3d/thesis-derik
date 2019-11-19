package com.thesisderik.appthesis.layout;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DynamicLayoutIntegrator<N,E> {

	int iterations = 3;
	
	ArrayList<DynamicLayout<N,E>> dinamicLayouts = new ArrayList<>();
	
	ArrayList<Integer> enforceFactors = new ArrayList<>();
	
	Map<N,Point2D> nodes = new HashMap<>();
	
	public void addDynamicLayoutToStack(DynamicLayout<N,E> dl) {
		
		addDynamicLayoutToStack(dl ,1);
		
	}
	
	public void addDynamicLayoutToStack(DynamicLayout<N,E> dl, Integer enforceFactor) {
	
	dinamicLayouts.add(dl);
	enforceFactors.add(enforceFactor);
	
	}

	public Point2D dataForNode(N id) {

		return nodes.get(id);
	}

	public void execute() {
		
		Random r = new Random();
		

		for(DynamicLayout<N,E> dinaLay: dinamicLayouts) {
			for(N node : dinaLay.graph.getVertices()) {
				Point2D initP = new Point2D.Double();
				initP.setLocation(r.nextDouble()*100.0, r.nextDouble()*100.0);
				nodes.put(node, initP);
			}
		}

		for(int currLay = 0; currLay< dinamicLayouts.size(); currLay++) {
			
			for(int layTimes = 0; layTimes < enforceFactors.get(currLay)*iterations; layTimes++ ) {
				
				dinamicLayouts.get(currLay).execute( nodes );
				
			}
			
		}
		
	}
	
	
}