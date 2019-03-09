import java.util.ArrayList;
import java.util.Random;

public class KMeansCluster {
	public static void doClustering(int k, ArrayList<Image> images) {
		Random rand = new Random();
		
		//Pick k centers at random
		Cluster[] clusters = new Cluster[k];
		for (int i = 0; i < k; i++) {
			boolean selectedValue = false;
			while (selectedValue == false) {
				int randomIndex = rand.nextInt(images.size());
				
				boolean alreadyTaken = false;
				for (int j = 0; j < i; j++) 
					if (clusters[j].index == randomIndex)
						alreadyTaken = true;
				
				if (alreadyTaken == false) {
					selectedValue = true;
					clusters[i] = new Cluster(images.get(randomIndex).copy(), randomIndex);
				}
			}
		}
		
		boolean hasConverged = false;
		int iteration = 1;
		while (hasConverged == false) {	
			iteration++;
			
			//Each center claims closest points.
			for (Cluster c : clusters)
				c.clearImages();
			for (Image image : images) {
				double closestClusterDistance = Double.MAX_VALUE;
				Cluster closestCluster = null;
				for (Cluster c : clusters) {
					if (Image.getDistance(c.center, image) < closestClusterDistance) {
						closestClusterDistance = Image.getDistance(c.center, image);
						closestCluster = c;
					}
				}
				
				closestCluster.imagesInCluster.add(image);
			}
			
			//Recompute centers by averaging points in cluster.
			for (Cluster c : clusters)
				c.computeAverage();
			
			//Check if we're done.
			boolean foundDifference = false;
			for (Cluster c : clusters) 
				if (c.hasChanged()) 
					foundDifference = true;
			
			if (foundDifference == false)
				hasConverged = true;
			
			
		}
		
		System.out.println("Number of iterations: " + iteration);
		for (int i = 0; i < clusters.length; i++) {
			System.out.println("Cluster #" + i);
			for (Image image : clusters[i].imagesInCluster)
				System.out.println(" - " + image.getImageName());
			System.out.println();
		}
	}
}

class Cluster {
	Image center;
	int index;
	ArrayList<Image> imagesInCluster;
	ArrayList<Image> imagesInLastCluster;
	
	public Cluster(Image center, int index) {
		this.center = center;
		this.index = index;
		
		imagesInCluster = new ArrayList<Image>();
		imagesInLastCluster = new ArrayList<Image>();
	}
	
	public void clearImages() {
		imagesInLastCluster.clear();
		for (Image image : imagesInCluster)
			imagesInLastCluster.add(image);
		imagesInCluster.clear();
	}
	
	public void computeAverage() {
		for (int x = 0; x < center.getImageSize(); x++) {
			for (int y = 0; y < center.getImageSize(); y++) {
				for (int rgb = 0; rgb < 3; rgb++) {
					double total = 0;
					int instances = 0;
					
					for (Image image : imagesInCluster) {
						total += image.getImageData(x, y, rgb);
						instances++;
					}
					float newValue = (float) (total / instances);
					
					if (instances > 0) {
						center.setImageData(x, y, rgb, newValue);
					}
				}
			}
		}
	}
	
	public boolean hasChanged() {		
		boolean allImagesSame = true;
		for (Image image : imagesInCluster) 
			if (imagesInLastCluster.contains(image) == false) 
				allImagesSame = false;
		for (Image image : imagesInLastCluster)
			if (imagesInCluster.contains(image) == false)
				allImagesSame = false;
		
		return !allImagesSame;
	}
}
