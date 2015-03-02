package tests.RefContestantTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sorter.model.AbstractContestant;
import sorter.model.Configuration;
import sorter.model.ContestantProperties;
import sorter.model.Database;
import sorter.model.LapContestant;
import sorter.model.MarathonCompetition;
import sorter.model.MarathonContestant;
import sorter.model.Time;

public class MarathonContestantTest {
	private ContestantProperties racerInfo;
	private MarathonContestant marathonContestant;
	private Configuration config;

	@Before
	public void setUp() throws IOException {
		File file = new File("testfiles/config/marathonContestant.ini");
		config = new Configuration(file);
		AbstractContestant.setConfiguration(config);
		
		racerInfo = new ContestantProperties(new String[]{"Name"});
		racerInfo.put("Name","Hannah");
		marathonContestant = new MarathonContestant(racerInfo);
	}
	
	@After
	public void tearDown() throws IOException{
		racerInfo = null;
		marathonContestant = null;
		AbstractContestant.setConfiguration(new Configuration());
	}
	
	@Test
	public void testTotalTime(){
		marathonContestant.addStartTime(new Time("12.00.01"));
		marathonContestant.addFinishTime(new Time("12.01.15"));
		assertEquals("00.01.14", marathonContestant.getTotalTime().toString());
	}
	
	@Test
	public void testToString(){
		marathonContestant.addStartTime(new Time("12.00.01"));
		marathonContestant.addFinishTime(new Time("12.21.15"));
		String match = "Hannah;00.21.14;12.00.01;12.21.15";
		assertEquals(match, marathonContestant.toString(new MarathonCompetition(new Database())));
	}
	
	@Test
	public void testCompareTo(){
		MarathonContestant racerAwesome = new MarathonContestant(racerInfo);
		marathonContestant.addStartTime(new Time("12.00.01"));
		marathonContestant.addFinishTime(new Time("12.02.15"));
		racerAwesome.addStartTime(new Time("12.00.01"));
		racerAwesome.addFinishTime(new Time("12.01.15"));
		assertTrue(marathonContestant.compareTo(racerAwesome) < 0 );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidObjectToCompareTo() {
		marathonContestant.compareTo(new LapContestant(new ContestantProperties(new String[]{})));
	}
	
	@Test
	public void testToStringSeveralStartTimes()
	{
		marathonContestant.addStartTime(new Time("12.00.00"));
		marathonContestant.addStartTime(new Time("12.01.00"));
		marathonContestant.addStartTime(new Time("12.02.00"));
		marathonContestant.addFinishTime(new Time("12.20.00"));
		String match = "Hannah;00.20.00;12.00.00;12.20.00;Flera starttider? 12.01.00 12.02.00";
		assertEquals(match, marathonContestant.toString(new MarathonCompetition(new Database())));
	}
	
	@Test
	public void testToStringSeveralFinishTimes()
	{
		marathonContestant.addStartTime(new Time("12.00.00"));
		marathonContestant.addFinishTime(new Time("12.20.00"));
		marathonContestant.addFinishTime(new Time("12.21.00"));
		marathonContestant.addFinishTime(new Time("12.22.00"));
		String match = "Hannah;00.20.00;12.00.00;12.20.00;Flera måltider? 12.21.00 12.22.00";
		assertEquals(match, marathonContestant.toString(new MarathonCompetition(new Database())));
	}
	
	@Test
	public void testToStringImpossibleTotalTime() throws IOException
	{
		config.setProperty(Configuration.KEY_SHORTEST_POSSIBLE_TIME, "13.00.00");
		
		marathonContestant.addStartTime(new Time("12.00.00"));
		marathonContestant.addFinishTime(new Time("12.01.00"));
		String match = "Hannah;00.01.00;12.00.00;12.01.00;Omöjlig totaltid?";
		assertEquals(match, marathonContestant.toString(new MarathonCompetition(new Database())));
	}


	
	@Test
	public void testToStringShortFormat() throws IOException
	{
		marathonContestant.addStartTime(new Time("12.00.01"));
		marathonContestant.addFinishTime(new Time("12.21.15"));
		String match = "Hannah;00.21.14";
		assertEquals(match, marathonContestant.toString(new MarathonCompetition(new Database()), true));
	}
}
