package com.jau.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

/**
 * shows the main menu
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class MainMenu {
	
	
	public static final int START_BUTTON_INDEX = 0;
	public static final int CREDITS_BUTTON_INDEX = 1;
	public static final int HELP_BUTTON_INDEX = 2;
	public static final int EXIT_BUTTON_INDEX = 3;
	public static final int BACK_BUTTON_INDEX = 4;
	
	public static final int MENU_MODE = 0;
	public static final int CREDITS_MODE = 1;
	public static final int HELP_MODE = 2;
	
	int WIDTH, HEIGHT;
	
	boolean started = false;
	
	Image background;
	
	public static CustomButton startButton = new CustomButton("Start", START_BUTTON_INDEX, Game.WIDTH/2 - 450, Game.HEIGHT/2 - 120, 200, 80);
	public static CustomButton creditsButton = new CustomButton("Credits", CREDITS_BUTTON_INDEX, Game.WIDTH/2 - 450, Game.HEIGHT/2 + 0, 200, 80);
	public static CustomButton helpButton = new CustomButton("Help",  HELP_BUTTON_INDEX, Game.WIDTH/2 + 225,Game.HEIGHT/2 -120, 200, 80);
	public static CustomButton exitButton = new CustomButton("Exit", EXIT_BUTTON_INDEX, Game.WIDTH/2 + 225, Game.HEIGHT/2 -0, 200, 80);	
	public static CustomButton backButton = new CustomButton("Back", BACK_BUTTON_INDEX, 100, 100, 200, 80);
	
	public static int mode = MENU_MODE;
	
	public Credits credits = new Credits();
	public HelpScreen helpScreen = new HelpScreen();
	
	/**
	 * Makes the main menu
	 * @param WIDTH - width of the screen
	 * @param HEIGHT - height of the screen
	 */
	public MainMenu(int WIDTH, int HEIGHT){
		loadImages();
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		
		startButton.active = true;
		creditsButton.active = true;
		helpButton.active = true;
		exitButton.active = true;
		backButton.active = false;


		}	
	
	
	/**
	 * loads all the images required
	 */
	private void loadImages() {
		
		background = Game.loader.loadImage("res/images/Title.png");
		
		
	}
	
	
	/**
	 * draws the main menu
	 * @param g - Graphics object
	 */
	public void draw(Graphics g) 
	{	
		
		if (mode == MENU_MODE) {
			g.drawImage(background, 0, 0, WIDTH, HEIGHT-22, null);
			
		} else if(mode == CREDITS_MODE) {
			credits.draw(g);
		} else if(mode == HELP_MODE) {
			helpScreen.draw(g);
		}
		
		startButton.draw(g);
		helpButton.draw(g);
		backButton.draw(g);
		creditsButton.draw(g);
		exitButton.draw(g);

		
	}


	/**
	 * called when credits button is clicked
	 */
	public static void creditsButtonClicked() {
		
		creditsButton.active = false;
		helpButton.active = false;
		exitButton.active = false;
		startButton.active = false;
		
		backButton.active = true;
		
		mode = CREDITS_MODE;
		
		
	}


	/**
	 * called when exit button is clicked
	 */
	public static void exitButtonClicked() {
		System.exit(JFrame.EXIT_ON_CLOSE);
		
	}


	/**
	 * called when help button is clicked
	 */
	public static void helpButtonClicked() {
		creditsButton.active = false;
		helpButton.active = false;
		exitButton.active = false;
		startButton.active = false;
			
		backButton.active = true;
		
		mode = HELP_MODE;
		
	}
	
	/**
	 * called when start button is clicked
	 */
	public static void startButtonClicked() {

		Game.gameState = Game.GAME;

		creditsButton.active = false;
		helpButton.active = false;
		exitButton.active = false;
		startButton.active = false;
		backButton.active = false;
		
		Timer override = new Timer();
		override.schedule(new TimerTask() {

			int counter = 0;
			
			@Override
			public void run() {

				Game.player.setX(Game.currentLevel.spawnPoint.x);
				Game.player.setY(Game.currentLevel.spawnPoint.y);
				Game.camera.setXOff(Game.currentLevel.spawnPoint.x);
				Game.camera.setYOff(Game.currentLevel.spawnPoint.y);

				
				if (counter>3) {
					override.cancel();
				}
				counter++;
			}
			
		}, 0, 10);
		
	}

	/**
	 * called when back button is clicked
	 */
	public static void backButtonClicked() {
		creditsButton.active = true;
		helpButton.active = true;
		exitButton.active = true;
		startButton.active = true;
		backButton.active = false;

		
		mode = MENU_MODE;
		
	}


	/**
	 * updates all buttons based on mouse event
	 * @param e - Mouse Event
	 */
	public static void updateButtons(MouseEvent e) {

		creditsButton.update(e);
		helpButton.update(e);
		exitButton.update(e);
		startButton.update(e);
		backButton.update(e);

		
	}
	
	
	
}
