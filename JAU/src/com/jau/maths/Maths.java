package com.jau.maths;

public class Maths {

	/**
	 * returns the distance between 2 points
	 * @param x1 - first x pos
	 * @param y1 - first y pos
	 * @param x2 - second x pos
	 * @param y2 - second y pos
	 * @return
	 */
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
	}
	
	/**
	 * return the value between the max and min
	 * @param num - value to be clamped
	 * @param min - min number
	 * @param max - max number
	 * @return
	 */
	public static double clamp(double num, double min, double max) {
		return Math.max(min, Math.min(max, num));
	}

}
