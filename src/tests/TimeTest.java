package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import register.model.Time;

public class TimeTest {
	private Time t1;
	private Time t2;
	private Time t3;

	@Before
	public void setUp() throws Exception {
		t1 = new Time("10.15.34");
		t2 = new Time("12.14.39");
		t3 = new Time("09.12.31");
	}

	@Test
	public void testToString() {
		assertEquals("10.15.34", t1.toString());
	}

	@Test
	public void testTimeFormat() {
		new Time("01.03.04");
	}

	@Test
	public void testGetTotalTime() {
		assertEquals("01.59.05", Time.getTotalTime(t1, t2).toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetTotalTimeNegativeTime() throws IllegalArgumentException {
		Time.getTotalTime(t1, t3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidConstructorParameters() throws IllegalArgumentException {
		new Time("isdjf");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongNumbers() throws IllegalArgumentException {
		new Time("11.64.77");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongHours() throws IllegalArgumentException {
		new Time("100.55.34");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongFormat() throws IllegalArgumentException {
		new Time("2.3.49");
	}

	@Test
	public void testEquals() {
		String st = "12.01.12";
		assertTrue(new Time(st).equals(new Time(st)));
	}

	@Test
	public void testEqualsNotTime() {
		String st = "12.01.12";
		assertFalse(new Time(st).equals(null));
	}

    @Test
    public void testCompare() {
        Time t1 = new Time("12.01.01");
        Time t2 = new Time("12.01.01");
        assertTrue(t1.compareTo(t2) == 0);

        t1 = new Time("12.12.05");
        t2 = new Time("13.02.02");
        assertTrue(t1.compareTo(t2) < 0);
        
        t2 = new Time("12.12.05");
        t1 = new Time("13.02.02");
        assertTrue(t1.compareTo(t2) > 0);
    }
    
    @Test
    public void testAddTimes(){
    	Time start = new Time("12.01.01");
        Time finish = new Time("13.02.02");
        assertEquals(finish, start.add(new Time("01.01.01")));
        finish = new Time("15.07.00");
        assertEquals(finish, start.add(new Time("03.05.59")));
        finish = new Time("00.07.00");
        assertEquals(finish, start.add(new Time("12.05.59")));
    }
}
