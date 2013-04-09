package com.algorithms.stars;

import java.util.LinkedList;

public class ConstellationNode extends Node {
	private int x, y;
	private LinkedList<StarNode> stars;
	
	public ConstellationNode(StarNode node) {
		super();
		this.stars = new LinkedList<StarNode>();
		createNodeGraph(node);
		
		// Calculate center by averaging all pixels
		for (StarNode starNode : stars) {
			x += starNode.getX();
			y += starNode.getY();
		}
		x /= stars.size();
		y /= stars.size();
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public LinkedList<StarNode> getStars() {
		return stars;
	}

	private void createNodeGraph(Node node) {
		createNodeGraph(node, null);
	}
	
	private void createNodeGraph(Node node, Edge enteringEdge) {
		stars.add((StarNode)node);
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
