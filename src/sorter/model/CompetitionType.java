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
		
		for (AbstractContestant contestant : contestants) {
			if (contestant.getInformation("Namn").equals("")){
                contestant.setClassName("ICKE-EXISTERANDE-STARTNUMMER");
				incorrectlyRegisteredContestants.add(contestant);
			} else {
				if (!currentClass.equals(contestant.getClassName())) {
					currentClass = contestant.getClassName();
                    headerLine = generateHeader(new ArrayList<AbstractContestant>(db.getAllContestantsByClass(currentClass).values()), useShortFormat);
                    sb.append(currentClass + "\n");
            		sb.append(headerLine);
				}
				sb.append(contestant.toString(this, useShortFormat) + "\n");
			}
		}
		if(incorrectlyRegisteredContestants.size() > 0){
			sb.append("Icke existerande startnummer\n");
			sb.append(generateHeader(incorrectlyRegisteredContestants, useShortFormat));
            for(AbstractContestant contestant : incorrectlyRegisteredContestants) {
                sb.append(contestant.toString(this, useShortFormat) + "\n");
            }
		}
		
		if (currentClass.equals(""))
			sb.insert(0, headerLine);
		return sb.toString().replaceAll(";", "; ").replaceAll("\\s+\n", "\n").trim();
	}
	
	/**
	 * Writes the contents of this competition to a string and returns it. The result
	 *  will be formatted like a result file in the excel file format, including the header. 
	 *  This method also adds placement to the contestants.
	 * @param useShortFormat Whether or not to print all the columns into the result
	 * @return A string with the results.
	 */
	public String toResultStringWithPlacement(boolean useShortFormat) {
		StringBuilder sb = new StringBuilder();
		
		List<AbstractContestant> incorrectlyRegisteredContestants = new ArrayList<AbstractContestant>();
		List<AbstractContestant> contestants = new ArrayList<AbstractContestant>(db.getAllContestantEntries().values());
		List<AbstractContestant> contestantsByClass = new ArrayList<AbstractContestant>();
		
		sortByClass(contestants);
		String currentClass = "";
		
		for (AbstractContestant contestant : contestants) {
			if (contestant.getInformation("Namn").equals("")){
                contestant.setClassName("ICKE-EXISTERANDE-STARTNUMMER");
				incorrectlyRegisteredContestants.add(contestant);
			} else {
				if (!currentClass.equals(contestant.getClassName())) {
					currentClass = contestant.getClassName();
					if(contestantsByClass.size() > 0) {
						sb.append(contestantsByClass.get(0).getClassName() + "\n");
						sortWithinClass(contestantsByClass, sb, useShortFormat);
						printIncorrectlyRegisteredContestants(incorrectlyRegisteredContestants, sb, useShortFormat);
					}
					contestantsByClass.clear();
					incorrectlyRegisteredContestants.clear();
				}
				
				if(contestant.completedRace())
					contestantsByClass.add(contestant);
				else
					incorrectlyRegisteredContestants.add(contestant);
			}
		}
		
		if(contestantsByClass.size() > 0) {
			sb.append(currentClass + "\n");
			sortWithinClass(contestantsByClass, sb, useShortFormat);
		}
		
		if(incorrectlyRegisteredContestants.size() > 0){
            for(AbstractContestant contestant : incorrectlyRegisteredContestants) {
                sb.append(";" + contestant.toString(this, useShortFormat) + "\n");
            }
		}
		
		return sb.toString().replaceAll(";", "; ").replaceAll("\\s+\n", "\n").trim();
	}
	
	private void printIncorrectlyRegisteredContestants(List<AbstractContestant> contestants, StringBuilder sb, boolean useShortFormat) {
		for( AbstractContestant c : contestants) {
			sb.append(";" + c.toString(this, useShortFormat) + "\n" );
		}
	}
	
	private void sortWithinClass(List<AbstractContestant> contestants, StringBuilder sb, boolean useShortFormat) {
		Collections.sort(contestants);
		String incompleted = "";

		sb.append("Plac;" + generateHeader(new ArrayList<AbstractContestant>(contestants), useShortFormat));
		int place = 1;
		for (int i = contestants.size()-1; i >= 0 ; i--) {
			AbstractContestant contestant = contestants.get(i);
			if (contestant.completedRace()) {
				contestant.toString(this,true);
				sb.append(place + ";" + contestant.toString(this, useShortFormat)
						+ "\n");
				place++;
			} else {
				incompleted += ";" + contestant.toString(this, useShortFormat)
						+ "\n";
			}
		}
		sb.append(incompleted);
	}

	private void sortByClass(List<AbstractContestant> contestants) {
		Collections.sort(contestants, new Comparator<AbstractContestant>() {
			public int compare(AbstractContestant contestant1, AbstractContestant contestant2) {
				return contestant2.getClassName().compareTo(contestant1.getClassName());
			}
		});
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
