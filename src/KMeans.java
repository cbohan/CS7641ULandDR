import java.io.BufferedReader;
import java.io.FileReader;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.core.Debug.Random;
import weka.core.EuclideanDistance;

public class KMeans {	
	public static void doKMeans(String dataset, int k, int groupSize, String[] groupNames) {
		SimpleKMeans kmeans = new SimpleKMeans();
		Random rand = new Random();
		kmeans.setSeed(rand.nextInt());
		kmeans.setPreserveInstancesOrder(true);
		try {
			kmeans.setNumClusters(k);
			kmeans.setDistanceFunction(new EuclideanDistance());
			Instances data = new Instances(new BufferedReader(new FileReader(dataset))); 
			data.setClassIndex(data.numAttributes() - 1);
			Remove filter = new Remove();
			filter.setAttributeIndices(("" + (data.classIndex() + 1)));
			filter.setInputFormat(data);
			Instances dataClusterer = Filter.useFilter(data, filter);
			
			kmeans.buildClusterer(dataClusterer);
			
			int[] assignments = kmeans.getAssignments();
			int i=0;
			for(int clusterNum : assignments) {
				int groupNum = i / groupSize;
				String groupName = groupNames[groupNum];
			    System.out.printf(groupName + " -> Cluster " + clusterNum + " \n");
			    i++;
			}
		} catch (Exception e) { 
			e.printStackTrace();		
		}
	}
}
