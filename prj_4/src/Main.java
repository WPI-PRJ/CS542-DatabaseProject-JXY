import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		Relation relation = new Relation("city");
		System.out.println("Due to the size of the database, I will only display the first 10 items, if you want to see more, open the csv files");
		relation.update();
		System.out.println("After the update:\n");
		relation.show();
		relation.redo();
		System.out.println("After the redo:\n");
		relation.show();
	}
}
