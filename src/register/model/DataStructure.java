package register.model;

import java.util.Map;
import java.util.TreeMap;

public class DataStructure {
	private Map<String, Contestant> contestantEntries;
	private String[] contestantColumnNames;

	public DataStructure() {
		contestantEntries = new TreeMap<String, Contestant>();
	}

	public void addContestantEntry(String startNumber, Contestant contestant) {
		contestantEntries.put(startNumber, contestant);
        contestant.setStartNumber(startNumber);
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
	
	public Contestant removeContestant(String startNumber) {
		return contestantEntries.remove(startNumber);
	}
	

	public boolean equals(Object obj) {
		if (obj instanceof DataStructure)
		{
			DataStructure ds = (DataStructure) obj;
			return ds.getAllContestantEntries().equals(getAllContestantEntries());
		}
		return false;
	}

    public int getMaxLaps() {
        int maxLaps = 0;
        for(Contestant c : contestantEntries.values()) {
            if(c.getLapsCompleted() > maxLaps)
                maxLaps = c.getLapsCompleted();
        }
        return maxLaps;
    }

	public void clearContestantEntries() {
		contestantEntries.clear();
		contestantColumnNames = null;
	}
}
