package sorter.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class CompetitionType {
	
	protected Database db;

	public CompetitionType(Database db) {
		this.db = db;
	}
	
	/**
	 * Writes the contents of this competition to a string and returns it. The result
	 *  will be formatted like a result file in the excel file format, including the header.
	 */
	public String toResultString() {
		return toResultString(false);
	}
	/**
	 * Writes the contents of this competition to a string and returns it. The result
	 *  will be formatted like a result file in the excel file format, including the header.
	 * @param useShortFormat Whether or not to print all the columns into the result
	 */
	public String toResultString(boolean useShortFormat)
	{
		StringBuilder sb = new StringBuilder();
		StringBuilder incorrectlyRegisteredContestants = new StringBuilder();
		String headerLine = generateHeader(useShortFormat);
		
		List<AbstractContestant> contestants = new ArrayList<AbstractContestant>(db.getAllContestantEntries().values());
		sortByClass(contestants);
		String currentClass = "";
		for (AbstractContestant c : contestants) {
			if (c.getInformation("Namn").equals("")){
				incorrectlyRegisteredContestants.append(c.toString(this, useShortFormat) + "\n");
			} else{
				if (!currentClass.equals(c.getClassName())) {
					currentClass = c.getClassName();
					appendClassHeader(headerLine, currentClass, sb);
				}
				sb.append(c.toString(this, useShortFormat) + "\n");
			}
		}
		if(incorrectlyRegisteredContestants.length() > 0){
			sb.append("Icke existerande startnummer\n");
			sb.append(generateHeader(useShortFormat));
			sb.append(incorrectlyRegisteredContestants);
		}
		
		if (currentClass.equals(""))
			sb.insert(0, headerLine);
		return sb.toString().replaceAll(";", "; ").replaceAll("\\s+\n", "\n").trim();
	}

	private void sortByClass(List<AbstractContestant> contestants) {
		Collections.sort(contestants, new Comparator<AbstractContestant>() {
			public int compare(AbstractContestant o1, AbstractContestant o2) {
				return o2.getClassName().compareTo(o1.getClassName());
			}
		});
	}
	
	private void appendClassHeader(String headerLine, String className, StringBuilder sb) {
		sb.append(className + "\n");
		sb.append(headerLine);
	}
	
	
	public String generateHeader() {
		return generateHeader(false);
	}
	public abstract String generateHeader(boolean useShortFormat);
	
	/**
	 * Sorts the contents of the database associated with this competition type
	 *  and returns it as a list. The algorithm used for sorting depends on the
	 *  competition type.
	 * @return A sorted list, containing all contestants
	 */
	public abstract List<AbstractContestant> sort();
}
