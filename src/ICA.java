import java.io.BufferedReader;
import java.io.FileReader;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.IndependentComponents;

public class ICA {
	public static void doICA(String dataset, int outputNumAtts) {
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
									
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
