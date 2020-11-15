package com.jau.game;

import java.awt.Image;
/**
 * Animation class - a data type to store images with related data and import them
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class Animation {
	
	//making variables
	private String path;

	private int numPics;
	private Image[] images;

	/**
	 * imports images based off base path and the number of pictures. (ex. player0 to player9 is 10 pics)
	 * @param path - base path (String)
	 * @param numPics - number of picture (integer)
	 */
	public Animation(String path, int numPics) {
		this.path = path;
		this.numPics  = numPics;
		
		//initializes image array with number of pictures
		images = new Image[numPics];
		
		for (int i = 0; i<numPics; i++) {
			
			Loader loader = new Loader();
			
			//loads each image based on the path, index and adds .png
			images[i] = loader.loadImage(path + i + ".png");
		}
		
	}
	
	/**
	 * returns the base path of the animation
	 * @return String
	 */
	public String getPath() {
		return path;
	}

	/**
	 * returns the numbers of pictures in the animation
	 * @return Integer
	 */
	public int getNumPics() {
		return numPics;
	}
	
	/**
	 * returns the array of images loaded
	 * @return Image[]
	 */
	public Image[] getImages() {
		return images;
	}

}
