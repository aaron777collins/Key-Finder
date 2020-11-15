package com.jau.game;

import java.awt.Image;
import java.awt.image.BufferedImage;
/**
 * Data type for storing the image, image path, id, whether it is solid and it loads the image on creation
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class Block {
	
	private String path;
	private Image img;
	private int id;
	private boolean solid;

	/**
	 * loads the image of the block and stores it's data
	 * @param path - path of the image for the block
	 * @param id - id of the block in the game
	 * @param solid - whether the block is solid or not (boolean)
	 */
	public Block(String path, int id, boolean solid) {
	
		//stores all data
		this.path = path;
		
		img = Game.loader.loadImage(path);
		
		this.setId(id);
		
		this.solid = solid;
	
	}
	
	/**
	 * returns the image of the block
	 * @return Image
	 */
	public Image getImg() {
		return img;
	}

	/**
	 * returns the Id of the block
	 * @return Integer
	 */
	public int getId() {
		return id;
	}

	/**
	 * sets the id of the block
	 * @param id - id of the block
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * returns whether the block is solid or not
	 * @return Boolean
	 */
	public boolean isSolid() {
		return solid;
	}

}
