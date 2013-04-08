package com.algorithms.stars;

public class Edge {		
	private PixelNode first, second;
	private int averageIntensity;
	
	private Edge parent;
	private int rank;
	
	public Edge (PixelNode right, PixelNode left) {
			this.first = right;
			this.second = left;
			this.averageIntensity = (right.getIntensity() + left.getIntensity())/2;
			this.rank = 0;
	}
	
	public int getAverageIntensity() {
		return this.averageIntensity;
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