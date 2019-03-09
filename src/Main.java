
public class Main {

	public static void main(String[] args) {
		DogDataLoader.init();
		DigitDataLoader.init();
		KMeansCluster.doClustering(3, DogDataLoader.dogImages);
		//KMeansCluster.doClustering(5, DigitDataLoader.digitImages);
		//ExpectationMaximizationCluster.doClustering(3, DogDataLoader.dogImages);
		//ExpectationMaximizationCluster.doClustering(5, DigitDataLoader.digitImages);
	}

}
