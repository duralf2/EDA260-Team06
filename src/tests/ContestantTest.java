package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import register.model.Contestant;
import register.model.Time;

public class ContestantTest {
	private Contestant contestant;
	private Time startTime;
	private Time finishTime;

	@Before
	public void setUp() throws Exception {
		contestant = new Contestant("David");
		startTime = new Time("10.10.10");
		finishTime = new Time("10.10.12");
	}

	@Test
	public void testGetName() {
		assertTrue(contestant.getName().equals("David"));
	}

	@Test
	public void testSetTime() {
		contestant.setStartTime(startTime);
		contestant.setFinishTime(finishTime);
		assertEquals(contestant.getStartTime(), startTime);
		assertEquals(contestant.getFinishTime(), finishTime);
	}

	
	@Test
	public void testSetName() {
		contestant.setName("Bob");
		assertEquals("Bob", contestant.getName());
	}
	
	@Test
	public void testTotalTime() {
		contestant.setStartTime(startTime);
		contestant.setFinishTime(finishTime);
		assertEquals("00.00.02", contestant.getTotalTime());
	}


	@Test
	public void testEquals() {
		Contestant contestant1 = new Contestant("Testname");
		Contestant contestant2 = new Contestant("Testname");
		assertTrue(contestant1.equals(contestant2));
	}
}
