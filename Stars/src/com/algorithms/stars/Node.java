package com.algorithms.stars;

import java.util.LinkedList;
import java.util.List;

public class Node {
	private LinkedList<Edge> edges;
	
	public Node() {
		this.edges = new LinkedList<Edge>();
	}
	
	public void addEdge(Edge edge) {
		edges.add(edge);
	}
	
	public Edge getEdge() {
		return edges.peek();
	}
	
	public List<Edge> getEdges() {
		return edges;
	}
	
}
