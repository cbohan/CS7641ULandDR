import java.util.ArrayList;

public class DogDataLoader {
	static ArrayList<Image> dogImages = new ArrayList<Image>();
	
	public static void init() {
		for (int i = 0; i <= 14; i++)
			dogImages.add(new Image("data//dogs//Chow" + i + ".jpg"));
		for (int i = 0; i <= 14; i++)
			dogImages.add(new Image("data//dogs//GreatDane" + i + ".jpg"));
		for (int i = 0; i <= 14; i++)
			dogImages.add(new Image("data//dogs//IrishWolfhound" + i + ".jpg"));
	}
}
