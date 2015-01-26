package register.model;

import java.util.HashMap;
import java.util.Map;

public class DataStructure {
	private HashMap<String, Time> timeEntries;
	private HashMap<String, Contestant> contestantEntries;

	public DataStructure() {
		timeEntries = new HashMap<String, Time>();
		contestantEntries = new HashMap<String, Contestant>();
	}

	public void addTimeEntry(String startNumber, Time time) {
		timeEntries.put(startNumber, time);
	}

	public Map<String, Time> getAllTimeEntries() {
		return timeEntries;
	}

    @Override
	public boolean equals(Object obj) {
		return ((DataStructure)obj).getAllTimeEntries().equals(getAllTimeEntries());
    }

	public void addContestantEntry(String startNumber, Contestant contestant) {
		contestantEntries.put(startNumber, contestant);
	}

	public Map<String, Contestant> getAllContestantEntries() {
		return contestantEntries;
	}
}
