package com.algorithms.stars;

import java.util.LinkedList;

public class StarNode extends Node {
	private int x, y;
	private LinkedList<PixelNode> pixels;
	
	public StarNode(PixelNode node) {
		super();
		this.pixels = new LinkedList<PixelNode>();
		createNodeGraph(node);
		
		// Calculate center by averaging all pixels
		for (PixelNode pixelNode : pixels) {
			x += pixelNode.getX();
			y += pixelNode.getY();
		}
		x /= pixels.size();
		y /= pixels.size();
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	private void createNodeGraph(Node node) {
		createNodeGraph(node, null);
	}
	
	private void createNodeGraph(Node node, Edge enteringEdge) {
		pixels.add((PixelNode)node);
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
