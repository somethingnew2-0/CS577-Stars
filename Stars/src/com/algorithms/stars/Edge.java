package com.algorithms.stars;

public class Edge {		
	private Node first, second;
	private int weight;
	
	private Edge parent;
	private int rank;
	
	public Edge (Node first, Node right, int weight) {
			this.first = first;
			this.second = right;
			this.weight = weight;
			this.rank = 0;
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
	
	public Edge getParent() {
		return parent;
	}

	public void setParent(Edge parent) {
		this.parent = parent;
	}

	public int getRank() {
		return rank;
	}
	
	public void incrementRank() {
		rank++;
	}
}