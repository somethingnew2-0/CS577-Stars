package com.algorithms.stars;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
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
	private static final int N = 100000;
	private static final float CONSTELLATION_CONSTANT = 1.9f;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File inFile = new File("stars.jpg");
		BufferedImage starImg = null, constellationImg = null;
		try {
			starImg = ImageIO.read(inFile);
		} catch (IOException exc) {
			System.out.println("Could not access input file.");
			System.exit(0);
		}
		
		 ColorModel cm = starImg.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = starImg.copyData(null);
		 constellationImg = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		
		//int [] pixels = starImg.getRGB(0, 0, starImg.getWidth(), starImg.getHeight(), null, 0, starImg.getWidth());
		int width = starImg.getWidth();
		int height = starImg.getHeight();
		
		int capacity = width * height; // initial capacity set to total # pixels
		
		// make a priority queue to hold edges with custom comparator
		PriorityQueue<Edge> edgeMaxPriorityQueue = new PriorityQueue<Edge>(capacity, new Comparator<Edge>() {
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
						edgeMaxPriorityQueue.add(edge);
					}
				}
				if(y < height - 1) {
					Edge edge = new PixelEdge(currentNode, nextColumnRight[y+1]);
					if(edge.getWeight() > EDGE_INTENSITY_FALLOFF) {
						totalPixelNodes.add(currentNode);
						totalPixelNodes.add(nextColumnRight[y+1]);
						edgeMaxPriorityQueue.add(edge);
					}
				}
			}
		}
		
		List<PixelNode> usedNodes = new LinkedList<PixelNode>();
		// Run Kruskal's algorithm for the Max-Spanning Tree
		for (int i = 0; i < N && !edgeMaxPriorityQueue.isEmpty(); i++) {
			Edge edge = edgeMaxPriorityQueue.poll();
			Node first = edge.getFirst();
			Node second = edge.getSecond();
			if(UnionFind.union(first, second)) {
				first.addEdge(edge);
				second.addEdge(edge);
			}
			usedNodes.add((PixelNode)first);
		}
		
		Set<PixelNode> stars = new HashSet<PixelNode>();
		for (PixelNode pixelNode : usedNodes) {
			stars.add((PixelNode)UnionFind.find(pixelNode));
		}
		System.out.println(stars.size());
		
		PriorityQueue<Edge> starMinPriorityQueue = new PriorityQueue<Edge>(capacity, new Comparator<Edge>() {
			public int compare(Edge edgeOne, Edge edgeTwo) {
				return edgeOne.getWeight() - edgeTwo.getWeight();
			};
		});
		
		List<StarNode> totalStarNodes = new LinkedList<StarNode>();
		
		Graphics2D g2d = starImg.createGraphics();
	    BasicStroke bs = new BasicStroke(2);
	    g2d.setStroke(bs);
	    g2d.setColor(Color.RED);
		
		int broadphaseLegSize = (int)(Math.sqrt(stars.size() * CONSTELLATION_CONSTANT));
		int broadphaseCellWidth = starImg.getWidth() / broadphaseLegSize;
		int broadphaseCellHeight = starImg.getHeight() / broadphaseLegSize;
		List<StarNode>[][] broadphaseStars = new LinkedList[broadphaseLegSize][broadphaseLegSize];
		for (PixelNode pixelNodeRoot : stars) {
			StarNode starNode = new StarNode(pixelNodeRoot);
			
			g2d.drawOval(starNode.getX() - (starNode.getApproximateSize() / 2), starNode.getY() - (starNode.getApproximateSize() / 2), starNode.getApproximateSize(), starNode.getApproximateSize());
			
			totalStarNodes.add(starNode);
//			System.out.println("StarNode: " + starNode.getX() + " " + starNode.getY());
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
		
		try {
		    // retrieve image
		    File outputfile = new File("second-stars-"+N+".jpg");
		    ImageIO.write(starImg, "jpg", outputfile);
		} catch (IOException e) {
			System.out.println("Could not write output file.");
			System.exit(0);
		}
		
		
		// Add edges between stars of neighboring broadphase star cells
		for (int x = 0; x < broadphaseLegSize - 1; x++) {
			for (int y = 0; y < broadphaseLegSize - 1; y++) {
				List<StarNode> broadphaseStarCell = broadphaseStars[x][y];
				if(broadphaseStarCell != null) {
					List<StarNode> neighborStarCell = broadphaseStars[x+1][y];
					if(neighborStarCell != null) {
						for (StarNode starNode : broadphaseStarCell) {
							for (StarNode neighborStarNode : neighborStarCell) {
								starMinPriorityQueue.add(new StarEdge(starNode, neighborStarNode));	
							}							
						}
					}
					neighborStarCell = broadphaseStars[x][y+1];
					if(neighborStarCell != null) {
						for (StarNode starNode : broadphaseStarCell) {
							for (StarNode neighborStarNode : neighborStarCell) {
								starMinPriorityQueue.add(new StarEdge(starNode, neighborStarNode));	
							}							
						}
					}
				}
			}
			
		}
		
		// Run Kruskal's algorithm for the Min-Spanning Tree
		while (!starMinPriorityQueue.isEmpty()) {
			Edge edge = starMinPriorityQueue.poll();
			Node first = edge.getFirst();
			Node second = edge.getSecond();
			if(UnionFind.union(first, second)) {
				first.addEdge(edge);
				second.addEdge(edge);
			}
		}	
		
		Set<StarNode> constellations = new HashSet<StarNode>();
		for (StarNode starNode : totalStarNodes) {
			constellations.add((StarNode)UnionFind.find(starNode));
		}
		
		g2d = constellationImg.createGraphics();
        bs = new BasicStroke(2);
        g2d.setStroke(bs);
        g2d.setColor(Color.RED);
        
		List<ConstellationNode> totalConstellationNodes = new LinkedList<ConstellationNode>();
		for (StarNode starNodeRoot : constellations) {
			ConstellationNode constellationNode = new ConstellationNode(starNodeRoot);
			System.out.println("ConstellationNode: " + constellationNode.getX() + " " + constellationNode.getY());
			for (StarNode starNode : constellationNode.getStars()) {
				System.out.println("     StarNode: " + starNode.getX() + " " + starNode.getY());
				for (Edge starEdge : starNode.getEdges()) {
					StarNode firstStar = (StarNode) starEdge.getFirst();
					StarNode secondStar = (StarNode) starEdge.getSecond();
					g2d.drawLine(firstStar.getX(), firstStar.getY(), secondStar.getX(), secondStar.getY());	
				}
			}
			totalConstellationNodes.add(constellationNode);
		}
		
		System.out.println(constellations.size());
		
		try {
		    // retrieve image
		    File outputfile = new File("constellation.jpg");
		    ImageIO.write(constellationImg, "jpg", outputfile);
		} catch (IOException e) {
			System.out.println("Could not write output file.");
			System.exit(0);
		}
	}	
}
