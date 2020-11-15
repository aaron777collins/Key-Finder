package com.jau.game;

/**
 * NOT USED in GAME>> ignore this class
 */
public class Light {

	private int x, y;
	private double intensity;
	
	public Light(int x, int y, double intensity) {
		this.setX(x);
		this.setY(y);
		this.setIntensity(intensity);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getIntensity() {
		return intensity;
	}

	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}

}
