
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * The StoreFileObject will read/write an object from/to file
 * @author XK,JTY,YM
 * @version 0.1
 */
public class StoreFileObject 
{
	/**
     * write a object to file	
     * @param path the file path
     * @param obj the object
     * @return 
     */
	public static void writeObject(String path,Object obj) throws IOException{
		
		File f=new File(path); 
		FileOutputStream out=new FileOutputStream(f); 
		FileChannel channel=out.getChannel();
		FileLock lock=channel.lock();
		ObjectOutputStream objWrite=new ObjectOutputStream(out);
		objWrite.writeObject(obj);
		objWrite.flush();
		lock.release();
		channel.close();
		objWrite.close();
		
		
		}

	/**
     * read a object from file	
     * @param path the file path
     * @return the content of the file, as a object
     */
	public static Object readObject(String path) throws IOException, ClassNotFoundException{
		//add a exclusive read lock
		
		FileInputStream in=new FileInputStream(path); 
		ObjectInputStream objRead=new ObjectInputStream(in);
		Object obj=objRead.readObject();		
		objRead.close();
		
		
		return obj;
		}
}