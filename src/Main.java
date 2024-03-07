package main;

// Importing necessary libraries
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import javax.imageio.ImageIO;

// Main class
public class Main {
	
	// Main method
	public static void main(String[] args) throws Exception {
		// Initializing and declaring some useful variables
		int i = 0, j = 0, width, height, r_width, r_height, scale;
		
		// Loads the image we will convert to ASCII
		BufferedImage img = ImageIO.read(new File("convento.jpg"));
		
		// Creates the output .txt file and the object that will
		// write the characters on it
		File result = new File("ascii_art.txt");
		FileWriter txtwrite = new FileWriter("ascii_art.txt");
		
		// Creates a desktop object, which will be used to open
		// the result file
		Desktop desktop = Desktop.getDesktop();
		
		// Saving width and height of original image
		width = img.getWidth();
		height = img.getHeight();
		// Saving the ratio of the image so result won't be distorted
		// + 1 so scale will never be 0 
		scale = width / height + 1;
		// Setting the height and width of result image
		// width is doubled cause characters are taller, and not perfect squares like pixels
		r_height = 50;
		r_width = 2 * r_height * scale;
		
		// Creates space in the memory for the result image
		BufferedImage r_img = new BufferedImage(r_width, r_height, img.getType());
		// Creates a Graphics2D object, able to draw onto r_img
		Graphics2D g2d = r_img.createGraphics();
		// Draws the original image into r_img, but resized
		g2d.drawImage(img, 0, 0, r_width, r_height, null);
		// Discards g2d to free memory
		g2d.dispose();
		
		// Clears the buffer for txtwrite to avoid malfunctioning
		txtwrite.flush();
		
		// Runs through each pixel of original image
		// from left to right, up to down
		for (i = 0; i < r_height; i++) {
			for (j = 0; j < r_width; j++) {
				// Gets the color, store hue, saturation and brightness
				Color temp = new Color(r_img.getRGB(j, i));
				float[] hsb = Color.RGBtoHSB(temp.getRed(), temp.getGreen(), temp.getBlue(), null);
				
				// Use the stored brightness value to determine the character to be used
				if (hsb[2] < 0.2) {
					txtwrite.write('.');
				} else if (hsb[2] < 0.4) {
					txtwrite.write(':');
				} else if (hsb[2] < 0.6) {
					txtwrite.write(';');
				} else if (hsb[2] < 0.8) {
					txtwrite.write('+');
				} else {
					txtwrite.write('#');
				}
	
			}
			txtwrite.write('\n');
		}
		
		// Frees the buffer
		txtwrite.close();
		
		// Open result file
		desktop.open(result);
	}

}
