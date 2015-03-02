package tests;

import static org.junit.Assert.*;
import gui.model.ContestantTimes;
import gui.model.TimeRegistrationHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TimeRegistrationHandlerTest {
	File nameFile = new File("testfiles/RegistrationTestFiles/namn.txt");
	File timeFile = new File("testfiles/RegistrationTestFiles/atimes.txt");
	ContestantTimes times;
	TimeRegistrationHandler registrationhandler;

	@Before
	public void setUp() throws Exception {
		timeFile.delete();
		try {
			timeFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		times = new ContestantTimes(nameFile, timeFile);
		registrationhandler = new TimeRegistrationHandler(times);
	}

	@After
	public void tearDown() throws Exception {
		timeFile.delete();
		times = null;
		registrationhandler = null;
	}

	@Test
	public void testRegisterStartNumber() {
		assertTrue(registrationhandler.register("1"));

		Map<String, ArrayList<String>> entries = times.getTimes();
		ArrayList<String> conTimes = entries.get("1");
		assertTrue(entries.containsKey("1"));
		assertEquals(1, conTimes.size());
	}

	@Test
	public void testRegisterMassStart() {
		assertTrue(registrationhandler.register("*"));
		Map<String, ArrayList<String>> entries = times.getTimes();
		Set<Entry<String, ArrayList<String>>> entrySet = entries.entrySet();
		for (Entry<String, ArrayList<String>> contestants : entrySet) {
			assertEquals(1, contestants.getValue().size());
		}
	}

	@Test
	public void testPreRegister() {
		registrationhandler.preRegister();
		Map<String, ArrayList<String>> entries = times.getTimes();
		ArrayList<String> conTimes = entries.get("Pre-registered time");
		assertTrue(entries.containsKey("Pre-registered time"));
		assertEquals(1, conTimes.size());
	}

	@Test
	public void testRemovePreRegister() {
		registrationhandler.preRegister();
		Map<String, ArrayList<String>> entries = times.getTimes();
		registrationhandler.removePreRegisteredTime();
		assertFalse(entries.containsKey("Pre-registered time"));
	}

	@Test
	public void testAssignStartNumberToPreRegistration() {
		registrationhandler.preRegister();
		Map<String, ArrayList<String>> entries = times.getTimes();
		String t = times.getPreRegisteredTime();
		assertTrue(registrationhandler.assignPreRegistrationStartNumber("356"));
		assertTrue(entries.containsKey("356"));
		assertEquals(t, entries.get("356").get(0));
		assertFalse(entries.containsKey("Pre-registered time"));
	}

	@Test
	public void testRegisterInvalid() {
		assertFalse(registrationhandler.assignPreRegistrationStartNumber("a"));
		Map<String, ArrayList<String>> entries = times.getTimes();
		assertEquals(5, entries.size());
		String error = registrationhandler.getLastError();
		assertEquals("Invalid startnumber, must be a number.", error);
	}

	@Test
	public void testRegisterInvalidPreRegistration() {
		registrationhandler.assignPreRegistrationStartNumber("111");
		Map<String, ArrayList<String>> entries = times.getTimes();
		assertEquals(5, entries.size());
		assertFalse(entries.containsKey("111"));
		String error = registrationhandler.getLastError();
		assertEquals("No preregistered time set.", error);
	}
}
