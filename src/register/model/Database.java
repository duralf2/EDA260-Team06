package register.model;

import java.util.Map;
import java.util.TreeMap;

public class Database {
	private Map<String, AbstractContestant> contestantEntries;

	public Database() {
		contestantEntries = new TreeMap<String, AbstractContestant>();
	}

	public void addContestantEntry(String startNumber,
			AbstractContestant contestant) {
		contestantEntries.put(startNumber, contestant);
	}

	public AbstractContestant getContestant(String startNumber) {
		return contestantEntries.get(startNumber);
	}

	public Map<String, AbstractContestant> getAllContestantEntries() {
		return contestantEntries;
	}

	public AbstractContestant removeContestant(String startNumber) {
		return contestantEntries.remove(startNumber);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Database) {
			Database ds = (Database) obj;
			return ds.getAllContestantEntries().equals(
					getAllContestantEntries());
		}
		return false;
	}


	public void clearContestantEntries() {
		contestantEntries.clear();
	}
}
