package com.algorithms.stars;

import java.util.LinkedList;
import java.util.List;

public class Node {
	private LinkedList<Edge> edges;
	private Node parent;
	private int rank;
	
	public Node() {
		this.edges = new LinkedList<Edge>();
		this.rank = 0;
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
	
	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getRank() {
		return rank;
	}
	
	public void incrementRank() {
		rank++;
	}
	
	protected void createNodeGraph(Node node) {
		createNodeGraph(node, null);
	}
	
	protected void createNodeGraph(Node node, Edge enteringEdge) {
		for (Edge edge : node.getEdges()) {
			if(edge == enteringEdge) {
				continue;
			}
			if(edge.getFirst() == node) {
				createNodeGraph(edge.getSecond(), edge);
			} else {
				createNodeGraph(edge.getFirst(), edge);
			}
		}
		
	}
}
