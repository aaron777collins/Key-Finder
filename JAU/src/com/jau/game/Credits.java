package com.jau.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Credits is the credits part of the menu
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class Credits  {

	//creating variables
	Image creditsBackground;
	
	int width, height;

	/**
	 * initializes all variables and loads background
	 */
	public Credits() {
		
		//initializing variables
		width = 1280;
		height= 720;
		
		//loading credits background
		creditsBackground = Game.loader.loadImage("res/images/CreditScreenBackground.png");
		
	}
	
	/**
	 * draws the images, text, etc.
	 * @param g - Graphics object for drawing on screen
	 */
	public void draw(Graphics g) {

		   g.drawImage(creditsBackground, 0, 0, width, height-22, null);
		   

		   g.setColor(Color.BLACK);
		   g.setFont(Font.decode("arial-BOLD-36"));
		   g.drawString("Aaron- Game loop,entity logic", 1280/2-300, 300);
		   
		   g.setFont(Font.decode("arial-BOLD-31"));
		   g.setColor(Color.BLACK);
		   g.drawString("Josh - Entity animation and creation, background graphics,dialog and story", 1280/2-600, 400);
		   
		   g.setFont(Font.decode("arial-BOLD-36"));
		   g.setColor(Color.BLACK);
		   g.drawString("Uday - Main Menu loop, Credit loop", 1280/2-340, 500);
		   
		   
		}
	
	
	}


