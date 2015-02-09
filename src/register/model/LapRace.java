package register.model;

import java.io.PrintWriter;

public class LapRace implements CompetitionType {
	private int nbrOfLaps = 0;
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
       for(AbstractContestant c:db.getAllContestantEntries().values()){
    	   if(((LapContestant)c).getLapsCompleted()>nbrOfLaps){
    		   nbrOfLaps = ((LapContestant)c).getLapsCompleted();
    	   }
       }
        return nbrOfLaps;
    }
    
    //TODO NbrOfLaps = 0 om man aldrig callat printColumnNames(med getMaxLaps då ja)
    public int getMaxLaps(){
    	return nbrOfLaps;
    }

}
