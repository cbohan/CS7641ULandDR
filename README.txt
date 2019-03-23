Code can be found at: https://github.com/cbohan/CS7641ULandDR

This project requires weka.jar and StudentFilters.jar to run. They are included in the git repo.

NOTE: When performing PCA, the following warnings occur. This didn't cause any issues for me and
can be ignored.
Mar 22, 2019 7:41:04 PM com.github.fommil.netlib.LAPACK <clinit>
WARNING: Failed to load implementation from: com.github.fommil.netlib.NativeSystemLAPACK
Mar 22, 2019 7:41:04 PM com.github.fommil.netlib.LAPACK <clinit>
WARNING: Failed to load implementation from: com.github.fommil.netlib.NativeRefLAPACK

NOTE: EM Clustering occasionally causes an unhandled exception. If that happens, just rerun. It is
fairly rare so it is unlikely to happen more than once in a row.

Each of the sections of the project are relegated to a method with the name doExperiment# where
# is the number of the step. To run the program uncomment the step you want to run in the main
method of the Main class. Many doExperiment methods output a lot of text into the console so, if 
that is the case, I would recommend that you comment out parts of the method and run it in parts.

For doExperiment5, there are two try catch blocks. One for PCA and one for ICA. Within each of 
these blocks are two lines:
//int[] pokemonClusters = KMeans.doKMeans(icaPokemonSansClassIndex, 34, 25, pokemonNames);
int[] pokemonClusters = ExpectationMaximization.doEM(icaPokemonSansClassIndex, 34, 25, pokemonNames);

Uncomment the first line and comment out the second line to perform K-means clustering and put those
clusters into the data before neural network learning. Leave as is to to EM clustering.