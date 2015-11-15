import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 *@author xiongkuang,TengyangJia,Meiyang
 **/
public class Main {
	
	
	
	public static void main(String[] args) {
		try {
			//open the two relations that need to search
			Tables city=new Tables("city","city.csv");
			Tables country=new Tables("country","country.csv");
			List<String[]> joinResult=new ArrayList<String[]>();
			
			city.open();
			country.open();
			
			Join cityCountry=new Join(city,country);
			System.out.println("Here is all the cities whose population is more than 40% "
					+ "of the population of their entire country:");
			
			while(true)
			{
				String[] tmp=cityCountry.getNext();
				joinResult.add(tmp);
				if(tmp==null) 
				{
					System.out.println("The End");
					break;
				}
				if(Integer.parseInt(tmp[4]) > 0.4* Integer.parseInt(tmp[11]))
					System.out.println(tmp[1]);
				
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
