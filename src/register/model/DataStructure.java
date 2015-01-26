package register.model;

import java.util.HashMap;
import java.util.Map;

public class DataStructure {
	private HashMap<String, Time> entries;
	
	public DataStructure() {
		entries = new HashMap<String, Time>();
	}
	
	public	void addEntry(String startNumber, Time time)
	{
		entries.put(startNumber, time);
	}
	
	public Map<String, Time> getAllEntries(){
		return entries;
	}
}
