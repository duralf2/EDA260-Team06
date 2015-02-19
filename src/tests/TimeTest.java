package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sorter.model.Time;

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
		assertEquals("03.02.08", Time.getTotalTime(t3, t2).toString());
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
		Time t1 = new Time("12.01.12");
		Time t2 = new Time("13.01.12");
		Time t3 = new Time("12.02.12");
		Time t4 = new Time("12.01.13");
		assertTrue(t1.equals(t1));
		assertFalse(t2.equals(t1));
		assertFalse(t3.equals(t1));
		assertFalse(t4.equals(t1));
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
    
    @Test
    public void testApplyMultiplier(){
        assertEquals("20.31.30", new Time("10.15.45").multiply(2).toString());
    }
}
