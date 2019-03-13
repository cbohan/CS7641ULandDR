import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	private double[] imageData;
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
			
			imageData = new double[imageSize * imageSize];
			float[] pixel = new float[4];
			for (int x = 0; x < imageSize; x++) {
				for (int y = 0; y < imageSize; y++) {
					img.getRaster().getPixel(x, y, pixel);
					double totalRGB = 0;
					for (int rgb = 0; rgb < 3; rgb++) {
						totalRGB += pixel[rgb] / 255.0;
					}
					imageData[(x * imageSize) + y] = totalRGB / 3.0;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Image copy() {
		Image image = new Image();
		image.imageData = new double[imageSize * imageSize];
		for (int x = 0; x < imageSize; x++)
			for (int y = 0; y < imageSize; y++)
				image.imageData[x * imageSize + y] = this.imageData[x * imageSize + y];
		image.imageName = this.imageName;
		image.imageSize = this.imageSize;
		image.width = this.width;
		image.height = this.height;
		
		return image;
	}
	
	public double[] getImageData() { return imageData; }
	public int getImageSize() { return imageSize; }
	public String getImageName() { return imageName; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	public static double getDistance(Image image1, Image image2) {
		double distanceSquared = 0;
		
		for (int x = 0; x < image1.imageSize; x++) 
			for (int y = 0; y < image1.imageSize; y++) 
				distanceSquared += (image1.imageData[x * image1.imageSize + y] - 
					image2.imageData[x * image1.imageSize + y]) * 
					(image1.imageData[x * image1.imageSize + y] - 
					image2.imageData[x * image1.imageSize + y]);
		
		return Math.sqrt(distanceSquared);
	}
}
