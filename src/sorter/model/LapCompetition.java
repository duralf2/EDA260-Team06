package sorter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods for generating header and sorting the results of a
 * lap competition
 * @extends CompetitionType
 */
public class LapCompetition extends CompetitionType {

	public LapCompetition(Database db) {
		super (db);
	}

	@Override
	public String generateHeader(boolean useShortFormat) {

		StringBuilder sb = new StringBuilder();
		for (String h : db.getContestantColumnNames())
			sb.append(h + ";");
		sb.append("#Varv;TotalTid");
		int maxLaps = getMaxLaps();

		for (int i = 1; i <= maxLaps; i++)
			sb.append(";Varv" + i);
		
		if (!useShortFormat) {
			sb.append(";Start");
	
			for (int i = 1; i <= maxLaps - 1; i++)
				sb.append(";Varvning" + i);
	
			sb.append(";Mål");
		}
		sb.append("\n");

		return sb.toString();
	}
	
	//TODO: rename to getMostLaps?
	/**
	 * Calculates the highest amount of laps a contestant in this competition has completed. 
	 * @return the highest number of laps completed in this competition.
	 */
	public int getMaxLaps() {
		int maxLaps = 0;
		for (AbstractContestant c : db.getAllContestantEntries().values()) {
			if (((LapContestant) c).getLapsCompleted() > maxLaps) {
				maxLaps = ((LapContestant) c).getLapsCompleted();
			}
		}
		return maxLaps;
	}

	public List<AbstractContestant> sort() {
		// TODO LapRace; Implement sort algorithm! Take a look at the algorithm
		// in the Sorter class
		return new ArrayList<AbstractContestant>();
	}
}
