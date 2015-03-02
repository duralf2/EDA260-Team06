package sorter;

import gui.model.StartNumberComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import sorter.model.AbstractContestant;
import sorter.model.CompetitionType;
import sorter.model.Database;

public class Sorter {

	private CompetitionType competitionType;
	private Database db;

	public Sorter(CompetitionType competitionType, Database db) {
		this.competitionType = competitionType;
		this.db = db;
	}

	/**
	 * Writes the contents of this competition to a string and returns it. The
	 * result will be formatted like a result file in the excel file format,
	 * including the header.
	 * 
	 * @param useShortFormat
	 *            Whether or not to print all the columns into the result
	 * @return A string with the results.
	 */
	public String toResultString(boolean useShortFormat) {
		StringBuilder sb = new StringBuilder();
		List<AbstractContestant> incorrectlyRegisteredContestants = new ArrayList<AbstractContestant>();
		String headerLine = competitionType.generateHeader(useShortFormat);

		List<AbstractContestant> contestants = new ArrayList<AbstractContestant>(
				db.getAllContestantEntries().values());

		sortByClass(contestants);
		String currentClass = "";

		for (AbstractContestant contestant : contestants) {
			if (contestant.getInformation("Namn").equals("")) {
				contestant.setClassName("ICKE-EXISTERANDE-STARTNUMMER");
				incorrectlyRegisteredContestants.add(contestant);
			} else {
				if (!currentClass.equals(contestant.getClassName())) {
					currentClass = contestant.getClassName();
					headerLine = competitionType.generateHeader(
							new ArrayList<AbstractContestant>(db
									.getAllContestantsByClass(currentClass)
									.values()), useShortFormat);
					sb.append(currentClass + "\n");
					sb.append(headerLine);
				}
				sb.append(contestant.toString(competitionType, useShortFormat)
						+ "\n");
			}
		}
		if (incorrectlyRegisteredContestants.size() > 0) {
			sb.append("Icke existerande startnummer\n");
			sb.append(competitionType.generateHeader(
					incorrectlyRegisteredContestants, useShortFormat));
			for (AbstractContestant contestant : incorrectlyRegisteredContestants) {
				sb.append(contestant.toString(competitionType, useShortFormat)
						+ "\n");
			}
		}

		if (currentClass.equals(""))
			sb.insert(0, headerLine);
		return sb.toString().replaceAll(";", "; ").replaceAll("\\s+\n", "\n")
				.trim();
	}

	/**
	 * Writes the contents of this competition to a string and returns it. The
	 * result will be formatted like a result file in the excel file format,
	 * including the header. This method also adds placement to the contestants.
	 * 
	 * @param useShortFormat
	 *            Whether or not to print all the columns into the result
	 * @return A string with the results.
	 */
	public String toResultStringWithPlacement(boolean useShortFormat) {
		StringBuilder sb = new StringBuilder();

		List<AbstractContestant> incorrectlyRegisteredContestants = new ArrayList<AbstractContestant>();
		List<AbstractContestant> contestants = new ArrayList<AbstractContestant>(
				db.getAllContestantEntries().values());
		List<AbstractContestant> contestantsByClass = new ArrayList<AbstractContestant>();

		List<AbstractContestant> missingName = new ArrayList<AbstractContestant>();

		sortByClass(contestants);
		String currentClass = "";

		for (AbstractContestant contestant : contestants) {

			if (contestant.getInformation("Namn").equals("")) {
				missingName.add(contestant);
			} else {
				if (!currentClass.equals(contestant.getClassName())) {
					sb.append(currentClass + "\n");
					currentClass = contestant.getClassName();
					if (contestantsByClass.size() > 0) {
						sortWithinClass(contestantsByClass, sb, useShortFormat);
					}
					printIncorrectlyRegisteredContestants(
							incorrectlyRegisteredContestants, sb,
							useShortFormat, contestantsByClass.size() == 0);
					contestantsByClass.clear();
					incorrectlyRegisteredContestants.clear();
				}

				if (contestant.completedRace())
					contestantsByClass.add(contestant);
				else
					incorrectlyRegisteredContestants.add(contestant);

			}
		}

		sb.append(currentClass + "\n");
		if (contestantsByClass.size() > 0) {
			sortWithinClass(contestantsByClass, sb, useShortFormat);
		}

		printIncorrectlyRegisteredContestants(incorrectlyRegisteredContestants,
				sb, useShortFormat, contestantsByClass.size() == 0);

		if (missingName.size() > 0) {
			sb.append("Icke existerande startnummer\n");
			sb.append(competitionType.generateHeader(missingName,
					useShortFormat));
			for (AbstractContestant contestant : missingName) {
				sb.append(contestant.toString(competitionType, useShortFormat)
						+ "\n");
			}
		}

		return sb.toString().replaceAll(";", "; ").replaceAll("\\s+\n", "\n")
				.trim();
	}

	private void printIncorrectlyRegisteredContestants(
			List<AbstractContestant> contestants, StringBuilder sb,
			boolean useShortFormat, boolean writeHeader) {

		if (contestants.size() > 0) {
			if (writeHeader) {
				sb.append("Plac;"
						+ competitionType.generateHeader(
								new ArrayList<AbstractContestant>(contestants),
								useShortFormat));
			}
			for (AbstractContestant c : contestants) {
				sb.append(";" + c.toString(competitionType, useShortFormat)
						+ "\n");
			}
		}
	}

	private void sortWithinClass(List<AbstractContestant> contestants,
			StringBuilder sb, boolean useShortFormat) {
		Collections.sort(contestants);
		String incompleted = "";
		TreeMap<String, String> t = new TreeMap<String, String>(
				new StartNumberComparator());

		sb.append("Plac;"
				+ competitionType.generateHeader(
						new ArrayList<AbstractContestant>(contestants),
						useShortFormat));
		int place = 1;
		for (int i = contestants.size() - 1; i >= 0; i--) {
			AbstractContestant contestant = contestants.get(i);
			if (contestant.completedRace()) {
				sb.append(place + ";"
						+ contestant.toString(competitionType, useShortFormat)
						+ "\n");
				place++;
			} else {
				t.put(contestant.getInformation("StartNr"),
						";"
								+ contestant.toString(competitionType,
										useShortFormat) + "\n");
			}
		}
		for (String s : t.values()) {
			incompleted += s;
		}
		sb.append(incompleted);
	}

	private void sortByClass(List<AbstractContestant> contestants) {
		Collections.sort(contestants, new Comparator<AbstractContestant>() {
			public int compare(AbstractContestant contestant1,
					AbstractContestant contestant2) {
				return contestant2.getClassName().compareTo(
						contestant1.getClassName());
			}
		});
	}

}
