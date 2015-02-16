package tests.RefContestantTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import register.model.CompetitionFactory;
import register.model.CompetitionType;
import register.model.LapCompetition;
import register.model.MarathonCompetition;
import register.model.Configuration;
public class CompetitionFactoryTest {
	private CompetitionFactory cf;
	private Configuration rp;
	
	@Before
	public void setUp(){
		try{
			rp = new Configuration();
			cf = new CompetitionFactory(rp);
		}catch(IOException e){
			fail("Could not read data/config.ini");
		}
	}
	
	@Test
	public void testCompFact() {
		assertTrue(cf.createCompetition(null)!=null);;
	}
	
	@Test
	public void testLapRace() {
			rp.setProperty(Configuration.KEY_RACE_TYPE, Configuration.VALUE_RACE_LAPS);
			CompetitionType ct = cf.createCompetition(null);
			assertTrue(ct instanceof LapCompetition);
	}
	
	@Test
	public void testMarathonRace() {
		rp.setProperty(Configuration.KEY_RACE_TYPE, Configuration.VALUE_RACE_MARATHON);
		CompetitionType ct = cf.createCompetition(null);
		assertTrue(ct instanceof MarathonCompetition);
	}
}
