package ie.gmit.dip;

import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.EnumSet;

public class Menu {

	public static Kernel k = Kernel.HORIZONTAL_LINES;

	public static EnumSet<Kernel> kernelSet = EnumSet.allOf(Kernel.class);

	static void displayMenu() throws Exception {
		displayHeader();

		// Loop for menu
		boolean loop = true;

		do {
			displayOptions();
			System.out.println(ConsoleColour.RESET);
			int choice = getInput();

			switch (choice) {
			case 1: // Enter image name and apply filter
				try {
					BufferedImage inputImage = ImageIO.read(new File(FileSearcher.enterFile()));
					ImageProcess.readImage(inputImage, k); // process' inputed file, passes file & kernel
				} catch (Exception e) { // Double Safety Net Validation
				}
				break;
			case 2:
				System.out.println(ConsoleColour.GREEN);
				kernelSet.forEach(System.out::println);

				System.out.println(ConsoleColour.RESET);
				break;
			case 3:

				// Loop boolean
				boolean setLoop = false;
				String s;

				do {
					System.out.println(ConsoleColour.GREEN);
					System.out.println("Enter filter name (Not case sensitive) OR Enter -1 to return to Main Menu");
					System.out.println(ConsoleColour.RESET);
					s = getStringInput();
					try {
						if (s.equals("-1")) {
							System.out.println("Returning to Main Menu");
							break;
						}
						setKernel(s);
						setLoop = true;
					} catch (Exception e) {
						System.out.println(ConsoleColour.RED);
						System.out.println("Invalid Filter, Please Try Again");
						System.out.println(ConsoleColour.RESET);
						setLoop = false;
					}
				} while (!setLoop);
				if (setLoop) {
					System.out.println(ConsoleColour.GREEN);
					System.out.println("Filter is currently: " + s);
					System.out.println(ConsoleColour.RESET);
				}
				break;
			case 4:
				Scanner biasScan = new Scanner(System.in);
				System.out.println(ConsoleColour.GREEN);
				System.out.println("Exposure bias is currently: " + ImageProcess.getBias());
				System.out.println("Change bias if required; (higher brightens, lower darkens)");
				System.out.println(ConsoleColour.RESET);
				double biasChoice = 0;
				double multiChoice = 0;

				try {
					biasChoice = biasScan.nextDouble();
					ImageProcess.setBias(biasChoice);
					System.out.println(ConsoleColour.GREEN);
					System.out.println("Confirmed, Bias is now: " + ImageProcess.getBias());
					System.out.println(ConsoleColour.RESET);
				} catch (Exception e) {
					System.out.println(ConsoleColour.RED);
					System.out.println("Invalid Input");
					System.out.println(ConsoleColour.RESET);
					biasChoice = 0;
					break;
				}

				System.out.println(ConsoleColour.GREEN);
				System.out.println("Multiplication Factor is Currently: " + ImageProcess.getMultiFactor());
				System.out.println("Input new Multiplication Factor (increases effect of the kernel)");
				System.out.println(ConsoleColour.RESET);

				try {
					while (multiChoice < 1) {
						System.out.println("Enter");
						multiChoice = biasScan.nextDouble();
					}
					ImageProcess.setMultiFactor(multiChoice);
					System.out.println(ConsoleColour.GREEN);
					System.out.println("Mutliplication factor is now " + ImageProcess.getMultiFactor());
					System.out.println(ConsoleColour.RESET);
				} catch (Exception e) {
					System.out.println(ConsoleColour.RED);
					System.out.println("Invalid Input");
					System.out.println(ConsoleColour.RESET);
					multiChoice = 0;
					break;
				}
				break;
			case 5:
				if (k != null) {
					System.out.println(ConsoleColour.GREEN);
					System.out.println("Kernel is Currently: " + k.toString());
					System.out.println(ConsoleColour.RESET);
				} else if (k == null) {
					System.out.println(ConsoleColour.RED);
					System.out.println("Choose A Filter ");
					System.out.println(ConsoleColour.RESET);
				}
				break;
			case 6:
				System.out.println(ConsoleColour.RED);
				System.out.println("Quitting");
				System.out.println(ConsoleColour.RESET);
				loop = false;
				break;
			default:
				System.out.println(ConsoleColour.RED);
				System.out.println("Invalid input, try again");
				System.out.println(ConsoleColour.RESET);
			}
		} while (loop);
	}

	private static void displayOptions() {
		System.out.println();
		System.out.println(ConsoleColour.GREEN);
		System.out.println("1) Enter name of image");
		System.out.println("2) Display list of available filters");
		System.out.println("3) Pick A filter");
		System.out.println("4) Change the multiplication factor and bias");
		System.out.println("5) Show Filter currently selected");
		System.out.println("6) Quit");
		System.out.println("\nSelect Option [1-6]>");
		System.out.println(ConsoleColour.RESET);
	}

	private static void displayHeader() {
		System.out.println(ConsoleColour.YELLOW_BOLD);
		System.out.println(ConsoleColour.PURPLE_BACKGROUND);
		System.out.println("***************************************************");
		System.out.println("* GMIT - Dept. Computer Science & Applied Physics *");
		System.out.println("*                                                 *");
		System.out.println("*           Image Filtering System V1.0           *");
		System.out.println("*     H.Dip in Science (Software Development)     *");
		System.out.println("*            Ellen Finnegan - g00398778           *");
		System.out.println("*                                                 *");
		System.out.println("***************************************************");
		System.out.print(ConsoleColour.RESET);
	}

	private static int getInput() {
		@SuppressWarnings("resource")
		Scanner scanForInput = new Scanner(System.in);
		int choice = -1;
		try {
			choice = Integer.parseInt(scanForInput.nextLine());
		} catch (Exception e) {
			System.out.println("Choose a valid input");
		}
		return choice;
	}

	@SuppressWarnings("resource")
	private static String getStringInput() {
		Scanner scanStringInput = new Scanner(System.in);
		String s = scanStringInput.nextLine().toUpperCase();
		return s;
	}

	private static void setKernel(String s) {
		k = Kernel.valueOf(s);
	}

	private static void createKernel() {

		System.out.println(ConsoleColour.PURPLE);
		System.out.println("Choose size of filter:\n1) 3x3\n2) 5x5\n3) 9x9");
		System.out.println(ConsoleColour.RESET);
		int kernelSizeChoice = -1;
		int kernelSize = 0;
		while (kernelSizeChoice < 1 || kernelSizeChoice > 3) {
			System.out.println(ConsoleColour.CYAN);
			System.out.println("Select valid input");
			System.out.println(ConsoleColour.RESET);
			kernelSizeChoice = getInput();
		}
		switch (kernelSizeChoice) {
		case 1:
			kernelSize = 3;
			break;
		case 2:
			kernelSize = 5;
			break;
		case 3:
			kernelSize = 9;
			break;

		default:
			System.out.println("Not right");
		}
		System.out.println("Enter values");
		try (Scanner kScan = new Scanner(System.in)) {
			double[][] newK = new double[kernelSize][kernelSize];
			for (int i = 0; i < newK.length; i++) {
				for (int j = 0; j < newK[i].length; j++) {
					newK[i][j] = kScan.nextDouble();
				}
			}
		}
	}

}
