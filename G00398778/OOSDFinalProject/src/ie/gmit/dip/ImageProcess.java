package ie.gmit.dip;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;



public class ImageProcess {
	private static Scanner sc = new Scanner(System.in);

	public static Kernel k;
	private static double bias = 0.0d;
	private static double multiFactor = 1;

	// this reads inputted image
	public static void readImage(BufferedImage inputImage, Kernel kern) throws IOException {
		k = kern;
		writeImage(inputImage);
	}

	// this writes image
	private static void writeImage(BufferedImage inputImage) throws IOException {
		String outputFileName = getFileOutputName() + ".PNG";
		BufferedImage outputImage = convolute(inputImage);
		// Writing output image
		try {
			ImageIO.write(outputImage, "PNG", new File(outputFileName));
		} catch (FileNotFoundException e) {
			System.out.println(ConsoleColour.GREEN);
			System.out.println("Error while writing file...\n Outputting to the default directory");
			System.out.println(ConsoleColour.RESET);
			ImageIO.write(outputImage, "PNG", new File("output.png"));
		}
	}

	private static String getFileOutputName() throws FileNotFoundException {
		Scanner sc1 = new Scanner(System.in);
		System.out.println(ConsoleColour.RED);
		System.out.println("Enter name for output image:");
		System.out.println(ConsoleColour.RESET);
		String outputPictureName = sc1.nextLine().trim();
		System.out.println(ConsoleColour.RED);
		System.out.println("Enter output directory name, if blank output is to default project folder.");
		System.out.println(ConsoleColour.RESET);
		String outputDirectory = sc1.nextLine().trim();
		String delimiter = FileSearcher.getOS();

		File f = new File(outputDirectory);
		if (!(f).exists()) {
			System.out.println(ConsoleColour.RED);
			System.out.println("Directory not found; outputting to default project folder");
			System.out.println(ConsoleColour.RESET);
			return outputPictureName;
		} else {
			return outputDirectory + delimiter + outputPictureName;
		}

	}

	private static BufferedImage convolute(BufferedImage image) {
		BufferedImage imageO = getImageChoice(image);

		BufferedImage output = new BufferedImage(imageO.getWidth(), imageO.getHeight(), imageO.getType());

		int height = image.getHeight();
		int width = image.getWidth();

		for (int xCoord = 0; xCoord < (width); xCoord++) {
			for (int yCoord = 0; yCoord < (height); yCoord++) {

				int outR, outG, outB, outA;

				// R,G,B values that'll contain total R,G,B values post multiplication
				double red = 0, green = 0, blue = 0, alpha = 0;
				int outRGB = 0;

				// offset added to xCoord & yCoord & follows footprint of kernel

				try {
					for (int xOffset = Math.negateExact(k.getKernels().length / 2); xOffset <= k.getKernels().length
							/ 2; xOffset++) {
						for (int yOffset = Math.negateExact(k.getKernels().length / 2); yOffset <= k.getKernels().length
								/ 2; yOffset++) {

							// basic wrapping
							int realX = (xCoord - k.getKernels().length / 2 + xOffset + width) % width;
							int realY = (yCoord - k.getKernels().length / 2 + yOffset + height) % height;

							int RGB = image.getRGB((realX), (realY));
							int A = (RGB >> 24) & 0xFF;
							int R = (RGB >> 16) & 0xFF;
							int G = (RGB >> 8) & 0xFF;
							int B = (RGB) & 0xFF;

							red += (R * (k.getKernels()[yOffset + k.getKernels().length / 2])[xOffset
									+ k.getKernels().length / 2] * multiFactor);
							green += (G * k.getKernels()[yOffset + k.getKernels().length / 2][xOffset
									+ k.getKernels().length / 2] * multiFactor);
							blue += (B * k.getKernels()[yOffset + k.getKernels().length / 2][xOffset
									+ k.getKernels().length / 2] * multiFactor);
							alpha += (A * k.getKernels()[yOffset + k.getKernels().length / 2][xOffset
									+ k.getKernels().length / 2] * multiFactor);

						}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("ERROR, TRY AGAIN");
				}

				outR = (int) Math.min(Math.max((red + bias), 0), 255);
				outG = (int) Math.min(Math.max((green + bias), 0), 255);
				outB = (int) Math.min(Math.max((blue + bias), 0), 255);
				outA = (int) Math.min(Math.max((alpha + bias), 0), 255);

				// Reassemble colour channels into one variable
				outRGB = outRGB | (outA << 24);
				outRGB = outRGB | (outR << 16);
				outRGB = outRGB | (outG << 8);
				outRGB = outRGB | outB;

				// Outputting individual colour channels
				output.setRGB(xCoord, yCoord, new Color(outR, outG, outB).getRGB());
			}
		}
		System.out.println(ConsoleColour.RED);
		System.out.println("Processing...");
		System.out.println(ConsoleColour.RESET);
		return output;
	}

	private static BufferedImage getImageChoice(BufferedImage image) {

		return image; 
	}

	public static double getBias() {
		return bias;
	}

	public static void setBias(double bias) {
		ImageProcess.bias = bias;
	}

	public static double getMultiFactor() {
		return multiFactor;
	}

	public static void setMultiFactor(double multiFactor) {
		ImageProcess.multiFactor = multiFactor;
	}

}
