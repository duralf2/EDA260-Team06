package register.model;

import java.io.PrintWriter;
import java.util.Map;

public class LapRace implements CompetitionType {
	private int nbrOfLaps;
	@Override
	public void printColumnNames(Database db, PrintWriter pw, StringBuilder sb) {
		sb.append("StartNr;Namn;");
		sb.append("#Varv;");
		sb.append("TotalTid;");
		nbrOfLaps=getMaxLaps(db);
		for (int i = 1; i <= nbrOfLaps; i++)
			sb.append("Varv" + i + ";");
		sb.append("Start;");
		
		for (int i = 1; i <= nbrOfLaps - 1; i++)
			sb.append("Varvning" + i + ";");

		sb.append("Mål\n");
		
	}
	
    private int getMaxLaps(Database db) {
        int maxLaps = 0;
       for(db.getAllContestantEntries().values()
        
        
//        for(StandardContestant c : contestantEntries.values()) {
//            if(c.getLapsCompleted() > maxLaps)
//                maxLaps = c.getLapsCompleted();
//       
//        }
//        nbrOfLaps = maxLaps;
        return nbrOfLaps;
    }

}
