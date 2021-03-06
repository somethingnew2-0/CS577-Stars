package com.algorithms.stars;

public class UnionFind {

	// A union find data structure methods using pointers
	public static Node find(Node node) {
		if(node.getParent() == null) {
			return node;
		} else {
			// use path compression
			Node parentNode = find(node.getParent());
			node.setParent(parentNode);
			return parentNode;
		}
	}
	
	public static boolean union(Node first, Node second) {     
        Node firstRoot = find(first);
        Node secondRoot = find(second);
		if(firstRoot == secondRoot) {
			return false;
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
		return true;
	}
}
