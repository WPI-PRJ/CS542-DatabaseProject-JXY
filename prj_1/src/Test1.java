/**This file is used for the Concurrency Validation
 *There is a file lock in the write method of the metadata and the real data
 *The lock is exclusive. 
 *In the test case. There is a array that all of the value is all 1. Another array that all of the value is 2.
 * */


public class Test1 {
	public static void main(String[] args) {
		
		XStore test=new XStore();
		
		
		byte[] bytes = new byte[10];
		for(int i=0;i<10;i++)
		{
			bytes[i] = 1;
		}
		
		byte[] bytes2 = new byte[10];
		for(int i=0;i<10;i++)
		{
			bytes2[i] = 2;
		}
		
		test.Put(1, bytes);
		
		System.out.println("Here to show put and get at the same time:");
		test.getAndput(1, bytes2);
		
		try {
			Thread.sleep(500);//wait for the remove
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Here to show Remove and get at the same time:");
		
		
		test.removeAndget(1);
		
	
	}

}
