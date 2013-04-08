package com.algorithms.stars;

public class Edge {

	private int average;
	private PixelNode right, left;
	
	public Edge (PixelNode right, PixelNode left) {
			this.right = right;
			this.left = left;
			average = (right.getIntensity() + left.getIntensity())/2;
	}
	
	public int getAverage() {
		return this.average;
	}
		
	
}