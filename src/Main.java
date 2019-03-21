import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.Remove;

public class Main {
	
	public static final String DIGIT_TRAINING_DATASET = "data" + File.separator + "digitTraining.arff"; 
	public static final String DIGIT_TEST_DATASET = "data" + File.separator + "digitTest.arff"; 
	public static final String POKEMON_TRAINING_DATASET = "data" + File.separator + "pokemonTraining.arff"; 
	public static final String POKEMON_TEST_DATASET = "data" + File.separator + "pokemonTest.arff"; 
	
	private static String[] digitNames = new String[] { "zero", "one", "two", "three", "four" };
	private static String[] pokemonNames = new String[] { "Bulbasaur", "Ivysaur" , "Venusaur",
			"Charmander", "Charmeleon", "Charizard", "Squirtle", "Wartortle", "Blastoise" ,
			"Caterpie", "Metapod", "Butterfree", "Weedle", "Kakuna", "Beedrill", "Pidgey", 
			"Pidgeotto", "Pidgeot", "Rattata", "Raticate", "Spearow", "Fearow", "Ekans", "Arbok",
			"Pikachu", "Raichu", "Sandshrew", "Sandslash", "NidoranF", "Nidorina", "Nidoqueen",
			"NidoranM", "Nidorino", "Nidoking"};
	
	private static final int NEURAL_NET_TRAINING_TIME = 500;
	
	public static void main(String[] args) {
		PokemonARFFGenerator.generateFile(25, false);
		DigitARFFGenerator.generateFile();
		
		//doExperiment1();
		//doExperiment2();
		//doExperiment3();
		doExperiment4();
		//doExperiment5();		
	}
	
	private static void doExperiment1() {
		System.out.println("DIGITS");
		KMeans.doKMeans(DIGIT_TRAINING_DATASET, 5, 15, digitNames);
		ExpectationMaximization.doEM(DIGIT_TRAINING_DATASET, 5, 15, digitNames);
		
		System.out.println("\n\nPOKEMON");
		KMeans.doKMeans(POKEMON_TRAINING_DATASET, 34, 25, pokemonNames);
		ExpectationMaximization.doEM(POKEMON_TRAINING_DATASET, 34, 25, pokemonNames);
	}
	
	private static void doExperiment2() {
		System.out.println("Digit PCA: ");
		PCA.doPCA(DIGIT_TEST_DATASET, .95, true, null, true);
		System.out.println("\n\nPokemon PCA: ");
		PCA.doPCA(POKEMON_TRAINING_DATASET, .95, true, null, true);
		
		System.out.println("DIGIT ICA: ");
		ICA.doICA(DIGIT_TEST_DATASET, 10, true, null);
		System.out.println("\n\nPOKEMON ICA: ");
		ICA.doICA(POKEMON_TRAINING_DATASET, 6, true, null);
		
		System.out.println("DIGIT RCA: ");
		RCA.doRCA(DIGIT_TEST_DATASET, 10, true, null);
		System.out.println("\n\nPOKEMON RCA: ");
		RCA.doRCA(POKEMON_TRAINING_DATASET, 6, true, null);
		
		RandomSubsetFilter.doRandomSubsetFilter(DIGIT_TEST_DATASET, 10);
		RandomSubsetFilter.doRandomSubsetFilter(POKEMON_TRAINING_DATASET, 2);	
	}
	
	private static void doExperiment3() {
		KMeans.doKMeans(PCA.doPCA(DIGIT_TRAINING_DATASET, .95), 5, 15, digitNames);
		ExpectationMaximization.doEM(PCA.doPCA(DIGIT_TRAINING_DATASET, .95), 5, 15, digitNames);
		
		KMeans.doKMeans(PCA.doPCA(POKEMON_TRAINING_DATASET, .95), 34, 25, pokemonNames);
		ExpectationMaximization.doEM(PCA.doPCA(POKEMON_TRAINING_DATASET, .95), 34, 25, pokemonNames);
		
		KMeans.doKMeans(ICA.doICA(DIGIT_TRAINING_DATASET, 3), 5, 15, digitNames);
		ExpectationMaximization.doEM(ICA.doICA(DIGIT_TRAINING_DATASET, 3), 5, 15, digitNames);
		
		KMeans.doKMeans(ICA.doICA(POKEMON_TRAINING_DATASET, 10), 34, 25, pokemonNames);
		ExpectationMaximization.doEM(ICA.doICA(POKEMON_TRAINING_DATASET, 10), 34, 25, pokemonNames);
		
		KMeans.doKMeans(RCA.doRCA(DIGIT_TRAINING_DATASET, 50), 5, 15, digitNames);
		ExpectationMaximization.doEM(RCA.doRCA(DIGIT_TRAINING_DATASET, 50), 5, 15, digitNames);
				
		KMeans.doKMeans(RCA.doRCA(POKEMON_TRAINING_DATASET, 7), 34, 25, pokemonNames);
		ExpectationMaximization.doEM(RCA.doRCA(POKEMON_TRAINING_DATASET, 7), 34, 25, pokemonNames);
		
		KMeans.doKMeans(RandomSubsetFilter.doRandomSubsetFilter(DIGIT_TRAINING_DATASET, 500), 5, 15, digitNames);
		ExpectationMaximization.doEM(RandomSubsetFilter.doRandomSubsetFilter(DIGIT_TRAINING_DATASET, 500), 5, 15, digitNames);
						
		KMeans.doKMeans(RandomSubsetFilter.doRandomSubsetFilter(POKEMON_TRAINING_DATASET, 9), 34, 25, pokemonNames);
		ExpectationMaximization.doEM(RandomSubsetFilter.doRandomSubsetFilter(POKEMON_TRAINING_DATASET, 7), 34, 25, pokemonNames);		
	}
	
	private static void doExperiment4() {
		long startTime = System.nanoTime();
		
		doPokemonDimensionalityReducedNN(
				PCA.doPCA(POKEMON_TRAINING_DATASET, 1, false, pokemonNames, true),
				PCA.doPCA(POKEMON_TEST_DATASET, 1, false, pokemonNames, false));
		//doPokemonDimensionalityReducedNN(ICA.doICA(POKEMON_TRAINING_DATASET, 9, false, pokemonNames));
		//doPokemonDimensionalityReducedNN(RCA.doRCA(POKEMON_TRAINING_DATASET, 9, false, pokemonNames));
		//doPokemonDimensionalityReducedNN(RandomSubsetFilter.doRandomSubsetFilter(POKEMON_TRAINING_DATASET, 2, false, pokemonNames));
		
		long endTime = System.nanoTime();
		long difference = endTime - startTime;
		double seconds = (double)difference / 1000000000.0;
		
		System.out.println("RUNNING TIME: " + seconds);
	}
	
	private static void doExperiment5() {
		try {
			Instances pcaPokemon = PCA.doPCA(POKEMON_TRAINING_DATASET, .95, false, pokemonNames, true);
			pcaPokemon.setClassIndex(pcaPokemon.numAttributes() - 1);
			Remove filter = new Remove();
			filter.setAttributeIndices(("" + (pcaPokemon.classIndex() + 1)));
			filter.setInputFormat(pcaPokemon);
			Instances pcaPokemonSansClassIndex = Filter.useFilter(pcaPokemon, filter);
			
			int[] pokemonClusters = KMeans.doKMeans(pcaPokemonSansClassIndex, 34, 25, pokemonNames);
			//doPokemonDimensionalityReducedNN(prependClusterToInstance(pcaPokemon, pokemonClusters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static Instances prependClusterToInstance(Instances instances, int[] clusters) {
		try {
			Add cluster = new Add();
			cluster.setAttributeIndex("first");
			cluster.setAttributeName("cluster");
			cluster.setInputFormat(instances);
			Instances newData = Filter.useFilter(instances, cluster);
			newData.setClassIndex(newData.numAttributes() - 1);				
			
			for (int i = 0; i < instances.size(); i++) {
				Instance curInstance = newData.get(i);
				curInstance.setValue(0, clusters[i]);
			}
			
			return newData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static void doPokemonDimensionalityReducedNN(Instances drInstances, Instances testInstances) {			
		MultilayerPerceptron pokemonNeuralNetworkClassifier = new MultilayerPerceptron();
		pokemonNeuralNetworkClassifier.setLearningRate(0.1);
		pokemonNeuralNetworkClassifier.setMomentum(0.2);
		pokemonNeuralNetworkClassifier.setTrainingTime(NEURAL_NET_TRAINING_TIME);
		pokemonNeuralNetworkClassifier.setHiddenLayers("a");
		
		try {
			pokemonNeuralNetworkClassifier.buildClassifier(drInstances);
			
			Evaluation pokemonEvaluation = new Evaluation(drInstances);
			pokemonEvaluation.evaluateModel(pokemonNeuralNetworkClassifier, testInstances);
			
			System.out.print("Pokemon correctly classified by neural net: ");
			System.out.println(100.0 * pokemonEvaluation.correct() / pokemonEvaluation.numInstances() + "%");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
