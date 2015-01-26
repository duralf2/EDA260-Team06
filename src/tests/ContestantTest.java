package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import register.model.Contestant;

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

}
