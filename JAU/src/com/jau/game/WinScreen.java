package com.jau.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

/**
 * makes win screen
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class WinScreen {
	
	public static final int  EXIT_BUTTON_INDEX = 7;

	Image image = Game.loader.loadImage("res/images/YouWin.png");
	static CustomButton button1;

	public static boolean active = false;
	
	/**
	 * makes win screen
	 * @param renderer - Renderer object
	 */
	public WinScreen(Renderer renderer) {
		
		button1 = new CustomButton("Exit", EXIT_BUTTON_INDEX, 950, 190, 200, 80);

		button1.active = false;

		
	}
	
	/**
	 * draws win screen on the screen
	 * @param g - Graphics object
	 */
	public void draw(Graphics g) {
		
		if (active) {
			button1.active = true;
			
			g.drawImage(image, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			button1.draw(g);
			
		} else {
			button1.active = false;
		}
		
	}

	/**
	 * called when the exit button is clicked
	 */
	public static void exitClicked() {

		System.exit(JFrame.EXIT_ON_CLOSE);
		
	}

	/**
	 * updates all the buttons on screen
	 * @param e - Mouse Event
	 */
	public static void updateButtons(MouseEvent e) {
		button1.update(e);
	}

}
