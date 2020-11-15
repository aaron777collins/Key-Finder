package com.jau.game;

/**
 * Holds the text for the dialog and its state
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class DialogHolder {

	
	//making variables
	public String text;
	public int state;
	
	/**
	 * makes dialog with text info and state
	 * @param text - Text of the dialog
	 * @param state - Who is saying the dialog
	 */
	public DialogHolder(String text, int state) {
		this.text = text;
		this.state = state;
	}

}
