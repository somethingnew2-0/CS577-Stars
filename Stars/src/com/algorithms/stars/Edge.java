package com.algorithms.stars;

public class Edge {		
	private Node first, second;
	private int weight;
	
	public Edge (Node first, Node right, int weight) {
			this.first = first;
			this.second = right;
			this.weight = weight;
	}
	
	public Node getFirst() {
		return first;
	}

	public Node getSecond() {
		return second;
	}

	public int getWeight() {
		return this.weight;
	}
}