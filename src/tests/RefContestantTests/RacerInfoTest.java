package tests.RefContestantTests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.*;

import register.model.RacerInfo;

public class RacerInfoTest {
	
	private RacerInfo info;

	@Before
	public void setUp() throws Exception {
		info = new RacerInfo();
	}

	@Test
	public void testPutPull() {
		info.put("asdf", "Honung");
		assertEquals("Honung", info.get("asdf"));
	}

	@Test
	public void testReplacePull() {
		info.put("asdf", "----");
		info.put("asdf", "Honung");
		assertEquals("Honung", info.get("asdf"));
	}

}
