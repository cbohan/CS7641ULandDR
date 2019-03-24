import java.io.BufferedReader;
import java.io.FileReader;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.core.Debug.Random;
import weka.core.EuclideanDistance;

public class KMeans {	
	public static int[] doKMeans(String dataset, int k, int groupSize, String[] groupNames) {
		try {
			Instances data = new Instances(new BufferedReader(new FileReader(dataset))); 
			data.setClassIndex(data.numAttributes() - 1);
			Remove filter = new Remove();
			filter.setAttributeIndices(("" + (data.classIndex() + 1)));
			filter.setInputFormat(data);
			Instances dataClusterer = Filter.useFilter(data, filter);
			return doKMeans(dataClusterer, k, groupSize, groupNames);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static int[] doKMeans(Instances dataset, int k, int groupSize, String[] groupNames) {
		System.out.println("\nK-Means Clustering:");
		
		SimpleKMeans kmeans = new SimpleKMeans();
		Random rand = new Random();
		kmeans.setSeed(rand.nextInt());
		kmeans.setPreserveInstancesOrder(true);
		try {
			kmeans.setNumClusters(k);
			kmeans.setDistanceFunction(new EuclideanDistance());
			kmeans.buildClusterer(dataset);
			
			int[] assignments = kmeans.getAssignments();
			int i=0;
			for(int clusterNum : assignments) {
				int groupNum = i / groupSize;
				if (groupNames != null) {
					String groupName = groupNames[groupNum];
				    System.out.print(groupName + " -> Cluster " + clusterNum + " \n");
				}
			    i++;
			}
			
			System.out.println("Squared error: " + kmeans.getSquaredError());
			
			return assignments;
		} catch (Exception e) { 
			e.printStackTrace();		
		}
		
		return null;
	}
	
	public static double kMeansAverageSquaredError(String dataset, int k, int groupSize, String[] groupNames, int runs) {
		double totalError = 0;
		for (int i = 0; i < runs; i++)
			totalError += kMeansSquaredError(dataset, k, groupSize, groupNames);
		
		return totalError / runs;
	}
	
	private static double kMeansSquaredError(String datasetName, int k, int groupSize, String[] groupNames) {
		
		try {
			Instances data = new Instances(new BufferedReader(new FileReader(datasetName))); 
			data.setClassIndex(data.numAttributes() - 1);
			Remove filter = new Remove();
			filter.setAttributeIndices(("" + (data.classIndex() + 1)));
			filter.setInputFormat(data);
			Instances dataset = Filter.useFilter(data, filter);
			
			SimpleKMeans kmeans = new SimpleKMeans();
			Random rand = new Random();
			kmeans.setSeed(rand.nextInt());
			kmeans.setPreserveInstancesOrder(true);
			
			kmeans.setNumClusters(k);
			kmeans.setDistanceFunction(new EuclideanDistance());
			kmeans.buildClusterer(dataset);
	
			return kmeans.getSquaredError();
			
		} catch (Exception e) { 
			e.printStackTrace();		
		}
		
		return 0;
	}
}
