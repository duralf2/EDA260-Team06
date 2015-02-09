package register.model;

import io.ReadFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class ContestantFactory {

	private Properties properties;

	public ContestantFactory() throws IOException {
		this (new RaceProperties());
	}
	
	public ContestantFactory(Properties properties) throws IOException {
		this.properties = properties;
	}

	public void createRegisteredContestants(Database ds)
			throws IOException {
		List<String[]> fileRows = ReadFile.readCSV(new File(properties
				.getProperty(RaceProperties.KEY_NAME_FILE_PATH)));

		readContestantColumns(ds, fileRows.get(0));

		for (int i = 1; i < fileRows.size(); i++) {
			String[] line = fileRows.get(i);

			String startNumber = line[0].trim();
			StandardContestant contestant = createContestant(ds.getContestantColumnNames(), line);
			ds.addContestantEntry(startNumber, contestant);
		}
	}

	private void readContestantColumns(Database ds,
			String[] contestantColumns) {
		for (int i = 0; i < contestantColumns.length; i++) {
			contestantColumns[i] = contestantColumns[i].trim();
		}
		ds.setContestantColumnNames(contestantColumns);
	}

	// TODO krashar om information saknas (line.lenght < columnNames.lenght)
	public StandardContestant createContestant(String[] columnNames, String[] line) {

		RacerInfo info = createRacerInfo(columnNames, line);

		StandardContestant contestant = null;
		if (properties.getProperty(RaceProperties.KEY_RACE_TYPE).equals(RaceProperties.VALUE_RACE_MARATHON)) {
			contestant = new MarathonContestant(info); 
		}
		else if (properties.getProperty(RaceProperties.KEY_RACE_TYPE).equals(RaceProperties.VALUE_RACE_LAPS)) {
			contestant = new LapContestant(info); 
		}

		return contestant;
	}

	private RacerInfo createRacerInfo(String[] columnNames, String[] line) {
		RacerInfo info = new RacerInfo();
		for (int i = 0; i < line.length; i++) {
			info.put(columnNames[i], line[i]);
		}
		return info;
	}

	
	
	public static void main(String[] args) throws IOException {
		new ContestantFactory()
				.createRegisteredContestants(new Database());
	}
}
