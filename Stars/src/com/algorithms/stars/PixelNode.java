package com.algorithms.stars;

import java.awt.image.BufferedImage;

public class PixelNode extends Node {
	private int intensity, x, y;
	
	public PixelNode(BufferedImage image, int x, int y) {
		super();
		this.intensity = image.getRGB(x, y) & 0xFF;
		this.x = x;
		this.y = y;
	}
	
	public int getIntensity() {
		return intensity;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
