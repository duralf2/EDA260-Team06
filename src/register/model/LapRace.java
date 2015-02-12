package register.model;

import java.io.PrintWriter;

public class LapRace implements CompetitionType {

	@Override
	public void printColumnNames(Database db, PrintWriter pw) {
		StringBuilder sb = new StringBuilder();
		sb.append("StartNr;Namn;");
		//TODO RacerInfo?
		sb.append("#Varv;TotalTid;");
		int maxLaps = getMaxLaps(db);

		for (int i = 1; i <= maxLaps; i++)
			sb.append("Varv" + i + ";");
		sb.append("Start;");

		for (int i = 1; i <= maxLaps - 1; i++)
			sb.append("Varvning" + i + ";");

		sb.append("Mål\n");
		pw.write(sb.toString());
	}

	private int getMaxLaps(Database db) {
		int maxLaps = 0;
		for (AbstractContestant c : db.getAllContestantEntries().values()) {
			if (((LapContestant) c).getLapsCompleted() > maxLaps) {
				maxLaps = ((LapContestant) c).getLapsCompleted();
			}
		}
		return maxLaps;
	}

	// TODO NbrOfLaps = 0 om man aldrig callat printColumnNames(med getMaxLaps
	// då ja)
	// public int getMaxLaps() {
	// return maxLaps;
	// }

}
