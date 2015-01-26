package register.model;

import java.util.HashMap;
import java.util.Map;

public class DataStructure {
	private HashMap<String, Contestant> contestantEntries;

	public DataStructure() {
		contestantEntries = new HashMap<String, Contestant>();
	}

	public void addContestantEntry(String startNumber, Contestant contestant) {
		contestantEntries.put(startNumber, contestant);
	}

	public Map<String, Contestant> getAllContestantEntries() {
		return contestantEntries;
	}
	
	public boolean equals(Object obj) {
		DataStructure ds = (DataStructure)obj;
		return ds.getAllContestantEntries().equals(getAllContestantEntries());
	}
}
