import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class DigitARFFGenerator {
	static ArrayList<Image> zeros = new ArrayList<Image>();
	static ArrayList<Image> ones = new ArrayList<Image>();
	static ArrayList<Image> twos = new ArrayList<Image>();
	static ArrayList<Image> threes = new ArrayList<Image>();
	static ArrayList<Image> fours = new ArrayList<Image>();
	
	
	
	public static void generateFile() {
		for (int i = 0; i <= 14; i++)
			zeros.add(new Image("data//digits//zero" + i + ".png"));
		for (int i = 0; i <= 14; i++)
			ones.add(new Image("data//digits//one" + i + ".png"));
		for (int i = 0; i <= 14; i++)
			twos.add(new Image("data//digits//two" + i + ".png"));
		for (int i = 0; i <= 14; i++)
			threes.add(new Image("data//digits//three" + i + ".png"));
		for (int i = 0; i <= 14; i++)
			fours.add(new Image("data//digits//four" + i + ".png"));
		
		File training = new File(Main.DIGIT_TRAINING_DATASET);
		File test = new File(Main.DIGIT_TEST_DATASET);
		
		try {
			for (File file : new File[] { training, test } )
			{	
				FileOutputStream outputStream = new FileOutputStream(file);
				BufferedWriter bufferedWriter 
					= new BufferedWriter(new OutputStreamWriter(outputStream));
				
				WriteLine(bufferedWriter, "@RELATION digits");
				WriteLine(bufferedWriter, "");
				for (int i = 0; i < zeros.get(0).getImageData().length; i++)
					WriteLine(bufferedWriter, "@ATTRIBUTE	pixel" + i + "	REAL");
				WriteLine(bufferedWriter, "@ATTRIBUTE	digit	{zero,one,two,three,four}");
				
				WriteLine(bufferedWriter, "@DATA");
				for (Image zero : zeros) {
					StringBuilder builder = new StringBuilder();
					for (double value : zero.getImageData())
						builder.append(value + ",");
					builder.append("zero");
					WriteLine(bufferedWriter, builder.toString());
				}
				for (Image one : ones) {
					StringBuilder builder = new StringBuilder();
					for (double value : one.getImageData())
						builder.append(value + ",");
					builder.append("one");
					WriteLine(bufferedWriter, builder.toString());
				}
				for (Image two : twos) {
					StringBuilder builder = new StringBuilder();
					for (double value : two.getImageData())
						builder.append(value + ",");
					builder.append("two");
					WriteLine(bufferedWriter, builder.toString());
				}
				for (Image three : threes) {
					StringBuilder builder = new StringBuilder();
					for (double value : three.getImageData())
						builder.append(value + ",");
					builder.append("three");
					WriteLine(bufferedWriter, builder.toString());
				}
				for (Image four : fours) {
					StringBuilder builder = new StringBuilder();
					for (double value : four.getImageData())
						builder.append(value + ",");
					builder.append("four");
					WriteLine(bufferedWriter, builder.toString());
				}
				
					
				
				bufferedWriter.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void WriteLine(BufferedWriter bufferedWriter, String line) throws IOException {
		bufferedWriter.write(line);
		bufferedWriter.newLine();
	}
}
