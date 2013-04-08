package com.algorithms.stars;

import java.util.LinkedList;
import java.util.List;

public class PixelNode {
	private int intensity, x, y;
	private LinkedList<Edge> edges;
	
	public PixelNode(int intensity, int x, int y) {
		this.intensity = intensity;
		this.x = x;
		this.y = y;
		this.edges = new LinkedList<Edge>();
	}
	
	public int getIntensity() {
		return intensity;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
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
