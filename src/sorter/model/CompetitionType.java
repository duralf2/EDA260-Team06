package sorter.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Abstract superclass to the different types of competitions, contains methods needed by each
 * subclass.
 */
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
	 * @return A string with the results.
	 */
	public String toResultString(boolean useShortFormat)
	{
		StringBuilder sb = new StringBuilder();
		List<AbstractContestant> incorrectlyRegisteredContestants = new ArrayList<AbstractContestant>();
		String headerLine = generateHeader(useShortFormat);
		
		List<AbstractContestant> contestants = new ArrayList<AbstractContestant>(db.getAllContestantEntries().values());
		
		sortByClass(contestants);
		String currentClass = "";
		
		for (AbstractContestant c : contestants) {
			if (c.getInformation("Namn").equals("")){
                c.setClassName("ICKE-EXISTERANDE-STARTNUMMER");
				incorrectlyRegisteredContestants.add(c);
			} else {
				if (!currentClass.equals(c.getClassName())) {
					currentClass = c.getClassName();
                    headerLine = generateHeader(new ArrayList<AbstractContestant>(db.getAllContestantsByClass(currentClass).values()), useShortFormat);
					appendClassHeader(headerLine, currentClass, sb);
				}
				sb.append(c.toString(this, useShortFormat) + "\n");
			}
		}
		if(incorrectlyRegisteredContestants.size() > 0){
			sb.append("Icke existerande startnummer\n");
			sb.append(generateHeader(incorrectlyRegisteredContestants, useShortFormat));
            for(AbstractContestant c : incorrectlyRegisteredContestants) {
                sb.append(c.toString(this, useShortFormat) + "\n");
            }
		}
		
		if (currentClass.equals(""))
			sb.insert(0, headerLine);
		return sb.toString().replaceAll(";", "; ").replaceAll("\\s+\n", "\n").trim();
	}
	
	public String toResultStringWithPlacement(boolean useShortFormat) {
		StringBuilder sb = new StringBuilder();
		List<AbstractContestant> incorrectlyRegisteredContestants = new ArrayList<AbstractContestant>();
		List<AbstractContestant> contestants = new ArrayList<AbstractContestant>(db.getAllContestantEntries().values());
		List<AbstractContestant> contestantsByClass = new ArrayList<AbstractContestant>();
		
		sortByClass(contestants);
		String currentClass = "";
		
		for (AbstractContestant c : contestants) {
			if (c.getInformation("Namn").equals("")){
                c.setClassName("ICKE-EXISTERANDE-STARTNUMMER");
				incorrectlyRegisteredContestants.add(c);
			} else {
				if (!currentClass.equals(c.getClassName())) {
					currentClass = c.getClassName();
					if(contestantsByClass.size() > 0) {
						sb.append(contestantsByClass.get(0).getClassName() + "\n");
						sortWithinClass(contestantsByClass, sb);
					}
					contestantsByClass = new ArrayList<AbstractContestant>();
					
				}
				contestantsByClass.add(c);
			}
			
		}
		
		if(contestantsByClass.size() > 0) {
			sb.append(currentClass + "\n");
			sortWithinClass(contestantsByClass, sb);
		}
		
		if(incorrectlyRegisteredContestants.size() > 0){
			sb.append("Icke existerande startnummer\n");
			sb.append(generateHeader(incorrectlyRegisteredContestants, useShortFormat));
            for(AbstractContestant c : incorrectlyRegisteredContestants) {
                sb.append(c.toString(this, useShortFormat) + "\n");
            }
		}
		
	//	if (currentClass.equals(""))
		//	sb.insert(0, headerLine);
		return sb.toString().replaceAll(";", "; ").replaceAll("\\s+\n", "\n").trim();
	}
	
	private void sortWithinClass(List<AbstractContestant> contestants, StringBuilder sb) {
		Collections.sort(contestants);
		String incompleted = "";

		sb.append("Plac;" + generateHeader(new ArrayList<AbstractContestant>(contestants), true));
		int place = 1;
		for (int i = 0; i < contestants.size(); i++) {
			AbstractContestant contestant = contestants.get(i);
			if (contestant.completedRace()) {
				sb.append(place + ";" + contestant.toString(this, true)
						+ "\n");
				place++;
			} else {
				incompleted += ";" + contestant.toString(this, true)
						+ "\n";
			}
		}
		sb.append(incompleted);
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
	
	/**
	 * delegates method call to generateHeader(boolean)
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
