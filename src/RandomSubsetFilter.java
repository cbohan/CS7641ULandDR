import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.RandomSubset;
import weka.filters.unsupervised.attribute.Remove;

public class RandomSubsetFilter {
	static RandomSubset rsFilter;
	
	public static Instances doRandomSubsetFilter(String dataset, int ouputNumAtts) {
		return doRandomSubsetFilter(dataset, ouputNumAtts, false, null, true);
	}
	
	public static Instances doRandomSubsetFilter(String dataset, int outputNumAtts, boolean printInstances, String[] pokemonNames, boolean createNewRandomSubset) {
		try {
			Instances data = new Instances(new BufferedReader(new FileReader(dataset)));
			data.setClassIndex(data.numAttributes() - 1);
			Remove filter = new Remove();
			filter.setAttributeIndices(("" + (data.classIndex() + 1)));
			filter.setInputFormat(data);
			Instances dataRS = Filter.useFilter(data, filter);
			
			if (createNewRandomSubset) {
				rsFilter = new RandomSubset();
				Random rand = new Random();
				rsFilter.setSeed(rand.nextInt());
				rsFilter.setNumAttributes(outputNumAtts);
				rsFilter.setInputFormat(dataRS);
			}
			for (int i = 0; i < dataRS.numInstances(); i++)
				rsFilter.input(dataRS.instance(i));
			rsFilter.batchFinished();
			Instances transformedData = rsFilter.getOutputFormat();
			Instance processed;
			while ((processed = rsFilter.output()) != null)
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
