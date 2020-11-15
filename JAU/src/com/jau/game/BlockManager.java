package com.jau.game;
/**
 * Stores all the blocks in the game that exist with their image and properties
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class BlockManager {

	//stores array of blocks that exist
	private Block[] blocks;
	
	//making constants for each block / entity spawner block
	public static final int AIR = 0;
	public static final int GRASS = 1;
	public static final int DIRT = 2;
	public static final int BOARDER = 3;
	public static final int STONE = 4;
	public static final int DIALOG1 = 5;
	public static final int KEY = 6;
	public static final int SPIKES = 7;
	public static final int SPAWN = 8;
	public static final int DIALOG2 = 9;
	public static final int DIALOG3 = 10;
	public static final int DIALOG4 = 11;
	public static final int DIALOG5 = 12;
	public static final int DIALOG6 = 13;
	public static final int DIALOG7 = 14;
	public static final int DIALOG8 = 15;
	public static final int DIALOG9 = 16;
	public static final int DIALOG10 = 17;
	public static final int DIALOG11 = 18;
	public static final int BSPIKES = 19;

	
	public static final int DOOR = 29;

	public static final int GOOD_CHARLIE = 30;
	public static final int BAD_CHARLIE = 31;
	public static final int CORRUPT = 32;
	public static final int SHADOW = 33;
	public static final int EYE = 34;
	public static final int BAD_CHARLIE_WEAK = 35;




	//setting entity start index
	public static final int entityStartIndex = 30;

	
	//number of blocks / entities that are different total (not all are used)
	private int numBlocks = 36;
	
	/**
	 * initializes blocks array with blocks and stores them for rendering and logic
	 */
	public BlockManager() {
		
		blocks = new Block[numBlocks];
		
		blocks[AIR] = new Block("res/images/air.jpg", AIR, false);
		blocks[GRASS] = new Block("res/images/grass.png", GRASS, true);
		blocks[DIRT] = new Block("res/images/dirt.png", DIRT, true);
		blocks[BOARDER] = new Block("res/images/white.png", BOARDER, true);
		blocks[STONE] = new Block("res/images/stone.png", STONE, true);
		blocks[DIALOG1] = new Block("res/images/pink_block.png", DIALOG1, false);
		blocks[KEY] = new Block("res/images/Key.png", KEY, false);
		blocks[SPIKES] = new Block("res/images/SpikeBig.png", SPIKES, true);
		blocks[SPAWN] = new Block("res/images/spawn.png", SPAWN, false);
		
		blocks[DIALOG2] = new Block("res/images/pink_block.png", DIALOG2, false);
		blocks[DIALOG3] = new Block("res/images/pink_block.png", DIALOG3, false);
		blocks[DIALOG4] = new Block("res/images/pink_block.png", DIALOG4, false);
		blocks[DIALOG5] = new Block("res/images/pink_block.png", DIALOG5, false);
		blocks[DIALOG6] = new Block("res/images/pink_block.png", DIALOG6, false);
		blocks[DIALOG7] = new Block("res/images/pink_block.png", DIALOG7, false);
		blocks[DIALOG8] = new Block("res/images/pink_block.png", DIALOG8, false);
		blocks[DIALOG9] = new Block("res/images/pink_block.png", DIALOG9, false);
		blocks[DIALOG10] = new Block("res/images/pink_block.png", DIALOG10, false);
		blocks[DIALOG11] = new Block("res/images/pink_block.png", DIALOG11, false);		
		blocks[BSPIKES] = new Block("res/images/BSBig.png", BSPIKES, true);

		


		
		
		blocks[DOOR] = new Block("res/images/Door.png", DOOR, false);

		
		blocks[GOOD_CHARLIE] = new Block("res/images/player_standing0.png", GOOD_CHARLIE, false);
		blocks[BAD_CHARLIE] = new Block("res/images/charlie_standing0.png", BAD_CHARLIE, false);
		blocks[CORRUPT] = new Block("res/images/corrupt_standing0.png", CORRUPT, false);
		blocks[SHADOW] = new Block("res/images/shadow_standing0.png", SHADOW, false);
		blocks[EYE] = new Block("res/images/eye_standing0.png", EYE, false);
		blocks[BAD_CHARLIE_WEAK] = new Block("res/images/charlie_standing0.png", BAD_CHARLIE_WEAK, false);





		
		

		
	}
	
	/**
	 * returns the block array full of all the blocks, their data and images
	 * @return Block[]
	 */
	public Block[] getBlocks() {
		return blocks;
	}

}
