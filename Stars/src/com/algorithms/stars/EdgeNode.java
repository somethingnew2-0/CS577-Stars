package com.algorithms.stars;

public class EdgeNode {
	private Edge edge;
	private EdgeNode parent;
	private int rank;
	
	public EdgeNode(Edge edge) {
		this.edge = edge;
		this.rank = 0;
	}

	public EdgeNode getParent() {
		return parent;
	}

	public void setParent(EdgeNode parent) {
		this.parent = parent;
	}

	public Edge getEdge() {
		return edge;
	}

	public int getRank() {
		return rank;
	}
	
	public void incrementRank() {
		rank++;
	}


}
