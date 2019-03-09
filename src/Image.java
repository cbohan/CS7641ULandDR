import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	private float[][][] imageData;
	private String imageName;
	private int imageSize;
	private int width, height;
	
	private Image() {}
	public Image(String fileName) {
		BufferedImage img = null;
		try {			
			File file = new File(fileName);
			img = ImageIO.read(file);
			imageName = file.getName();
			imageSize = img.getWidth();			
			
			if (img.getWidth() != img.getHeight())
				System.err.println("Image width != image hieght: " + fileName);
			if (imageSize != 100 && imageSize != 28)
				System.err.println("Image is not 100x100 or 28x28: " + fileName);
			
			width = img.getWidth();
			height = img.getHeight();
			
			imageData = new float[imageSize][imageSize][3];
			float[] pixel = new float[4];
			for (int x = 0; x < imageSize; x++) {
				for (int y = 0; y < imageSize; y++) {
					img.getRaster().getPixel(x, y, pixel);
					for (int rgb = 0; rgb < 3; rgb++)
						imageData[x][y][rgb] = pixel[rgb] / 255f;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Image copy() {
		Image image = new Image();
		image.imageData = new float[imageSize][imageSize][3];
		for (int x = 0; x < imageSize; x++)
			for (int y = 0; y < imageSize; y++)
				for (int rgb = 0; rgb < 3; rgb++)
					image.imageData[x][y][rgb] = this.imageData[x][y][rgb];
		image.imageName = this.imageName;
		image.imageSize = this.imageSize;
		image.width = this.width;
		image.height = this.height;
		
		return image;
	}
	
	public float getImageData(int x, int y, int rgb) { return imageData[x][y][rgb]; }
	public void setImageData(int x, int y, int rgb, float value) { imageData[x][y][rgb] = value; }
	public int getImageSize() { return imageSize; }
	public String getImageName() { return imageName; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	public static double getDistance(Image image1, Image image2) {
		double distanceSquared = 0;
		
		for (int x = 0; x < image1.imageSize; x++) 
			for (int y = 0; y < image1.imageSize; y++) 
				for (int rgb = 0; rgb < 3; rgb++) 
					distanceSquared += (image1.imageData[x][y][rgb] - image2.imageData[x][y][rgb]) * (image1.imageData[x][y][rgb] - image2.imageData[x][y][rgb]);
		
		return Math.sqrt(distanceSquared);
	}
}
