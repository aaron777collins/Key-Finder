package com.jau.game;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Makes evil charlie weak to be fought once before fighting the proper evil charlie
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class EvilCharlieWeak extends EvilCharlie { // weak version of evil charlie
	
	/**
	 * makes a evil charlie that is weaker
	 * @param x - x position
	 * @param y - y position
	 * @param width - width of entity
	 * @param height - height of entity
	 * @param speed - speed of entity
	 * @param jumpForce - jump force of entity
	 */
	public EvilCharlieWeak(int x, int y, int width, int height, int speed, int jumpForce) {
		super(x, y, width, height, speed, jumpForce);//passes info to the evil charlie class
		super.health = 2; //sets health to 2
	}

	/**
	 * hurts evil charlie (less health)
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
				Renderer.DBox = new DisplayDialog(Dialogs.level5_4);
				DisplayDialog.active = true;
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
