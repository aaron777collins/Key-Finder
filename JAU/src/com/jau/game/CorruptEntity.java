package com.jau.game;
/**
 * Corrupt Entity that extends the entity class, is the same but is faster and has different animations
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class CorruptEntity extends Entity {

	/**
	 * makes corrupt entity which is the same as the regular entity but faster and different animations
	 * @param x - starting x position
	 * @param y - starting y position
	 * @param width - width of the entity
	 * @param height - height of the entity
	 * @param speed - speed of the entity
	 * @param jumpForce - jump force of the entity
	 */
	public CorruptEntity(int x, int y, int width, int height, int speed, int jumpForce) {
		super(x, y, width, height, speed + 2, jumpForce); //passing info on to entity class
	}

	/**
	 * loads all the animations
	 */
	@Override
	public void loadAnimations() {
		
		Animation standing = new Animation("res/images/corrupt_standing", 1);
		Animation standingRight = new Animation("res/images/corrupt_standingRight", 1);
		Animation standingLeft = new Animation("res/images/corrupt_standingLeft", 1);
		Animation walkingRight = new Animation("res/images/corrupt_walkingRight", 4);
		Animation walkingLeft = new Animation("res/images/corrupt_walkingLeft", 4);
		
		animationM = new AnimationManager(standing, standingRight, standingLeft, walkingRight, walkingLeft, standing, standing, standing);
		animationLoop = new AnimationLoop(animationM);
		
	}
	
}
