
public class Record {
	private String[] attrs = null;
	private String[] values = null;
	
	public Record(String[] attrs, String[] values) {
		// TODO Auto-generated constructor stub
		this.attrs = new String[attrs.length];
		this.values = new String[attrs.length];
		for(int i = 0; i < attrs.length; ++i) {
			this.attrs[i] = attrs[i];
			this.values[i] = values[i];
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String string = "";
		for(int i = 0; i < values.length; ++i) {
			string += values[i] + ",";
		}
		string += "\n";
		return string;
	}
	
	public void update(int index) {
		int orig_value = Integer.parseInt(values[index]);
		int new_value = (int)(orig_value * 1.02);
		values[index] = new_value + "";
	}
	
	public String getAttr(int index) {
		return values[index];
	}
}
