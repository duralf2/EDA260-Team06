package register.model;

import io.ReadFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class ContestantFactory {
	private String[] nameHeader;
	private Properties properties;

	public ContestantFactory(Properties properties) throws IOException {
		this.properties = properties;
	}

	
	public void createRegisteredContestants(Database db) throws IOException {
		List<String[]> fileRows = ReadFile.readCSV(new File(properties
				.getProperty(RaceProperties.KEY_NAME_FILE_PATH)));
		nameHeader = trimContestantColumnNames(fileRows.get(0));
		
		for (int i = 1; i < fileRows.size(); i++) {
			String[] line = fileRows.get(i);
			String startNumber = line[0].trim();
			AbstractContestant contestant = createContestant( line);
			db.addContestantEntry(startNumber, contestant);
		}
	}

	private String[] trimContestantColumnNames(String[] contestantColumns) {
		for (int i = 0; i < contestantColumns.length; i++) {
			contestantColumns[i] = contestantColumns[i].trim();
		}
		return contestantColumns;
	}

	private AbstractContestant createContestant(String[] line) {
		RacerInfo info = createRacerInfo(line);
		AbstractContestant contestant = createContestant(info);

		return contestant;
	}

	public AbstractContestant createContestant() {
		return createContestant(createRaceInfo());
	}
	public AbstractContestant createContestant(RacerInfo info) {
		AbstractContestant contestant = null;
		if (isMarathonRace()) {
			contestant = new MarathonContestant(info);
		} else if (isLapRace()) {
			contestant = new LapContestant(info);
		}
		return contestant;
	}
	
	public RacerInfo createRaceInfo()
	{
		return new RacerInfo(nameHeader);
	}

	private boolean isLapRace() {
		return properties.getProperty(RaceProperties.KEY_RACE_TYPE).equals(
				RaceProperties.VALUE_RACE_LAPS);
	}

	private boolean isMarathonRace() {
		return properties.getProperty(RaceProperties.KEY_RACE_TYPE).equals(
				RaceProperties.VALUE_RACE_MARATHON);
	}

	private RacerInfo createRacerInfo(String[] line) {
		RacerInfo info = new RacerInfo(nameHeader);
		for (int i = 0; i < Math.min(line.length, nameHeader.length); i++) {
			info.put(nameHeader[i], line[i]);
		}
		return info;
	}
}