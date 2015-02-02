package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

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
		contestant.addStartTime(startTime);
		contestant.addFinishTime(finishTime);
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
		contestant.addStartTime(startTime);
		contestant.addFinishTime(finishTime);
		assertEquals("00.00.02", contestant.getTotalTime());
	}


	@Test
	public void testEquals() {
		Contestant contestant1 = new Contestant("Testname");
		Contestant contestant2 = new Contestant("Testname");
		assertTrue(contestant1.equals(contestant2));
	}
	
	@Test
	public void testEqualsNotContestant() {
		Contestant contestant1 = new Contestant("Testname");
		ContestantTest test = new ContestantTest();
		assertFalse(contestant1.equals(test));
	}
	
	@Test
	public void testGetStartTimes() {
		Time t = new Time("00.00.02");
		contestant.addStartTime(startTime);
		contestant.addStartTime(t);
		LinkedList<Time> start = contestant.getStartTimes();
		assertEquals(startTime, start.get(0));
		assertEquals(t, start.get(1));
	}
	
	@Test
	public void testGetFinishTimes() {
		Time t = new Time("00.00.02");
		contestant.addFinishTime(startTime);
		contestant.addFinishTime(t);
		LinkedList<Time> finish = contestant.getFinishTimes();
		assertEquals(startTime, finish.get(0));
		assertEquals(t, finish.get(1));
	}

    @Test
    public void testGetlapTimes() {
        Time t = new Time("00.00.02");
        contestant.addLapTime(startTime);
        contestant.addLapTime(t);
        LinkedList<Time> lap = contestant.getLapTimes();
        assertEquals(startTime, lap.get(0));
        assertEquals(t, lap.get(1));
    }
}
