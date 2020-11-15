package com.jau.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

/**
 * Custom Button is a button class that calls certain methods depending on it's id when it is clicked
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class CustomButton {

	//making variables
	int width;
	int height;
	boolean hover = false;
	boolean click = false;
	boolean active = false;
	Color color = new Color(0, 0, 230);
	int id;
	
	int x = 0;
	int y = 0;
	
	String text = "";
	
	/**
	 * makes custom button with text, a function to be called depending on its id when it's clicked and the position/dimensions
	 * @param text - text that the button displays
	 * @param id - id of the function to be called
	 * @param x - x position of the button
	 * @param y - y position of the button
	 * @param width - width of the button
	 * @param height - height of the button
	 */
	public CustomButton(String text, int id, int x, int y, int width, int height) {

		//sets variables to their corresponding inputs
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.id = id;
		
		this.width = width;
		this.height = height;
		
		this.text = text;
		
	}
	
	/**
	 * checks to see if the mouse is clicking a button
	 * @param e - MouseEvent for checking info about the mouse click
	 */
	public void update(MouseEvent e) {
		
		int mouseX = e.getX() - 5;
		int mouseY = e.getY() - 27;
		
		//if the mouse clicked the button on screen, it calls mouse clicked (passing in e)
		if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height) {
			mouseClicked(e);
		}
	}

	/**
	 * Sets the button text
	 * @param text - text the button will display
	 */
	public void setButtonText(String text) {
		this.text= text;
	}
	
	/**
	 * returns the text displayed by the button
	 * @return String
	 */
	public String getButtonText() {
		return text;
	}
	
	/**
	 * if mouse is clicked and the button is active, it changes color and calls the corresponding method
	 * @param e - MouseEvent depicting where they clicked
	 */
	public void mouseClicked (MouseEvent e) {
		if (active) {
			click = true;
			color = new Color(0, 230, 0);
			
			if (id == MainMenu.START_BUTTON_INDEX) {
				MainMenu.startButtonClicked();
			}
			if (id == MainMenu.BACK_BUTTON_INDEX) {
				MainMenu.backButtonClicked();
			}
			if (id == MainMenu.EXIT_BUTTON_INDEX) {
				MainMenu.exitButtonClicked();
			}
			if (id == MainMenu.CREDITS_BUTTON_INDEX) {
				MainMenu.creditsButtonClicked();
			}
			if (id == MainMenu.HELP_BUTTON_INDEX) {
				MainMenu.helpButtonClicked();
			}
			
			
			if (id == LoseScreen.RESTART_BUTTON_INDEX) {
				LoseScreen.restartClicked();
			}
			if (id == LoseScreen.EXIT_BUTTON_INDEX) {
				LoseScreen.exitClicked();
			}
			
			if (id == WinScreen.EXIT_BUTTON_INDEX) {
				WinScreen.exitClicked();
			}
			
		}
	}
	
	/**
	 * draws the button with text and all
	 * @param g - Graphics for drawing the object
	 */
	public void draw(Graphics g) {
		
		//sets the color
		color = (new Color(0, 0, 230));
		
		//if the button isn't on, don't continue
		if (!active) {
			return;
		}
		
		//otherwise draw the button
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
		//making button font
		g.setColor(Color.WHITE);
		
		g.setFont(Font.decode("arial-Bold-24"));
		
		FontMetrics metrics = g.getFontMetrics();
		
		//getting font width to properly draw the text
		int widthT = metrics.stringWidth(text);
		
		g.drawString(text, x + width/2 - widthT/2, y + height/2);
		
	}
	
}
