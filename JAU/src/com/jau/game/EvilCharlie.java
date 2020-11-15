package com.jau.game;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Evil charlie is the final boss of the game (extends entity)
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class EvilCharlie extends Entity {

	/**
	 * makes a evil charlie
	 * @param x - x position
	 * @param y - y position
	 * @param width - width of entity
	 * @param height - height of entity
	 * @param speed - speed of entity
	 * @param jumpForce - jump force of entity
	 */
	public EvilCharlie(int x, int y, int width, int height, int speed, int jumpForce) {
		super(x, y, width, height, speed, jumpForce); //passes info to entity
	}
	
	/**
	 * loads the animations of evil charlie
	 */
	@Override
	public void loadAnimations() {
		
		Animation standing = new Animation("res/images/charlie_standing", 1);
		Animation standingRight = new Animation("res/images/charlie_standingRight", 1);
		Animation standingLeft = new Animation("res/images/charlie_standingLeft", 1);
		Animation walkingRight = new Animation("res/images/charlie_walkingRight", 4);
		Animation walkingLeft = new Animation("res/images/charlie_walkingLeft", 4);
		
		animationM = new AnimationManager(standing, standingRight, standingLeft, walkingRight, walkingLeft, standing, standing, standing);
		animationLoop = new AnimationLoop(animationM);
		
	}
	
	/**
	 * moves evil charlie towards the player (no running away)
	 */
	@Override
	public void makeDecision(Level level, Player player, Camera camera) {

		if (player.getScreenX() - (getX() - player.getX() - camera.getXOff()) > Game.BLOCK_SIZE * 2) {
			direction = 0;
		} else if (player.getScreenX() - (getX() - player.getX() - camera.getXOff()) < -Game.BLOCK_SIZE * 2) {
			direction = 1;
		}
		
		boolean shouldJump = false;
		
		if (isSolidRelativeToFeet(1,0, level, player, camera)  && !isSolidRelativeToFeetLeft(1,-1, level, player, camera)) {
			shouldJump = true;
		}
		if (isSolidRelativeToFeet(-1,0, level, player, camera) && !isSolidRelativeToFeetRight(-1,-1, level, player, camera)) {
			shouldJump = true;
		}
				
		//checks if direction is 0, if so, moved right speed, otherwise, left speed
		moveEntity((direction == 0) ? getSpeed() : -getSpeed(), shouldJump, level, player, camera);
		
	}
	
	/**
	 * hurts evil charlie (he has just as much health as the player)
	 */
	@Override
	public void hurt(int side) {
		
		//side == 0 = left
		//side == 1 = right
		
		
		if (!immortal) {
			health--;
			
			if (side == 0) {
				
				move(knockBack, 0);
				direction = side;
					
			} else if (side == 1) {

				move(-knockBack, 0);
				direction = side;
				
			}
			jumped = true;
			yAcc = -knockBack;
			move(0, -5);
			if (health < 0) {
				health = 0;
				
				Game.entities.remove(this);
				
				int[] indexes = getBlockIndexAtFeet(Game.currentLevel, Game.player, Game.camera);
				
				//add key to left of dead bad charlie
				Game.currentLevel.setBlock(indexes[0], indexes[1], Game.blockManager.getBlocks()[BlockManager.KEY]);
				
			}
			
			immortal = true;
			Timer timer = new Timer();
			
			timer.schedule(new TimerTask(){

				int timesRun = 0;
				
				@Override
				public void run() {
					
					if (timesRun > 0) {
						immortal = false;
						timer.cancel();

					}
					timesRun++;
					
				}
				
			}, 0, (long)1500);
		}
		
	}
	
	
}
