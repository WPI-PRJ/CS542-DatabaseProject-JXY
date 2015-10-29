import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;


/**
 * The XStore is a value store. It is designed based on the concept of database.
 * The store based on a single file. The metadata and the real data both have been writen to the file.
 * There is a hashtable object in the store. It stored the index information of the real data.
 * The real data stored by bytes in a file. You can find the position of data by the metadata, which stored in the hashtable.
 * @author XK,JTY,YM
 * @version 0.3
 */

public class XStore {
	 /* constant  */
	static Integer M = new Integer(1000 * 1000 * 1);
	
	 /* the HashTable buffer is the buffer of the metadata. The HashTable object will be written to the db file*/
	private static Hashtable<Integer, int[]> buffer;
	
	/* the byte array dataBuffer is the buffer of the real data. It will written to file directly. 
	 * The key of the real data not stored in the databuffer. The key will be stored in the metadata. not with the real data */
	private  byte[] dataBuffer=new byte[5*M];
	
	/*the relative storage position in file. */
	private int position=0;
	
	/*the index information, will be stored in the hashtable   */
	private int[] indexData=new int[2];
	
	
	
	/**
	   * Store data under the given key
	   * @param key key of the value
	   * @param data the data to be stored
	   * @return 
	   */
	@SuppressWarnings("unchecked")
	synchronized void Put(int key, byte[] data)//stores data under the given key
	{
			try {
				/*read the metadata from the db file*/
				buffer=(Hashtable<Integer, int[]>) StoreFileObject.readObject("cs542.db");
				
				/*load the data from the db file, to the buffer*/
				dataBuffer=StoreFileData.readData("cs542.db", 4*M, M);
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(this.position+data.length<=4*M)
			{
			/*define the index information*/
			indexData[1]=data.length;
			indexData[0]=this.position;
			
			if(buffer==null) {
				buffer = new Hashtable<Integer, int[]>();
			}
			
			/*put the index data into the hashtable*/
			buffer.put(key, indexData);
			/*allocate a area on the buffer for the data */
			for(int i=0;i<data.length;i++)
			{
				dataBuffer[this.position+i]=data[i];
			}
		
			/*write the metadata and the data on the databuffer to the same db file */
			try {
				StoreFileObject.writeObject("cs542.db", buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StoreFileData.writeData("cs542.db", dataBuffer, M);
			
			/*change the position to the next available value*/
			position=this.position+data.length;
			}
			else if(this.position+data.length>4*M)
			{System.out.println("out of memory!");}
		}
	
	
	/**
	   * retrieves the data, from the file
	   * @param key key of the value
	   * @return the value which has the given the key
	   */
	@SuppressWarnings({ "unchecked" })
	synchronized byte[] Get(int key) //retrieves the data
	{
		try{
			/*read the hashtable from the file*/
			buffer=(Hashtable<Integer, int[]>) StoreFileObject.readObject("cs542.db");
	        
	        if(buffer.containsKey(key))//check if the value in the store
	        {
	        	
	        	/*the get method get the value directly from the file, not from the databuffer*/
	        	byte[] getValue = new byte[buffer.get(key)[1]];
	            getValue=StoreFileData.readData("cs542.db", buffer.get(key)[1], M+buffer.get(key)[0]);
	        	
//	            System.out.println("GET METHOD:Here is the value under key "+key);
//	            System.out.println(getValue);
//	    		String byteArrayString = Arrays.toString(getValue);
//	    		System.out.println(byteArrayString); 
	            return getValue;
	        }
	            else{ 
	            	System.out.println("No value under this key.");
	            	return null;}
	            
			} catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace(); 
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	        }
		return null;
	}
	
	
	/**
	   * remove the data, from the file
	   * @param key key of the value
	   * @return 
	   */
	@SuppressWarnings({ "unchecked" })
	synchronized void Remove(int key)
	{
		try{
			/*read the metadata and load the real data from the file */
				buffer=(Hashtable<Integer, int[]>) StoreFileObject.readObject("cs542.db");
				dataBuffer=StoreFileData.readData("cs542.db", 5*M, M);

				/*remove the data from the databuffer, clear the fragment,then write to file*/
	            if(buffer.containsKey(key))
	            {
	            	int len=buffer.get(key)[1];
	            	int pos=buffer.get(key)[0];
	            	
	            	for(int i=pos;i<this.position;i++)
	            	{
	            		dataBuffer[i]=dataBuffer[i+len];
	            	}
	            	this.position=this.position-len;
	            	Enumeration<Integer> keys=buffer.keys();
					while (keys.hasMoreElements()) 
					{
					    int dkey = (int)keys.nextElement();
					    int[] value = buffer.get(dkey);
					    if(value[0]>pos)
					    {value[0]=value[0]-len;}
					}
	            	buffer.remove(key);
	            }
	            StoreFileObject.writeObject("cs542.db", buffer);
	            StoreFileData.writeData("cs542.db", dataBuffer, M);
	            
			} catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace(); 
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	        }
		}
	
	
	/**
	   * display the index information of the data in the file
	   * @param 
	   * @return 
	   */
	@SuppressWarnings("unchecked")
	public void showElements()
    {
    	try{
    		buffer=(Hashtable<Integer, int[]>) StoreFileObject.readObject("cs542.db");
			Enumeration<int[]> ele=buffer.elements();
			
			/*iterate the hashtable, print the index information*/
			while (ele.hasMoreElements()) 
			{
				String byteArrayString = Arrays.toString(ele.nextElement());
				System.out.println("[position,size]:"+byteArrayString);
			} 
			
			} catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {    
	            e.printStackTrace(); 
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	        }
    	
    }
	
	
	/**
	   * display the keys of the data in the file
	   * @param 
	   * @return 
	   */
	@SuppressWarnings("unchecked")
	public void showKeys()
    {
    	try{
    		buffer=(Hashtable<Integer, int[]>) StoreFileObject.readObject("cs542.db");
			Enumeration<Integer> keys=buffer.keys();
			
			// display search result
			while (keys.hasMoreElements()) 
			{
				System.out.println("The key:"+keys.nextElement().toString());
			} 
			
			} catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {    
	            e.printStackTrace(); 
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	        }
    	
    }
    
    /**
     * show the table size
     * @param 
     * @return 
     */
    @SuppressWarnings("unchecked")
	public int sizeOfTable(){
    	try{
			//read the hashtable from the file
			buffer=(Hashtable<Integer, int[]>) StoreFileObject.readObject("cs542.db");			
			} catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {    
	            e.printStackTrace(); 
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	        }
    	return buffer.size();
    	
    }
    
    /**
     * show get and put at the same time 
     * @param key the key of the data to be put
     * @param data the value of the data
     * @return 
     */
    public void getAndput(int key, byte[] data) {
    	  
    	Thread t1 = new Thread(new Runnable()
    	{
    				public void run()
    				{
    				Put(key,data);
    				}  
    	}, "t1");

    	Thread t2 = new Thread(new Runnable()
    	{
    				public void run()
    				{
    					Get(key);
    					byte[] getValue=Get(key);
    			        System.out.println("GET METHOD:Here is the value under key "+key);
    					String byteArrayString = Arrays.toString(getValue);
    					System.out.println(byteArrayString);
//	    				try {
//							Thread.sleep(500);//wait for the put
//							
//						} catch (InterruptedException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//	    				byte[] getValue2=Get(key);
//	    		        System.out.println("GET METHOD:Here is the value under key "+key);
//	    				String byteArrayString1 = Arrays.toString(getValue2);
//	    				System.out.println(byteArrayString1);
    				}        
    	}, "t2"
    	);
    	t1.start();
    	t2.start();
 
    }
    
    /**
     * show remove and get at the same time
     * @param key the key of the data to be removed
     * @return 
     */
    public void removeAndget(int key)
    {
    	Thread t1 = new Thread(new Runnable(){
    				public void run()
    				{
    				Remove(key);
    				}  }, "t1");

    	Thread t2 = new Thread(
    			new Runnable(){
    				public void run()
    				{
	    				try {
							Thread.sleep(500);//wait for the remove
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    				Get(key); 
    				}
    			}, "t2"
    				
    	);
    	t1.start();
    	t2.start();
    }
    
    /**
     * get after reboot, simulated by clear the metadata
     * @param key the key of the data to be get
     * @return 
     */
    public void rebootAndget(int key){
    	/*clear the buffer, the same situation as reboot, simulate the reboot*/
    	buffer.clear();
    	System.out.println("The store has been cleared.");
    	System.out.println("Get key after the store have been cleared:");
    	byte[] getValue=Get(key);
        System.out.println("GET METHOD:Here is the value under key "+key);
		String byteArrayString = Arrays.toString(getValue);
		System.out.println(byteArrayString);
    }
    
//    /**
//     * to check if the data will store to a position out of the given 4M after write the given data
//     * @return 
//     */
//    public boolean isOverFlow()
//    {
//    	if(position<=4*M)
//    		return false;
//    	else
//    		return true;
//    }
    

}