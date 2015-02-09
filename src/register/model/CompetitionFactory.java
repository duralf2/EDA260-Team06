package register.model;

import java.util.Properties;

public class CompetitionFactory {
	private Properties prop;
	CompetitionType ct;
	
	public CompetitionFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public CompetitionFactory(Properties prop){
		this.prop = prop;
	}
	
	public CompetitionType createCompetition(){
		if(prop.getProperty(RaceProperties.KEY_RACE_TYPE).equals(RaceProperties.VALUE_RACE_MARATHON)){
			ct = new MarathonRace();
		}else if(prop.getProperty(RaceProperties.KEY_RACE_TYPE).equals(RaceProperties.VALUE_RACE_LAPS)){
			ct = new LapRace();
		}
		return ct;
	}
	
	
}
