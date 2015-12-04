import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		DB database = new DB("cs542");
		System.out.println("Due to the size of the database, I will only display the first 10 items, if you want to see more, open the csv files");
		database.update();
		System.out.println("After the update:\n");
		database.show();
		database.redo();
		System.out.println("After the redo:\n");
		database.show();
		database.undo();
		System.out.println("After the undo:\n");
		database.show();
	}
}
