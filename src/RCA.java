import java.io.BufferedReader;
import java.io.FileReader;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.RandomProjection;

public class RCA {
	public static void doRCA(String dataset, int outputNumAtts) {
		try {
			Instances dataRCA = new Instances(new BufferedReader(new FileReader(dataset)));
			
			RandomProjection rcaFilter = new RandomProjection();
			rcaFilter.setNumberOfAttributes(outputNumAtts);
			rcaFilter.setInputFormat(dataRCA);
			for (int i = 0; i < dataRCA.numInstances(); i++)
				rcaFilter.input(dataRCA.instance(i));
			rcaFilter.batchFinished();
			Instances transformedData = rcaFilter.getOutputFormat();
			Instance processed;
			while ((processed = rcaFilter.output()) != null)
				transformedData.add(processed);			
									
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
