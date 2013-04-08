package com.algorithms.stars;

public class PixelNode {
	private int intensity, x, y;
	
	public PixelNode(int intensity, int x, int y) {
		this.intensity = intensity;
		this.x = x;
		this.y = y;
	}
	
	public int getIntensity() {
		return this.intensity;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
