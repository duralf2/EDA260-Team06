package sorter.model;

import java.io.IOException;
import java.util.Properties;

/**
 * A factory for making contestants based on the contents of the config file.
 */
public class ContestantFactory {
	private String[] nameHeader;
	private Properties properties;

	public ContestantFactory(Properties properties) throws IOException {
		this.properties = properties;
	}

	
	/**
	 * Creates a contestant with no additional contestant information.
	 * @return a Contestant object based on the competition type.
	 */
	public AbstractContestant createContestant() {
		return createContestant(createContestantProperties());
	}
	
	/**
	 * Creates a contestant with the specified contestant information.
	 * @param info additional information about each contestant such as sponsorship etc.
	 * @return
	 */
	public AbstractContestant createContestant(ContestantProperties info) {
		AbstractContestant contestant = null;
		if (isMarathonRace()) {
			contestant = new MarathonContestant(info);
		} else if (isLapRace()) {
			contestant = new LapContestant(info);
		}
		return contestant;
	}
	
	/**
	 * Creates a ContestantProperties object with the fields specified in the nameHeader array.
	 * @return a ConstestantProperties with the fields present in nameHeader
	 */
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

	/**
	 * Updates the nameHeader to contain the specified fields.
	 * @param contestantColumns a String[] containing the new header fields.
 	 */
	public void setContestantColumnNames(String[] contestantColumns) {
		nameHeader = contestantColumns;
	}
}
