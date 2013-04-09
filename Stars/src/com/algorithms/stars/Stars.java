package com.algorithms.stars;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import javax.imageio.ImageIO;

public class Stars {
	
	private static final int EDGE_INTENSITY_FALLOFF = 128;

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
		PriorityQueue<Edge> pixelMaxPriorityQueue = new PriorityQueue<Edge>(capacity, new Comparator<Edge>() {
			public int compare(Edge edgeOne, Edge edgeTwo) {
				return edgeTwo.getWeight() - edgeOne.getWeight();
			};
		});
		
		Set<PixelNode> totalPixelNodes = new HashSet<PixelNode>();
		// iterate through all pixels and add unique nodes with edges like so
		// where dots represent pixels and dashes are edges
		// .-.-.-.-.
		// | | | | |
		// .-.-.-.-.
		// | | | | |
		// .-.-.-.-.
		// | | | | |
		// .-.-.-.-.
		PixelNode[] nextColumnRight = new PixelNode[height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				PixelNode currentNode;				
				// Inline getBlue() with & 0xFF mask
				if(x == 0) {
					if(y == 0) {
						currentNode = new PixelNode(starImg, 0, 0);
					}
					else {
						currentNode = nextColumnRight[y];
					}
					if(y < height - 1) {
						nextColumnRight[y+1] = new PixelNode(starImg, x, y + 1);
					}
				} else {
					currentNode = nextColumnRight[y];
				}
				if(x < width - 1) {
					nextColumnRight[y] = new PixelNode(starImg, x + 1, y);

					Edge edge = new PixelEdge(currentNode, nextColumnRight[y]);
					if(edge.getWeight() > EDGE_INTENSITY_FALLOFF) {
						totalPixelNodes.add(currentNode);
						totalPixelNodes.add(nextColumnRight[y]);
						pixelMaxPriorityQueue.add(edge);
					}
				}
				if(y < height - 1) {
					Edge edge = new PixelEdge(currentNode, nextColumnRight[y+1]);
					if(edge.getWeight() > EDGE_INTENSITY_FALLOFF) {
						totalPixelNodes.add(currentNode);
						totalPixelNodes.add(nextColumnRight[y+1]);
						pixelMaxPriorityQueue.add(edge);
					}
				}
			}
		}
		
		while (!pixelMaxPriorityQueue.isEmpty()) {
			Edge edge = pixelMaxPriorityQueue.poll();
			Node first = edge.getFirst();
			Node second = edge.getSecond();
			if(UnionFind.union(first, second)) {
				first.addEdge(edge);
				second.addEdge(edge);
			}
		}
		
		Set<PixelNode> stars = new HashSet<PixelNode>();
		for (PixelNode pixelNode : totalPixelNodes) {
			stars.add((PixelNode)UnionFind.find(pixelNode));
		}
		System.out.println(stars.size());
		
		PriorityQueue<Edge> starMinPriorityQueue = new PriorityQueue<Edge>(capacity, new Comparator<Edge>() {
			public int compare(Edge edgeOne, Edge edgeTwo) {
				return edgeOne.getWeight() - edgeTwo.getWeight();
			};
		});
		
		int broadphaseLegSize = (int)(Math.sqrt(stars.size()));
		int broadphaseCellWidth = starImg.getWidth() / broadphaseLegSize;
		int broadphaseCellHeight = starImg.getHeight() / broadphaseLegSize;
		List<StarNode>[][] broadphaseStars = new LinkedList[broadphaseLegSize][broadphaseLegSize];
		for (PixelNode pixelNodeRoot : stars) {
			StarNode starNode = new StarNode(pixelNodeRoot);
			System.out.println("StarNode: " + starNode.getX() + " " + starNode.getY());
			LinkedList<StarNode> broadphaseCell = (LinkedList<StarNode>) broadphaseStars[starNode.getX()/(broadphaseCellWidth+1)][starNode.getY()/(broadphaseCellHeight+1)];
			if(broadphaseCell == null) {
				broadphaseStars[starNode.getX()/(broadphaseCellWidth+1)][starNode.getY()/(broadphaseCellHeight+1)] = broadphaseCell = new LinkedList<StarNode>();
			} else {
				for (StarNode neighborStarNode : broadphaseCell) {
					starMinPriorityQueue.add(new StarEdge(starNode, neighborStarNode));
				}
			}
			broadphaseCell.add(starNode);
		}
		System.out.println("Done");
	}

}
