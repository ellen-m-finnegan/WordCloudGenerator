package ie.gmit.dip;

import java.util.*;
import java.io.*;

public class Menu {
	// Sets a default maximum number of words
	private static final int MAX_WORDS = 25;

	private static Scanner console = new Scanner(System.in);

	private static String imageFileName="image.png";
	private static String inputFileName;
	private static String url;
	private static int maxWords =MAX_WORDS;
	public boolean fileReal;
	public  int isFile=0;
	public boolean running = true;

	public Menu()  {
		String contentType;
		maxWords = MAX_WORDS;
		System.out.println("Word Cloud Generator - Ellen Finnegan - G00398778\n");
		// The user chooses between a file or URL
		do {
			System.out.print("Type f to use a text file or url to use a URL:\n");
			contentType = console.next().toUpperCase();
		} while (!contentType.equals("F") && !contentType.equals("URL"));

		if (contentType.equals("F")) {
			isFile = 0;
			setFile();// Runs the file input function
		} else if (contentType.equals("URL")) {
			isFile = 1;
			setURL();// Runs the URL input function
		}

	}

	// User chooses what they wish to do from the menu.
	public void displayMenu() throws Throwable {
		String choice;
		System.out.println("******************************MENU******************************\n");
		System.out.println("Enter 1 to generate Word Cloud\n");
		System.out.println("Enter 2 to set Image Name\n");
		System.out.println("Enter 3 to set Max. Number of Words to Display in Image\n");
		System.out.println("Enter 4 to Exit\n");
		System.out.printf("Default maximum number of words is: %d\n", getMaxWords());
		System.out.printf("Default image name is: %s\n", getImageFileName());
		choice = console.next();

		if (choice.equals("1")) {
			Parser parser = new Parser();// Parses a file/url
			parser.fileIgnoreParse();
			System.out.println("==================================================");
			// Dictates if we parse a file or a URL
			if (isFile == (int) 0) {
					parser.parseFile(new FileInputStream(Menu.getFile()));
					}
			else {
				System.out.println(getURL());	
				parser.parseUrl(getURL());
				}
			
			// After parsing the image
			ImageCloud img=new ImageCloud();
			img.GenerateImage(parser.list, parser.ignoreList);
		//parser.parseFile(hashmap);
		//img.GenerateImage(hashmap);
		} else if (choice.equals("2")) {
			setImageFileName();
		} else if (choice.equals("3")) {
			setMaxWords();
		} else if (choice.equals("4")) {
			System.out.println("Goodbye!");
			running = false;
		} else {
			System.out.println();
			System.out.println("ERROR - Not an option, please try again");
			
			choice = console.nextLine();
		}
		// While running is true it will run, if not it closes
		while (running == true) {
			displayMenu();
		}
		System.exit(0);

	}

	// The user enters the file to be parsed
	public void setFile() {
		String input = "";
		boolean fileReal = false;

		while (!fileReal) {
			System.out.println("Enter the file name (please include the (.txt))\n");
			input = console.next();
			if (new File(input).isFile()) { // checks if file exists
				fileReal = true;
			} else {
				System.out.println("ERROR - Not valid, try again");
			}
		}
		inputFileName = input;

	}// end of function
	public static String getFile() {
		return inputFileName;
	}

	// the user enters the url to be parsed
	public void setURL() {
		String inputUrl = null;
		boolean validURL = false;

			while (!validURL) {
				System.out.print("Enter the full URL you wish to use: \n");
				inputUrl = console.next();
				
				if (inputUrl.startsWith("http://") || inputUrl.startsWith("https://")) {
					validURL = true;
				} else {
					System.out.println("Enter a valid URL.\n");
				}
			}
			url = inputUrl;// sets the URL

		
	}
	public static String getURL() {
		return url;
	}
	// User enters the file name for the png image
	
	public void setImageFileName() {
		@SuppressWarnings("resource")
		Scanner console = new Scanner(System.in);
		System.out.println("Enter a name for the output file\n");
		imageFileName = console.nextLine();
		imageFileName += ".png";
		System.out.printf("Image name is now saved as %s\n", imageFileName);
	}
	public static String getImageFileName() {
		return imageFileName;
	}

	// User enters the no. of words to output
	private void setMaxWords() {
		int maxWords = getMaxWords();
		System.out.printf("The max. number of words is currently: %d\n", maxWords);
		System.out.println("Enter the max. number of words you wish to be displayed: \n");
		maxWords = console.nextInt();
		System.out.printf("The max. number of words is now %d\n", maxWords);
		Menu.maxWords = maxWords;

	}
	public static int getMaxWords() {
		return maxWords;
	}

}
