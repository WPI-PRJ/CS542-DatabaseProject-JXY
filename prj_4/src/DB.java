import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class DB {
	//The attributes of the database
	private String[] attrs = null;
	//The data of the database
	private ArrayList<Row> data = new ArrayList<Row>();
	//the path of the csv file
	private String file_path = null;
	//the path of the log file
	private String log_path = null;
	//the path of the backup file
	private String backup_path = null;
	
	public DB(String file_name) throws IOException {
		// TODO Auto-generated constructor stub
		
		//Initial of the data and the log file
		this.file_path = file_name + ".csv";
		this.log_path = file_name + ".log";
		this.backup_path = file_name + "A" + ".csv";
		
		File file = new File(file_path);
		File log = new File(log_path);
		
		Scanner scanner = new Scanner(new File("data.csv"));
		
		if(!file.exists()) {
			file.createNewFile();
		}
		
		if(!log.exists()) {
			log.createNewFile();
		}
		
		//Initial of the backup file
		File backup_file = new File(backup_path);
		
		if(!backup_file.exists()) {
			backup_file.createNewFile();
		}
		
		//load the attributes of the database
		attrs = scanner.nextLine().split(",");
		
		FileWriter writer = new FileWriter(file);
		
		String att = "";
		for(String s:attrs) {
			att = att + s + ",";
		}
		writer.write(att+"\n");
		//load the data from the data.csv
		while(scanner.hasNextLine()) {
			Row row = new Row(scanner.nextLine().split(","));
			data.add(row);
			writer.write(row.toString());
		}
		writer.close();
		
		//save the data into the backup file
		scanner = new Scanner(file);
		
		writer = new FileWriter(backup_file);
		
		while(scanner.hasNextLine()) {
			writer.write(scanner.nextLine());
			writer.write("\n");
		}		
		writer.close();
	}
	
	public void update() throws IOException {
		//load the file
		File file = new File(file_path);
		FileWriter writer = new FileWriter(file);
		//load the log file
		File log = new File(log_path);
		FileWriter log_writer = new FileWriter(log_path);
		//Write the attributes into the file
		String att = "";
		for(int i = 0; i < attrs.length; ++i) {
			att = att + attrs[i] + ",";
		}
		writer.write(att + "\n");
		//update all the rows and store the log into log file
		int index = 1;
		for(Row r:data) {
			//The format of the log file is index,attribute,old value,new value
			String log_line = "";
			log_line = log_line + index + ",";
			log_line = log_line + attrs[4] + ",";
			log_line = log_line + r.getAttr(4) + ",";
			r.update();
			log_line = log_line + r.getAttr(4) + "\n";
			log_writer.write(log_line);
			writer.write(r.toString());
			++index;
		}
		log_writer.write("commit");
		writer.close();
		log_writer.close();
	}
	
	public void redo() throws IOException {
		//load the backup file and the log file
		File log = new File(log_path);
		Scanner scanner = new Scanner(log);
		File backup_file = new File(backup_path);
		Scanner file_scanner = new Scanner(backup_file);
		ArrayList<String> container = new ArrayList<String>();
		//write the attributes into the file
		String att = "";
		for(int i = 0; i < attrs.length; ++i) {
			att = att + attrs[i] + ",";
		}
		container.add(att + "\n");
		//redo the change to the backup file
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.equals("commit"))
				break;
			String[] log_line = line.split(",");
			//find the row that matches the log
			while(file_scanner.hasNextLine()) {
				String[] datas = file_scanner.nextLine().split(",");
				if(datas[0].equals(log_line[0])) {
					//replace the value with the new value
					//get the index of the attribute that changed in log
					int index = 0;
					for(int i = 0; i < attrs.length;++i) {
						if(attrs[i].equals(log_line[1]))
							index = i;
					}
					String new_line = "";
					for(int i = 0;i < datas.length;++i) {
						if(i != index)
							new_line  += datas[i];
						else
							new_line += log_line[3];
						if(i != datas.length-1)
							new_line += ",";
						else
							new_line += "\n";
					}
					//String new_line = datas[0] + "," + datas[1] + "," + datas[2] + "," + datas[3] + "," + log_line[3] + "\n";
					container.add(new_line);
					break;
				}
			}
		}
		//write the row back
		FileWriter writer = new FileWriter(backup_file);
		for(String s:container) {
			writer.write(s);
		}
		writer.close();
	}
	
	public void undo() throws IOException {
		//load the backup file and the log file
		File log = new File(log_path);
		Scanner scanner = new Scanner(log);
		File backup_file = new File(backup_path);
		Scanner file_scanner = new Scanner(backup_file);
		ArrayList<String> container = new ArrayList<String>();
		//write the attributes into the file
		String att = "";
		for(int i = 0; i < attrs.length; ++i) {
			att = att + attrs[i] + ",";
		}
		container.add(att + "\n");
		//redo the change to the backup file
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.equals("commit"))
				break;
			String[] log_line = line.split(",");
			//find the row that matches the log
			while(file_scanner.hasNextLine()) {
				String[] datas = file_scanner.nextLine().split(",");
				if(datas[0].equals(log_line[0])) {
					//replace the value with the new value
					//get the index of the attribute that changed in log
					int index = 0;
					for(int i = 0; i < attrs.length;++i) {
						if(attrs[i].equals(log_line[1]))
							index = i;
					}
					String new_line = "";
					//build the new line
					for(int i = 0;i < datas.length;++i) {
						if(i != index)
							new_line  += datas[i];
						else
							new_line += log_line[2];
						if(i != datas.length-1)
							new_line += ",";
						else
							new_line += "\n";
					}
					//String new_line = datas[0] + "," + datas[1] + "," + datas[2] + "," + datas[3] + "," + log_line[3] + "\n";
					container.add(new_line);
					break;
				}
			}
		}
		//write the row back
		FileWriter writer = new FileWriter(backup_file);
		for(String s:container) {
			writer.write(s);
		}
		writer.close();
	}
	
	public void show() throws FileNotFoundException {
		File file = new File(file_path);
		File backup = new File(backup_path);
		File log = new File(log_path);
		Scanner file_scanner = new Scanner(file);
		Scanner backup_scanner = new Scanner(backup);
		Scanner log_scanner = new Scanner(log);
		
		System.out.println("The data in the data file:");
		System.out.println("-----------------------------------------------------");
		for(int i = 0;i<11;++i) {
			System.out.println(file_scanner.nextLine());
		}
		System.out.println("");
		System.out.println("The data in the backup file:");
		System.out.println("-----------------------------------------------------");
		for(int i = 0;i<11;++i) {
			System.out.println(backup_scanner.nextLine());
		}
		System.out.println("");
		System.out.println("The data in the log file:");
		System.out.println("-----------------------------------------------------");
		for(int i = 0;i<11;++i) {
			System.out.println(log_scanner.nextLine());
		}
		System.out.println("");
	}
	
	public String getFilepath() {
		return file_path;
	}
	
	public void setLogPath(String log_path) {
		this.log_path = log_path;
	}
	
	public String getLogPath() {
		return log_path;
	}
	
	//The inner class to store the row of the database
	private class Row {
		//The data of the database
		private String[] values = null;
		
		public Row(String[] values) {
			// TODO Auto-generated constructor stub
			//Create one new row from given values
			this.values = new String[attrs.length];
			for(int i = 0; i < attrs.length; ++i) {
				this.values[i] = values[i];
			}
		}
		
		@Override
		//convert this row to string
		public String toString() {
			// TODO Auto-generated method stub
			String string = "";
			for(int i = 0; i < values.length; ++i) {
				string += values[i] + ",";
			}
			string += "\n";
			return string;
		}
		
		//Increase the population of this record
		public void update() {
			int orig_value = Integer.parseInt(values[4]);
			int new_value = (int)(orig_value * 1.02);
			values[4] = new_value + "";
		}
		
		//Get the value of the attribute given by index
		public String getAttr(int index) {
			return values[index];
		}
	}
}
