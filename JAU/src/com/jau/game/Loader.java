package com.jau.game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

/**
 * loads any type of information (does all I/O)
 * @author Aaron, Udayvir, Josh
 * @date June 25, 2018
 * @course ICS4U1-01
 */
public class Loader {

	/**
	 * loads an image and returns it
	 * @param path - path of the image
	 * @return Image
	 */
	public Image loadImage(String path) {
		
		BufferedImage img = null;
		try {
			
		    //img = (BufferedImage) ImageIO.read(getClass().getClassLoader().getResourceAsStream(path));
			
			img = ImageIO.read(new File(path));
			
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
		return img;
		
	}
	
	/**
	 * saves the level
	 * @param level - Level object
	 * @param name - name of the level
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void saveLevel(Level level, String name) throws FileNotFoundException, IOException {
		
		
		/*
		ClassLoader classLoader = getClass().getClassLoader();

		String filePath = classLoader.getResource("saves/" + name + ".jau").getFile();
		
		*/
		
		
		
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(new File("res/saves/" + name + ".jau")));

		
		output.writeObject(level.toString());
		output.flush();
		output.close();
		
		
	}

	/**
	 * Loads the level
	 * @param currentLevelName - level name
	 * @param blockManager - Block manager holding all types of blocks
	 * @return Level object
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Level loadLevel(String currentLevelName, BlockManager blockManager) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		
		ObjectInputStream input = new ObjectInputStream(new FileInputStream("res/saves/" + currentLevelName + ".jau"));
		
		Object obj = input.readObject();
		
		/*
		
		ClassLoader classLoader = Loader.class.getClassLoader();

		InputStream input = classLoader.getResourceAsStream("saves/" + currentLevelName + ".jau");
		
		BufferedReader textReader = new BufferedReader(new InputStreamReader(input));
		
		String text = "";
		
		String l;
        while((l = textReader.readLine()) != null) {
           text = text + l;
        } 
        textReader.close();
				
		//ObjectInputStream input = new ObjectInputStream(new FileInputStream(new File(filePath)));
		//Object obj = input.readObject();
		
		//input.close();
		
		*/
		
		//placeholder
		Level level = new Level(0,0,0);
		return level.convert(obj, blockManager);
	}

}
