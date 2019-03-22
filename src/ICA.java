import java.io.BufferedReader;
import java.io.FileReader;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.IndependentComponents;
import weka.filters.unsupervised.attribute.Remove;

public class ICA {
	static IndependentComponents icaFilter;
	
	public static Instances doICA(String dataset, int outputNumAtts) {
		return doICA(dataset, outputNumAtts, false, null, true);
	}
	
	public static Instances doICA(String dataset, int outputNumAtts, boolean printInstances, String[] pokemonNames, boolean createNewICA) {
		try {
			Instances data = new Instances(new BufferedReader(new FileReader(dataset)));
			data.setClassIndex(data.numAttributes() - 1);
			Remove filter = new Remove();
			filter.setAttributeIndices(("" + (data.classIndex() + 1)));
			filter.setInputFormat(data);
			Instances dataICA = Filter.useFilter(data, filter);
			
			if (createNewICA) {
				icaFilter = new IndependentComponents();
				icaFilter.setInputFormat(dataICA);
				icaFilter.setOutputNumAtts(outputNumAtts);	
			}
			
			for (int i = 0; i < dataICA.numInstances(); i++)
				icaFilter.input(dataICA.instance(i));
			icaFilter.batchFinished();		
			Instances transformedData = icaFilter.getOutputFormat();
			Instance processed;
			while ((processed = icaFilter.output()) != null)
				transformedData.add(processed);

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
				
				if (printInstances) 
					System.out.println(newData);
				return newData;
			}
			
			if (printInstances) 
				System.out.println(transformedData);
			return transformedData;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return null;
	}
}
