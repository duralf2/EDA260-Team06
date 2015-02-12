package register.model;

import java.io.PrintWriter;

public class MarathonRace implements CompetitionType{

	@Override
	public String printColumnNames(Database db, PrintWriter pw) {
		StringBuilder sb = new StringBuilder();
		sb.append("StartNr;Namn;TotalTid;Start;Mål\n");
		return sb.toString();
	}

}
