package register.model;

import java.io.File;
import java.util.List;

public interface CompetitionType {
	
	/**
	 * Prints the database associated with this competition type to the specified
	 *  file. The output will be formatted like a result file in the excel file
	 *  format, including the header.
	 * @param file The file to print to
	 */
	public void print(File file);
	
	/**
	 * Sorts the contents of the database associated with this competition type
	 *  and returns it as a list. The algorithm used for sorting depends on the
	 *  competition type.
	 * @return A sorted list, containing all contestants
	 */
	public List<AbstractContestant> sort();
	public String generateHeader();
}
