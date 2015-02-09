package register.model;

import java.util.Map;
import java.util.TreeMap;

public class Database {
	private Map<String, StandardContestant> contestantEntries;
	private String[] contestantColumnNames;

	public Database() {
		contestantEntries = new TreeMap<String, StandardContestant>();
	}

	public void addContestantEntry(String startNumber, StandardContestant contestant) {
		contestantEntries.put(startNumber, contestant);
	}

	public void setContestantColumnNames(String[] columns) {
		contestantColumnNames = columns;
	}

	public StandardContestant getContestant(String startNumber) {
		return contestantEntries.get(startNumber);
	}

	public Map<String, StandardContestant> getAllContestantEntries() {
		return contestantEntries;
	}
	
	public String[] getContestantColumnNames()
	{
		return contestantColumnNames;
	}
	
	public StandardContestant removeContestant(String startNumber) {
		return contestantEntries.remove(startNumber);
	}
	

	public boolean equals(Object obj) {
		if (obj instanceof Database)
		{
			Database ds = (Database) obj;
			return ds.getAllContestantEntries().equals(getAllContestantEntries());
		}
		return false;
	}



	public void clearContestantEntries() {
		contestantEntries.clear();
		contestantColumnNames = null;
	}
}
