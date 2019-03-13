import java.io.File;

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
	
	public static void main(String[] args) {
		PokemonARFFGenerator.generateFile(25, false);
		DigitARFFGenerator.generateFile();
		//KMeans.doKMeans(DIGIT_TRAINING_DATASET, 5, 15, digitNames);
		//ExpectationMaximization.doEM(DIGIT_TRAINING_DATASET, 5, 15, digitNames);
		
		//KMeans.doKMeans(POKEMON_TRAINING_DATASET, 34, 25, pokemonNames);
		//ExpectationMaximization.doEM(POKEMON_TRAINING_DATASET, 34, 25, pokemonNames);
		//PCA.doPCA(DIGIT_TEST_DATASET);
		//ICA.doICA(DIGIT_TEST_DATASET, 10);
		RCA.doRCA(DIGIT_TEST_DATASET, 10);
	}

}
