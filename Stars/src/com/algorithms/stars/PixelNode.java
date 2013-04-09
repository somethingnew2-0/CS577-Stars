package com.algorithms.stars;

import java.util.LinkedList;
import java.util.List;

public class PixelNode extends Node {
	private int intensity, x, y;
	
	public PixelNode(int intensity, int x, int y) {
		super();
		this.intensity = intensity;
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
