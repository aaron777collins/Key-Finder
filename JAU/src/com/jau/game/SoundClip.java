package com.jau.game;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

/**
 * sound clip class
 * @author Aaron, Udayvir, Josh - COLLAB with Matthew Armaly
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public enum SoundClip {
	
	//initializes several instances of the enum to be used
//	DUNGEONMUSIC("assets/Dungeon Music.wav"),
	//CITYMUSIC("assets/Clock Town First Day.wav"),
	//LABMUSIC("assets/Breaking Bad Theme.wav"),
	//FORESTMUSIC("assets/woodstheme.wav"),
	//RUSSIAMUSIC("assets/Soviet Army dancing to Hard Bass.wav"),
	//SPACEMUSIC("assets/Bag Raiders - Shooting Stars.wav"),
	//CAVEMUSIC("assets/MINE DIAMONDS.wav");
	
	//makes instances of the audio
	DEAD("res/audio/dead.wav"),
	KEY("res/audio/key.wav"),
	LEVEL1("res/audio/background1.wav"),
	LEVEL5("res/audio/background5.wav");

 
   
   // Each sound effect has its own clip, loaded with its own sound file.
   private Clip clip;
   
   /**
    * Constructor to construct each element of the enum with its own sound file.
    * @param path is the name of sound file
    */
   SoundClip(String path) {
      try {
         // Use File to import music
    	 File soundFile = new File(path);
         // Set up an audio input stream piped from the sound file.
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
         // Get a clip resource.
         clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioInputStream);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
   
   /**
    * Begin a sound clip from the beginning
    */
   public void play() {
     if (clip.isRunning()) {
        clip.stop();   // Stop the player if it is still running
     }
     clip.setFramePosition(0); // rewind to the beginning
     clip.start();     // Start playing
   }
   
   /**
    * Ends the clip if it is running.
    */
   public void stop() {
	   if (clip.isRunning())
           clip.stop();   // Stop the player if it is still running
   }
   
   // Optional static method to pre-load all the sound files.
   static void init() {
      values(); // calls the constructor for all the elements
   }
   
   public Clip getClip() {
	   return clip;
   }
   
}
