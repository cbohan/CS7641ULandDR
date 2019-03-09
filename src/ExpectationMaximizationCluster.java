import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ExpectationMaximizationCluster {
	public static void doClustering(int k, ArrayList<Image> images) {
		Random rand = new Random();
		
		//Pick k centers at random
		EMCluster[] clusters = new EMCluster[k];
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
					clusters[i] = new EMCluster(images.get(randomIndex).copy(), randomIndex);
				}
			}
		}
		
		//Convert images to EMImages
		ArrayList<EMImage> emImages = new ArrayList<EMImage>();
		for (Image image : images)
			emImages.add(new EMImage(image));
		
		int iteration = 0;
		for (int i = 0; i < 20; i++) {
			expectation(clusters, emImages);
			maximization(clusters, emImages);
			iteration++;
		}
		
		System.out.println("Number of iterations: " + iteration);
		for (int i = 0; i < clusters.length; i++) {
			System.out.println("Cluster #" + i);
			
			for (EMImage image : emImages) {
				EMCluster maxCluster = null;
				double maxClusterProb = -1;
				for (EMCluster cluster : clusters) {
					/*System.out.println(image.getClusterProbability(cluster) + " > " +
								maxClusterProb);*/
					
					if (image.getClusterProbability(cluster) > maxClusterProb) {
						maxClusterProb = image.getClusterProbability(cluster);
						maxCluster = cluster;
					}
						
				}
				
				if (maxCluster == clusters[i]) 
					System.out.println(" - " + image.image.getImageName());				
			}
			System.out.println();
		}
	}
	
	private static void expectation(EMCluster[] clusters, ArrayList<EMImage> images) {
		for (EMImage image : images) {
			double totalProbability = 0;
			image.clearClusters();
			
			for (EMCluster cluster : clusters) {
				double scaledDifference = Math.pow(Image.getDistance(cluster.center, image.image), 2);
				//Why scale the square difference by one over the square root of the width of
				//an image? It seems to work. I don't know why.
				scaledDifference /= Math.sqrt(cluster.center.getWidth());
				//System.out.println(scaledDifference);
				double probability = Math.exp(-.5 * scaledDifference);
				image.setClusterProbability(cluster, probability);
				totalProbability += probability;
			}
			
			for (EMCluster cluster : clusters)
				image.updateClusterProbability(cluster, totalProbability);
		}
	}
	
	private static void maximization(EMCluster[] clusters, ArrayList<EMImage> images) {
		for (EMCluster cluster : clusters) {
			cluster.computeAverage(images);
		}
	}
}

class EMImage {
	Image image;
	HashMap<EMCluster, Double> clusterProbability;
	
	public EMImage(Image image) {
		this.image = image;
		clusterProbability = new HashMap<EMCluster, Double>();
	}
	
	public Image getImage() { return image; }
	public double getClusterProbability(EMCluster cluster) { return clusterProbability.get(cluster); } 
	public void clearClusters() { clusterProbability.clear(); }
	public void setClusterProbability(EMCluster cluster, double probability) {
		clusterProbability.put(cluster, probability);
	}
	public void updateClusterProbability(EMCluster cluster, double normalizationFactor) {
		double oldProb = clusterProbability.get(cluster);
		double newProb = oldProb / normalizationFactor;
		clusterProbability.put(cluster, newProb);
	}
	
	
}

class EMCluster {
	Image center;
	int index;
	
	public EMCluster(Image center, int index) {
		this.center = center;
		this.index = index;
	}
	
	public void computeAverage(ArrayList<EMImage> images) {
		for (int x = 0; x < center.getImageSize(); x++) {
			for (int y = 0; y < center.getImageSize(); y++) {
				for (int rgb = 0; rgb < 3; rgb++) {
					double total = 0;
					double  instances = 0;
					
					for (EMImage image : images) {
						total += image.image.getImageData(x, y, rgb) * 
							image.getClusterProbability(this);
						instances += image.getClusterProbability(this);
					}
					
					float newValue = (float) (total / instances);
					
					if (instances > 0) {
						center.setImageData(x, y, rgb, newValue);
					}
				}
			}
		}
	}
}