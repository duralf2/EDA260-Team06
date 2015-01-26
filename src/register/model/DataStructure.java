package register.model;

import java.util.HashMap;
import java.util.Map;

public class DataStructure {
	private HashMap<String, Time> timeEntries;

	public DataStructure() {
		timeEntries = new HashMap<String, Time>();
	}

	public void addEntry(String startNumber, Time time) {
		timeEntries.put(startNumber, time);
	}

	public Map<String, Time> getAllEntries() {
		return timeEntries;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((DataStructure)obj).getAllEntries().equals(getAllEntries());
	}
}
