import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Join {
	
	private Tables right;
	private Tables left;
	private Tables result=null;
	private String[] l=null;
	private String[] r=null;
	
	
	public Join(Tables L,Tables R)
	{
		right=R;
		r=R.getNext();
		left=L;
		l=L.getNext();
	}
	
	
	
	public String[] getNext(){
		String[] joinResult=null;
		if(l==null) return null;
		while(true)
		{
			if(r==null)
			{
				l=left.getNext();
				if(l==null) break;
				right.close();
				right.open();
				r=right.getNext();
			}
			
			if(l[2].equals(r[0]))
			{
				joinResult=stringUnion(l,r);
				r=right.getNext();
				break;
			}
			else r=right.getNext();
		}
		return joinResult;
	}

	
	
	public String[] stringUnion(String[] s1, String[] s2)
	{
		List<String> tmp = new ArrayList<String>();
		for(String s : s1)
		{
			tmp.add(s);
		}
		for(String s : s2)
		{
			tmp.add(s);
		}
		return tmp.toArray(new String[0]);
		
	}

	public Tables getResult() {
		return result;
	}




}