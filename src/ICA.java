import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.IndependentComponents;

public class ICA {
	public static Instances doICA(String dataset, int outputNumAtts) {
		return doICA(dataset, outputNumAtts, false, null);
	}
	
	public static Instances doICA(String dataset, int outputNumAtts, boolean printInstances, String[] pokemonNames) {
		try {
			Instances dataICA = new Instances(new BufferedReader(new FileReader(dataset)));
			
			IndependentComponents icaFilter = new IndependentComponents();
			icaFilter.setInputFormat(dataICA);
			icaFilter.setOutputNumAtts(outputNumAtts);
			for (int i = 0; i < dataICA.numInstances(); i++)
				icaFilter.input(dataICA.instance(i));
			icaFilter.batchFinished();			
			Instances transformedData = icaFilter.getOutputFormat();
			Instance processed;
			while ((processed = icaFilter.output()) != null)
				transformedData.add(processed);
			
			if (printInstances) {
				System.out.println(transformedData);
				
				Kurtosis kurtosis = new Kurtosis();
				double[][] values = new double[transformedData.size()][outputNumAtts];
				for (int i = 0; i < transformedData.size(); i++) {
					Instance curInstance = transformedData.get(i);
					for (int j = 0; j < curInstance.toDoubleArray().length; j++)
						values[i][j] = curInstance.toDoubleArray()[j];
					
				}
				
				double avgK = 0;
				double[] kurtValues = new double[transformedData.size()];
				for (int i = 0; i < outputNumAtts; i++) {
					for (int j = 0; j < transformedData.size(); j++)
						kurtValues[j] = values[j][i];
					
					System.out.println(kurtosis.evaluate(kurtValues));
					avgK += Math.abs(kurtosis.evaluate(kurtValues));
				}
				System.out.println(avgK / outputNumAtts);
				
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
				pokemonName.setInputFormat(transformedData);
				Instances newData = Filter.useFilter(transformedData, pokemonName);
				newData.setClassIndex(newData.numAttributes() - 1);				
				
				for (int i = 0; i < transformedData.size(); i++) {
					Instance curInstance = newData.get(i);
					String curName = pokemonNames[i / 25];
					curInstance.setValue(newData.numAttributes() - 1, curName);
				}
				
				return newData;
			}
			
			return transformedData;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return null;
	}
}
