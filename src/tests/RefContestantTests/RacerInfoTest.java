package tests.RefContestantTests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import register.model.RacerInfo;

public class RacerInfoTest {
	
	private RacerInfo info;

	@Before
	public void setUp() throws Exception {
		info = new RacerInfo(new String[]{"StartNr", "Namn", "Klubb"});
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

	@Test
	public void testPullNonExistingData() {
		info.put("asdf", "Honung");
		assertEquals("", info.get("qwerty"));
	}
	
	@Test
	public void testToString()
	{
		info.put("StartNr", "1");
		info.put("Klubb", "Apa");
		assertEquals("1;;Apa;", info.toString());
	}
}
