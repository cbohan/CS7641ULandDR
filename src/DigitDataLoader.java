import java.util.ArrayList;

public class DigitDataLoader {
	static ArrayList<Image> digitImages = new ArrayList<Image>();
	
	public static void init() {
		for (int i = 0; i <= 14; i++)
			digitImages.add(new Image("data//digits//zero" + i + ".png"));
		for (int i = 0; i <= 14; i++)
			digitImages.add(new Image("data//digits//one" + i + ".png"));
		for (int i = 0; i <= 14; i++)
			digitImages.add(new Image("data//digits//two" + i + ".png"));
		for (int i = 0; i <= 14; i++)
			digitImages.add(new Image("data//digits//three" + i + ".png"));
		for (int i = 0; i <= 14; i++)
			digitImages.add(new Image("data//digits//four" + i + ".png"));
	}
}
