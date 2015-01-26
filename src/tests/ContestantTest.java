package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import register.model.Contestant;
import register.model.Time;

public class ContestantTest {
	private Contestant contestant;

	@Before
	public void setUp() throws Exception {
		contestant = new Contestant("David");
	}

	@Test
	public void testGetName() {
		assertTrue(contestant.getName().equals("David"));
	}

	@Test
	public void testSetTime()
	{
		Time startTime = new Time("10.10.10");
		Time finishTime = new Time("10.10.12");
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
}