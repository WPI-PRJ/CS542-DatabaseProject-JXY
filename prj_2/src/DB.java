import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//This is the class used to load the data file and then store the database in the memory and test if the functions can work well 
public class DB {
	//This array contains the name of the attributes
	public String[] attributes = {"Title", "Year", "Format", "Genre", "Director", "Writer", "Country", "Studio", "Price", "Catalog No"};
	//This is the actual data of the database, we assume that it will not exceed 1000 rows
	public row[] data = new row[1000];
	//This is the number of rows in this database
	public int size = 0;
	//initialization of the database
	DB(String filename) throws IOException {
		//Read the file and get the data in the file
		FileReader in = new FileReader(filename);
		String s1 = null;
		BufferedReader br = new BufferedReader(in);
		int i = 0;
		  while((s1 = br.readLine()) != null) {
			  //These lines of code handle the ", " in the file, if we do not do it, the title like "No Children, No Pets" will be splited
			  //Firstly replace ", " with "@", assuming this character will not show up in our database
			  s1 = s1.replace(", ", "@");
			  //split the string by ","
			  String[] sourceStrArray = s1.split(",");
			  for(int j = 0; j < sourceStrArray.length; ++j) {
				  //recover the ", "
				  sourceStrArray[j] = sourceStrArray[j].replace("@", ", ");
			  }
			  //make a new row and then add it into the database
			  data[i] = new row(sourceStrArray);
			  ++i;
		  }
		  //increase the size of the database
		 size = i;
		 br.close();
		 in.close();
	}
	
	//display all the attributes of the database
	public void showAll() {
		System.out.println("Title\t Year\t Format\t Genre\t Director\t Writer\t Country\t Studio\t Price\t Catalog No");
		int i = 0;
		while(data[i] != null) {
			System.out.println(data[i].Title + "\t" + data[i].Year + "\t" + data[i].Format + "\t" + data[i].Genre + "\t" + data[i].Director + "\t" + data[i].Writer + "\t" + data[i].Country + "\t" + data[i].Studio + "\t" + data[i].Price + "\t" + data[i].Catalog_No);
			++i;
		}
	}
	
	//display some selected rows of the database
	public void show(int key) {
		int i = key;
		System.out.println(data[i].Title + "\t" + data[i].Year + "\t" + data[i].Format + "\t" + data[i].Genre + "\t" + data[i].Director + "\t" + data[i].Writer + "\t" + data[i].Country + "\t" + data[i].Studio + "\t" + data[i].Price + "\t" + data[i].Catalog_No);
	}
	
	public class row {
		//attributes
		String Title;
		String Year;
		String Format; 
		String Genre;
		String Director;
		String Writer;
		String Country;
		String Studio;
		String Price;
		String Catalog_No;
		public row(String[] line) {
			//Initialization of the row
			Title = line[0];
			Year = line[1];
			Format = line[2];
			Genre = line[3];
			Director = line[4];
			Writer = line[5];
			Country = line[6];
			Studio = line[7];
			Price = line[8];
			if(line.length >9)
				Catalog_No = line[9];
			else
				Catalog_No = "";
		}
		public String get(int index) {
			//Return the attribute we need
			switch (index) {
			case 0:
				return Title;
			case 1:
				return Year;
			case 2:
				return Format;
			case 3:
				return Genre;
			case 4:
				return Director;
			case 5:
				return Writer;
			case 6:
				return Country;
			case 7:
				return Studio;
			case 8:
				return Price;
			case 9:
				return Catalog_No;
			default:
				return "Wrong";
			}
		}
	}
}
