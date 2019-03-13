import java.io.BufferedReader;
import java.io.FileReader;

import weka.clusterers.EM;
import weka.core.Instances;
import weka.core.Debug.Random;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class ExpectationMaximization {
	public static void doEM(String dataset, int k, int groupSize, String[] groupNames) {
		EM em = new EM();
		Random rand = new Random();
		em.setSeed(rand.nextInt());
		try {
			em.setNumClusters(k);
			em.setMaxIterations(1000);
			Instances data = new Instances(new BufferedReader(new FileReader(dataset))); 
			data.setClassIndex(data.numAttributes() - 1);
			Remove filter = new Remove();
			filter.setAttributeIndices(("" + (data.classIndex() + 1)));
			filter.setInputFormat(data);
			Instances dataClusterer = Filter.useFilter(data, filter);
			
			
			em.buildClusterer(dataClusterer);
						
			for (int i = 0; i < dataClusterer.size(); i++) {
				int groupNum = i / groupSize;
				String groupName = groupNames[groupNum];
				
				int bestCluster = 0;
				double highestClusterProb = 0;
				for (int j = 0; j < k; j++) {
					if (em.distributionForInstance(dataClusterer.get(i))[j] > highestClusterProb) {
						bestCluster = j;
						highestClusterProb = em.distributionForInstance(dataClusterer.get(i))[j];
					}
				}
				
			    System.out.printf(groupName + " -> Cluster " + bestCluster + " \n");
			    i++;
			}
		} catch (Exception e) { 
			e.printStackTrace();		
		}
	}
}
