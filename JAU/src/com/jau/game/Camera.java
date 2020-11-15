package com.jau.game;
/**
 * Camera which stores an offset for the whole level/player and gets updated every frame
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class Camera {

	//stores offsets
	private double xOff, yOff;
	
	/**
	 * initializes offsets
	 * @param xOff - x offset to start
	 * @param yOff - y offset to start
	 */
	public Camera(int xOff, int yOff) {
	
		this.xOff = xOff;
		this.yOff = yOff;
		
		
	}
	
	/**
	 * moves the camera to focus more on the player by moving everything in the opposite direction as the distance from the center.
	 * @param player - Player object
	 * @param WIDTH - width of the game
	 * @param HEIGHT - height of the game
	 */
	public void update(Player player, int WIDTH, int HEIGHT) {
		
		//if the distance is greater than a threshold, update the x offset
		if (Math.abs(player.getX() - xOff) > 5) {
			Game.updateLights = true;
			try {
			xOff += (player.getX() - xOff)/ 100;
			} catch(Exception e) {
				
			}
		} 
		
		//if the dboxes are active, add an extra offset to make the player further upward
		int DBoxYOffset = (int)((DisplayDialog.active) ?  Game.BLOCK_SIZE * 3 : 0);
		
		//if the distance is greater than a threshold, update the y offset
		if (Math.abs(player.getY() + DBoxYOffset - yOff) > 5) {
			Game.updateLights = true;
			try {
			yOff += (player.getY() + DBoxYOffset - yOff)/ 100;
			} catch(Exception e) {
				
			}
		}
		
	}
	
	/**
	 * returns the x offset
	 * @return Double
	 */
	public double getXOff() {
		return xOff;
	}
	
	/**
	 * returns the y offset
	 * @return Double
	 */
	public double getYOff() {
		return yOff;
	}
	
	/**
	 * sets the x offset
	 * @param xOff - x offset
	 */
	public void setXOff(double xOff) {
		this.xOff = xOff;
	}
	
	/**
	 * sets the y offset
	 * @param yOff - y offset
	 */
	public void setYOff(double yOff) {
		this.yOff = yOff;
	}

}
