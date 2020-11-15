package com.jau.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * checks whether keys are pressed or not
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class KeyChecker implements KeyListener{

	private boolean one, two, three, four, five, six, seven, eight, nine, zero, space, w, a, s, d, e, t, b = false;
	
	/**
	 * checks whether a key is pressed and sets its corresponding value to true
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
			
		if (e.getKeyCode() == KeyEvent.VK_1) {
			one = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_2) {
			two = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_3) {
			three = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_4) {
			four = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_5) {
			five = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_6) {
			setSix(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_7) {
			setSeven(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_8) {
			setEight(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_9) {
			setNine(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			setW(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			Player.direction = Player.LEFT;

			setA(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			setS(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			Player.direction = Player.RIGHT;

			setD(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			setE(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_T) {
			setT(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_B) {
			setB(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			setSpace(true);
			Game.punch();

		}
		if(e.getKeyCode() == KeyEvent.VK_0) {
			setZero(true);
		}
	}

	/**
	 * checks whether a key is released and sets its corresponding value to false
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_1) {
			one = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_2) {
			two = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_3) {
			three = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_4) {
			four = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_5) {
			five = false;
		}	
		if (e.getKeyCode() == KeyEvent.VK_6) {
			setSix(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_7) {
			setSeven(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_8) {
			setEight(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_9) {
			setNine(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			setW(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			setA(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			setS(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			setD(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			setE(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_T) {
			setT(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_B) {
			setB(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			setSpace(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_0) {
			setZero(false);
		}

	}

	/**
	 * returns whether one is pressed 
	 * @return Boolean
	 */
	public boolean isOne() {
		return one;
	}

	/**
	 * returns whether two is pressed 
	 * @return Boolean
	 */
	public boolean isTwo() {
		return two;
	}

	/**
	 * returns whether three is pressed 
	 * @return Boolean
	 */
	public boolean isThree() {
		return three;
	}

	/**
	 * returns whether four is pressed 
	 * @return Boolean
	 */
	public boolean isFour() {
		return four;
	}

	/**
	 * returns whether five is pressed 
	 * @return Boolean
	 */
	public boolean isFive() {
		return five;
	}

	/**
	 * checks for keys typed (not used)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * returns whether W is pressed 
	 * @return Boolean
	 */
	public boolean isW() {
		return w;
	}

	/**
	 * sets whether w is pressed
	 * @param w - pressed?
	 */
	public void setW(boolean w) {
		this.w = w;
	}

	/**
	 * returns whether A is pressed 
	 * @return Boolean
	 */
	public boolean isA() {
		return a;
	}

	/**
	 * sets whether A is pressed
	 * @param a - Pressed?
	 */
	public void setA(boolean a) {
		this.a = a;
	}

	/**
	 * returns whether S is pressed 
	 * @return Boolean
	 */
	public boolean isS() {
		return s;
	}

	/**
	 * sets whether S is pressed
	 * @param s - Pressed?
	 */
	public void setS(boolean s) {
		this.s = s;
	}

	/**
	 * returns whether D is pressed 
	 * @return Boolean
	 */
	public boolean isD() {
		return d;
	}

	/**
	 * sets whether D is pressed
	 * @param d - Pressed?
	 */
	public void setD(boolean d) {
		this.d = d;
	}

	/**
	 * returns whether E is pressed 
	 * @return Boolean
	 */
	public boolean isE() {
		return e;
	}

	/**
	 * sets whether e is pressed
	 * @param e - Pressed?
	 */
	public void setE(boolean e) {
		this.e = e;
	}

	/**
	 * returns whether T is pressed 
	 * @return Boolean
	 */
	public boolean isT() {
		return t;
	}

	/**
	 * sets whether T is pressed
	 * @param t - Pressed?
	 */
	public void setT(boolean t) {
		this.t = t;
	}

	/**
	 * returns whether B is pressed 
	 * @return Boolean
	 */
	public boolean isB() {
		return b;
	}

	/**
	 * sets whether B is pressed
	 * @param b - Pressed?
	 */
	public void setB(boolean b) {
		this.b = b;
	}

	/**
	 * returns whether 6 is pressed 
	 * @return Boolean
	 */
	public boolean isSix() {
		return six;
	}

	/**
	 * sets whether 6 is pressed
	 * @param six - Pressed?
	 */
	public void setSix(boolean six) {
		this.six = six;
	}

	/**
	 * returns whether 7 is pressed 
	 * @return Boolean
	 */
	public boolean isSeven() {
		return seven;
	}

	/**
	 * sets whether Seven is pressed
	 * @param seven - Pressed?
	 */
	public void setSeven(boolean seven) {
		this.seven = seven;
	}

	/**
	 * returns whether space is pressed 
	 * @return Boolean
	 */
	public boolean isSpace() {
		return space;
	}

	/**
	 * sets whether Space is pressed
	 * @param space - Pressed?
	 */
	public void setSpace(boolean space) {
		this.space = space;
	}

	/**
	 * returns whether 8 is pressed 
	 * @return Boolean
	 */
	public boolean isEight() {
		return eight;
	}

	/**
	 * sets whether eight is pressed
	 * @param eight - Pressed?
	 */
	public void setEight(boolean eight) {
		this.eight = eight;
	}

	/**
	 * returns whether 9 is pressed 
	 * @return Boolean
	 */
	public boolean isNine() {
		return nine;
	}

	/**
	 * sets whether 9 is pressed
	 * @param nine - Pressed?
	 */
	public void setNine(boolean nine) {
		this.nine = nine;
	}

	/**
	 * returns whether 0 is pressed 
	 * @return Boolean
	 */
	public boolean isZero() {
		return zero;
	}

	/**
	 * sets whether zero is pressed
	 * @param zero - Pressed?
	 */
	public void setZero(boolean zero) {
		this.zero = zero;
	}

}
