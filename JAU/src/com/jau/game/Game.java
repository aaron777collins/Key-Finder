/**
 * The main game loop which controls the whole game
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */

package com.jau.game;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game implements Runnable {

	//creating all variables to be initialized now or later depending on what they require
	public static Loader loader = new Loader();
	
	public static SoundClip sound = SoundClip.LEVEL1;
	public static SoundClip keySound = SoundClip.KEY;
	
	public static int MAIN_MENU = 0;
	public static int GAME = 1;
	
	public static final int NUM_OF_LEVELS = 5;
	
	public static int gameState = MAIN_MENU;
		
	public static Game game;
	
	public static boolean dead = false;
	
	private static final boolean EDITING_LEVELS = true;
		
	public static boolean updateEntities = false;
	
	public static int levelNum = 0;
	
	public static String currentLevelName;
	public static Level currentLevel;
	
	private static final int TARGET_REFRESH = 60;
	public static final int TARGET_FPS = 60;
	
	public static Image[] background = new Image[2];
	
	public int blockIdSelected = 0;
	
	public static int HEADER_SIZE;
	
	private KeyChecker keys;
	private MouseChecker mouse;
	
	public boolean initialized = false;
	
	private static String TITLE = "KeyFinder";
	private static JFrame frame;
	public static boolean running = false;
	public static boolean updateLights = true;
	
	public static BlockManager blockManager;
	
	public static Player player;
	public static Camera camera;
	
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public static Renderer renderer;
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public MainMenu menu = new MainMenu(WIDTH, HEIGHT);
	
	public static final double GRAVITY = 0.3;

	public static final double MAX_GRAVITY = 7;
	
	public static int BLOCK_SIZE = WIDTH/25;

	public static boolean hasKey = false;
	
	/**
	 * The game class (main game loop class) is instantiated to exit static context
	 */
	public Game() {
		
		//initializing images/game
		initializeImages();
		initializeGame();
		
		//setting running to true
		running = true;
		
		//starting thread to be the game loop
		Thread thread = new Thread(this);
		thread.start();
		
		
	}
	
	public static void main(String[] args) {
		
		//exiting static context by instantiating the class
		game = new Game();		
	}

	/**
	 * initializes images by creating the block manager (which grabs all the images and stores it once created)
	 */
	private void initializeImages() {
		blockManager = new BlockManager();			
	}


	/**
	 * closes the game - this method was created for the level editor to save the level before closing (this is now part of the closing procedure)
	 */
	protected void closingGame() {
		try {
			//tells the loader to save the current level
			loader.saveLevel(currentLevel, currentLevelName);
		} catch (IOException e) {
			//otherwise prints error
			e.printStackTrace();
		}
		
		//closing everything down
		System.exit(0);
	}


	public void initializeGame() {
		
		//if you are editing levels it tries to create/ load the level
		if (EDITING_LEVELS) {
			
			//asks for level to load
			currentLevelName = JOptionPane.showInputDialog(frame, "What is the name of the level you wish to edit?");

			try {
				
				//makes a level to be what the level loader finds.. otherwise it generates one
				Level level = loader.loadLevel(currentLevelName, blockManager);
				
				//sets the currentLevel to that loaded level
				currentLevel = level;
				//sets the background animation to be whatever image is appropriate for the current level at the animation index
				background[0] = BackgroundManager.getImage(currentLevel.backgroundIndex)[0];
				background[1] = BackgroundManager.getImage(currentLevel.backgroundIndex)[1];
				
			} catch (ClassNotFoundException | IOException e) {
				//generates level because level couldn't be found
				System.out.println("Failed to load level, generating one...");
				
				//asks for number of blocks in width and height
				int numWidth = Integer.parseInt(JOptionPane.showInputDialog(frame, "How many blocks wide would you like your level?"));
				int numHeight = Integer.parseInt(JOptionPane.showInputDialog(frame, "How many blocks high would you like your level?"));

				//sets current level to a new level.. generating it
				currentLevel = new Level(numWidth, numHeight, BLOCK_SIZE).generateLevel(blockManager);
			}
			
		} else {
		
			//if you aren't editing.. which in most cases is true, it sets the current level name to be level0
			currentLevelName = "Level" + levelNum;
			
			try {
				//tries to load level0
				Level level = loader.loadLevel(currentLevelName, blockManager);
				currentLevel = level;
			} catch (ClassNotFoundException | IOException e) {
				//otherwise prints error
				e.printStackTrace();
			}
			
			//sets background to current level's background at proper indexes
			background[0] = BackgroundManager.getImage(currentLevel.backgroundIndex)[0];
			background[1] = BackgroundManager.getImage(currentLevel.backgroundIndex)[1];
			
		}
		
		//makes the player regardless of editing and the camera (from here on doesn't matter about editing)
		player = new Player(0,0, BLOCK_SIZE, BLOCK_SIZE*2,3, 6);
		camera = new Camera(0,0);		
		
		//sets initialized to true
		initialized = true;
		
	}
	
	/**
	 * Sets up the JFrame and adds the key/mouse listeners (plus the window listener for when you close while editing)
	 */
	public void makeFrame() {
		//makes key and mouse listeners
		keys = new KeyChecker();
		mouse = new MouseChecker();
		
		//makes JFrame, adds the renderer (JPanel) to it and adds the listeners
		frame = new JFrame(TITLE);
		frame.add(renderer);
		renderer.setLayout(null);
		frame.pack();
		frame.addKeyListener(keys);
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);
		frame.setSize(1280, 720);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		//if editing levels, manually call our own method for closing instead of closing normally
		if (EDITING_LEVELS) {
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					closingGame();
				}
			});

		
		} else {
			//otherwise if normal playing, exit on close
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		//sets frame to be visible
		frame.setVisible(true);
		
		//creates header size variable to be used later
		HEADER_SIZE = HEIGHT - frame.getContentPane().getHeight() - 5;
		
	}

	/**
	 * main game loop / other small setup
	 */
	public void run() {
		
		//making renderer
		renderer = new Renderer(this);
		
		//making window and adding renderer to screen (must be done here to avoid errors)
		makeFrame();
		
		//makes another thread for the renderer in order to speed up the game
		Thread thread2 = new Thread(renderer);
		thread2.start();
		
		//setting up variables to be used to account for lag
		double lastUpdate = System.nanoTime()/1000000;
		double targetTime = 1000 / TARGET_REFRESH;
		
		//makes quick timer to force the player a couple times to be teleported to spawn
		Timer override = new Timer();
		override.schedule(new TimerTask() {

			int counter = 0;
			
			@Override
			public void run() {

				player.setX(currentLevel.spawnPoint.x);
				player.setY(currentLevel.spawnPoint.y);
				camera.setXOff(currentLevel.spawnPoint.x);
				camera.setYOff(currentLevel.spawnPoint.y);

				
				if (counter>10) {
					override.cancel();
				}
				counter++;
			}
			
		}, 0, 10);
		
		//main game loop starts
		while(running) {
			
			
			//essentially its locking in the frame rate at 60fps and if any lag happens, it will do all the frames that it missed until it catches up
			double now = System.nanoTime()/1000000;
			
			while (now - lastUpdate > targetTime) {
				lastUpdate += targetTime;
				
				//gives renderer a current version of the game class
				renderer.updateGame(game);
			
				//updates everything
				updateAll();
			}
			
		
		}

	}
	
	/**
	 * updates all logic for the game
	 */
	private void updateAll() {
		
		
		//updates the player in general
		player.update();
		
		//specifically updates gravity
		player.updateGravity();
		//checks for the collision of the player
		player.checkCollision(currentLevel, camera, WIDTH, HEIGHT);
		
		//if the game isn't still in main menu (the game preloads the level to make starting the game smoother)
		if (gameState != MAIN_MENU) {
			
			//if editing levels, check for all key bindings to change the blocks
			if (EDITING_LEVELS) {
				
				//for each key, if pressed, change the current block selected to the corresponding block
				if (keys.isOne()) {
					blockIdSelected = BlockManager.AIR;
				}
				if (keys.isTwo()) {
					blockIdSelected = BlockManager.GRASS;
				}
				if (keys.isThree()) {
					blockIdSelected = BlockManager.DIRT;
				}
				if (keys.isFour()) {
					blockIdSelected = BlockManager.BOARDER;
				}
				if (keys.isFive()) {
					blockIdSelected = BlockManager.STONE;
				}
				if (keys.isSix()) {
					blockIdSelected = BlockManager.KEY;
				}
				if (keys.isSeven()) {
					blockIdSelected = BlockManager.SPIKES;
				}
				if (keys.isEight()) {
					blockIdSelected = BlockManager.DOOR;
				}
				if (keys.isNine()) {
					blockIdSelected = BlockManager.SPAWN;
				}
				if (keys.isZero()) {
					blockIdSelected = BlockManager.BSPIKES;
				}
				//pressing e asks which entity block to select and forces the key to be off after to avoid glitches
				if (keys.isE()) {
					keys.setE(false);
					player.setxVel(0);
					player.setyVel(0);
					try {
					blockIdSelected = Integer.parseInt(JOptionPane.showInputDialog(frame, "What entity would you like to select? \n GOOD_CHARLIE = 30 \n BAD_CHARLIE = 31 \n CORRUPT = 32 \n SHADOW = 33 \n EYE = 34 \n BAD_CHARLIE_WEAK = 35" ));
					} catch(Exception e) {
						//if error, set current block selected to air
						blockIdSelected = BlockManager.AIR;
					}
					
				}
				
				//if T is true, ask which dialog trigger block they want
				if (keys.isT()) {
					keys.setT(false);
					player.setxVel(0);
					player.setyVel(0);
					try {
						//due to the mass amount of dialogs, not much was written as it's simpler to check the class for which block you want
						blockIdSelected = Integer.parseInt(JOptionPane.showInputDialog(frame, "What dialog would you like to select? \n dialog1 = 5 \n"));
					} catch(Exception e) {
						//if error, choose air block
						blockIdSelected = BlockManager.AIR;
					}
					
				}
				
				//if b is true, it asks for which level background you want to set
				if (keys.isB()) {
					keys.setB(false);
					player.setxVel(0);
					player.setyVel(0);
					try {
						currentLevel.backgroundIndex = Integer.parseInt(JOptionPane.showInputDialog(frame, "What background index would you like to select? (refer to BackgroundManager class for indexes)"));
					} catch(Exception e) {
						currentLevel.backgroundIndex = 0;
	
					}
					
					try {
						//tests to see if the images exist
						Image test = BackgroundManager.getImage(currentLevel.backgroundIndex)[0];
						Image test1 = BackgroundManager.getImage(currentLevel.backgroundIndex)[1];
	
					} catch(Exception e) {
						//if not, prints error and sets it to mario background
						currentLevel.backgroundIndex = 0;
						System.out.println("error retrieving images, setting to default");
					}
					//else, sets the background to the index selected
					background[0] = BackgroundManager.getImage(currentLevel.backgroundIndex)[0];
					background[1] = BackgroundManager.getImage(currentLevel.backgroundIndex)[1];
	
					
				}
				
				
				//if mouse is clicked
				if (mouse.isClicked()) {
					
					int blockXIndex = (int) ((mouse.getX() + player.getX() + camera.getXOff()) / BLOCK_SIZE);
					int blockYIndex =(int) ((mouse.getY() + player.getY() + camera.getYOff() - HEADER_SIZE) / BLOCK_SIZE);
	
					//check if the block selected is an entity
						if (blockIdSelected >= BlockManager.entityStartIndex) {
							//if so, entities are qued to be updated
							updateEntities = true;
						}
					
					try {
						//tries to set the block to the current block selected
					currentLevel.setBlock(blockXIndex, blockYIndex, blockManager.getBlocks().clone()[blockIdSelected]);
					} catch(Exception e) {
						//otherwise prints out of bounds
						System.out.println("Out of Bounds");
					}
				}
			} 
			
			//if the player isn't dead, check for key presses
			if (!Game.dead) {
			
				//checks for keys and if a block is there.. then if it can move, it moves the player and sets the animation
			
				if (keys.isA() && !player.isSolidRelativeToFeetRight(-1, 0, currentLevel, camera) && !player.isSolidRelativeToFeetRight(-1, -1, currentLevel, camera)) {
					
					player.setxVel(-player.getSpeed());
					if (player.getState() != AnimationLoop.PUNCHING_LEFT && player.getState() != AnimationLoop.PUNCHING_RIGHT) {
						player.setState(AnimationLoop.WALKING_LEFT);
					}
				}
				if (keys.isD() && !player.isSolidRelativeToFeetLeft(1, 0, currentLevel, camera) && !player.isSolidRelativeToFeetLeft(1, -1, currentLevel, camera)) {
					
					player.setxVel(player.getSpeed());
					if (player.getState() != AnimationLoop.PUNCHING_LEFT && player.getState() != AnimationLoop.PUNCHING_RIGHT) {
	
						player.setState(AnimationLoop.WALKING_RIGHT);
					}
	
				}
				
				if (keys.isD() && keys.isW() && player.isSolidRelativeToTop(0, 0, currentLevel, camera)) {
					
	
					player.move(0, 1);
				}
				if (keys.isA() && keys.isW() && player.isSolidRelativeToTop(0, 0, currentLevel, camera)) {
					
	
					player.move(0, 1);
				}
				//checks for jumping aswell and if it can, it sets the animation and moves the player
				if (keys.isW()) {
					if (player.canJump(currentLevel, camera)) {
						player.jump(-player.getJumpForce());
					}
					
					if (player.getState() != AnimationLoop.PUNCHING_LEFT && player.getState() != AnimationLoop.PUNCHING_RIGHT) {
		
						
						player.setState(AnimationLoop.STANDING);
						
						if (keys.isA()) {
							
	
							player.setState(AnimationLoop.STANDING_LEFT);
						}
						if (keys.isD()) {
							
	
							player.setState(AnimationLoop.STANDING_RIGHT);
						}
		
					}
				}
	
			}
			
			//updates the camera (tells the camera to try to focus more on the player since it could have moved)
			camera.update(player, WIDTH, HEIGHT);
			
			try {
				//loops through each entity and updates it
				for (Entity entity : entities) {
					entity.update(player, camera, currentLevel, GRAVITY);
				}
			} catch(Exception e) {
				
			}
		}
		
	}

	/**
	 * called by the key checker directly to check if the player can punch and if so, punches
	 */
	public static void punch() {						
		
		//if the player can't punch, is dead or the player isn't facing sideways, don't continue because it won't work
			if(!Player.canPunch || Game.dead || player.getState() == AnimationLoop.STANDING) {
				return;
			}

			//sets can punch to false
			Player.canPunch = false;
		
			//checks which direction player is facing
			int direction = 0;
			if (player.getState() == AnimationLoop.STANDING_LEFT || player.getState() == AnimationLoop.WALKING_LEFT) {
				direction = 1;
			} 
			
			//for each entity, it sees if it can hurt it
			try {
			for (Entity entity : Game.entities) {
				
				int playerX = (player.getScreenX() - player.getPlayerWidth()/2);
				int playerY = (player.getScreenY() - player.getPlayerHeight()/2);
				int entityX = (int)(entity.getX() - player.getX() - camera.getXOff()); // middle
				int entityY = (int)(entity.getY() - player.getY() - camera.getYOff()) + 1;
				int entityY2 = (int)(entity.getY() - player.getY() - camera.getYOff() + entity.getHeight()) - 1;


				if (Math.abs(playerX - entityX) < Game.BLOCK_SIZE * 1.5 && ((entityY< playerY +player.getPlayerHeight() && entityY > playerY) || (entityY2< playerY +player.getPlayerHeight() && entityY2 > playerY))) {
					entity.hurt(direction);
				}
			}
			}catch(Exception e) {
				//if it can't find an entity that it thought existed, or it deleted one, it goes here
				System.out.println("Couldn't find entity or there's an error.. one got deleted?");
			}
						
			//sets animations to proper direction
			if (direction == 0) {
				player.animationLoop.update(AnimationLoop.PUNCHING_RIGHT);
			}
			if (direction == 1) {
				player.animationLoop.update(AnimationLoop.PUNCHING_LEFT);
			}
			
			//sets timer to allow user to punch after short period of time(prevents unfair punching)
			Timer timer3 = new Timer();
			timer3.schedule(new TimerTask() {

				int counter = 0;

				
				@Override
				public void run() {
					
					if(counter > 0) {
						
						if (Player.direction == 0) {
							player.animationLoop.update(AnimationLoop.STANDING_RIGHT);
						}
						if (Player.direction == 1) {
							player.animationLoop.update(AnimationLoop.STANDING_LEFT);
						}
						
						Player.canPunch = true;
						
						timer3.cancel();
					}

					counter++;
				}
				
				
				
			}, 0, 200);
			
	}
	
	/**
	 * returns whether the game is in editing mode
	 * @return A boolean
	 */
	public static boolean isEditing() {
		return EDITING_LEVELS;
	}
	
	/**
	 * returns the camera object of the game
	 * @return Camera object
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * returns the player object currently
	 * @return Player object
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * changes to the next level
	 */
	public static void nextLevel() {
		
		//sets player to standing and dead to false in the case they died at the door
		player.setState(AnimationLoop.STANDING);
		
		if (dead) {
			dead = false;
		}
		
		//increases level num and updates level name
		levelNum++;
		currentLevelName = "Level" + levelNum;
		
		//if the current level is past the number of levels, you win
		if (levelNum >= NUM_OF_LEVELS + 1) {
			//clears all entities
			entities = new ArrayList<Entity>();
			//shows winscreen
			WinScreen.active = true;
			//stops audio
			sound.stop();
			//plays key sound
			keySound = SoundClip.KEY;
			keySound.play();
			
			//exits out of function
			return;
		}
		
		//otherwise load the next level and set it to the current level
		try {
			Level level = loader.loadLevel(currentLevelName, blockManager);
			currentLevel = level;
			//load current entities
			entities = currentLevel.loadEntities();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		//set player health to 6
		player.setHealth(6);

		//set background to proper background
		background[0] = BackgroundManager.getImage(currentLevel.backgroundIndex)[0];
		background[1] = BackgroundManager.getImage(currentLevel.backgroundIndex)[1];
		
		//resets player to need to find the key
		hasKey = false;

		//teleports the player and reloads the entities to avoid loading errors
		Timer timer2 = new Timer();
		timer2.schedule(new TimerTask() {

			int counter = 0;
			
			@Override
			public void run() {

				if (counter < 2) {
					entities = currentLevel.loadEntities();
				}
				
				if(counter < 5) {
					
					//moving player to spawn several times to override
					player.setX(currentLevel.spawnPoint.x);
					player.setY(currentLevel.spawnPoint.y);
					camera.setXOff(currentLevel.spawnPoint.x);
					camera.setYOff(currentLevel.spawnPoint.y);
					
					//overriding speech to be reset
					DisplayDialog.text = "";
					DisplayDialog.active = false;
					
				} else {
					//if it happened 5 times, exit loop of override
					timer2.cancel();
				}
				
				counter++;
				
			}
			
		}, 0, 10);
		
		//stop sounds
		sound.stop();
		
		//set sound track to proper sound track
		if (levelNum > 0 && levelNum != 5) {
			sound = SoundClip.LEVEL1;
		} else {
			sound = SoundClip.LEVEL5;
		}
		
		//sets it to loop
		sound.getClip().loop(Clip.LOOP_CONTINUOUSLY);
		//plays the sound
		sound.play();
		
	}


	/**
	 * restarts the level
	 */
	public static void restartLevel() {
		
		//closes lose screen
		LoseScreen.active = false;
		
		//sets player health to 6
		player.setHealth(6);
		
		//loads level and entities
		try {
			Level level = loader.loadLevel(currentLevelName, blockManager);
			currentLevel = level;
			entities = currentLevel.loadEntities();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			//TODO:
			//make this the you won screen
		}
		
		//resets being dead or having the key
		hasKey = false;
		dead = false;

		//sets timer to override and teleport the player / load entities properly / set health / clear dialogs
		Timer timer2 = new Timer();
		timer2.schedule(new TimerTask() {

			int counter = 0;
			
			@Override
			public void run() {
				
				if (counter < 2) {
					entities = currentLevel.loadEntities();
				}

				if(counter < 5) {
					
					sound.stop();
					
					if (levelNum > 0 && levelNum != 5) {
						sound = SoundClip.LEVEL1;
					} else {
						sound = SoundClip.LEVEL5;
					}
					
					sound.getClip().loop(Clip.LOOP_CONTINUOUSLY);

					if (counter >3) {
						sound.play();
					}
					
					player.setState(AnimationLoop.STANDING);
					player.setHealth(6);
					Game.dead = false;
					
					
					//moving player to spawn several times to override
					player.setX(currentLevel.spawnPoint.x);
					player.setY(currentLevel.spawnPoint.y);
					camera.setXOff(currentLevel.spawnPoint.x);
					camera.setYOff(currentLevel.spawnPoint.y);
					
					//overriding speech to be reset
					DisplayDialog.text = "";
					DisplayDialog.active = false;
					
				} else {
					timer2.cancel();
				}
				
				counter++;
				
			}
			
		}, 0, 10);
		
	}

	
	
}
	

