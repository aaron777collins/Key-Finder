
package com.jau.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import com.jau.maths.Maths;

/**
 * The renderer for all graphics processing
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class Renderer extends JPanel implements Runnable {
	
	//making all variables and constants
	Game game;
	
	public static boolean showDeadLoop = true;
	
	public HeartManager heartM = new HeartManager(0, 0);
	
	public LoseScreen loseScreen;
	public WinScreen winScreen;
	
	public static int animationNum = 0;
		
	public static final int SHADOW_RESOLUTION_X = Game.WIDTH/20;
	public static final int SHADOW_RESOLUTION_Y = Game.HEIGHT/20;
	
	public static DisplayDialog DBox = new DisplayDialog(Dialogs.intro1);
	
	public static final double ambientLight = 0; //lower is lighter
	
	public static final int SHADOW_BLOCK_WIDTH = Game.WIDTH/SHADOW_RESOLUTION_X;
	public static final int SHADOW_BLOCK_HEIGHT = Game.HEIGHT/SHADOW_RESOLUTION_Y;
		
	public double[][] shadows;
	
	
	//lights aren't used anymore but are still semi implemented.. they just aren't active
	ArrayList<Light> lights = new ArrayList<Light>();
	
	LightManager lightM;
	
	/**
	 * makes renderer but used for being added to JFrame
	 * @param game - Game class that runs game
	 */
	public Renderer(Game game) {
		
		//stores the game class to be references
		this.game = game;
		
		//makes timer to flip the background index every half a second
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				animationNum = (animationNum == 0) ? 1 : 0;
			}
			
		}, 0, (long)500);
		
	}

	/**
	 * Initializes and runs the renderer
	 */
	@Override
	public void run() {


		//this code from here
		shadows = new double[SHADOW_RESOLUTION_X][SHADOW_RESOLUTION_Y];
		
		lights.add(new Light(500, 100 + 500, 3));
		lights.add(new Light(500 + 1000, 100 + 500, 3));
		
		lightM = new LightManager(shadows, lights, game.getPlayer(), game.getCamera());
		//to here isn't used at the moment but would be used for adding lighting (TODO:?)
		
		double lastUpdate = System.nanoTime()/1000000;
		double targetTime = 1000 / Game.TARGET_FPS;
		
		//loads entities
		Game.entities = Game.currentLevel.loadEntities();
		
		//initializes win and lose screen with renderer class
		loseScreen = new LoseScreen(this);
		winScreen = new WinScreen(this);
		
		//renderer loop 
		while(Game.running) {

			//catches up on any lag that may have made it skip a frame and renders it
			double now = System.nanoTime()/1000000;
			
			while (now - lastUpdate > targetTime) {
				lastUpdate += targetTime;
				
				//checks whether entities need to be respawned due to editing
				checkUpdateEntities();
				//updates lights.. not currently in game but it was in development
				lightM.update();
				//repaints the screen
				this.repaint(); 
			}
			
		}
		
	}

	/**
	 * if entities need to be updated, it sets variable to false, clears the entities and loads the entities again with the new spawners
	 */
	private void checkUpdateEntities() {
		if (Game.updateEntities) {
			Game.updateEntities = false;
			Game.entities.clear();
			Game.entities = Game.currentLevel.loadEntities();
		}
	}

	/**
	 * paints the screen
	 */
	@Override
	public void paint(Graphics g) {
		
		//if in the main menu, draw main menu
		if (Game.gameState == Game.MAIN_MENU) {
			
			game.menu.draw(g);
			
		} else {
			//otherwise render the game
			if (WinScreen.active) {
				//if the player won, render win screen instead and don't render anything else
				winScreen.draw(g);
				return;
			}
		
			
			//would clear screen but background clears it anyways so this isn't being used
			//g.clearRect(0, 0, Game.WIDTH, Game.HEIGHT);
			
			//draws the background at the current animation index
			g.drawImage(Game.background[animationNum], 0, 0, Game.WIDTH, Game.HEIGHT, null);
			
			//getting values to avoid position changes during rendering
			double cameraX = game.getCamera().getXOff();
			double cameraY = game.getCamera().getYOff();
			double playerX = game.getPlayer().getX();
			double playerY = game.getPlayer().getY();
			
			//calculates which blocks on the map to render
			
			//formula for getting block in top left just out of screen:
			int blockXIndexStart = (int)(-Game.currentLevel.getBLOCK_SIZE()*2 + playerX + cameraX) / Game.currentLevel.getBLOCK_SIZE();
			int blockYIndexStart = (int)(-Game.currentLevel.getBLOCK_SIZE()*2 + playerY + cameraY) / Game.currentLevel.getBLOCK_SIZE();
			
			int blockXIndexEnd = blockXIndexStart + (Game.WIDTH / Game.BLOCK_SIZE);
			int blockYIndexEnd = blockYIndexStart + (Game.HEIGHT / Game.BLOCK_SIZE);
			
			//renders only blocks or entities that are in range
			for (int i = blockXIndexStart; i<blockXIndexEnd + 5; i++) {
				
				for (int j = blockYIndexStart; j<blockYIndexEnd + 5; j++) {
					
					
					try {
					//renders all blocks/entities in their own proper way
					//if air
					if(Game.currentLevel.getBlocks()[i][j].getId() == 0) {
						//do nothing
					} else if(Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.KEY) {
						Image img = Game.currentLevel.getBlocks()[i][j].getImg();
						g.drawImage(img, (int)((i * Game.BLOCK_SIZE) - playerX - cameraX), (int)((j * Game.BLOCK_SIZE) - playerY - cameraY + (Game.BLOCK_SIZE / 1.5) / 2), Game.BLOCK_SIZE, (int)(Game.BLOCK_SIZE / 1.5), null);
						
					} else if(Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.DOOR) {
						Image img = Game.currentLevel.getBlocks()[i][j].getImg();
						g.drawImage(img, (int)((i * Game.BLOCK_SIZE) - playerX - cameraX), (int)((j * Game.BLOCK_SIZE) - playerY - cameraY), Game.BLOCK_SIZE, Game.BLOCK_SIZE*2, null);
						
					} else if(Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.SPIKES || Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.BSPIKES) {
						Image img = Game.currentLevel.getBlocks()[i][j].getImg();
						g.drawImage(img, (int)((i * Game.BLOCK_SIZE) - playerX - cameraX), (int)((j * Game.BLOCK_SIZE) - playerY - cameraY - ((int)(Game.BLOCK_SIZE * 1.5) - Game.BLOCK_SIZE)), Game.BLOCK_SIZE, (int)(Game.BLOCK_SIZE * 1.5), null);
					}else if(Game.currentLevel.getBlocks()[i][j].getId() >= BlockManager.entityStartIndex && Game.currentLevel.getBlocks()[i][j].getId() != BlockManager.EYE) {
						//otherwise draw players
						if (Game.isEditing()) {
							Image img = Game.currentLevel.getBlocks()[i][j].getImg();
							g.drawImage(img, (int)((i * Game.BLOCK_SIZE) - playerX - cameraX), (int)((j * Game.BLOCK_SIZE) - playerY - cameraY), Game.BLOCK_SIZE, Game.BLOCK_SIZE * 2, null);
						}
						
					} else {
					//otherwise draw blocks
						
						//if it is dialog boxes, certain spawners not excluded above or anything not meant to be drawn, don't draw it
						if((Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.EYE || Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.SPAWN
								
								|| Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.DIALOG1
								|| Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.DIALOG2
								|| Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.DIALOG3
								|| Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.DIALOG4
								|| Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.DIALOG5
								|| Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.DIALOG6
								|| Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.DIALOG7
								|| Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.DIALOG8
								|| Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.DIALOG9
								|| Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.DIALOG10
								|| Game.currentLevel.getBlocks()[i][j].getId() == BlockManager.DIALOG11

								) && !Game.isEditing()) {
	
						} else {
							//otherwise draw the standard blocks
							Image img = Game.currentLevel.getBlocks()[i][j].getImg();
							g.drawImage(img, (int)((i * Game.BLOCK_SIZE) - playerX - cameraX), (int)((j * Game.BLOCK_SIZE) - playerY - cameraY), Game.BLOCK_SIZE, Game.BLOCK_SIZE, null);
						}
	
					} 
					
					} catch(Exception e) {
						
					}
				}
				
			}	
				
			//renders the player
			game.getPlayer().render(g, game.getCamera().getXOff(), game.getCamera().getYOff(), WIDTH, HEIGHT);
			
			//draws the lighting..except we aren't using lighting so this does nothing at the moment
			try {
			for (int i = 0; i<SHADOW_RESOLUTION_X; i++) {
				for (int j = 0; j<SHADOW_RESOLUTION_Y; j++) {
					if (shadows[i][j] > 0) {
						g.setColor(new Color(0f,0f,0f, (float)(Maths.clamp(shadows[i][j], 0f, 1f))));
						g.fillRect(i * SHADOW_BLOCK_WIDTH, j * SHADOW_BLOCK_HEIGHT,  SHADOW_BLOCK_WIDTH, SHADOW_BLOCK_HEIGHT);
					}
				}
			}
			}catch(Exception e) {
				System.out.println("Error rendering Shadows");
				if (shadows == null) {
					System.out.println("(Shadows haven't been generated.. printing black screen)");
	
				}
				
				g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
				
			}
			
			//draws all active entities (not spawners)
			try {
			for (Entity entity : Game.entities) {
				entity.draw(g, game.getPlayer(), game.getCamera());
			}
			} catch(Exception e) {
				System.out.println("Error drawing entity.. possible that you added a new one or deleted one");
			}
				
			if (DisplayDialog.active) {
				//if there is a dialog being shown, draw it
				DBox.draw(g, game.getPlayer(), game.getCamera(), Game.currentLevel);
			}
			
			//draw the hearts in the top left
			try {
			heartM.draw(game, g);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			//checks if player is dead, shows animation of death and then shows lose screen
			if (Game.dead) {
				
				if (showDeadLoop) {
					
				} else {				
					LoseScreen.active = true;
					loseScreen.draw(g);
				}
			} else {
				LoseScreen.active = false;
				showDeadLoop = true;
			}
		}
	}

	/**
	 * updates the renderer's copy of the game object
	 * @param game2 - the current game object
	 */
	public void updateGame(Game game2) {
		game = game2;
	}
	
}
