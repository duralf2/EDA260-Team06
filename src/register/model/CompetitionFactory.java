package register.model;

import java.util.Properties;

public class CompetitionFactory {
	private Properties prop;
	
	public CompetitionFactory(Properties prop){
		this.prop = prop;
	}
	
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
