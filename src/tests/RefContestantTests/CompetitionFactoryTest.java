package tests.RefContestantTests;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;

import register.model.CompetitionFactory;
import register.model.CompetitionType;
import register.model.LapRace;
import register.model.MarathonRace;
import register.model.RaceProperties;
public class CompetitionFactoryTest {
	private CompetitionFactory cf;
	private RaceProperties rp;
	
	@Before
	public void setUp(){
		try{
			rp = new RaceProperties();
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
			rp.setProperty(RaceProperties.KEY_RACE_TYPE, RaceProperties.VALUE_RACE_LAPS);
			CompetitionType ct = cf.createCompetition(null);
			assertTrue(ct instanceof LapRace);
	}
	
	@Test
	public void testMarathonRace() {
		rp.setProperty(RaceProperties.KEY_RACE_TYPE, RaceProperties.VALUE_RACE_MARATHON);
		CompetitionType ct = cf.createCompetition(null);
		assertTrue(ct instanceof MarathonRace);
	}
}
