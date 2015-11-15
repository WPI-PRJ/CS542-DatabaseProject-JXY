
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**This class stores the data,representing the relation with operation open(),getNext() and close()
 * 
 * @author xiongkuang,MeiYang,TengyangJia
 * **/
public class Tables {
	//a string array list that stores each record of the table
	private List<String[]> tuple=new ArrayList<String[]>();
	//the table name
	private String tableName;
	//the table path
	private String tablePath;
	
	private FileReader in=null; 
	private BufferedReader br=null; 
	
	
	
	public Tables(String name, String path) throws FileNotFoundException{
		this.tableName=name;
		this.tablePath=path;
	}	
	
	/**read the data from file and load it to the string array list
	 * **/
	public void open(){
		try {
			this.in = new FileReader(tablePath);
			this.br = new BufferedReader(in);
			String line="";
			while ((line = br.readLine()) != null) {
				String[] row = line.split(",");
				tuple.add(row);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**return a record from the table, every call of the method will get the next record of the previous call in the table
	 * @return String[] a record of the table
	 * **/
	public String[] getNext(){
		if(tuple.size()>1)
		{
			String[] r=tuple.get(1);
			tuple.remove(1);
			return r;
		}
		else return null;
	}

	/**this will close and clear all the resource we allocated for this read/open
	 * **/
	public void close(){
		
		try {
			tuple.clear();
			in.close();
			br.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	/**can print a selected attribute of a relation, projection, not used in this project
	 * **/
	public void select(String attribute)
	{
		int attrNo=-1;
		int i,j;
		for(i=0;i<tuple.get(0).length;i++)
		{
			if(tuple.get(0)[i].equals(attribute))
			{
				attrNo=i;
				break;
			}
		}
		if(attrNo!=-1)
		{
			for(j=0;j<tuple.size();j++)
			{
				System.out.println(tuple.get(j)[attrNo]);
			}
		}
		else System.out.println("No matched attribute in the table.");
	}
	
	
	

	public List<String[]> getTuple() {
		return tuple;
	}

	public void setTuple(List<String[]> tuple) {
		this.tuple = tuple;
	}




	public String getTableName() {
		return tableName;
	}




	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
