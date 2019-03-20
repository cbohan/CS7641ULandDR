import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.RandomProjection;
import weka.filters.unsupervised.attribute.Remove;

public class RCA {
	public static Instances doRCA(String dataset, int outputNumAtts) {
		return doRCA(dataset, outputNumAtts, false, null);
	}
	public static Instances doRCA(String dataset, int outputNumAtts, boolean printInstances, String[] pokemonNames) {
		try {
			Instances data = new Instances(new BufferedReader(new FileReader(dataset)));
			data.setClassIndex(data.numAttributes() - 1);
			Remove filter = new Remove();
			filter.setAttributeIndices(("" + (data.classIndex() + 1)));
			filter.setInputFormat(data);
			Instances dataRCA = Filter.useFilter(data, filter);
			
			RandomProjection rcaFilter = new RandomProjection();
			Random rand = new Random();
			rcaFilter.setSeed(rand.nextInt());
			rcaFilter.setNumberOfAttributes(outputNumAtts);
			rcaFilter.setInputFormat(dataRCA);
			for (int i = 0; i < dataRCA.numInstances(); i++)
				rcaFilter.input(dataRCA.instance(i));
			rcaFilter.batchFinished();
			Instances transformedData = rcaFilter.getOutputFormat();
			Instance processed;
			while ((processed = rcaFilter.output()) != null)
				transformedData.add(processed);			
						
			if (printInstances)
				System.out.println(transformedData);
			
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
