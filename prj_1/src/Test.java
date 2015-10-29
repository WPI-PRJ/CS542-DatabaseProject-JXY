

/**This test file is used for the Fragmentation Verification.
 * There are 8 arrays. 7 of them are 1MB, and one of them is 1/2 MB. They are the data that will be Put() into the store.
 * According to the Project Description, the test process can be divided into 4 steps.
 * 
 * In the result. The position represent for the relative offset of the value in the file. The size is the bytes the vale occupied in the file.
 * 
 * 
 * */

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XStore test=new XStore();
		Integer M = new Integer(1000 * 1000 * 1);
		
		byte[] bytes1 = new byte[1*M];
		for(int i=0;i<(int)(1*M);i++)
		{
			bytes1[i] = 1;
		}
		
		byte[] bytes2 = new byte[1*M];
		for(int i=0;i<(int)(1*M);i++)
		{
			bytes2[i] = 2;
		}
		
		byte[] bytes3 = new byte[1*M];
		for(int i=0;i<M;i++)
		{
			bytes3[i] = 3;
		}
		
		byte[] bytes4 = new byte[M];
		for(int i=0;i<M;i++)
		{
			bytes4[i] =4;
		}
		
		byte[] bytes5=new byte[(int) (0.5*M)];
		for(int i=0;i<(0.5*M);i++)
		{
			bytes5[i] = 5;
		}
		
		byte[] bytes6=new byte[M];
		for(int i=0;i<M;i++)
		{
			bytes6[i] = 6;
		}
		
		
		byte[] bytes7 = new byte[M];
		for(int i=0;i<M;i++)
		{
			bytes7[i] = 7;
		}
		
		byte[] bytes8 = new byte[M];
		for(int i=0;i<M;i++)
		{
			bytes8[i] = 8;
		}
		//first step
		test.Put(1, bytes1);
		test.Put(2, bytes2);
		test.Put(3, bytes3);
		test.Put(4, bytes4);
		
		
		//second step
		test.Remove(2);
		test.Put(5, bytes5);
		test.Put(6, bytes6);
		
		//third step
		test.Remove(3);
		test.Put(7, bytes7);
		
		
		//fourth step
		test.Remove(5);
		test.Put(8, bytes8);
		
		//lay out display
		test.showKeys();
		test.showElements();
		
		
		
		
		
		
		
		/**********************************Layout Display**************************************/
//		System.out.println("This is the keys of data in the cs542.db:");
//		test.showKeys();
//		System.out.println("This is the position and size of data in the cs542.db:");
//		test.showElements();
		
		/*********************************Concurrency Validation*********************************/
		
//		test.Put(7, bytes7);
		
		
//		test.Put(8,bytes8);
//		System.out.println("Here to show put and get at the same time:");
//		test.getAndput(7, bytes7);
//		
//		
//		System.out.println("Here to show remove and get at the same time:");
		
		

		
		/*********************************Fragmentation Validation*********************************/
		
		
		
		

	}

}
