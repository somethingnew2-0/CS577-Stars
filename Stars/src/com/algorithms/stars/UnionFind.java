package com.algorithms.stars;

public class UnionFind {

	// A union find data structure using pointers
	public UnionFind() { }	

	public EdgeNode find(EdgeNode node) {
		if(node.getParent() == null) {
			return node;
		} else {
			// use path compression
			EdgeNode parentNode = find(node);
			node.setParent(parentNode);
			return parentNode;
		}
	}
	
	public void union(EdgeNode first, EdgeNode second) {
		EdgeNode firstRoot = find(first);
		EdgeNode secondRoot = find(second);

		if(firstRoot == secondRoot) {
			return;
		}
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
