package register.model;

import java.util.HashMap;
import java.util.Map;

public class DataStructure {
	private HashMap<String, Contestant> contestantEntries;
	private String[] contestantColumnNames;

	public DataStructure() {
		contestantEntries = new HashMap<String, Contestant>();
	}

	public void addContestantEntry(String startNumber, Contestant contestant) {
		contestantEntries.put(startNumber, contestant);
	}

	public void setContestantColumnNames(String[] columns) {
		contestantColumnNames = columns;
	}

	public Contestant getContestant(String startNumber) {
		return contestantEntries.get(startNumber);
	}

	public Map<String, Contestant> getAllContestantEntries() {
		return contestantEntries;
	}
	
	public String[] getContestantColumnNames()
	{
		return contestantColumnNames;
	}

	public boolean equals(Object obj) {
		if (obj instanceof DataStructure)
		{
			DataStructure ds = (DataStructure) obj;
			return ds.getAllContestantEntries().equals(getAllContestantEntries());
		}
		return false;
	}
}
