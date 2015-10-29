/**This file is used for Durability Validation
 * */


public class Test2 {
	
	public static void main(String[] args) {
		
		XStore test=new XStore();
	
	/**********************************Layout Display**************************************/
	System.out.println("This is the keys of data in the cs542.db:");
	test.showKeys();
	System.out.println("This is the position and size of data in the cs542.db:");
	test.showElements();
	
	/*********************************Reboot Validation*********************************/
	byte[] bytes = new byte[10];
	for(int i=0;i<10;i++)
	{
		bytes[i] = 1;
	}
	
	test.Put(1, bytes);
	System.out.println("Here to show reboot and get, here reboot means clear the megadata in the memory:");
	test.rebootAndget(1);
	
	
	
	}
	
	

	

}
