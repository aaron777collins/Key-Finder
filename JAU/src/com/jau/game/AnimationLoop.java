package com.jau.game;

import java.awt.Image;

/**
 * manages animation's timing, which animation and iterates the indexes
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class AnimationLoop {
	
	//makes constants
	public static final int STANDING = 0;
	public static final int STANDING_RIGHT = 1;
	public static final int STANDING_LEFT = 2;
	public static final int WALKING_RIGHT = 3;
	public static final int WALKING_LEFT = 4;
	public static final int PUNCHING_RIGHT = 5;
	public static final int PUNCHING_LEFT = 6;
	public static final int DEAD = 7;

	//stores animation manager to grab the images
	AnimationManager animationM;
	
	//makes index variable
	private int index = 0;
	
	/**
	 * returns the index of the current animation
	 * @return Integer
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * sets the index of the current animation
	 * @param index - what index is going to be set to
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	//makes state variable
	private int state;
	
	/**
	 * Makes animation loop class by storing animation manager for now to keep track of the images
	 * @param animationM
	 */
	public AnimationLoop(AnimationManager animationM) {
		//sets animation manager (with all the images)
		this.animationM = animationM;
	}
	
	/**
	 * updates the animation loop to change what the player is doing
	 * @param state - what state the player should be in
	 */
	public void update(int state) {
		
		//sets current state to the state
		//resets index if necessary or increments it (called by timer of the entity's class)
		this.state = state;
		if (state == STANDING) {
			index = 0;
		}
		if (state == STANDING_RIGHT) {
			index = 0;
		}
		if (state == STANDING_LEFT) {
			index = 0;
		}
		if (state == WALKING_RIGHT) {
			if (index < animationM.getWalkingRight().getNumPics()-1) {
				index++;
			} else {
				index = 0;
			}
		}
		if (state == WALKING_LEFT) {
			if (index < animationM.getWalkingLeft().getNumPics()-1) {
				index++;
			} else {
				index = 0;
			}
		}
		if (state == PUNCHING_RIGHT) {
			index = 0;
		}
		if (state == PUNCHING_LEFT) {
			index = 0;
		}
		
		if (state == DEAD) {
			if (index < animationM.getDead().getNumPics()-1) {
				index++;
			} else {
				index = 0;
			}
			
			//if the player has fully shown the dead animation, don't show the dead animation anymore, play the dead sound
			if (index >= 14) {
				if (Renderer.showDeadLoop) {
					Game.sound.stop();
					Game.sound = SoundClip.DEAD;
					Game.sound.play();
				}
				Renderer.showDeadLoop = false;
			}
			
		}

	}
	
	/**
	 * returns the proper image at the proper index 
	 * @return Image
	 */
	public Image getImage() {
		
		//checks each state, sets the index to 0 if it is too high and returns the proper image
		if (state == STANDING) {
			index = 0;
			return animationM.getStanding().getImages()[index];
		}
		if (state == STANDING_RIGHT) {
			index = 0;
			return animationM.getStandingSideRight().getImages()[index];
		}
		if (state == STANDING_LEFT) {
			index = 0;
			return animationM.getStandingSideLeft().getImages()[index];
		}
		if (state == WALKING_RIGHT) {
			return animationM.getWalkingRight().getImages()[index];
		}
		if (state == WALKING_LEFT) {
			return animationM.getWalkingLeft().getImages()[index];
		}
		if (state == DEAD) {
			return animationM.getDead().getImages()[index];
		}
		if (state == PUNCHING_RIGHT) {
			index = 0;
			return animationM.getPunchingRight().getImages()[index];
		}
		if (state == PUNCHING_LEFT) {
			index = 0;
			return animationM.getPunchingLeft().getImages()[index];
		}
		index = 0;
		return animationM.getStanding().getImages()[index];
	}

}
