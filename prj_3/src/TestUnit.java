import java.io.FileNotFoundException;
import java.util.Arrays;

public class TestUnit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Tables testCity=new Tables("testCity","testCity.csv");
			Tables testCountry=new Tables("testCountry","testCountry.csv");
//			Join test =new Join(testCity,testCountry);
			testCity.open();
			testCountry.open();
			
//			testCity.select(" Name");
//			System.out.println(testCity.getTuple().get(0)[1]);
//			System.out.println(Arrays.toString(testCity.getTuple().get(1)));
//			
//			
//			testCity.close();
//			testCity.open();
//			
			
			Join test=new Join(testCity,testCountry);
			for(int i=0;i<20;i++)
				System.out.println(Arrays.toString(test.getNext()));
				
			
//			
//			String[] s1={"a","b","3"};
//			String[] s2={"c","d","4"};
//			System.out.println(Arrays.toString(test.stringUnion(s1, s2)));
			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
