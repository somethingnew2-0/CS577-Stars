package com.algorithms.stars;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

import javax.imageio.ImageIO;

public class Stars {

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
				return edgeOne.getAverage() - edgeTwo.getAverage();
			};
		});
		
		// TODO: iterate through all pixels and do operations
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				
				Color color = new Color(starImg.getRGB(i,j));
				int r = color.getRed();
				PixelNode node = new PixelNode(r);
				color = new Color(starImg.getRGB(i + 1, j));
				r = color.getRed();
				PixelNode neighbor = new PixelNode(r);
				Edge edge = new Edge(node, neighbor);
				
				
			}
		}
		
	}

}
