package com.jau.game;

import java.util.ArrayList;

import com.jau.maths.Maths;

/**
 * NOT USED IN GAME>> ignore this class
 */
public class LightManager {

	public double[][] shadows;
	private ArrayList<Light> lights;
	private Player player;
	private Camera camera;
	
	public LightManager(double[][] shadows, ArrayList<Light> lights, Player player, Camera camera) {
		this.shadows = shadows;
		this.lights = lights;
		this.player = player;
		this.camera = camera;
	}

	public void update() {
		

		if(Game.updateLights) {
			
			try {
			
				//redeclaring so that info doesn't change during calculations
				double cameraX = camera.getXOff();
				double cameraY = camera.getYOff();
				
				int playerX = player.getX();
				int playerY = player.getY();
									
				for (int i = 0; i<Renderer.SHADOW_RESOLUTION_X; i++) {
					for (int j = 0; j<Renderer.SHADOW_RESOLUTION_Y; j++) {
						
						shadows[i][j] = Renderer.ambientLight;
						
						double shadowX = i * Renderer.SHADOW_BLOCK_WIDTH + (Renderer.SHADOW_BLOCK_WIDTH/2);
						double shadowY = j * Renderer.SHADOW_BLOCK_HEIGHT +  ( Renderer.SHADOW_BLOCK_HEIGHT/2);
						
						for(Light light : lights) {
						
							double lightX = light.getX() - playerX - cameraX;
							double lightY = light.getY() - playerY - cameraY;
							
							double newPossibility = ((shadowX- lightX) * (shadowX- lightX) + (shadowY - lightY) * (shadowY - lightY)) / 150000;

								shadows[i][j] *= newPossibility;
							
								shadows[i][j] /= light.getIntensity();
							
						}
							
					}
				}
				
				Game.updateLights = false;
				
			} catch(Exception e) {
				System.out.println("Error calculating lighting");
			}
			
			
		}
	
	}
	


}
