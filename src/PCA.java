import java.io.BufferedReader;
import java.io.FileReader;

import weka.attributeSelection.PrincipalComponents;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.Remove;

public class PCA {
	private static PrincipalComponents pca;

	public static Instances doPCA(String dataset, double vc) { 
		return doPCA(dataset, vc, false, null, true); 
	}
	public static Instances doPCA(String dataset, double vc, boolean printEigenValues, String[] pokemonNames, boolean createNewPCA) {
		try {
			Instances data = new Instances(new BufferedReader(new FileReader(dataset)));
			data.setClassIndex(data.numAttributes() - 1);
			Remove filter = new Remove();
			filter.setAttributeIndices(("" + (data.classIndex() + 1)));
			filter.setInputFormat(data);
			Instances dataPCA = Filter.useFilter(data, filter);
			
			if (createNewPCA){
				pca = new PrincipalComponents();
				pca.setVarianceCovered(vc);
				pca.buildEvaluator(dataPCA);
			}
			Instances transformed = pca.transformedData(dataPCA);
						
			if (printEigenValues) {
				int substantialEigenValues = 0;
				for (int i = 0; i < pca.getEigenValues().length; i++) {
					substantialEigenValues += (pca.getEigenValues()[i] > .01) ? 1 : 0;
					System.out.println(pca.getEigenValues()[i]);
				}
				System.out.println(substantialEigenValues + " / " + pca.getEigenValues().length);
			}
			
			if (pokemonNames != null) {
				Add pokemonName = new Add();
				pokemonName.setAttributeIndex("last");
				String possibleNames = "";
				for (int i = 0; i < pokemonNames.length; i++) {
					possibleNames += pokemonNames[i];
					if (i < pokemonNames.length - 1)
						possibleNames += ",";
				}
				pokemonName.setNominalLabels(possibleNames);
				pokemonName.setAttributeName("name");
				pokemonName.setInputFormat(transformed);
				Instances newData = Filter.useFilter(transformed, pokemonName);
				newData.setClassIndex(newData.numAttributes() - 1);				
				
				for (int i = 0; i < transformed.size(); i++) {
					Instance curInstance = newData.get(i);
					String curName = pokemonNames[i / 25];
					curInstance.setValue(newData.numAttributes() - 1, curName);
				}
				
				return newData;
			}
			
			return transformed;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return null;
	}
}
