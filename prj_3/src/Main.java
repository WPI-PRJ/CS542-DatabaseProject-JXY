import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	
	
	public static void main(String[] args) {
		try {
			Tables city=new Tables("city","city.csv");
			Tables country=new Tables("country","country.csv");
			List<String[]> joinResult=new ArrayList<String[]>();
			
			city.open();
			country.open();
			
			Join cityCountry=new Join(city,country);
			
			while(true)
			{
				String[] tmp=cityCountry.getNext();
				joinResult.add(tmp);
				if(tmp==null) break;
			}
			
//			System.out.println(joinResult.size());
			
//			System.out.println(Integer.parseInt(joinResult.get(0)[11]));
			
			for(int i=0;i<joinResult.size()-1;i++)
			{
				if(Integer.parseInt(joinResult.get(i)[4]) >= 0.4* Integer.parseInt(joinResult.get(i)[11]))
					System.out.println(joinResult.get(i)[1]);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
