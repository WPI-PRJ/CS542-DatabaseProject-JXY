import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class Index {
	//The buffer to store the index entries, using hash table
	static Hashtable<String, List> indexBuffer = new Hashtable<String, List>();
	
	public static void main(String[] args) throws IOException {
		//The string to store the input of the user
		String input = null;
		while(input != "Exit") {
			//Necessary information to display
			System.out.println("Please Enter the Attributes You Want to Creat");
			System.out.println("0-11 Refers to:");
			System.out.println("0.Title 1.Year 2.Format 3.Genre 4.Director 5.Writer 6.Country 7.Studio 8.Price 9.Catalog No");
			System.out.println("Please use \"-\" to saparate the attributes (For example Title and Year is 0-1)");
			//The input of the user to determine witch attributes to be indexed, separated by "-"
			Scanner in=new Scanner(System.in);
			input = in.nextLine();
			//Initialization of the database
			DB db = new DB("movies");
			//Handle the input string and make it into an array of attributes called atts
			String[] atts = input.split("-");
			System.out.println("The attributes you choose are:");
			//Display the attributes in the console
			for(int i = 0; i < atts.length; ++i) {
				try{
					System.out.print(db.attributes[new Integer(atts[i])] + " ");
				}
				catch(Exception e) {
					System.out.print("Wrong input format!");
					return;
				}
			}
			System.out.println("\n\n");
			//Create the index of the giving attributes
			CreateIndex(db, atts);
			//Get the value that the user want to find
			System.out.println("Please enter the value you want to find");
			input = in.nextLine();
			//Get the data from the database that satisfy the need
			//Test for the remove function, if you need, delete the "//" for the three lines below
			//System.out.println("Remove something? Enter x for remove nothing");
			//String delete_key = in.nextLine();
			//Remove(delete_key);
			Query(db, input);
		}
	}
	
	//Function put for putting one index entry into the hashtable
	public static void Put(String key, String data_value) {
		if(indexBuffer.get(data_value) == null) {
			//If there is no list for this value, create a new one
			List list = new ArrayList();
			list.add(key);
			indexBuffer.put(data_value, list);
		}
		else {
			//If we can find the list for this value, just add the new data into the list
			List list = (List) indexBuffer.get(data_value);
			list.add(key);
		}
	}
	
	public static String Get(String data_value) {
		List list;
		//string used to store the keys for the given datavalue.
		String key = "";
		//checking if here is one existing index entry can satisfy the data_value
		if(indexBuffer.containsKey(data_value)) {
			//Get the list satisfy the value
			list = (List) indexBuffer.get(data_value);
			Iterator it1 = list.iterator();
			//Add all the keys into the string key separated by "-"
	        while(it1.hasNext()){
	        	key = key + "-" + it1.next();
	        }
	        //For the value deleted, the key will be ""
	        if(key.equals(""))
	        	return "Not Found!";
	        //Return the string of the keys 
	        return key;
		}
		else
			//If we cannot find this list, return "Not Found!"
			return "Not Found!";
	}
	
	public static void Remove(String key) {
		//Just search all the index entries to find if here is one entry that has the given key
        Enumeration<String> en1 = indexBuffer.keys();
         while(en1.hasMoreElements()) {
            List list = indexBuffer.get(en1.nextElement());
			Iterator it1 = list.iterator();
	        while(it1.hasNext()){
	        	String tmp = (String) it1.next();
	        	//If so, delete it
	        	if(tmp.equals(key))
	        		it1.remove();
	        }
        }
	}
	
	public static void CreateIndex(DB database, String[] atts) {
		//This function calls the get function several times to put all the index entries into the hashtable
		indexBuffer = new Hashtable<String, List>();
		for(int i = 0; i < database.size; ++i) {
			//convert the integer into string
			String tmp_key = "" + i;
			String data_value = "";
			for (int j =0; j < atts.length; ++j) {
				//For every row of the database, we pick up the attributes we need and then add them together as the datavalue for the index entry
				data_value += database.data[i].get(new Integer(atts[j]));
				if (j != atts.length-1)
					//separate the different attributes by "|"
					data_value += "|";
			}
			//Put the entry in to the hash table
			Put(tmp_key, data_value);
		}
		System.out.println("Index was created successfully!");
	}
	
	public static void Query(DB database, String value) {
		//Get the keys calling the get function
		String result = Get(value);
		//handle the string separated by "-"
		String[] keys = result.split("-");
		//If here is no information in the keys or the only data is "Not Found!", means we cannot find this value in the database
		if(keys.length == 0 || keys[0] == "Not Found!") {
	        System.out.println("Not Found!\n\n\n");
	        return;
		}
		//If we have the data, call the function show in the class row to display this row of the database
		System.out.println("\n\n------------------------------------------------------Result-----------------------------------------------------\n");
		for(int i =1; i < keys.length; ++i) // i start from 1 to miss the first null value
			database.show(Integer.parseInt(keys[i]));
		System.out.println("\n\n\n");
	}
}
