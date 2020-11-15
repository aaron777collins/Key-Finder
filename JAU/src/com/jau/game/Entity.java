package com.jau.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import com.jau.maths.Maths;

/**
 * Entity is a generalized enemy class and uses the player's animations.
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class Entity {
	
	//making variables
	public boolean initialized, jumped = false;
	private int x, y, width, height, speed, jumpForce;
	public double xVel, yVel, xAcc, yAcc;
	public int direction = 0;
	public boolean jumpQued = false;
	public String type = "normal";
	
	int knockBack = 10;
	
	public int health = 6;
	public boolean immortal = false;
	
	private int state = AnimationLoop.STANDING;
	
	Point[] previousPositions = new Point[5];
	
	private int loopCounter = 0;
	private int countMax = 10;
	
	AnimationLoop animationLoop;
	AnimationManager animationM;
	
	/**
	 * Making an Entity to patrol the level
	 * @param x - the x position
	 * @param y - the y position
	 * @param width - width of the entity
	 * @param height - height of the entity
	 * @param speed - speed of the entity
	 * @param jumpForce - jump force of the entity
	 */
	public Entity(int x, int y, int width, int height, int speed, int jumpForce) {
		//setting up variables
		this.x = x;
		this.y = y;
		this.xVel = 0;
		this.yVel = 0;
		this.xAcc = 0;
		this.yAcc = 0;
		this.width = width;
		this.height = height;
		
		this.speed = speed;
		this.jumpForce = jumpForce;
		
		//loading all animations
		loadAnimations();
		
	}

	/**
	 * returns whether the entity has been initialized yet
	 * @return Boolean
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * returns whether the entity is jumping at the moment
	 * @return Boolean
	 */
	public boolean isJumped() {
		return jumped;
	}

	/**
	 * returns the speed of the entity
	 * @return Integer
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * returns the jump force of the entity
	 * @return Integer
	 */
	public int getJumpForce() {
		return jumpForce;
	}

	/**
	 * returns the x velocity
	 * @return Double
	 */
	public double getxVel() {
		return xVel;
	}

	/**
	 * returns the y velocity
	 * @return Double
	 */
	public double getyVel() {
		return yVel;
	}

	/**
	 * returns the x acceleration
	 * @return Double
	 */
	public double getxAcc() {
		return xAcc;
	}

	/**
	 * returns the y acceleration
	 * @return Double
	 */
	public double getyAcc() {
		return yAcc;
	}
	
	/**
	 * Moves the entity but checks for collision first
	 * @param dx - amount in the X to be moved (positive for right, negative for left)
	 * @param toJump - whether the player should be trying to jump
	 * @param level - Level object
	 * @param player - Player object
	 * @param camera - Camera object
	 */
	public void moveEntity(int dx, boolean toJump, Level level, Player player, Camera camera) {
		
		//checks which direction the entity is trying to move, activates the right animation and then moves the entity
		if (dx > 0) {
			if (!isSolidRelativeToFeetLeft(1, 0, level, player, camera) && !isSolidRelativeToFeetLeft(1, -1, level, player, camera) && !isSolidRelativeToFeetLeft(1, -1, level, player, camera)) {
				move(dx, 0);
				setState(AnimationLoop.WALKING_RIGHT);
			}
		} else if (dx < 0) {
			if (!isSolidRelativeToFeetRight(-1, 0, level, player, camera) && !isSolidRelativeToFeetRight(-1, -1, level, player, camera) && !isSolidRelativeToFeetRight(-1, -1, level, player, camera)) {
				move(dx, 0);
				setState(AnimationLoop.WALKING_LEFT);
			}
		}
		
		//if it wants to jump, it checks if it can and jumps if so (with animation)
		if (toJump) {
			if (canJump(level, player, camera)) {
				jump(-jumpForce);
			}
			
			if (dx == 0) {
				setState(AnimationLoop.STANDING);
			} else if(dx > 0) {
				setState(AnimationLoop.STANDING_RIGHT);
			} else if(dx < 0) {
				setState(AnimationLoop.STANDING_LEFT);
			}
		}
		
		
	}

	/**
	 * sets the animation state of the entity
	 * @param state - animation state of the entity(int)
	 */
	private void setState(int state) {
		this.state = state;	
	}
	
	/**
	 * makes the player jump
	 * @param force - force of jump (integer)
	 */
	public void jump(int force) {
		if(jumped) {
			return;
		} else {
			jumped = true;
			yVel = 0;
			yAcc = force;
		}
		
	}
	
	/**
	 * hurts the entity
	 * @param side - which side the entity is being hurt from (0 - left, 1 - right)
	 */
	public void hurt(int side) {
		
		//side == 0 = left
		//side == 1 = right
		
		//checks if the entity is immortal
		if (!immortal) {
			
			//if not, the entity gets hurt and knocked back
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
				//if the entity has no health, remove it from the game
				health = 0;
				
				Game.entities.remove(this);
			}
			
			//makes the entity immortal for 1.5 seconds
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
	
	/**
	 * moves the entity (forced)
	 * @param dx - amount in x
	 * @param dy - amount in y
	 */
	public void move(int dx, int dy) {
		//moves entity
		x += dx;
		y += dy;
		
		//stores previous positions
		previousPositions[0] = new Point(x, y); 
		
		for (int i = previousPositions.length-1; i> 0; i--) {
			
			previousPositions[i] = previousPositions[i-1];
		}
	}
	
	/**
	 * returns the x position of the entity
	 * @return Integer
	 */
	public int getX() {
		return x;
	}

	/**
	 * returns the y position of the entity
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * returns the width of the entity
	 * @return Integer
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * returns the height of the entity
	 * @return Integer
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * moves the player using "AI"
	 * @param level - Level object
	 * @param player - Player object
	 * @param camera - Camera object
	 */
	public void makeDecision(Level level, Player player, Camera camera) {
		
		//changes direction if there is a wall
		if((isSolidRelativeToFeetLeft(1,0, level, player, camera) && isSolidRelativeToFeetLeft(1,-1, level, player, camera))) {
			direction = 1;
		}
		
		if ((isSolidRelativeToFeetRight(-1,0, level, player, camera) && isSolidRelativeToFeetRight(-1,-1, level, player, camera))) {
			direction = 0;
		}
		
		boolean shouldJump = false;
		
		//checks whether it should try to jump (if there is a block to jump over)
		if (isSolidRelativeToFeet(1,0, level, player, camera)  && !isSolidRelativeToFeetLeft(1,-1, level, player, camera)) {
			shouldJump = true;
		}
		if (isSolidRelativeToFeet(-1,0, level, player, camera) && !isSolidRelativeToFeetRight(-1,-1, level, player, camera)) {
			shouldJump = true;
		}
				
		//checks if direction is 0, if so, tries to move right by "speed", otherwise, tries to move left by "speed"
		moveEntity((direction == 0) ? speed : -speed, shouldJump, level, player, camera);
		
	}
	
	/**
	 * updates the entity
	 * @param player - Player object
	 * @param camera - Camera object
	 * @param level - Level object
	 * @param gravity - gravity of the entity
	 */
	public void update(Player player, Camera camera, Level level, double gravity) {
		
		//increments a counter
		loopCounter++;
		
		//if greater than a threshold, update the animation to go to the next frame
		if (loopCounter > countMax) {
			loopCounter = 0;
			animationLoop.update(state);
		}
		
		/**
		 * if the x velocity of the entity is less than 0.2, set the entity from walking in a direction to standing
		 */
		if (Math.abs(xVel) < 0.2) {
			if (state == AnimationLoop.WALKING_RIGHT) {
				state = AnimationLoop.STANDING_RIGHT;
			}
			if (state == AnimationLoop.WALKING_LEFT) {
				state = AnimationLoop.STANDING_LEFT;
			}
		}
		
		//applies gravity to the entity
		applyGravity(gravity);
		//checks for collision
		checkCollision(level, player, camera, width, height);

		//tries to move the entity
		makeDecision(level, player, camera);
		
		//updates velocity, position, acceleration
		xVel += xAcc;
		yVel += yAcc;
		move((int)xVel, (int)yVel);
		xAcc = 0;
		yAcc = 0;
		
	}
	
	/**
	 * checks for if the entity is colliding with the level
	 * @param level - Level object
	 * @param player - Player object
	 * @param camera - Camera object
	 * @param WIDTH - width of the screen
	 * @param HEIGHT - height of the screen
	 */
	public void checkCollision(Level level, Player player, Camera camera, int WIDTH, int HEIGHT) {
		
		updateFriction();
		
		int blockSize = level.getBLOCK_SIZE();
		double cameraX = camera.getXOff();
		double cameraY = camera.getYOff();
		
		//gets the position of the entity on screen
		//entity X is the left of the player and entity Y is the bottom of the player
		int entityX = (int) (x - player.getY() - cameraX) + 5; //the + 5 and -5 allows for the entity to fall though 1 space blocks (without hitting the sides)
		
		int entityY = (int) (y - player.getY() - cameraY) + height;
		//entity is the right
		int entityX2 = (int) (x - player.getY() - cameraX) + width - 5;
		
		boolean cancelSide = false;
		
		
		//checks several places around the entity and checks for barriers, then handles them appropriately
		
		
		if (isSolidRelativeToTop(0, 0, level, player, camera)) { 
			//checks to side (top section) of player

			if (yVel < 0) {
				yVel = 0;
				xVel = 0;
				cancelSide = true;
			}
					
		
		}
		
		//bottom collision
		if (isSolidRelativeToBelow(0,0, level, player, camera)) {
			
			if (yVel > 0) {
				yAcc =0;
				yVel =0;
			
			}
			
			int off = getBlockYOffset((int)(entityY + camera.getYOff() + player.getY()), blockSize);
			
			if (off > 1) {
				//prevent glitching above floors
				move(0, -1);
			}
			
			if (yVel >= 0) {
				jumped = false;
			}
		}
		
		//fixing clipping through walls when jumping up straight walls (right)
		if (isSolidRelativeToTop(1, 0, level, player, camera) && isSolidRelativeToTop(1, -1, level, player, camera) && isSolidRelativeToBelow(0, -2, level, player, camera) && isSolidRelativeToBelow(0, -1, level, player, camera)) {
			move(-speed, 0);
		}
		
		//fixing clipping through walls when jumping up straight walls (left)
		if (isSolidRelativeToTop(-1, 0, level, player, camera) && isSolidRelativeToTop(-1, -1, level, player, camera) && isSolidRelativeToBelow(0, -2, level, player, camera) && isSolidRelativeToBelow(0, -1, level, player, camera)) {
			move(speed, 0);
		}
		
	
	}

	/**
	 * sets the position of the entity by force
	 * @param x - x position of the entity
	 * @param y - y position of the entity
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		
	}

	/**
	 * updates the friction of the entity
	 */
	private void updateFriction() {
		xVel = xVel/2;
	}

	/**
	 * gets the offset of the entity from the top of the block he is currently on
	 * @param y - y position of the player
	 * @param blockSize - blockSize of the level
	 * @return yOffset (integer)
	 */
	private int getBlockYOffset(int y, int blockSize) {
		return y % blockSize;
	}

	/**
	 * checks if the block is solid below it
	 * @param xOff - how many blocks to the right or left to offset from the block that is below the entity
	 * @param yOff - how many blocks down or up to offset from the block that is below the entity
	 * @param level - Level object
	 * @param player - Player object
	 * @param camera - Camera object
	 * @return Boolean
	 */
	public boolean isSolidRelativeToBelow(int xOff, int yOff, Level level, Player player, Camera camera) {
		
		boolean solid = false;
		
		//gets block indexes below the player
		int[] indexes = getBlockIndexBelowEntity(player, level, camera);
		
		//returns whether either of them are solid
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
	 * checks if the block is solid at the entity's feet
	 * @param xOff - how many blocks to the right or left to offset from the block that is at the feet of the entity
	 * @param yOff - how many blocks down or up to offset from the block that is at the feet of the entity
	 * @param level - Level object
	 * @param player - Player object
	 * @param camera - Camera object
	 * @return Boolean
	 */
	public boolean isSolidRelativeToFeet(int xOff, int yOff, Level level, Player player, Camera camera) {
		
		boolean solid = false;
		
		//gets block indexes at entity's feet
		int[] indexes = getBlockIndexAtFeet(level, player, camera);
		
		//returns whether either block is solid
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
	 * returns whether the entity can jump or not 
	 * @param level - Level object
	 * @param player - Player object
	 * @param camera - Camera object
	 * @return Boolean
	 */
	public boolean canJump(Level level, Player player, Camera camera) {
		return (!isSolidRelativeToTop(0, -1, level, player, camera) && isSolidRelativeToBelow(0, 0, level, player, camera));
	}
	
	/**
	 * returns whether the block is solid to the right foot
	 * @param xOff - how many blocks to right or left to offset answer
	 * @param yOff - how many blocks down or up to offset answer
	 * @param level - Level object
	 * @param player - Player object
	 * @param camera - Camera object
	 * @return Boolean
	 */
	public boolean isSolidRelativeToFeetRight(int xOff, int yOff, Level level, Player player, Camera camera) {
		
		boolean solid = false;
		
		int[] indexes = getBlockIndexAtFeet(level, player, camera);
		
		try {
			if (level.getBlocks()[indexes[2] + xOff][indexes[3] + yOff].isSolid()) {
				solid = true;
			}
		} catch(Exception e) {
			
		}
		return solid;
	
	}
	

	/**
	 * returns whether the block is solid to the left foot
	 * @param xOff - how many blocks to right or left to offset answer
	 * @param yOff - how many blocks down or up to offset answer
	 * @param level - Level object
	 * @param player - Player object
	 * @param camera - Camera object
	 * @return Boolean
	 */
	public boolean isSolidRelativeToFeetLeft(int xOff, int yOff, Level level, Player player, Camera camera) {
		
		boolean solid = false;
		
		int[] indexes = getBlockIndexAtFeet(level, player, camera);
		
		try {
			if (level.getBlocks()[indexes[0] + xOff][indexes[1] + yOff].isSolid()) {
				solid = true;
			}
		} catch(Exception e) {
			
		}
		return solid;
	
	}
	

	/**
	 * returns whether the block is solid to the top of the entity
	 * @param xOff - how many blocks to right or left to offset answer
	 * @param yOff - how many blocks down or up to offset answer
	 * @param level - Level object
	 * @param player - Player object
	 * @param camera - Camera object
	 * @return Boolean
	 */
	public boolean isSolidRelativeToTop(int xOff, int yOff, Level level, Player player, Camera camera) {
		
		boolean solid = false;
		
		int[] indexes = getBlockIndexAboveEntity(level, player, camera);
		
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
	 * returns whether the block is solid below entity
	 * @param xOff - how many blocks to right or left to offset answer
	 * @param yOff - how many blocks down or up to offset answer
	 * @param level - Level object
	 * @param player - Player object
	 * @param camera - Camera object
	 * @return Boolean
	 */
	private int[] getBlockIndexBelowEntity(Player player, Level level, Camera camera) {
		
		int[] indexes = new int[4];
		
		int blockSize = level.getBLOCK_SIZE();
		double cameraX = camera.getXOff();
		double cameraY = camera.getYOff();
		
		int entityX = (int) (x - player.getX() - cameraX) + 5; //the + 5 and -5 allows for the entity to fall though 1 space blocks (without hitting the sides)
		int entityY = (int) (y - player.getY() - cameraY) + height;
		
		//playerX2 is the right of the player - 5 (to allow to fall)
		int entityX2 = (int) (x - player.getX() - cameraX + width) - 5; //the + 5 and -5 allows for the entity to fall though 1 space blocks (without hitting the sides)
		
		//int blockX = (int)((i * blockSize) - x - cameraX);
		// becomes when solving for the index
		//(playerX + cameraX + x) / blockSize = i
				
		
		int blockXIndex = (int)(entityX + player.getX() + cameraX) / blockSize;
		int blockYIndex = (int)(entityY + player.getY() + cameraY) / blockSize;

		indexes[0] = blockXIndex;
		indexes[1] = blockYIndex;
		
		int blockX2Index = (int)(entityX2 + player.getX() + cameraX) / blockSize;
		
		indexes[2] = blockX2Index;
		indexes[3] = blockYIndex;
		
		
		return indexes; 
	}
	
	/**
	 * returns the block index at feet of entity
	 * @param xOff - how many blocks to right or left to offset answer
	 * @param yOff - how many blocks down or up to offset answer
	 * @param level - Level object
	 * @param player - Player object
	 * @param camera - Camera object
	 * @return int[]
	 */
	public int[] getBlockIndexAtFeet(Level level, Player player, Camera camera) {
			
		int[] indexes = new int[4];
		
		int blockSize = level.getBLOCK_SIZE();
		double cameraX = camera.getXOff();
		double cameraY = camera.getYOff();
		
		int entityX = (int) (x - player.getX() - cameraX) + 5; //the + 5 and -5 allows for the entity to fall though 1 space blocks (without hitting the sides)
		int entityY = (int) (y - player.getY() - cameraY) + height - height/4;
		
		//playerX2 is the right of the player - 5 (to allow to fall)
		int entityX2 = (int) (x - player.getX() - cameraX + width) - 5; //the + 5 and -5 allows for the entity to fall though 1 space blocks (without hitting the sides)
		
		//int blockX = (int)((i * blockSize) - x - cameraX);
		// becomes when solving for the index
		//(playerX + cameraX + x) / blockSize = i
				
		
		int blockXIndex = (int)(entityX + player.getX() + cameraX) / blockSize;
		int blockYIndex = (int)(entityY + player.getY() + cameraY) / blockSize;

		indexes[0] = blockXIndex;
		indexes[1] = blockYIndex;
		
		int blockX2Index = (int)(entityX2 + player.getX() + cameraX) / blockSize;
		
		indexes[2] = blockX2Index;
		indexes[3] = blockYIndex;
		
		
		return indexes; 
		}
	
	/**
	 * returns the block index above entity
	 * @param xOff - how many blocks to right or left to offset answer
	 * @param yOff - how many blocks down or up to offset answer
	 * @param level - Level object
	 * @param player - Player object
	 * @param camera - Camera object
	 * @return int[]
	 */
	private int[] getBlockIndexAboveEntity(Level level, Player player, Camera camera) {
		
	int[] indexes = new int[4];
	
	int blockSize = level.getBLOCK_SIZE();
	double cameraX = camera.getXOff();
	double cameraY = camera.getYOff();
	
	int entityX = (int) (x - player.getX() - cameraX) + 5; //the + 5 and -5 allows for the entity to fall though 1 space blocks (without hitting the sides)
	int entityY = (int) (y - player.getY() - cameraY);
	
	//playerX2 is the right of the player - 5 (to allow to fall)
	int entityX2 = (int) (x - player.getX() - cameraX + width) - 5; //the + 5 and -5 allows for the entity to fall though 1 space blocks (without hitting the sides)
	
	//int blockX = (int)((i * blockSize) - x - cameraX);
	// becomes when solving for the index
	//(playerX + cameraX + x) / blockSize = i
			
	
	int blockXIndex = (int)(entityX + player.getX() + cameraX) / blockSize;
	int blockYIndex = (int)(entityY + player.getY() + cameraY) / blockSize;

	indexes[0] = blockXIndex;
	indexes[1] = blockYIndex;
	
	int blockX2Index = (int)(entityX2 + player.getX() + cameraX) / blockSize;
	
	indexes[2] = blockX2Index;
	indexes[3] = blockYIndex;
	
	
	return indexes; 
	}

	/**
	 * apply's gravity to entity 
	 * @param gravity - Gravity of game
	 */
	public void applyGravity(double gravity) {
		if (yVel + gravity < Game.MAX_GRAVITY) {
			yAcc = gravity;		
			
		}
	}
	
	/**
	 * loads the entity's animations
	 */
	public void loadAnimations() {
		
		Animation standing = new Animation("res/images/player_standing", 1);
		Animation standingRight = new Animation("res/images/player_standingRight", 1);
		Animation standingLeft = new Animation("res/images/player_standingLeft", 1);
		Animation walkingRight = new Animation("res/images/player_walkingRight", 4);
		Animation walkingLeft = new Animation("res/images/player_walkingLeft", 4);
		
		animationM = new AnimationManager(standing, standingRight, standingLeft, walkingRight, walkingLeft, standing, standing, standing);
		animationLoop = new AnimationLoop(animationM);
		
	}

	/**
	 * draws the entity on screen
	 * @param g - Graphics object
	 * @param player - Player object 
	 * @param camera - Camera object
	 */
	public void draw(Graphics g, Player player, Camera camera) {
		
		if (immortal) {
			Image img = tintImage((BufferedImage)(animationLoop.getImage()));
			if ((int)(x - player.getX() - camera.getXOff()) > -200 && (int)(x - player.getX() - camera.getXOff()) < Game.WIDTH + 200 && (int)(y - player.getY() - camera.getYOff()) > -200 && (int)(y - player.getY() - camera.getYOff()) < Game.HEIGHT + 200) {
				
				g.drawImage(img, (int)(x - player.getX() - camera.getXOff()), (int)(y - player.getY() - camera.getYOff()), width, height, null);
			
			}
		} else {
			if ((int)(x - player.getX() - camera.getXOff()) > -200 && (int)(x - player.getX() - camera.getXOff()) < Game.WIDTH + 200 && (int)(y - player.getY() - camera.getYOff()) > -200 && (int)(y - player.getY() - camera.getYOff()) < Game.HEIGHT + 200) {
			
				g.drawImage(animationLoop.getImage(), (int)(x - player.getX() - camera.getXOff()), (int)(y - player.getY() - camera.getYOff()), width, height, null);
			
			}
		}
	}
	
	/**
	 * tints the image red when the entity is hurt
	 * @param img - regular img that would be displayed
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
