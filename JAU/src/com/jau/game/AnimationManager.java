package com.jau.game;
/**
 * Animation Manager holds the images/animations of the entity it corresponds to
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class AnimationManager {

	//making variables to hold the animations
	private Animation standing, standingSideRight, standingSideLeft, walkingRight, walkingLeft, punchingLeft, punchingRight, dead;

	/**
	 * sets each animation to it's corresponding variable
	 * @param standing - standing animation
	 * @param standingSideRight - standing right animation
	 * @param standingSideLeft - standing left animation
	 * @param walkingRight - walking right animation
	 * @param walkingLeft - walking left animation
	 * @param punchingRight - punching right animation
	 * @param punchingLeft - punching left animation
	 * @param dead - death animation
	 */
	public AnimationManager(Animation standing, Animation standingSideRight, Animation standingSideLeft, Animation walkingRight, Animation walkingLeft, Animation punchingRight, Animation punchingLeft, Animation dead) {

		//sets all animations to their corresponding animation
		this.standing = standing;
		this.standingSideLeft = standingSideLeft;
		this.standingSideRight = standingSideRight;
		
		this.walkingLeft = walkingLeft;
		this.walkingRight = walkingRight;
		
		this.punchingLeft = punchingLeft;
		this.punchingRight = punchingRight;
		
		this.setDead(dead);
		
	}
	
	/**
	 * returns the standing animation
	 * @return Animation object
	 */
	public Animation getStanding() {
		return standing;
	}

	/**
	 * returns the standing right animation
	 * @return Animation object
	 */
	public Animation getStandingSideRight() {
		return standingSideRight;
	}

	/**
	 * returns the standing left animation
	 * @return Animation object
	 */
	public Animation getStandingSideLeft() {
		return standingSideLeft;
	}

	/**
	 * returns the walking right animation
	 * @return Animation Object
	 */
	public Animation getWalkingRight() {
		return walkingRight;
	}

	/**
	 * returns the walking left animation
	 * @return Animation Object
	 */
	public Animation getWalkingLeft() {
		return walkingLeft;
	}

	/**
	 * returns the punching left animation
	 * @return Animation object
	 */
	public Animation getPunchingLeft() {
		return punchingLeft;
	}

	/**
	 * sets the punching left animation
	 * @param punchingLeft - punching left animation 
	 */
	public void setPunchingLeft(Animation punchingLeft) {
		this.punchingLeft = punchingLeft;
	}

	/**
	 * returns the punching right animation
	 * @return Animation Object
	 */
	public Animation getPunchingRight() {
		return punchingRight;
	}

	/**
	 * sets the punching right animation
	 * @param punchingRight - punching right animation
	 */
	public void setPunchingRight(Animation punchingRight) {
		this.punchingRight = punchingRight;
	}

	/**
	 * returns the dead animation
	 * @return Animation Object
	 */
	public Animation getDead() {
		return dead;
	}

	/**
	 * sets the dead animation
	 * @param dead - dead animation
	 */
	public void setDead(Animation dead) {
		this.dead = dead;
	}
	
	

}
