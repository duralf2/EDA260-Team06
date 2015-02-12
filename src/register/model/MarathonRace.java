package register.model;

import io.RacePrinter;

import java.io.File;
import java.io.IOException;

public class MarathonRace implements CompetitionType{

	@Override
	public void print(File file) {
		StringBuilder sb = new StringBuilder();
		sb.append("StartNr;Namn;TotalTid;Starttider;Måltider\n");
		try {
			new RacePrinter(file.getAbsolutePath()).print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
