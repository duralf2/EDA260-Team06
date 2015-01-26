package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import register.model.Time;

public class TimeTest {
	private Time t1;

	@Before
	public void setUp() throws Exception {
		t1 = new Time("10.15.34", "17.65.12");
	}

	@Test
	public void testGetStartTime() {
		assertTrue(t1.getStartTime().equals("10.15.34"));
	}

}
