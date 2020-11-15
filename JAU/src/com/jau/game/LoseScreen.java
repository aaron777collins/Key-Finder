package com.jau.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * displays lose screen
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class LoseScreen {
	
	public static final int  RESTART_BUTTON_INDEX = 5;
	public static final int  EXIT_BUTTON_INDEX = 6;

	Image image = Game.loader.loadImage("res/images/GameOverScreen.png");
	static CustomButton button1;
	static CustomButton button2;

	public static boolean active = false;
	
	/**
	 * initializes lose screen
	 * @param renderer - Renderer object
	 */
	public LoseScreen(Renderer renderer) {
		
		button1 = new CustomButton("Restart", RESTART_BUTTON_INDEX, 150, 500, 200, 80);
		button2 = new CustomButton("Exit", EXIT_BUTTON_INDEX, 950, 500, 200, 80);

		button1.active = false;
		button2.active = false;

		
	}
	
	/**
	 * draws the lose screen
	 * @param g - Graphics object
	 */
	public void draw(Graphics g) {
		
		if (active) {
			
			Game.entities = new ArrayList<Entity>();

			
			button1.active = true;
			button2.active = true;
			
			g.drawImage(image, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			button1.draw(g);
			button2.draw(g);
			
		} else {
			button1.active = false;
			button2.active = false;
		}
		
	}

	/**
	 * called when restart button is clicked
	 */
	public static void restartClicked() {
		button1.active = false;
		button2.active = false;

		LoseScreen.active = false;
		Game.restartLevel();
	}

	/**
	 * called when exit button is clicked
	 */
	public static void exitClicked() {

		System.exit(JFrame.EXIT_ON_CLOSE);
		
	}

	/**
	 * updates each button based on a mouse event
	 * @param e - Mouse Event
	 */
	public static void updateButtons(MouseEvent e) {
		button1.update(e);
		button2.update(e);		
	}

}
