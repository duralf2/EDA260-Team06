package register.model;

import java.io.PrintWriter;

public class MarathonRace implements CompetitionType{

	@Override
	public void printColumnNames(Database db, PrintWriter pw, StringBuilder sb) {
		sb.append("StartNr;Namn;");
		sb.append("TotalTid;");
		sb.append("Start;");
		sb.append("Mål\n");
		
	}

}
