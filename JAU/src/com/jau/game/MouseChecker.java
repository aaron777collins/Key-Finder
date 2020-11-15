package com.jau.game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Checks for any mouse clicks
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class MouseChecker implements MouseMotionListener, MouseListener {

	private int x, y = 0;
	private boolean clicked = false;
	
	/**
	 * if mouse is clicked, updates other components
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		LoseScreen.updateButtons(e);
		WinScreen.updateButtons(e);

		MainMenu.updateButtons(e);

	}

	/**
	 * when mouse enters window
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * when mouse exits window
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * stores mouse press
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		setX(e.getX());
		setY(e.getY());
		setClicked(true);
	}

	/**
	 * stores mouse release
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		setX(e.getX());
		setY(e.getY());
		setClicked(false);
	}

	/**
	 * returns whether the mouse is clicked
	 * @return Boolean
	 */
	public boolean isClicked() {
		return clicked;
	}

	/**
	 * sets whether the mouse is clicked
	 * @param clicked - Boolean
	 */
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	/**
	 * returns the x value of the mouse
	 * @return Integer
	 */
	public int getX() {
		return x;
	}

	/**
	 * sets the x value of the mouse (virtually)
	 * @param x - Integer
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * returns the y value of the mouse
	 * @return Integer
	 */
	public int getY() {
		return y;
	}

	/**
	 * sets the y value of the mouse (virtually)
	 * @param y - Integer
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * stores mouse dragged info
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		setX(e.getX());
		setY(e.getY());
		
	}

	/**
	 * stores mouse moved info
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		setX(e.getX());
		setY(e.getY());
		
	}

	
}
