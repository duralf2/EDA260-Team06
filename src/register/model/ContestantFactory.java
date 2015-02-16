package register.model;

import io.FileReader;

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


	public AbstractContestant createContestant() {
		return createContestant(createRaceInfo());
	}

	public AbstractContestant createContestant(ContestantProperties info) {
		AbstractContestant contestant = null;
		if (isMarathonRace()) {
			contestant = new MarathonContestant(info);
		} else if (isLapRace()) {
			contestant = new LapContestant(info);
		}
		return contestant;
	}

	public ContestantProperties createRaceInfo() {
		return new ContestantProperties(nameHeader);
	}

	private boolean isLapRace() {
		return properties.getProperty(Configuration.KEY_RACE_TYPE).equals(
				Configuration.VALUE_RACE_LAPS);
	}

	private boolean isMarathonRace() {
		return properties.getProperty(Configuration.KEY_RACE_TYPE).equals(
				Configuration.VALUE_RACE_MARATHON);
	}

	private ContestantProperties createRacerInfo(String[] line) {
		ContestantProperties info = new ContestantProperties(nameHeader);
		for (int i = 0; i < Math.min(line.length, nameHeader.length); i++) {
			info.put(nameHeader[i], line[i]);
		}
		return info;
	}

	public void setContestantColumnNames(String[] contestantColumns) {
		nameHeader = contestantColumns;
	}
}
