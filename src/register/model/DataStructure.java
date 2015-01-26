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

	public Contestant getContestant(String startNumber) {
		return contestantEntries.get(startNumber);
	}

	public Map<String, Contestant> getAllContestantEntries() {
		return contestantEntries;
	}
}
