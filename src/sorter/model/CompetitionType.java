package sorter.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sorter.Sorter;

/**
 * Abstract superclass to the different types of competitions, contains methods needed by each
 * subclass.
 */
public abstract class CompetitionType {
	
	protected Database db;
	protected Sorter sorter;

	public CompetitionType(Database db) {
		this.db = db;
		sorter = new Sorter(this, db);
	}
	

	/**
	 * 
	 * @param useShortFormat
	 * @return
	 */
	public String toResultString(boolean useShortFormat)
	{
			return sorter.toResultString(useShortFormat);
	}
	
	/**
	 * 
	 * @param useShortFormat
	 * @param config
	 * @return
	 */
	public String resultList(boolean useShortFormat, Configuration config) {
			return sorter.toResultStringWithPlacement(useShortFormat);
	}
	
	
	/**
	 * Delegates method call to generateHeader(boolean)
	 * @return a CSV style String of the header
	 */
	public String generateHeader() {
		return generateHeader(false);
	}
	
	/**
	 * Generates the CSV header for a competition
	 * @param useShortFormat to indicate if all columns should be included in result
	 * @return a String representation of the header.
	 */
	public abstract String generateHeader(boolean useShortFormat);
    
	/**
	 * Generates the CSV header for a competition
	 * @param contestants a list of contestants
	 * @param useShortFormat to indicate if all columns should be included in result
	 * @return a String representation of the header.
	 */
    public abstract String generateHeader(List<AbstractContestant> contestants, boolean useShortFormat);
	
	/**
	 * Sorts the contents of the database associated with this competition type
	 *  and returns it as a list. The algorithm used for sorting depends on the
	 *  competition type.
	 * @return A sorted list, containing all contestants
	 */
	public abstract List<AbstractContestant> sort();
}
