package com.jau.game;

/**
 * Holds all the dialogs in the game
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class Dialogs {
	
	//initializing all the dialogs in the game
	public static DialogHolder intro1 = new DialogHolder("Charlie was a kind man who was the hero in his small     town. However one day, he awoke to find that everyone hadgone missing.                                               ", DisplayDialog.NARRATOR);
	public static DialogHolder intro2 = new DialogHolder("Confused, he wandered around to hopefully find any traces of where everyone may have went...........                                 ", DisplayDialog.NARRATOR);

	public static DialogHolder level1_1 = new DialogHolder("W here has everyone went? W hy are there things that wantto harm me?                                               ", DisplayDialog.PLAYER);
	public static DialogHolder level1_LOCKED = new DialogHolder("This door appears to be locked. There is a key hole.                                  ", DisplayDialog.PLAYER);
	public static DialogHolder level1_HASKEY = new DialogHolder("This key looks like it is for a door..                    ", DisplayDialog.PLAYER);

	public static DialogHolder level2_1 = new DialogHolder("It's dark outside.                 ", DisplayDialog.PLAYER);

	public static DialogHolder level3_1 = new DialogHolder("I think I'm underground.                   ", DisplayDialog.PLAYER);

	public static DialogHolder level4_1 = new DialogHolder("W hat happened here?                   ", DisplayDialog.PLAYER);

	public static DialogHolder level5_1 = new DialogHolder("W hat happened to my hometown?                  ", DisplayDialog.PLAYER);

	public static DialogHolder level5_2 = new DialogHolder("Hahaha........                 ", DisplayDialog.CHARLIE);

	public static DialogHolder level5_3 = new DialogHolder("W hat was that sound... is anyone there...?                   ", DisplayDialog.PLAYER);

	public static DialogHolder level5_4 = new DialogHolder("You can't W IN...                     ", DisplayDialog.CHARLIE);

	public static DialogHolder level5_5 = new DialogHolder("Free my friends!                      ", DisplayDialog.PLAYER);

	
}
