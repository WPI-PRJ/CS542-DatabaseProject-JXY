import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * The StoreFileData can read/write an byte array from/to file in bytes, to a given position
 * @author XK,JTY,YM
 * @version 0.1
 */

public class StoreFileData {
	
	/**
     * write a byte array to file in bytes	,with a filelock
     * @param path the file path
     * @param bytes the byte to be written
     * @param position  the offset from the beginning of the file, begin write at position  
     * @return 
     */
	public static void writeData(String path,byte[] bytes,int position)
	{
		try {
			
			RandomAccessFile out2=new RandomAccessFile(path,"rw");
			FileChannel channel=out2.getChannel();
			FileLock lock=channel.lock();
			out2.seek(position-1);
			out2.write(bytes);
			lock.release();
			channel.close();//also release the lock
			out2.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
     * write a byte array to file in bytes	
     * @param path the file path
     * @param length the number of byte to be read
     * @param position  the offset from the beginning of the file, begin read at position  
     * @return 
     */
	public static byte[] readData(String path,int length,int position)
	{
		byte[] getValue = new byte[length];
		
		try {
			//add a exclusive read lock
			
	        RandomAccessFile freader2= new RandomAccessFile(path,"rw");
			freader2.seek(position-1);
	        freader2.read(getValue);
	        freader2.close();
	        

	        
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getValue;
        
	}

}
