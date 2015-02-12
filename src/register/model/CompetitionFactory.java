package register.model;

import java.io.IOException;
import java.util.Properties;

public class CompetitionFactory {
	private Properties prop;
	
	public CompetitionFactory() throws IOException {
		this (new RaceProperties());
	}
	
	public CompetitionFactory(Properties prop){
		this.prop = prop;
	}
	
	public CompetitionType createCompetition(){
		CompetitionType ct = null;
		if(prop.getProperty(RaceProperties.KEY_RACE_TYPE).equals(RaceProperties.VALUE_RACE_MARATHON)){
			ct = new MarathonRace();
		}else if(prop.getProperty(RaceProperties.KEY_RACE_TYPE).equals(RaceProperties.VALUE_RACE_LAPS)){
			ct = new LapRace();
		}
		return ct;
	}
	
	
}
