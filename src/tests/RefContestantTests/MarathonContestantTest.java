package tests.RefContestantTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.Database;
import register.model.LapContestant;
import register.model.MarathonContestant;
import register.model.MarathonRace;
import register.model.RacerInfo;
import register.model.Time;

public class MarathonContestantTest {
	private RacerInfo racerInfo;
	private MarathonContestant marathonContestant;
	
	@Before
	public void setUp() {
		racerInfo = new RacerInfo(new String[]{"Name"});
		racerInfo.put("Name","Hannah");
		marathonContestant = new MarathonContestant(racerInfo);
	}
	
	@After
	public void tearDown(){
		racerInfo = null;
		marathonContestant = null;
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
		assertEquals(match, marathonContestant.toString(new MarathonRace(new Database())));
	}
	
	@Test
	public void testCompareTo(){
		MarathonContestant racerAwesome = new MarathonContestant(racerInfo);
		marathonContestant.addStartTime(new Time("12.00.01"));
		marathonContestant.addFinishTime(new Time("12.02.15"));
		racerAwesome.addStartTime(new Time("12.00.01"));
		racerAwesome.addFinishTime(new Time("12.01.15"));
		assertTrue(marathonContestant.compareTo(racerAwesome) > 0 );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidObjectToCompareTo() {
		marathonContestant.compareTo(new LapContestant());
	}
	
	@Test
	public void testToStringSeveralStartTimes()
	{
		marathonContestant.addStartTime(new Time("12.00.00"));
		marathonContestant.addStartTime(new Time("12.01.00"));
		marathonContestant.addStartTime(new Time("12.02.00"));
		marathonContestant.addFinishTime(new Time("12.20.00"));
		String match = "Hannah;00.20.00;12.00.00;12.20.00;Flera starttider? 12.01.00 12.02.00";
		assertEquals(match, marathonContestant.toString(new MarathonRace(new Database())));
	}
	
	@Test
	public void testToStringSeveralFinishTimes()
	{
		marathonContestant.addStartTime(new Time("12.00.00"));
		marathonContestant.addFinishTime(new Time("12.20.00"));
		marathonContestant.addFinishTime(new Time("12.21.00"));
		marathonContestant.addFinishTime(new Time("12.22.00"));
		String match = "Hannah;00.20.00;12.00.00;12.20.00;Flera måltider? 12.21.00 12.22.00";
		assertEquals(match, marathonContestant.toString(new MarathonRace(new Database())));
	}
	
	@Test
	public void testToStringImpossibleTotalTime()
	{
		marathonContestant.addStartTime(new Time("12.00.00"));
		marathonContestant.addFinishTime(new Time("12.01.00"));
		String match = "Hannah;00.01.00;12.00.00;12.01.00;Omöjlig totaltid?";
		assertEquals(match, marathonContestant.toString(new MarathonRace(new Database())));
	}
}
