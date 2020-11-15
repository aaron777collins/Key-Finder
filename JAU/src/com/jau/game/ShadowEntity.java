package com.jau.game;

/**
 * makes shadow entity
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class ShadowEntity extends Entity { 

	/**
	 * makes instant kill shadow entity (extends Entity)
	 * @param x - x position 
	 * @param y - y position
	 * @param width - width of the entity
	 * @param height - height of the entity
	 * @param speed - speed of the entity
	 * @param jumpForce - jump force of the entity
	 */
	public ShadowEntity(int x, int y, int width, int height, int speed, int jumpForce) {
		super(x, y, width, height, speed, jumpForce);
		super.type = "shadow";
	}
	
	/**
	 * loads the animations for the entity
	 */
	@Override
	public void loadAnimations() {
		
		Animation standing = new Animation("res/images/shadow_standing", 1);
		Animation standingRight = new Animation("res/images/shadow_standingRight", 1);
		Animation standingLeft = new Animation("res/images/shadow_standingLeft", 1);
		Animation walkingRight = new Animation("res/images/shadow_walkingRight", 4);
		Animation walkingLeft = new Animation("res/images/shadow_walkingLeft", 4);
		
		animationM = new AnimationManager(standing, standingRight, standingLeft, walkingRight, walkingLeft, standing, standing, standing);
		animationLoop = new AnimationLoop(animationM);
		
	}

}
