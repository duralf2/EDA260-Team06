package register.model;

import java.util.ArrayList;
import java.util.List;

public class MarathonCompetition extends CompetitionType {
	public MarathonCompetition(Database db) {
		super (db);
	}

	public List<AbstractContestant> sort() {
		// TODO MarathonRace; Implement sort algorithm!
		return new ArrayList<AbstractContestant>();
	}

	@Override
	public String generateHeader(boolean useShortFormat) {
		StringBuilder sb = new StringBuilder();
		for (String h : db.getContestantColumnNames())
			sb.append(h + ";");
		sb.append("TotalTid;Starttider;Måltider\n");
		return sb.toString();
	}
}
