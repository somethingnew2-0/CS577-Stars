package com.algorithms.stars;

public class PixelEdge extends Edge {

	public PixelEdge(PixelNode first, PixelNode second) {
		super(first, second, (first.getIntensity() + second.getIntensity())/2);
	}

}
