package sorter.model;

import java.util.List;

/**
 * Contains method for generating a header and sorting the results of
 * a marathon competition
 *  @extends CompetitionType
 */
public class MarathonCompetition extends CompetitionType {
	public MarathonCompetition(Database db) {
		super (db);
	}

	@Override
	public String generateHeader(boolean useShortFormat) {
		StringBuilder sb = new StringBuilder();
		for (String h : db.getContestantColumnNames())
			sb.append(h + ";");
		
		sb.append("TotalTid");
		if (!useShortFormat)
			sb.append(";Starttider;Måltider");
		sb.append('\n');
		return sb.toString();
	}

    @Override
    public String generateHeader(List<AbstractContestant> contestants, boolean useShortFormat) {
        return generateHeader(useShortFormat);
    }
}
