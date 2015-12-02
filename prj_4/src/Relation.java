import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class Relation {
	private String[] attrs = null;
	private ArrayList<Record> data = new ArrayList<Record>();
	
	private String file_path = null;
	
	private String log_path = null;
	
	private String backup_path = null;
	
	public Relation(String file_name) throws IOException {
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
		
		//load the attributes
		attrs = scanner.nextLine().split(",");
		
		FileWriter writer = new FileWriter(file);
		
		String att = "";
		for(String s:attrs) {
			att = att + s + ",";
		}
		writer.write(att+"\n");
		//load the data
		while(scanner.hasNextLine()) {
			Record record = new Record(attrs, scanner.nextLine().split(","));
			data.add(record);
			writer.write(record.toString());
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
		
		File log = new File(log_path);
		FileWriter log_writer = new FileWriter(log_path);
		
		String att = "";
		for(int i = 0; i < attrs.length; ++i) {
			att = att + attrs[i] + ",";
		}
		writer.write(att + "\n");
		
		int index = 1;
		for(Record r:data) {
			String log_line = "";
			log_line = log_line + index + ",";
			log_line = log_line + attrs[4] + ",";
			log_line = log_line + r.getAttr(4) + ",";
			r.update(4);
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
		File log = new File(log_path);
		Scanner scanner = new Scanner(log);
		File backup_file = new File(backup_path);
		Scanner file_scanner = new Scanner(backup_file);
		ArrayList<String> container = new ArrayList<String>();
		
		String att = "";
		for(int i = 0; i < attrs.length; ++i) {
			att = att + attrs[i] + ",";
		}
		container.add(att + "\n");
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.equals("commit"))
				break;
			String[] log_line = line.split(",");
			while(file_scanner.hasNextLine()) {
				String[] datas = file_scanner.nextLine().split(",");
				if(datas[0].equals(log_line[0])) {
					String new_line = datas[0] + "," + datas[1] + "," + datas[2] + "," + datas[3] + "," + log_line[3] + "\n";
					container.add(new_line);
					break;
				}
			}
		}
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
}
