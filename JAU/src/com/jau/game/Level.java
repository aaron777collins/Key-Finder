package com.jau.game;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Holds level information
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class Level {
	
	private Block[][] blocks;
	private int numWidth, numHeight, BLOCK_SIZE;
	public int backgroundIndex = 0;
	public Point spawnPoint = new Point(200, 200);

	/**
	 * makes level object
	 * @param numWidth - number of blocks in width
	 * @param numHeight - number of blocks in height
	 * @param BLOCK_SIZE - block size
	 */
	public Level(int numWidth, int numHeight, int BLOCK_SIZE) {
		
		this.numWidth = numWidth;
		this.numHeight = numHeight;
		this.BLOCK_SIZE = BLOCK_SIZE;
		
	}
	
	/**
	 * returns the block size
	 * @return Integer
	 */
	public int getBLOCK_SIZE() {
		return BLOCK_SIZE;
	}

	/**
	 * returns the block array
	 * @return Block[][]
	 */
	public Block[][] getBlocks() {
		return blocks;
	}

	/**
	 * sets the block array
	 * @param blocks - Block array to be set to
	 */
	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks.clone();
	}

	/**
	 * generates level
	 * @param blockManager - Block manager with all types of blocks
	 * @return Level object
	 */
	public Level generateLevel(BlockManager blockManager) {
		
		Block[][] blocks = new Block[numWidth][numHeight];
		
		for (int i = 0; i<numWidth; i++) {
			for (int j = 0; j<numHeight; j++) {
				
				blocks[i][j] = blockManager.getBlocks().clone()[BlockManager.AIR];
			
				if (j == numHeight-25) {
					blocks[i][j] = blockManager.getBlocks().clone()[BlockManager.GRASS];
				}
				if (j > numHeight-25) {
					blocks[i][j] = blockManager.getBlocks().clone()[BlockManager.DIRT];
				}

				
				
				
				if (j >= numHeight-1) {
					blocks[i][j] = blockManager.getBlocks().clone()[BlockManager.BOARDER];
				}
				
				if (i == 0 || i == numWidth-1 || j == 0) {
					blocks[i][j] = blockManager.getBlocks().clone()[BlockManager.BOARDER];
				}
				
			
			}
		}
		
		Level newLevel = new Level(numWidth, numHeight, BLOCK_SIZE);
		
		newLevel.setBlocks(blocks.clone());
				
		return newLevel;
	}

	/**
	 * sets a block at a specific index to a certain block
	 * @param i - i index
	 * @param j - j index
	 * @param block - block to be set
	 */
	public void setBlock(int i, int j, Block block) {
		blocks[i][j] = block;
		
	}

	/**
	 * prints out the level to be saved and converted later
	 */
	@Override
	public String toString() {
		String str = "";
		
		str += numWidth + "-" + numHeight + "-" + BLOCK_SIZE + "-" + backgroundIndex + "-" + ":";
		
		for (int j = 0; j<numHeight; j++) {
			
			for (int i = 0; i<numWidth; i++) {
		
				str += blocks[i][j].getId() + "/";
								
			}
			
			str += ",";
			
		}
		
		return str;
		
	}
	
	/**
	 * converts a level file into a Level object
	 * @param obj - String from file
	 * @param blockManager - Block manager holding all blocks
	 * @return Level object
	 */
	public Level convert(Object obj, BlockManager blockManager) {
				
		String[] strings = obj.toString().split(":");
		
		//System.out.println("Printing Level Info: (DEBUGGING) \n" + obj.toString());
		
		//width then height then blockSize split by .
		String firstPart = strings[0];
				
		String[] vars = firstPart.split("-");
				
		numWidth = Integer.parseInt(vars[0].substring(vars[0].length()-3, vars[0].length()));
		numHeight = Integer.parseInt(vars[1]);
		BLOCK_SIZE = Integer.parseInt(vars[2]);
		try {
			backgroundIndex = Integer.parseInt(vars[3]);
		}catch(Exception e) {
			backgroundIndex = 0;
			System.out.println("Error: Background index not found. Setting to default.");
		}
		
		//vars[0] = width
		//vars[1] = height
		//vars[2] = block_size
		
		Level level = new Level(numWidth, Integer.parseInt(vars[1]), Integer.parseInt(vars[2]));
		
		//level data
		String secondPart = strings[1];
		
		String[] levelInfo = secondPart.split(",");
				
		Block[][] blocks = new Block[numWidth][numHeight];
		
		for (int j = 0; j<numHeight; j++) {

			String[] rowInfo = levelInfo[j].split("/");
			
			for (int i = 0; i<numWidth; i++) {
				
				//addressing 1d array as 2d formula (width*y) + x
				
				//getting block id from info
				/*
				int blockId = Integer.parseInt(levelInfo[((int)((level.getWIDTH()/level.getBLOCK_SIZE())) * j ) + i]);
				*/
				
				int blockId = Integer.parseInt(rowInfo[i]);
				
				if (blockId == BlockManager.SPAWN) {
					
					
					//MATHSSSSS
					//ONSCREENX = playerX - xOffset + Game.WIDTH/2 - playerWidth/2
					
					//(i * Game.BLOCK_SIZE) - playerX - cameraX = playerX - xOffset + Game.WIDTH/2 - playerWidth/2
					// (i* Game.BLOCK_SIZE) - cameraX + xOffset - Game.WIDTH/2 + playerWidth/2 = 2 * playerX
					// therefore 
					
					//playerX ((i* Game.BLOCK_SIZE) - cameraX + cameraX - Game.WIDTH/2 + playerWidth/2)/2
					//playerX ((i* Game.BLOCK_SIZE) - Game.WIDTH/2 + playerWidth/2)/2

					
					spawnPoint.x = ((i * Game.BLOCK_SIZE) - Game.WIDTH/2 + Game.BLOCK_SIZE/2)/2;
					spawnPoint.y = ((j * Game.BLOCK_SIZE) - Game.HEIGHT/2 + Game.BLOCK_SIZE/2)/2;

				}
						
				blocks[i][j] = Game.blockManager.getBlocks().clone()[blockId];
				
				//used for viewing level.. also debugging
				//System.out.println("i: " + i + " j: " + j + " Block: " + blockId);
				
			}
		}
		
		level.setBlocks(blocks);
		level.backgroundIndex = backgroundIndex;
		level.spawnPoint = spawnPoint;
		
		return level;
		
	}
	
	/**
	 * returns the number of blocks in width
	 * @return Integer
	 */
	public int getNumWidth() {
		return numWidth;
	}

	/**
	 * returns the number of blocks in height
	 * @return Integer
	 */
	public int getNumHeight() {
		return numHeight;
	}


	/**
	 * returns an arraylist of entities by finding every entity, creating their objects and putting it in the arraylist
	 * @return ArrayList of type Entity
	 */
	public ArrayList<Entity> loadEntities() {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		for (int i = 0; i<numWidth; i++) {
			for (int  j= 0; j<numHeight; j++) {
				if (blocks[i][j].getId() >= BlockManager.entityStartIndex) {
					
					//adding entities by index
					if (blocks[i][j].getId() == BlockManager.GOOD_CHARLIE) {
						entities.add(new Entity(i * BLOCK_SIZE, j* BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE*2, 3, 7));
					} else if (blocks[i][j].getId() == BlockManager.BAD_CHARLIE) {
						entities.add(new EvilCharlie(i * BLOCK_SIZE, j* BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE*2, 3, 7));
					} else if (blocks[i][j].getId() == BlockManager.CORRUPT) {
						entities.add(new CorruptEntity(i * BLOCK_SIZE, j* BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE*2, 3, 7));
					} else if (blocks[i][j].getId() == BlockManager.SHADOW) {
						entities.add(new ShadowEntity(i * BLOCK_SIZE, j* BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE*2, 3, 7));
					} else if (blocks[i][j].getId() == BlockManager.EYE) {
						entities.add(new SmallEntity(i * BLOCK_SIZE, j* BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, 3, 7));
					} else if (blocks[i][j].getId() == BlockManager.BAD_CHARLIE_WEAK) {
						entities.add(new EvilCharlieWeak(i * BLOCK_SIZE, j* BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE*2, 3, 7));
					} 
					
				}
			}
		}
		
		return entities;
	}
}
