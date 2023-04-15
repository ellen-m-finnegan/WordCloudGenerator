package ie.gmit.dip;

import java.io.File;
import java.util.Scanner;

public class FileSearcher {

	private final static String adjunct = ".png";
	private final static Scanner sc = new Scanner(System.in);

	// facilitates input of image and directory
	public static String enterFile() throws Exception {

		System.out.println(ConsoleColour.PURPLE);
		System.out.println("Enter Image Name");
		System.out.println(ConsoleColour.RESET);
		String fileName = sc.nextLine().trim() + adjunct; // Get file name, add .png
		System.out.println(ConsoleColour.PURPLE);
		System.out.println("Enter Directory Path");
		System.out.println(ConsoleColour.RESET);

		String delimiter = getOS();
		String directory = sc.nextLine().trim(); // Finds path

		String path = directory + delimiter + fileName;
		File target = new File(path);
		if (target.exists()) {
			System.out.println(ConsoleColour.CYAN);
			System.out.println("Finding File...");
			System.out.println(ConsoleColour.RESET);
			return target.getAbsolutePath();
		} else {
			System.out.println(ConsoleColour.CYAN);
			System.out.println("FILE NOT FOUND!");
			System.out.println(ConsoleColour.RESET);
			return null;
		}

	}

	public static String getOS() {
		String os = System.getProperty("os.name");
		String delimiter;
		if (os.contains("Windows")) {
			delimiter = "\\";
		} else {
			delimiter = "//";
		}
		return delimiter;
	}

	// conducts retrieval of file
	private static String findFile(String fileName, String directory) throws Exception {
		File[] fileList = new File(directory).listFiles();

		// Searching for file in file directory
		for (int i = 0; i < fileList.length; i++) {
			File f = fileList[i];
			if (f.getName().equalsIgnoreCase(fileName)) {
				System.out.println(ConsoleColour.CYAN);
				System.out.println("Finding File...");
				System.out.println(ConsoleColour.RESET);
				return f.getAbsolutePath();
			}
		}
		return null; // Won't happen due to safety net
	}
}
