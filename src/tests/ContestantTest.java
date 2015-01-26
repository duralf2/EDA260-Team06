package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import register.model.Contestant;
import register.model.Time;

public class ContestantTest {
	private Contestant contestant;
	private String name;

	@Before
	public void setUp() throws Exception {
		name = "David";
		contestant = new Contestant(name);
	}

	@Test
	public void testGetName() {
		assertTrue(contestant.getName().equals(name));
	}

	@Test
	public void testSetTime() {
		Time startTime = new Time("10.10.10");
		Time finishTime = new Time("10.10.12");
		contestant.setStartTime(startTime);
		contestant.setFinishTime(finishTime);
		assertEquals(contestant.getStartTime(), startTime);
		assertEquals(contestant.getFinishTime(), finishTime);
	}

	@Test
	public void testEquals() {
		Contestant contestant1 = new Contestant("Testname");
		Contestant contestant2 = new Contestant("Testname");
		assertTrue(contestant1.equals(contestant2));
	}
}