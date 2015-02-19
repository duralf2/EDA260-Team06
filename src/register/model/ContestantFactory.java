package register.model;

import java.io.IOException;
import java.util.Properties;

public class ContestantFactory {
	private String[] nameHeader;
	private Properties properties;

	public ContestantFactory(Properties properties) throws IOException {
		this.properties = properties;
	}


	public AbstractContestant createContestant() {
		return createContestant(createContestantProperties());
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

	public ContestantProperties createContestantProperties() {
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

	public void setContestantColumnNames(String[] contestantColumns) {
		nameHeader = contestantColumns;
	}
}
