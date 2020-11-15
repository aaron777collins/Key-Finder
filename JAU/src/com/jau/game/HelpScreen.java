package com.jau.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Displays the help part of the main menu
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class HelpScreen {

	Image helpBackground;
	
	int width, height;
	
	/**
	 * initializes the help screen
	 */
	public HelpScreen() {
		
		width = 1280;
		height= 720;
		
		helpBackground = Game.loader.loadImage("res/images/CreditScreenBackground.png");
		
	}
	
	/**
	 * draws the help screen
	 * @param g - Graphics object
	 */
	public void draw(Graphics g) {

		   g.drawImage(helpBackground, 0, 0, width, height-22, null);
		   
		   g.setColor(Color.BLACK);
		   g.setFont(Font.decode("arial-BOLD-34"));
		   g.drawString("Objective- Locate the Key and advance through the levels", 1280/2-450, 270);
		   
		   
		   g.setColor(Color.BLACK);
		   g.setFont(Font.decode("arial-BOLD-34"));
		   g.drawString("Move Right- D / RIGHT ARROW", 1280/2-240, 390);
		   
		   g.setFont(Font.decode("arial-BOLD-34"));
		   g.setColor(Color.BLACK);
		   g.drawString("Move Left - A / LEFT ARROW", 1280/2-230, 450);
		   
		   g.setFont(Font.decode("arial-BOLD-34"));
		   g.setColor(Color.BLACK);
		   g.drawString("Punch - SPACE", 1280/2-135, 510);
		   
		   g.setFont(Font.decode("arial-BOLD-34"));
		   g.setColor(Color.BLACK);
		   g.drawString("Jump - W / UP ARROW", 1280/2-180, 330);
		   
		   
		}

}
