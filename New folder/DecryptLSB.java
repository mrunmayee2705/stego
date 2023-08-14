import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;




public class DecryptLSB {
	public static void Decrypt() {
		String directory = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
		String newImageFileString = directory + "\\export.jpeg";
		File newImageFile = new File(newImageFileString);
		BufferedImage image;
		try {
			image = ImageIO.read(newImageFile);
			Pixel[] pixels = GetPixelArray(image);
			System.out.println(DecodeMessageFromPixels(pixels)); 
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	//reads the pixel data to extract hideen message iterates the pixel
	//extracts the lsb and reconstrcucts message
	private static Pixel[] GetPixelArray(BufferedImage imageToEncrypt){
		int height = imageToEncrypt.getHeight();
		int width = imageToEncrypt.getWidth();
		Pixel[] pixels = new Pixel[height * width];
		
		int count = 0;
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {				
				Color colorToAdd = new Color(imageToEncrypt.getRGB(x, y));
				pixels[count] = new Pixel(x, y, colorToAdd);
				count++;
			}
		}
		return pixels;
	};
	
	private static String DecodeMessageFromPixels(Pixel[] pixels) {
		boolean completed = false;
		int pixelArrayIndex = 0; 
		StringBuilder messageBuilder = new StringBuilder("");
		while(completed == false) {
			Pixel[] pixelsToRead = new Pixel[3];
			for(int i = 0; i < 3; i++) {
				pixelsToRead[i] = pixels[pixelArrayIndex];
				pixelArrayIndex++;
			}
			messageBuilder.append(ConvertPixelsToCharacter(pixelsToRead));
			if(IsEndOfMessage(pixelsToRead[2]) == true) {
				completed = true;
			}
		}
		return messageBuilder.toString();
	}
	// converts the LSBs of their color values into a binary string 
	//and then converts that binary string into a character.
	private static char ConvertPixelsToCharacter(Pixel[] pixelsToRead) {
		ArrayList<String> binaryValues = new ArrayList<String>();
		for(int i = 0; i < pixelsToRead.length; i++) {
			String[] currentBinary = TurnPixelIntegersToBinary(pixelsToRead[i]);
			binaryValues.add(currentBinary[0]);
			binaryValues.add(currentBinary[1]);
			binaryValues.add(currentBinary[2]);
		}
		return ConvertBinaryValuesToCharacter(binaryValues);
	}
	
	//converts rgb values into respective strings
	private static String[] TurnPixelIntegersToBinary(Pixel pixel) {
		String[] values = new String[3];
		values[0] = Integer.toBinaryString(pixel.getColor().getRed());
		values[1] = Integer.toBinaryString(pixel.getColor().getGreen());
		values[2] = Integer.toBinaryString(pixel.getColor().getBlue());
		return values;
	}

	// Checks if a pixel's blue channel LSB indicates the end of the message. 
	// If the LSB is '1', it's not the end; if it's '0', it's the end.
	
	private static boolean IsEndOfMessage(Pixel pixel) {
		if(TurnPixelIntegersToBinary(pixel)[2].endsWith("1")) {
			return false;
		}
		return true;
	}
	// //  the LSBs from the array of binary strings, removes any padding, converts the remaining binary string to an integer, 
	// and finally converts the integer back to a character, which represents the original message character that was encoded in the image. 
	// This process reconstructs the hidden message character by character.
	private static char ConvertBinaryValuesToCharacter(ArrayList<String> binaryValues) {
		StringBuilder endBinary = new StringBuilder("");
		for(int i = 0; i < binaryValues.size()-1; i++) {
			endBinary.append(binaryValues.get(i).charAt(binaryValues.get(i).length()-1));
		}
		String endBinaryString = endBinary.toString();
		String noZeros = RemovePaddedZeros(endBinaryString);
		int ascii = Integer.parseInt(noZeros, 2);
		return (char) ascii;
	}
	
	private static String RemovePaddedZeros(String endBinary) {
		StringBuilder builder = new StringBuilder(endBinary);
		int paddedZeros = 0;
		for(int i = 0; i < builder.length(); i++) {
			if(builder.charAt(i) == '0') {
				paddedZeros++;
			}
			else {
				break;
			}
		}
		for(int i = 0 ; i < paddedZeros; i++) {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}
}