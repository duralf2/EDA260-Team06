package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import register.model.Time;

public class TimeTest {
	private Time t1;
	private Time t2;

	@Before
	public void setUp() throws Exception {
		t1 = new Time("10.15.34");
		t2 = new Time("12.14.39");
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
		assertEquals("01.59.05", Time.getTotalTime(t1, t2));
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testInvalidConstructorParameters() {
		new Time("isdjf");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testWrongNumbers() {
		new Time("11.64.77");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testWrongHours() {
		new Time("100.55.34");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testWrongFormat() {
		new Time("2.3.49");
	}
	
	
}
