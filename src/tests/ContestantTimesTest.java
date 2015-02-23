package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gui.model.ContestantTimes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sorter.model.Time;

public class ContestantTimesTest {
	File nameFile = new File("testfiles/RegistrationTestFiles/namn.txt");
	File timeFile = new File("testfiles/RegistrationTestFiles/times.txt");
	ContestantTimes times;

	@Before
	public void setUp() {
		timeFile.delete();
		try {
			timeFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		times = new ContestantTimes(nameFile, timeFile);
	}

	@After
	public void tearDown() {
		timeFile.delete();
		times = null;
	}

	@Test
	public void testAddOneTime() {
		String expectedTime = Time.getCurrentTime();
		times.addTime("1", expectedTime);
		Map<String, ArrayList<String>> entries = times.getTimes();
		ArrayList<String> conTimes = entries.get("1");
		String actualTime = conTimes.get(0);
		assertEquals(expectedTime, actualTime);
	}

	@Test
	public void testAddMultipleTimes() {
		String t1 = "00.00.00";
		String t2 = "01.00.00";
		String t3 = "02.00.00";
		String t4 = "03.00.00";
		times.addTime("1", t1);
		times.addTime("1", t2);
		times.addTime("2", t3);
		times.addTime("2", t4);
		Map<String, ArrayList<String>> entries = times.getTimes();
		ArrayList<String> c1Times = entries.get("1");
		ArrayList<String> c2Times = entries.get("2");

		assertEquals(t1, c1Times.get(0));
		assertEquals(t2, c1Times.get(1));
		assertEquals(t3, c2Times.get(0));
		assertEquals(t4, c2Times.get(1));
	}

	@Test
	public void testPreRegisterTime() {
		String t = "00.00.00";
		times.preRegister(t);
		Map<String, ArrayList<String>> entries = times.getTimes();
		ArrayList<String> c1Times = entries.get("Pre-registered time");
		assertEquals(t, c1Times.get(0));
		String t2 = "11.11.11";
		times.preRegister(t2);
		entries = times.getTimes();
		c1Times = entries.get("Pre-registered time");
		assertEquals(t2, c1Times.get(0));
	}

	@Test
	public void testRemovePreRegisterTime() {
		String t = "00.00.00";
		times.preRegister(t);
		Map<String, ArrayList<String>> entries = times.getTimes();
		assertTrue(entries.containsKey("Pre-registered time"));
		times.removePreRegisteredTime();
		assertFalse(entries.containsKey("Pre-registered time"));
	}

	@Test
	public void testGetPreRegisterTime() {
		String t = "00.00.00";
		times.preRegister(t);
		assertEquals(t, times.getPreRegisteredTime());
	}

	@Test
	public void testPerformeMassStart() {
		int nbrRegisteredContestants = 5; // From name file
		String t = "00.00.00";
		times.performMassStart(t);
		Map<String, ArrayList<String>> entries = times.getTimes();
		assertEquals(nbrRegisteredContestants, entries.size());
		Set<Entry<String, ArrayList<String>>> entrySet = entries.entrySet();
		for (Entry<String, ArrayList<String>> contestants : entrySet) {
			assertEquals(contestants.getValue().get(0), t);
		}
	}

	@Test
	public void testisRegistered() {
		// Start numbers in namefile
		for (int i = 1; i < 6; i++) {
			assertTrue(times.isRegistered(i + ""));
		}
		assertFalse(times.isRegistered("38"));
	}

	@Test
	public void testReadWriteTimesFromFile() {
		String t1 = "00.00.00";
		String t2 = "11.11.11";
		times.performMassStart(t1);
		times.addTime("6", t1);
		times.addTime("7", t2);
		times.preRegister("t2");
		
		times = new ContestantTimes(nameFile, timeFile);
		Map<String, ArrayList<String>> entries = times.getTimes();
		assertEquals(t1, entries.get("6").get(0));
		assertEquals(t2, entries.get("7").get(0));
		assertEquals(8, entries.size()); // masstart = 5 + 2 + preregistratino 
		assertTrue(entries.containsKey("Pre-registered time"));
	}
}
