import java.io.BufferedReader;
import java.io.FileReader;

import weka.clusterers.EM;
import weka.core.Instances;
import weka.core.Debug.Random;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class ExpectationMaximization {
	public static int[] doEM(String dataset, int k, int groupSize, String[] groupNames) {
		try {
			Instances data = new Instances(new BufferedReader(new FileReader(dataset))); 
			data.setClassIndex(data.numAttributes() - 1);
			Remove filter = new Remove();
			filter.setAttributeIndices(("" + (data.classIndex() + 1)));
			filter.setInputFormat(data);
			Instances dataClusterer = Filter.useFilter(data, filter);
			return doEM(dataClusterer, k, groupSize, groupNames);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static int[] doEM(Instances data, int k, int groupSize, String[] groupNames) {
		System.out.println("\nEM Clustering:");
		
		EM em = new EM();
		Random rand = new Random();
		em.setSeed(rand.nextInt());
		try {
			em.setNumClusters(k);
			em.setMaxIterations(1000);
			em.buildClusterer(data);
			
			int[] assignments = new int[data.size()];
						
			for (int i = 0; i < data.size(); i++) {
				int groupNum = i / groupSize;
				
				int bestCluster = 0;
				double highestClusterProb = 0;
				for (int j = 0; j < k; j++) {
					if (em.distributionForInstance(data.get(i))[j] > highestClusterProb) {
						bestCluster = j;
						highestClusterProb = em.distributionForInstance(data.get(i))[j];
					}
				}
				
				assignments[i] = bestCluster; 
				if (groupNames != null) {
					String groupName = groupNames[groupNum];
				    System.out.print(groupName + " -> Cluster " + bestCluster + " \n");
				}
			}
			
			return assignments;
		} catch (Exception e) { 
			e.printStackTrace();		
		}
		
		return null;
	}
}
