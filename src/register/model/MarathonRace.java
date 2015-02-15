package register.model;

import io.RacePrinter;

import java.io.File;
import java.io.IOException;

public class MarathonRace implements CompetitionType{
	private Database db;
	
	public MarathonRace(Database db) {
		this.db = db;
	}

	@Override
	public void print(File file) {
		StringBuilder sb = new StringBuilder();
		sb.append("StartNr;Namn;");
		// TODO Använd headern i ContestantFactory för att få ut "StartNr;Namn;..." till raden ovan istället
		
		sb.append("TotalTid;Starttider;Måltider\n");
		for (AbstractContestant c : db.getAllContestantEntries().values()) {
			sb.append(c.toString(this) + "\n");
		}
		
		try {
			new RacePrinter(file.getAbsolutePath()).print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
