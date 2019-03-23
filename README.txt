Code can be found at: https://github.com/cbohan/CS7641ULandDR

This project requires weka.jar and StudentFilters.jar to run. They are included in the git repo.

NOTE: When performing PCA, the following warnings occur. This didn't cause any issues for me and
can be ignored.
Mar 22, 2019 7:41:04 PM com.github.fommil.netlib.LAPACK <clinit>
WARNING: Failed to load implementation from: com.github.fommil.netlib.NativeSystemLAPACK
Mar 22, 2019 7:41:04 PM com.github.fommil.netlib.LAPACK <clinit>
WARNING: Failed to load implementation from: com.github.fommil.netlib.NativeRefLAPACK

Each of the sections of the project are relegated to a method with the name doExperiment# where
# is the number of the step. To run the program uncomment the step you want to run in the main
method of the Main class. Many doExperiment methods output a lot of text into the console so, if 
that is the case, I would recommend that you comment out parts of the method and run it in parts.

 