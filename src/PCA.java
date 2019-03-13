import java.io.BufferedReader;
import java.io.FileReader;

import weka.attributeSelection.PrincipalComponents;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class PCA {
	public static void doPCA(String dataset) {
		try {
			Instances data = new Instances(new BufferedReader(new FileReader(dataset)));
			data.setClassIndex(data.numAttributes() - 1);
			Remove filter = new Remove();
			filter.setAttributeIndices(("" + (data.classIndex() + 1)));
			filter.setInputFormat(data);
			Instances dataPCA = Filter.useFilter(data, filter);
			
			PrincipalComponents pca = new PrincipalComponents();
			pca.buildEvaluator(dataPCA);
			//Instances transformed = pca.transformedData(dataPCA);
			
			int substantialEigenValues = 0;
			for (int i = 0; i < pca.getEigenValues().length; i++) {
				substantialEigenValues += (pca.getEigenValues()[i] > .01) ? 1 : 0;
			}
			
			System.out.println(substantialEigenValues + " / " + pca.getEigenValues().length);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
