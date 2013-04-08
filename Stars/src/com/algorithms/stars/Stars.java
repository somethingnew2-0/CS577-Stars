package com.algorithms.stars;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

public class Stars {
	
	private static final int EDGE_INTENSITY_FALLOFF = 64;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File inFile = new File("stars.jpg");
		BufferedImage starImg = null;
		try {
			starImg = ImageIO.read(inFile);
		} catch (IOException exc) {
			System.out.println("Could not access input file.");
			System.exit(0);
		}
		
		//int [] pixels = starImg.getRGB(0, 0, starImg.getWidth(), starImg.getHeight(), null, 0, starImg.getWidth());
		int width = starImg.getWidth();
		int height = starImg.getHeight();
		
		int capacity = width * height; // initial capacity set to total # pixels
		
		// make a priority queue to hold edges with custom comparator
		PriorityQueue<Edge> queue = new PriorityQueue<Edge>(capacity, new Comparator<Edge>() {
			public int compare(Edge edgeOne, Edge edgeTwo) {
				return edgeTwo.getAverageIntensity() - edgeOne.getAverageIntensity();
			};
		});
		
		Set<PixelNode> totalPixelNodes = new HashSet<PixelNode>();
		// iterate through all pixels and add unique nodes with edges
		PixelNode[] nextColumnRight = new PixelNode[height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				PixelNode currentNode;				
				// Inline getBlue() with & 0xFF mask
				if(x == 0) {
					if(y == 0) {
						currentNode = new PixelNode(starImg.getRGB(0, 0) & 0xFF, 0, 0);
					}
					else {
						currentNode = nextColumnRight[y];
					}
					if(y < height - 1) {
						nextColumnRight[y+1] = new PixelNode(starImg.getRGB(x, 1) & 0xFF, x, y + 1);
					}
				} else {
					currentNode = nextColumnRight[y];
				}
				if(x < width - 1) {
					nextColumnRight[y] = new PixelNode(starImg.getRGB(x + 1, y) & 0xFF, x + 1, y);

					Edge edge = new Edge(currentNode, nextColumnRight[y]);
					if(edge.getAverageIntensity() > EDGE_INTENSITY_FALLOFF) {
						totalPixelNodes.add(currentNode);
						totalPixelNodes.add(nextColumnRight[y]);
						queue.add(edge);
					}
				}
				if(y < height - 1) {
					Edge edge = new Edge(currentNode, nextColumnRight[y+1]);
					if(edge.getAverageIntensity() > EDGE_INTENSITY_FALLOFF) {
						totalPixelNodes.add(currentNode);
						totalPixelNodes.add(nextColumnRight[y+1]);
						queue.add(edge);
					}
				}
			}
		}
		
		while (!queue.isEmpty()) {
			Edge edge = queue.poll();
			System.out.println(edge.getAverageIntensity());
			PixelNode firstPixel = edge.getFirst();
			PixelNode secondPixel = edge.getSecond();
			Edge firstPixelEdge = firstPixel.getEdge();
			Edge secondPixelEdge = secondPixel.getEdge();
			if(firstPixelEdge == null || secondPixelEdge == null) {
				// Second pixel hasn't been added to a tree
				if(firstPixelEdge != null) {
					Edge firstRoot = UnionFind.find(firstPixelEdge);
					UnionFind.union(firstRoot, edge);
				}
				// First pixel hasn't been added to a tree
				if(secondPixelEdge != null) {
					Edge secondRoot = UnionFind.find(secondPixelEdge);
					UnionFind.union(secondRoot, edge);
				}
				firstPixel.addEdge(edge);
				secondPixel.addEdge(edge);
			} else {
				Edge firstRoot = UnionFind.find(firstPixelEdge);
				Edge secondRoot = UnionFind.find(secondPixelEdge);
				if(firstRoot != secondRoot) {
					UnionFind.union(firstRoot, edge);
					UnionFind.union(firstRoot, secondRoot);
					firstPixel.addEdge(edge);
					secondPixel.addEdge(edge);
				}
			}
		}
		
		totalPixelNodes = new TreeSet<PixelNode>(totalPixelNodes);
		Set<Edge> stars = new HashSet<Edge>();
		for (PixelNode pixelNode : totalPixelNodes) {
			stars.add(UnionFind.find(pixelNode.getEdge()));
		}
		
		System.out.println(stars.size());
	}

}
