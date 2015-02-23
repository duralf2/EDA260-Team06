package tests.RefContestantTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sorter.model.CompetitionFactory;
import sorter.model.CompetitionType;
import sorter.model.Configuration;
import sorter.model.LapCompetition;
import sorter.model.MarathonCompetition;
public class CompetitionFactoryTest {
	private CompetitionFactory cf;
	private Configuration config;
	
	@Before
	public void setUp(){
		try{
			config = new Configuration();
			cf = new CompetitionFactory(config);
		}catch(IOException e){
			fail("Could not read data/config.ini");
		}
	}
	
	@Test
	public void testCompFact() {
		assertTrue(cf.createCompetition(null)!=null);
	}
	
	@Test
	public void testLapRace() {
			config.setProperty(Configuration.KEY_RACE_TYPE, Configuration.VALUE_RACE_LAPS);
			CompetitionType ct = cf.createCompetition(null);
			assertTrue(ct instanceof LapCompetition);
	}
	
	@Test
	public void testMarathonRace() {
		config.setProperty(Configuration.KEY_RACE_TYPE, Configuration.VALUE_RACE_MARATHON);
		CompetitionType ct = cf.createCompetition(null);
		assertTrue(ct instanceof MarathonCompetition);
	}
}
