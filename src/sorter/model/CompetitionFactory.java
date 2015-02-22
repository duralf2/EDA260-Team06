package sorter.model;

import java.util.Properties;

/**
 * A Factory Method implementation to create Competition objects depending on the settings
 * in the config file.
 */
public class CompetitionFactory {
	private Properties prop;
	
	public CompetitionFactory(Properties prop){
		this.prop = prop;
	}
	
	/**
	 * Creates a CompetitionType object depending on what the race type setting in the config file 
	 * is set to.
	 * @param db the Database to use for this competition.
	 * @return an instance of a subclass of CompetitionType depending on the properties in the config file.
	 */
	public CompetitionType createCompetition(Database db) {
		CompetitionType ct = null;
		if(prop.getProperty(Configuration.KEY_RACE_TYPE).equals(Configuration.VALUE_RACE_MARATHON)){
			ct = new MarathonCompetition(db);
		}else if(prop.getProperty(Configuration.KEY_RACE_TYPE).equals(Configuration.VALUE_RACE_LAPS)){
			ct = new LapCompetition(db);
		}
		return ct;
	}
	
	
}
