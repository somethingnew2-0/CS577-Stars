package com.algorithms.stars;

public class UnionFind {

	// A union find data structure methods using pointers
	public static Edge find(Edge edge) {
		if(edge.getParent() == null) {
			return edge;
		} else {
			// use path compression
			Edge parentEdge = find(edge);
			if(edge != parentEdge) {
				edge.setParent(parentEdge);
			}
			return parentEdge;
		}
	}
	
	public static void union(Edge firstRoot, Edge secondRoot) {
        // first and second are not already in same set. Merge them.
		if(firstRoot.getRank() < secondRoot.getRank()) {
			firstRoot.setParent(secondRoot);
		} else if(firstRoot.getRank() > secondRoot.getRank()) {
			secondRoot.setParent(firstRoot);
		} else {
			secondRoot.setParent(firstRoot);
			firstRoot.incrementRank();
		}
	}
}
