package com.jau.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.Clip;

import com.jau.maths.Maths;

/**
 * makes the player
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class Player {
	
	private int x, y, screenX, screenY, playerWidth, playerHeight, speed, jumpForce;
	private double xVel, yVel, xAcc, yAcc;
	private boolean jumped = false;
	private int state = AnimationLoop.STANDING;
	
	public static boolean canPunch = true;
	
	
	public static final int RIGHT = 0;
	public static final int LEFT = 1;

	public static int direction = RIGHT;
	
	int knockBack = 3;
	private int health = 6;
	private boolean immortal = false;
	private int loopCounter = 0;
	private int countMax = 10;
	
	private boolean flung = false;
	
	AnimationLoop animationLoop;
	AnimationManager animationM;
	
	Point[] previousPositions = new Point[10];
	
	/**
	 * makes the player object
	 * @param x - x position
	 * @param y - y position
	 * @param playerWidth - width of the player
	 * @param playerHeight - height of the player
	 * @param speed - speed of the player
	 * @param jumpForce - jump force of the player
	 */
	public Player(int x, int y, int playerWidth, int playerHeight, int speed, int jumpForce) {
		this.setX(x);
		this.setY(y);
		
		this.setJumpForce(jumpForce);
		
		setxVel(xVel);
		setyVel(yVel);
		setxAcc(xAcc);
		setyAcc(yAcc);
		
		this.playerWidth = playerWidth;
		this.playerHeight = playerHeight;
		
		this.speed = speed;
		
		loadAnimations();
		
	}
	
	/**
	 * sets the animation state of the player
	 * @param state
	 */
	public void setState(int state) {
		this.state = state;
	}
	
	/**
	 * returns the animation state of the player
	 * @return Integer
	 */
	public int getState() {
		return state;
	}
	
	/**
	 * updates the player
	 */
	public void update() {
		
		loopCounter++;
		
		if (loopCounter > countMax) {
			loopCounter = 0;
			animationLoop.update(state);
		}
		
		if (Math.abs(xVel) < 0.2 && !Game.dead) {
			if (state == AnimationLoop.WALKING_RIGHT) {
				state = AnimationLoop.STANDING_RIGHT;
			}
			if (state == AnimationLoop.WALKING_LEFT) {
				state = AnimationLoop.STANDING_LEFT;
			}
		}
		
		xVel += xAcc;
		yVel += yAcc;
		
		xAcc = 0;
		yAcc = 0;
		
		move((int)(xVel), (int)(yVel));
		
		if (health <= 0) {
			//game over
			health = 0;
			
			if (!Game.dead) {
				Game.sound.stop();
				Game.sound = SoundClip.DEAD;
				Game.sound.play();
			}
			
			
				
			state = AnimationLoop.DEAD;
			
			Game.dead = true;
			}
		
	}
	
	/**
	 * checks the collision of the player
	 * @param level - Level object
	 * @param camera - Camera object
	 * @param WIDTH - width of the screen
	 * @param HEIGHT - height of the screen
	 */
	public void checkCollision(Level level, Camera camera, int WIDTH, int HEIGHT) {
		
		try {
			updateFriction();
			checkDialogs(level, camera);
			checkKey(level, camera);
			checkSpikes(level, camera);
			checkEntities(level, camera);
			checkDoor(level, camera);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		int blockSize = level.getBLOCK_SIZE();
		double cameraX = camera.getXOff();
		double cameraY = camera.getYOff();
		
		//player X is the left of the player and player Y is the bottom of the player
		int playerX = getScreenX() - playerWidth/2 + 5; //the + 5 and -5 allows for the player to fall though 1 space blocks (without hitting the sides)
		int playerY = getScreenY() + playerHeight/2;
		//playerX2 is the right
		int playerX2 = getScreenX() + playerWidth/2 - 5;
		

		
		boolean cancelSide = false;
		
		if (isSolidRelativeToTop(0, 0, level, camera)) { 
			//checks to side (top section) of player

			if (getyVel() < 0) {
				setyVel(0);
				setxVel(0);
				cancelSide = true;
			}
					
		
	}
		
		//bottom collision
		if (isSolidRelativeToBelow(0,0, level, camera)) {
			if (getyVel() > 0) {
				setyAcc(0);
				setyVel(0);
			
			}
			
			int off = getBlockYOffset((int)(playerY + camera.getYOff() + y), blockSize);
			
			if (off > 1) {
				//prevent glitching above floors
				move(0, -1);
			}
			
			if (getyVel() >= 0) {
				jumped = false;
			}
		}
		
		
		if (isSolidRelativeToFeet(0,0,level,camera) && isSolidRelativeToFeet(0, -1, level, camera)) {
			try {
			setPosition(previousPositions[9].x, previousPositions[9].y);
			}catch(Exception e) {
				System.out.println("Not enough moves to teleport yet.");
			}
		}
		
		if (flung) {
			
			if (xVel <= 0.1) {
				flung = false;
			}
		}

		
		//fixing clipping through walls when jumping up straight walls (right)
		if (isSolidRelativeToTop(1, 0, level, camera) && isSolidRelativeToTop(1, -1, level, camera) && isSolidRelativeToBelow(0, -2, level, camera) && isSolidRelativeToBelow(0, -1, level, camera)) {
			move(-speed, 0);
		}
		
		//fixing clipping through walls when jumping up straight walls (left)
		if (isSolidRelativeToTop(-1, 0, level, camera) && isSolidRelativeToTop(-1, -1, level, camera) && isSolidRelativeToBelow(0, -2, level, camera) && isSolidRelativeToBelow(0, -1, level, camera)) {
			move(speed, 0);
		}
		
	
	}

	/**
	 * checks for dialog triggers
	 * @param level - Level object
	 * @param camera - Camera object
	 */
	private void checkDialogs(Level level, Camera camera) {

		if (getBlockRelativeToFeetLeft(0, 0, level, camera) == BlockManager.DIALOG1 || getBlockRelativeToFeetRight(0, 0, level, camera) == BlockManager.DIALOG1) {
			Renderer.DBox = new DisplayDialog(Dialogs.intro1);
			DisplayDialog.active = true;
		}
		if (getBlockRelativeToFeetLeft(0, 0, level, camera) == BlockManager.DIALOG2 || getBlockRelativeToFeetRight(0, 0, level, camera) == BlockManager.DIALOG2) {
			Renderer.DBox = new DisplayDialog(Dialogs.intro2);
			DisplayDialog.active = true;
		}
		if (getBlockRelativeToFeetLeft(0, 0, level, camera) == BlockManager.DIALOG3 || getBlockRelativeToFeetRight(0, 0, level, camera) == BlockManager.DIALOG3) {
			Renderer.DBox = new DisplayDialog(Dialogs.level1_1);
			DisplayDialog.active = true;
		}
		if (getBlockRelativeToFeetLeft(0, 0, level, camera) == BlockManager.DIALOG4 || getBlockRelativeToFeetRight(0, 0, level, camera) == BlockManager.DIALOG4) {
			Renderer.DBox = new DisplayDialog(Dialogs.level2_1);
			DisplayDialog.active = true;
		}
		if (getBlockRelativeToFeetLeft(0, 0, level, camera) == BlockManager.DIALOG5 || getBlockRelativeToFeetRight(0, 0, level, camera) == BlockManager.DIALOG5) {
			Renderer.DBox = new DisplayDialog(Dialogs.level3_1);
			DisplayDialog.active = true;
		}
		if (getBlockRelativeToFeetLeft(0, 0, level, camera) == BlockManager.DIALOG6 || getBlockRelativeToFeetRight(0, 0, level, camera) == BlockManager.DIALOG6) {
			Renderer.DBox = new DisplayDialog(Dialogs.level4_1);
			DisplayDialog.active = true;
		}
		if (getBlockRelativeToFeetLeft(0, 0, level, camera) == BlockManager.DIALOG7 || getBlockRelativeToFeetRight(0, 0, level, camera) == BlockManager.DIALOG7) {
			Renderer.DBox = new DisplayDialog(Dialogs.level5_1);
			DisplayDialog.active = true;
		}
		if (getBlockRelativeToFeetLeft(0, 0, level, camera) == BlockManager.DIALOG8 || getBlockRelativeToFeetRight(0, 0, level, camera) == BlockManager.DIALOG8) {
			Renderer.DBox = new DisplayDialog(Dialogs.level5_2);
			DisplayDialog.active = true;
		}
		if (getBlockRelativeToFeetLeft(0, 0, level, camera) == BlockManager.DIALOG9 || getBlockRelativeToFeetRight(0, 0, level, camera) == BlockManager.DIALOG9) {
			Renderer.DBox = new DisplayDialog(Dialogs.level5_3);
			DisplayDialog.active = true;
		}
		if (getBlockRelativeToFeetLeft(0, 0, level, camera) == BlockManager.DIALOG10 || getBlockRelativeToFeetRight(0, 0, level, camera) == BlockManager.DIALOG10) {
			Renderer.DBox = new DisplayDialog(Dialogs.level5_4);
			DisplayDialog.active = true;
		}
		if (getBlockRelativeToFeetLeft(0, 0, level, camera) == BlockManager.DIALOG11 || getBlockRelativeToFeetRight(0, 0, level, camera) == BlockManager.DIALOG11) {
			Renderer.DBox = new DisplayDialog(Dialogs.level5_5);
			DisplayDialog.active = true;
		}

		
	}
	
	/**
	 * checks for the door at the player's feet
	 * @param level - Level object
	 * @param camera - Camera object
	 */
	private void checkDoor(Level level, Camera camera) {

		try {
			if (getBlockRelativeToFeetLeft(0, -1, level, camera) == BlockManager.DOOR || getBlockRelativeToFeetRight(0, -1, level, camera) == BlockManager.DOOR) {
				if(Game.hasKey) {
					Game.nextLevel();
				} else {
					if (!DisplayDialog.text.equals(Dialogs.level1_LOCKED.text)) {
						Renderer.DBox = new DisplayDialog(Dialogs.level1_LOCKED);
						DisplayDialog.active = true;
					}
				}
			}
		}catch(Exception e) {
			
		}
		
	}

	
	/**
	 * checks each entity around player
	 * @param level - Level object
	 * @param camera - Camera object
	 */
	private void checkEntities(Level level, Camera camera) {

		for (Entity entity : Game.entities) {
			
			int playerX = (getScreenX() - playerWidth/2);
			int playerY = getScreenY() - getPlayerHeight()/2;
			int entityX = (int)(entity.getX() - x - camera.getXOff() + entity.getWidth()/2); // middle
			int entityY = ((int)(entity.getY() - y - camera.getYOff())) + 1;
			int entityY2 = ((int)(entity.getY() - y - camera.getYOff() + entity.getHeight())) -1;
			
			if (entityX < playerX + playerWidth && entityX > playerX && ((entityY< playerY + playerHeight && entityY > playerY) || (entityY2< playerY + playerHeight && entityY2 > playerY))) {
				hurt(entity, camera);
			}
		}
		
	}
	
	/**
	 * hurts the player
	 * @param entity - what entity is hurting the player
	 * @param camera - Camera object
	 */
	public void hurt(Entity entity, Camera camera) {
		
		int playerXMiddle = getScreenX();
		int entityXMiddle = 0;
		try {
			entityXMiddle = (int)(entity.previousPositions[2].x - x - camera.getXOff() + entity.getWidth()/2);
		} catch(Exception e) {
			return;
		}
		if (!immortal) {
			
			if (entity.type.equals("shadow")) {
				health = 0;
			}
			
			health--;
			
			if (entityXMiddle < playerXMiddle) {
				
				setxVel(knockBack* 5);
					
			} else if (entityXMiddle > playerXMiddle) {

				setxVel(-knockBack* 5);
				
			}
			flung = true;
			jumped = true;
			setyAcc(-knockBack);
			move(0, -2);
			if (health <= 0) {
				//game over
				health = 0;
				
				Game.dead = true;

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
				
			}, 0, (long)3000);
		}
		
	}

	/**
	 * checks to see if the player can pick up the key
	 * @param level - Level object
	 * @param camera - Camera object
	 */
	private void checkKey(Level level, Camera camera) {

		if (getBlockRelativeToFeetLeft(0, 0, level, camera) == BlockManager.KEY || getBlockRelativeToFeetRight(0, 0, level, camera) == BlockManager.KEY) {
			Game.hasKey = true;
			
			if (!DisplayDialog.text.equals(Dialogs.level1_HASKEY.text)) {

				Renderer.DBox = new DisplayDialog(Dialogs.level1_HASKEY);
				DisplayDialog.active = true;
			}
			
			if (!Game.isEditing()) {
				
				int[] indexes = getBlockIndexAtFeet(level, camera);
				
				level.setBlock(indexes[0], indexes[1], Game.blockManager.getBlocks()[BlockManager.AIR]);
			}
			
			Game.keySound.play();
		}
		
	}
	
	/**
	 * checks to see if the player is on spikes
	 * @param level - Level object
	 * @param camera - Camera object
	 */
	private void checkSpikes(Level level, Camera camera) {

		if (getBlockRelativeToBottomMiddle(0, 0, level, camera) == BlockManager.SPIKES) {
			
			
			if (!immortal) {
				health--;
				if (health <= 0) {
					//game over
					health = 0;
					
					Game.dead = true;
					
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
					
				}, 0, (long)3000);
			}
			
			
		} else if(getBlockRelativeToBottomMiddle(0, 0, level, camera) == BlockManager.BSPIKES) {
			if(!immortal) {
				
				health = 0;
				Game.dead = true;
				
			}
		}
		
	}
	
	/**
	 * returns the block relative to the bottom of the player (middle)
	 * @param xOff - x block offset to be returned
	 * @param yOff - y block offset to be returned
	 * @param level - Level object
	 * @param camera - Camera
	 * @return Integer
	 */
	private int getBlockRelativeToBottomMiddle(int xOff, int yOff, Level level, Camera camera) {
		int[] indexes = getBlockIndexBelowMiddlePlayer(level, camera);
		return level.getBlocks()[indexes[0] + xOff][indexes[1] + yOff].getId();
	}

	/**
	 * returns the block relative to the left foot
	 * @param xOff - x block offset to be returned
	 * @param yOff - y block offset to be returned
	 * @param level - Level object
	 * @param camera - Camera
	 * @return Integer
	 */
	private int getBlockRelativeToFeetLeft(int xOff, int yOff, Level level, Camera camera) {
		int[] indexes = getBlockIndexAtFeet(level, camera);
		return level.getBlocks()[indexes[0] + xOff][indexes[1] + yOff].getId();
	}
	
	/**
	 * returns the block relative to the right foot
	 * @param xOff - x block offset to be returned
	 * @param yOff - y block offset to be returned
	 * @param level - Level object
	 * @param camera - Camera
	 * @return Integer
	 */
	private int getBlockRelativeToFeetRight(int xOff, int yOff, Level level, Camera camera) {
		int[] indexes = getBlockIndexAtFeet(level, camera);
		return level.getBlocks()[indexes[2] + xOff][indexes[3] + yOff].getId();
	}

	/**
	 * sets the position of the player
	 * @param x - x position
	 * @param y - y position
	 */
	private void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		
	}

	/**
	 * updates the friction of the player
	 */
	private void updateFriction() {
		setxVel(getxVel()/2);
	}

	/**
	 * returns y block offset
	 * @param y - y position of the player
	 * @param blockSize - block size
	 * @return Integer
	 */
	private int getBlockYOffset(int y, int blockSize) {
		return y % blockSize;
	}

	/**
	 * returns whether the block is solid relative to below the player
	 * @param xOff - x block offset to be returned
	 * @param yOff - y block offset to be returned
	 * @param level - Level object
	 * @param camera - Camera
	 * @return boolean
	 */
	public boolean isSolidRelativeToBelow(int xOff, int yOff, Level level, Camera camera) {
		
		boolean solid = false;
		
		int[] indexes = getBlockIndexBelowPlayer(level, camera);
		
		try {
			if (level.getBlocks()[indexes[0] + xOff][indexes[1] + yOff].isSolid()) {
				solid = true;
			}
			if (level.getBlocks()[indexes[2] + xOff][indexes[3] + yOff].isSolid()) {
				solid = true;
			}
		} catch(Exception e) {
			
		}
		return solid;
	
	}
	
	/**
	 * returns whether the block is solid relative to the player's feet
	 * @param xOff - x block offset to be returned
	 * @param yOff - y block offset to be returned
	 * @param level - Level object
	 * @param camera - Camera
	 * @return boolean
	 */
	public boolean isSolidRelativeToFeet(int xOff, int yOff, Level level, Camera camera) {
		
		boolean solid = false;
		
		int[] indexes = getBlockIndexAtFeet(level, camera);
		
		try {
			if (level.getBlocks()[indexes[0] + xOff][indexes[1] + yOff].isSolid()) {
				solid = true;
			}
			if (level.getBlocks()[indexes[2] + xOff][indexes[3] + yOff].isSolid()) {
				solid = true;
			}
		} catch(Exception e) {
			
		}
		return solid;
	
	}
	
	/**
	 * returns whether the block is solid relative to the right foot
	 * @param xOff - x block offset to be returned
	 * @param yOff - y block offset to be returned
	 * @param level - Level object
	 * @param camera - Camera
	 * @return boolean
	 */
	public boolean isSolidRelativeToFeetRight(int xOff, int yOff, Level level, Camera camera) {
		
		boolean solid = false;
		
		int[] indexes = getBlockIndexAtFeet(level, camera);
		
		try {
			if (level.getBlocks()[indexes[2] + xOff][indexes[3] + yOff].isSolid()) {
				solid = true;
			}
		} catch(Exception e) {
			
		}
		return solid;
	
	}
	
	/**
	 * returns whether the block is solid relative to the left foot
	 * @param xOff - x block offset to be returned
	 * @param yOff - y block offset to be returned
	 * @param level - Level object
	 * @param camera - Camera
	 * @return boolean
	 */
	public boolean isSolidRelativeToFeetLeft(int xOff, int yOff, Level level, Camera camera) {
		
		boolean solid = false;
		
		int[] indexes = getBlockIndexAtFeet(level, camera);
		
		try {
			if (level.getBlocks()[indexes[0] + xOff][indexes[1] + yOff].isSolid()) {
				solid = true;
			}
		} catch(Exception e) {
			
		}
		return solid;
	
	}
	
	/**
	 * returns whether the block is solid relative to top of the player
	 * @param xOff - x block offset to be returned
	 * @param yOff - y block offset to be returned
	 * @param level - Level object
	 * @param camera - Camera
	 * @return boolean
	 */
	public boolean isSolidRelativeToTop(int xOff, int yOff, Level level, Camera camera) {
		
		boolean solid = false;
		
		int[] indexes = getBlockIndexAbovePlayer(level, camera);
		
		try {
			if (level.getBlocks()[indexes[0] + xOff][indexes[1] + yOff].isSolid()) {
				solid = true;
			}
			if (level.getBlocks()[indexes[2] + xOff][indexes[3] + yOff].isSolid()) {
				solid = true;
			}
		} catch(Exception e) {
			
		}
		return solid;
	
	}
	
	/**
	 * returns the block indexes at middle below the player
	 * @param xOff - x block offset to be returned
	 * @param yOff - y block offset to be returned
	 * @param level - Level object
	 * @param camera - Camera
	 * @return int[]
	 */
	private int[] getBlockIndexBelowMiddlePlayer(Level level, Camera camera) {
		
		int[] indexes = new int[4];
		
		int blockSize = level.getBLOCK_SIZE();
		double cameraX = camera.getXOff();
		double cameraY = camera.getYOff();
		
		//player X is the left of the player + 5 (to allow to fall) and player Y is the bottom of the player
		int playerX = getScreenX();
		int playerY = getScreenY() + playerHeight/2;
		
		//int blockX = (int)((i * blockSize) - x - cameraX);
		// becomes when solving for the index
		//(playerX + cameraX + x) / blockSize = i
		
		int blockXIndex = (int)(playerX + x + cameraX) / blockSize;
		int blockYIndex = (int)(playerY + y + cameraY) / blockSize;

		indexes[0] = blockXIndex;
		indexes[1] = blockYIndex;
		
		return indexes; 
	}
	
	
	/**
	 * returns the block indexes below the player
	 * @param xOff - x block offset to be returned
	 * @param yOff - y block offset to be returned
	 * @param level - Level object
	 * @param camera - Camera
	 * @return int[]
	 */
	private int[] getBlockIndexBelowPlayer(Level level, Camera camera) {
		
		int[] indexes = new int[4];
		
		int blockSize = level.getBLOCK_SIZE();
		double cameraX = camera.getXOff();
		double cameraY = camera.getYOff();
		
		//player X is the left of the player + 5 (to allow to fall) and player Y is the bottom of the player
		int playerX = getScreenX() - playerWidth/2 + 5;
		int playerY = getScreenY() + playerHeight/2;
		
		//playerX2 is the right of the player - 5 (to allow to fall)
		int playerX2 = getScreenX() + playerWidth/2 - 5;
		
		//int blockX = (int)((i * blockSize) - x - cameraX);
		// becomes when solving for the index
		//(playerX + cameraX + x) / blockSize = i
		
		int blockXIndex = (int)(playerX + x + cameraX) / blockSize;
		int blockYIndex = (int)(playerY + y + cameraY) / blockSize;

		indexes[0] = blockXIndex;
		indexes[1] = blockYIndex;
		
		int blockX2Index = (int)(playerX2 + x + cameraX) / blockSize;
		
		indexes[2] = blockX2Index;
		indexes[3] = blockYIndex;
		
		
		return indexes; 
	}
	
	/**
	 * returns the block indexes at player's feet
	 * @param xOff - x block offset to be returned
	 * @param yOff - y block offset to be returned
	 * @param level - Level object
	 * @param camera - Camera
	 * @return int[]
	 */
	private int[] getBlockIndexAtFeet(Level level, Camera camera) {
			
			int[] indexes = new int[4];
			
			int blockSize = level.getBLOCK_SIZE();
			double cameraX = camera.getXOff();
			double cameraY = camera.getYOff();
			
			//player X is the left of the player + 5 (to allow to fall) and player Y is the bottom of the player
			int playerX = getScreenX() - playerWidth/2 + 5;
			int playerY = getScreenY() + playerHeight/3;
			
			//playerX2 is the right of the player - 5 (to allow to fall)
			int playerX2 = getScreenX() + playerWidth/2 - 5;
			
			//int blockX = (int)((i * blockSize) - x - cameraX);
			// becomes when solving for the index
			//(playerX + cameraX + x) / blockSize = i
			
			int blockXIndex = (int)(playerX + x + cameraX) / blockSize;
			int blockYIndex = (int)(playerY + y + cameraY) / blockSize;
	
			indexes[0] = blockXIndex;
			indexes[1] = blockYIndex;
			
			int blockX2Index = (int)(playerX2 + x + cameraX) / blockSize;
			
			indexes[2] = blockX2Index;
			indexes[3] = blockYIndex;
			
			
			return indexes; 
		}
	
	/**
	 * returns the block indexes at middle below the player
	 * @param xOff - x block offset to be returned
	 * @param yOff - y block offset to be returned
	 * @param level - Level object
	 * @param camera - Camera
	 * @return int[]
	 */
private int[] getBlockIndexAbovePlayer(Level level, Camera camera) {
		
		int[] indexes = new int[4];
		
		int blockSize = level.getBLOCK_SIZE();
		double cameraX = camera.getXOff();
		double cameraY = camera.getYOff();
		
		//player X is the left of the player + 5 (to allow to fall) and player Y is the top of the player
		int playerX = getScreenX() - playerWidth/2 + 5;
		int playerY = getScreenY() - playerHeight/2;
		
		//playerX2 is the right of the player - 5 (to allow to fall)
		int playerX2 = getScreenX() + playerWidth/2 - 5;
		
		//int blockX = (int)((i * blockSize) - x - cameraX);
		// becomes when solving for the index
		//(playerX + cameraX + x) / blockSize = i
		
		int blockXIndex = (int)(playerX + x + cameraX) / blockSize;
		int blockYIndex = (int)(playerY + y + cameraY) / blockSize;

		indexes[0] = blockXIndex;
		indexes[1] = blockYIndex;
		
		int blockX2Index = (int)(playerX2 + x + cameraX) / blockSize;
		
		indexes[2] = blockX2Index;
		indexes[3] = blockYIndex;
		
		
		return indexes; 
	}
	
	/**
	 * loads the animations of the player
	 */
	private void loadAnimations() {
		
		Animation standing = new Animation("res/images/player_standing", 1);
		Animation standingRight = new Animation("res/images/player_standingRight", 1);
		Animation standingLeft = new Animation("res/images/player_standingLeft", 1);
		Animation walkingRight = new Animation("res/images/player_walkingRight", 4);
		Animation walkingLeft = new Animation("res/images/player_walkingLeft", 4);
		
		Animation punchingRight = new Animation("res/images/player_punchingRight", 1);
		Animation punchingLeft = new Animation("res/images/player_punchingLeft", 1);
		
		Animation dead = new Animation("res/images/PlayerOofed", 15);


		
		animationM = new AnimationManager(standing, standingRight, standingLeft, walkingRight, walkingLeft, punchingRight, punchingLeft, dead);
		animationLoop = new AnimationLoop(animationM);
		
	}
	
	/**
	 * moves the player
	 * @param dx - how much to move in x
	 * @param dy - how much to move in y
	 */
	public void move(int dx, int dy) {
		
		Game.updateLights = true;
		
		previousPositions[0] = new Point(x, y); 
		
		for (int i = previousPositions.length-1; i> 0; i--) {
			
			previousPositions[i] = previousPositions[i-1];
		}
		
		x += dx;
		y += dy;
	}
	
	/**
	 * renders the player to the screen
	 * @param g - Graphics
	 * @param xOffset - camera x offset
	 * @param yOffset - camera y offset
	 * @param WIDTH - width of screen 
	 * @param HEIGHT - height of screen
	 */
	public void render(Graphics g, double xOffset, double yOffset, int WIDTH, int HEIGHT) {
		
		setScreenX((int) (x - xOffset + Game.WIDTH/2));
		setScreenY((int) (y - yOffset + Game.HEIGHT/2));
		
		if (immortal) {
			Image img = tintImage((BufferedImage)(animationLoop.getImage()));
			g.drawImage(img, getScreenX() - playerWidth/2, getScreenY() - playerHeight/2, playerWidth, playerHeight, null);

		} else {
			g.drawImage(animationLoop.getImage(), getScreenX() - playerWidth/2, getScreenY() - playerHeight/2, playerWidth, playerHeight, null);
		}
		
	}

	/**
	 * returns the x position
	 * @return Integer
	 */
	public int getX() {
		return x;
	}

	/**
	 * sets the x position
	 * @param x - x position
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * returns the y position
	 * @return Integer
	 */
	public int getY() {
		return y;
	}

	/**
	 * sets the y position
	 * @param y Integer
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * returns the player width
	 * @return Integer
	 */
	public int getPlayerWidth() {
		return playerWidth;
	}
	/**
	 * returns the player height
	 * @return Integer
	 */
	public int getPlayerHeight() {
		return playerHeight;
	}

	/**
	 * returns the speed of the player
	 * @return Integer
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * returns the x velocity
	 * @return Double
	 */
	public double getxVel() {
		return xVel;
	}

	/**
	 * sets the x velocity
	 * @param xVel2 - x velocity
	 */
	public void setxVel(double xVel2) {
		this.xVel = xVel2;
	}

	/**
	 * returns the y velocity
	 * @return Double
	 */
	public double getyVel() {
		return yVel;
	}

	/**
	 * sets the y velocity
	 * @param yVel
	 */
	public void setyVel(double yVel) {
		this.yVel = yVel;
	}

	/**
	 * returns the x acceleration
	 * @return
	 */
	public double getxAcc() {
		return xAcc;
	}

	/**
	 * sets the x acceleration
	 * @param xAcc - x acceleration
	 */
	public void setxAcc(double xAcc) {
		this.xAcc = xAcc;
	}

	/**
	 * returns the y acceleration
	 * @return Double
	 */
	public double getyAcc() {
		return yAcc;
	}

	/**
	 * sets the y acceleration
	 * @param yAcc - y acceleration
	 */
	public void setyAcc(double yAcc) {
		this.yAcc = yAcc;
	}

	/**
	 * returns the jump force
	 * @return Integer
	 */
	public int getJumpForce() {
		return jumpForce;
	}

	/**
	 * sets the jump force
	 * @param jumpForce - jump force
	 */
	public void setJumpForce(int jumpForce) {
		this.jumpForce = jumpForce;
	}

	/**
	 * updates the player's gravity
	 */
	public void updateGravity() {

		//sets max gravity acceleration
		if (getyVel() + Game.GRAVITY < Game.MAX_GRAVITY) {
			setyAcc(Game.GRAVITY);
		}


	}
	

	/**
	 * makes the player jump
	 * @param force
	 */
	public void jump(int force) {
		if(jumped) {
			return;
		} else {
			jumped = true;
			setyVel(0);
			setyAcc(force);
		}
		
	}

	/**
	 * determines whether the player can jump
	 * @param level - Level object
	 * @param camera - Camera object
	 * @return boolean
	 */
	public boolean canJump(Level level, Camera camera) {
		return (!isSolidRelativeToTop(0, -1, level, camera) && isSolidRelativeToBelow(0, 0, level, camera));
	}

	/**
	 * returns the x position of the player on the screen
	 * @return Integer
	 */
	public int getScreenX() {
		return screenX;
	}

	/**
	 * sets the x position of this variable
	 * @param screenX - x position of the player on the screen
	 */
	public void setScreenX(int screenX) {
		this.screenX = screenX;
	}

	/**
	 * returns the y position of the player on the screen
	 * @return Integer
	 */
	public int getScreenY() {
		return screenY;
	}

	/**
	 * sets the y position of this variable
	 * @param screenY - y position of the player on the screen
	 */
	public void setScreenY(int screenY) {
		this.screenY = screenY;
	}

	/**
	 * returns the player's health
	 * @return - Integer
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * sets the player's health
	 * @param health - player's new health
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * returns whether the player is immortal
	 * @return boolean
	 */
	public boolean isImmortal() {
		return immortal;
	}

	/**
	 * sets whether the player is immortal
	 * @param immortal - is player immortal
	 */
	public void setImmortal(boolean immortal) {
		this.immortal = immortal;
	}

	/**
	 * tints the player's image when he is immortal / hurt
	 * @param img - player's image
	 * @return tinted Image
	 */
	public BufferedImage tintImage(BufferedImage img) {

		BufferedImage img1 = new BufferedImage(img.getWidth(), img.getHeight(),  BufferedImage.TYPE_INT_ARGB);
		
	    for (int x = 0; x < img.getWidth(); x++) {
	        for (int y = 0; y < img.getHeight(); y++) {

	            Color color = new Color(img.getRGB(x, y), true);

	            color = new Color((int)(Maths.clamp(color.getRed() + color.getRed() * (Renderer.animationNum + 1) * (Renderer.animationNum + 1), 0, 255)), color.getGreen(), color.getBlue(), color.getAlpha());

	            img1.setRGB(x, y, color.getRGB());
	        }
	    }
	    
	    return img1;
	}
	
}
