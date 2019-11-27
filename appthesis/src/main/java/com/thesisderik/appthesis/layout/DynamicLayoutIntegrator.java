package com.thesisderik.appthesis.layout;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.vecmath.Point2d;

public class DynamicLayoutIntegrator<N,E> {

	int iterations = 300;
	
	ArrayList<DynamicLayout<N,E>> dinamicLayouts = new ArrayList<>();
	
	ArrayList<Integer> enforceFactors = new ArrayList<>();
	
	Map<N,Point2d> nodes = new HashMap<>();
	
	public void addDynamicLayoutToStack(DynamicLayout<N,E> dl) {
		
		addDynamicLayoutToStack(dl ,1);
		
	}
	
	public void addDynamicLayoutToStack(DynamicLayout<N,E> dl, Integer enforceFactor) {
	
	dinamicLayouts.add(dl);
	enforceFactors.add(enforceFactor);
	
	}

	public Point2d dataForNode(N id) {

		return nodes.get(id);
	}

	public void execute() {
		
		Random r = new Random();
		

		for(DynamicLayout<N,E> dinaLay: dinamicLayouts) {
			for(N node : dinaLay.graph.getVertices()) {
				Point2d initP = new Point2d();
				initP.set(100.0+r.nextDouble()*80.0, 45.0+r.nextDouble()*10.0);
				nodes.put(node, initP);
			}
		}

		for(int k=0; k<iterations; k++) {
			for(int currLay = 0; currLay< dinamicLayouts.size(); currLay++) {
				
				for(int layTimes = 0; layTimes < enforceFactors.get(currLay); layTimes++ ) {
					
					dinamicLayouts.get(currLay).execute( nodes , 1);
					
				}
				
			}
		}
		
	}
	
	
}