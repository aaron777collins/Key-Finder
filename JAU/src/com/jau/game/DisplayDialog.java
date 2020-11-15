package com.jau.game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTextPane;

/**
 * Displays the dialog on the screen
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class DisplayDialog {
	
	//making constants
	public static int NOTHING = 0;
	public static int PLAYER = 1;
	public static int CHARLIE = 2;
	public static int NARRATOR = 3;
	
	//making variables
	private static Image playerImg;
	private static Image charlieImg;
	private static Image narratorImg;
	private static int DBoxState = 0;
	public static String text = "";
	
	private static int startX = 300;
	private static int startY = 540;
	private static int endX = 1150;
	private static int endY = 620;
	private int currentX = 300;
	private int currentY = 520;
	private int counter = 0;
	
	public static boolean active = false;
	
	private static int state = NOTHING;
	
	/**
	 * sets the current text and state (person talking)
	 * @param dh - DisplayDialog holding the text and state
	 */
	public DisplayDialog(DialogHolder dh) {
		
		//sets text and state
		DisplayDialog.text = dh.text;
		DisplayDialog.state = dh.state;
		
		//loads images for dialog box
		playerImg = Game.loader.loadImage("res/images/CharlieDBox.png");
		charlieImg = Game.loader.loadImage("res/images/EvilCharlieDBox.png");
		narratorImg = Game.loader.loadImage("res/images/JoshuaDBox.png");

		
		//increases the counter continuously to increment the x position of where text is being drawn
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				counter++;
			}
			
		}, 0, (long)50);

		
	}
	
	/**
	 * draws the dialog on the screen
	 * @param g - Graphics
	 * @param player - Player object
	 * @param camera - Camera object
	 * @param level - Level object
	 */
	public void draw(Graphics g, Player player, Camera camera, Level level) {
		
		//draws the dialogs
		if (state == PLAYER) {
			g.drawImage(playerImg, 0, Game.HEIGHT / 3 * 2, Game.WIDTH, Game.HEIGHT / 3 - 22, null);
			
			
		} else if (state == CHARLIE) {
		
			g.drawImage(charlieImg, 0, Game.HEIGHT / 3 * 2, Game.WIDTH, Game.HEIGHT / 3 - 22, null);
		
		} else if (state == NARRATOR) {
		
			g.drawImage(narratorImg, 0, Game.HEIGHT / 3 * 2, Game.WIDTH, Game.HEIGHT / 3 - 22, null);
		
		}
		
		//draws the dialog image then text over top
		drawText(g);
		
	}

	/**
	 * draws text in the text box
	 * @param g - Graphics g
	 */
	private void drawText(Graphics g) {
		
		try {
		
		//draws the sentence out letter by letter in Comic Sans MS font with 24 size
		char[] characters = text.toCharArray();
		currentX = startX;
		currentY = startY;
		
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		
		for (int i = 0; i<counter; i++) {
			g.drawString(Character.toString(characters[i]), currentX, currentY);
			currentX +=15;
			if (currentX > endX) {
				currentX = startX;
				currentY += 24;
			}
		}
		
		} catch(Exception e) {
			active = false;
		}
		
	}

	/**
	 * returns the current state (person talking)
	 * @return Integer
	 */
	public int getDBoxState() {
		return DBoxState;
	}

	/**
	 * sets the current state (person talking)
	 * @param dBoxState - the person talking now (integer)
	 */
	public void setDBoxState(int dBoxState) {
		DBoxState = dBoxState;
	}

}
