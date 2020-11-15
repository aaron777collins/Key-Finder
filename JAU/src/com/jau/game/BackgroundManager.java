package com.jau.game;

import java.awt.Image;

/**
 * Loads all the backgrounds for the game and stores them for later use
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class BackgroundManager {
	
	//loads all images for future use
	public static Image img1 = Game.loader.loadImage("res/images/background1.jpg");
	public static Image final1 = Game.loader.loadImage("res/images/FireBackgroundF1.png");
	public static Image final2 = Game.loader.loadImage("res/images/FireBackgroundF2.png");
	public static Image level1 = Game.loader.loadImage("res/images/Background_1.png");
	public static Image level2 = Game.loader.loadImage("res/images/NightTime.png");
	public static Image level3 = Game.loader.loadImage("res/images/Level 3 WIP Background.png");
	public static Image level4 = Game.loader.loadImage("res/images/backgroundCorrupted.jpg");

	/**
	 * returns the background image at the index of backgrounds specified
	 * @param index - index of the background image array (which level is it)
	 * @return Image[] of background images for the current level
	 */
	public static Image[] getImage(int index) {
		
		if (index == 0) {
			Image[] imgs = {img1, img1};
			return imgs;
		}
		
		if (index == 1) {
			Image[] imgs = {level1, level1};
			return imgs;
		}
		
		if (index == 2) {
			Image[] imgs = {level2, level2};
			return imgs;
		}
		
		if (index == 3) {
			Image[] imgs = {level3, level3};
			return imgs;
		}
		
		if (index == 4) {
			Image[] imgs = {level4, level4};
			return imgs;
		}
		
		if (index == 5) {
			Image[] imgs = {final1, final2};
			return imgs;
		}
		
		Image[] imgs = {img1, img1};
		return imgs;
	}
		

	
}