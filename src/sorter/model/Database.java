package sorter.model;

import java.util.Map;
import java.util.TreeMap;

/**
 * Stores all the contestants and the CSV-header.
 * Provides methods for adding contestants, changing the csv header, clearing all contestant entries
 * and retrieving all current contestants. A print method is also defined.
 * Comparison between two instances of this class is also possible.
 */
public class Database {
	private Map<String, AbstractContestant> contestantEntries;
	private String[] contestantColumnNames;

	public Database() {
		contestantEntries = new TreeMap<String, AbstractContestant>();
	}
	
	/**
	 * Adds a contestant to the Database with the given start number.
	 * @param startNumber the start number of the contestant to be added
	 * @param contestant the Contestant object to be added.
	 */
	public void addContestantEntry(String startNumber,
			AbstractContestant contestant) {
		contestantEntries.put(startNumber, contestant);
	}
	
	/**
	 * Updates the CSV header for the database.
	 * @param columns the new CSV header 
	 */
	public void setContestantColumnNames(String[] columns) {
		contestantColumnNames = columns;
	}
	
	/**
	 * Returns a contestant entry from the database for a given start number.
	 * @param startNumber start number of the contestant to be returned
	 * @return a Contestant object.
	 */
	public AbstractContestant getContestant(String startNumber) {
		return contestantEntries.get(startNumber);
	}
	
	/**
	 * @return a Map of the current content of the database.
	 */
	public Map<String, AbstractContestant> getAllContestantEntries() {
		return contestantEntries;
	}
	
	/**
	 * @return the CSV-header for the contestants.
	 */
	public String[] getContestantColumnNames()
	{
		return contestantColumnNames;
	}
	
	/**
	 * Removes a contestant entry if it exists.
	 * @param startNumber the start number of the contestant to remove
	 * @return A Contestant object of the removed contestant.
	 */
	public AbstractContestant removeContestant(String startNumber) {
		return contestantEntries.remove(startNumber);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Database) {
			Database ds = (Database) obj;
			return ds.getAllContestantEntries().equals(
					getAllContestantEntries());
		}
		return false;
	}

	/**
	 * Removes all contestants in the Database
	 */
	public void clearContestantEntries() {
		contestantEntries.clear();
	}
	
	/**
	 * Generates a CSV-style String of all contestants currently in the system.
	 * @return a String in CSV format of all contestants.
	 */
	public String printResults(){
		return contestantEntries.toString();
	}
	
	
}
