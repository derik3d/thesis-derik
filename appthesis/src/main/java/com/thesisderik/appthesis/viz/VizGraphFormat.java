package com.thesisderik.appthesis.viz;

import java.util.ArrayList;

public class VizGraphFormat {

	private ArrayList<NodeViz> nodes = new ArrayList<>();
	
	private ArrayList<EdgeViz> edges = new ArrayList<>();
	

	public void addNode(NodeViz nv) {
		nodes.add(nv);
	}
	
	public void addEdge(EdgeViz ev) {
		edges.add(ev);
	}

	public ArrayList<NodeViz> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<NodeViz> nodes) {
		this.nodes = nodes;
	}

	public ArrayList<EdgeViz> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<EdgeViz> edges) {
		this.edges = edges;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edges == null) ? 0 : edges.hashCode());
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VizGraphFormat other = (VizGraphFormat) obj;
		if (edges == null) {
			if (other.edges != null)
				return false;
		} else if (!edges.equals(other.edges))
			return false;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else if (!nodes.equals(other.nodes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VizGraphFormat [nodes=" + nodes + ", edges=" + edges + "]";
	}
	
	
}
