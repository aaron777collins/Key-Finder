package com.jau.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

/**
 * manages displaying the player's health
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class HeartManager {

	int x, y = 0;
	Image fullHeart = Game.loader.loadImage("res/images/fullHeart.png");
	Image halfHeart = Game.loader.loadImage("res/images/halfHeart.png");

	/**
	 * makes heart manager
	 * @param x - x position on screen to display 
	 * @param y - y position on screen to display
	 */
	public HeartManager(int x, int y) {
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * draws the hearts on screen relative to its position
	 * @param game - Game object
	 * @param g - Graphics object
	 */
	public void draw(Game game, Graphics g) {
		
		int health = game.getPlayer().getHealth();
		
		int numFullHearts = (int)(health/2);
		
		g.setColor(new Color(0, 0, 0, 100));
		g.fillRect(x, y, 210, 100);
		
		g.setColor(Color.white);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		g.drawString("Health", x + 65, y+ 24);
		
		for (int i = 0; i<numFullHearts; i++) {
			g.drawImage(fullHeart, x + 20 + (i*game.BLOCK_SIZE) + i*3, y + 30, game.BLOCK_SIZE, game.BLOCK_SIZE, null);
		}
		
		if(health%2 > 0) {
			g.drawImage(halfHeart, x + 20 + (numFullHearts*game.BLOCK_SIZE) + numFullHearts*3, y + 30, game.BLOCK_SIZE, game.BLOCK_SIZE, null);
		}
	}

}
