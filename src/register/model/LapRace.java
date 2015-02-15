package register.model;

import io.RacePrinter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LapRace implements CompetitionType {
	private Database db;
	
	public LapRace(Database db) {
		this.db = db;
	}

	@Override
	public void print(File file) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("StartNr;Namn;");
		// TODO Använd headern i ContestantFactory för att få ut "StartNr;Namn;..." till raden ovan istället 
		sb.append("#Varv;TotalTid;");
		int maxLaps = getMaxLaps();

		for (int i = 1; i <= maxLaps; i++)
			sb.append("Varv" + i + ";");
		sb.append("Start;");

		for (int i = 1; i <= maxLaps - 1; i++)
			sb.append("Varvning" + i + ";");

		sb.append("Mål\n");
		for (AbstractContestant c : db.getAllContestantEntries().values()) {
			sb.append(c.toString(this) + "\n");
		}
		
		
		try {
			new RacePrinter(file.getAbsolutePath()).print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
		// TODO LapRace; Implement sort algorithm! Take a look at the algorithm in the Sorter class
		return new ArrayList<AbstractContestant>();
	}
}
