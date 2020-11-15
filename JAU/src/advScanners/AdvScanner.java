package advScanners;

import java.util.Scanner;

/**
 * "Advanced Scanner" | ICS4U | has a couple useful methods to speed up the process of gathering information
 * @author Aaron Collins
 * @version March 28, 2018
 */
public class AdvScanner {

	/**
	 * Gets a string of doubles from the user and parses it into an array of doubles
	 * @param num - number of numbers expected
	 * @param prompt - what the user see's as input for entering numbers
	 * @return An array of doubles that the user entered
	 */
	public double[] getDoubles(int num, String prompt) {
		double[] doubles = new double[num];
		
		Scanner s1 = new Scanner(System.in);
		s1.reset();
		
		System.out.println(prompt);
		
		String[] strings;
		
		strings = s1.nextLine().split(" ");
		
		for (int i = 0; i<num; i++) {
			
			try {
				doubles[i] = Double.parseDouble(strings[i]);
			} catch(Exception e) {
				System.out.println("missing number.. errors may be thrown.");
			}
			
		}
			
		
		return doubles;
	}
	
	/**
	 * Gets a string of doubles from the user and parses it into an array of doubles
	 * @param prompt - what the user see's as input for entering numbers
	 * @return An array of doubles that the user entered
	 */
	public double[] getDoubles(String prompt) {
		
		Scanner s1 = new Scanner(System.in);
		s1.reset();
		
		System.out.println(prompt);
		
		String[] strings;
		
		strings = s1.nextLine().split(" ");
		
		double[] doubles = new double[strings.length];
		
		for (int i = 0; i<strings.length; i++) {
			
			try {
				doubles[i] = Double.parseDouble(strings[i]);
			} catch(Exception e) {
				System.out.println("missing number.. errors may be thrown.");
			}
			
		}
			
		
		return doubles;
	}
	
	/**
	 * Gets a string of integers from the user and parses it into an array of integers
	 * @param num - number of numbers expected
	 * @param prompt - what the user see's as input for entering numbers
	 * @return An array of integers that the user entered
	 */
	public int[] getInts(int num, String prompt) {
		int[] ints = new int[num];
		
		Scanner s1 = new Scanner(System.in);
		s1.reset();
		
		System.out.println(prompt);
		
		String[] strings;
		
		strings = s1.nextLine().split(" ");
		
		for (int i = 0; i<num; i++) {
			
			try {
				ints[i] = Integer.parseInt(strings[i]);
			} catch(Exception e) {
				System.out.println("missing number.. errors may be thrown.");
			}
			
		}
			
		
		return ints;
	}
	
	/**
	 * Gets a string of integers from the user and parses it into an array of integers
	 * @param prompt - what the user see's as input for entering numbers
	 * @return An array of integers that the user entered
	 */
	public int[] getInts(String prompt) {
		
		Scanner s1 = new Scanner(System.in);
		s1.reset();
		
		String[] strings;
		
		System.out.println(prompt);
		
		strings = s1.nextLine().split(" ");
		
		int[] ints = new int[strings.length];
		
		for (int i = 0; i<strings.length; i++) {
			
			try {
				ints[i] = Integer.parseInt(strings[i]);
			} catch(Exception e) {
				System.out.println("missing number.. errors may be thrown.");
			}
			
		}
			
		
		return ints;
	}
	
	/**
	 * gets an integer from the user
	 * @param prompt - what the user see's as the question
	 * @return The integer of interest
	 */
	public int getInt(String prompt) {	
		
		Scanner s1 = new Scanner(System.in);
		
		System.out.println(prompt);
		int input = s1.nextInt();

		return input;
	}

	/**
	 * gets a double from the user
	 * @param prompt - what the user see's as the question
	 * @return The double of interest
	 */
	public double getDouble(String prompt) {	
		
		Scanner s1 = new Scanner(System.in);
		
		System.out.println(prompt);
		double input = s1.nextDouble();

		return input;
	}
	
	/**
	 * gets a string from the user
	 * @param prompt - what the user see's as the question
	 * @return The string of interest
	 */
	public String getString(String prompt) {

		Scanner s1 = new Scanner(System.in);
		
		System.out.println(prompt);
		String input = s1.next();

		return input;
		
	}
	
	/**
	 * gets a line(in string format) from the user
	 * @param prompt - what the user see's as the question
	 * @return The line of interest
	 */
	public String getLine(String prompt) {

		Scanner s1 = new Scanner(System.in);
		
		System.out.println(prompt);
		String input = s1.nextLine();

		return input;
		
	}

}
