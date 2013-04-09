package com.algorithms.stars;

public class StarEdge extends Edge {

	public StarEdge(StarNode first, StarNode second) {
		// Make the edge weight the distance^2 between two stars
		super(first, second, 
			((first.getX()-second.getX())*(first.getX()-second.getX())) +
			((first.getY()-second.getY())*(first.getY()-second.getY())));
	}

}
