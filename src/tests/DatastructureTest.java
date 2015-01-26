package tests;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

public class DatastructureTest {

	private DataStructure data;

	@Before
	public void setUp() throws Exception {
		data = new DataStructure();
	}

	@After
	public void tearDown() {
		data = null;
	}

	@Test
	public void testGetAllContestantEntries() {
		Contestant contestant1 = new Contestant("Name1");
		Contestant contestant2 = new Contestant("Name2");
		Contestant contestant3 = new Contestant("Name3");
		data.addContestantEntry("1", contestant1);
		data.addContestantEntry("2", contestant2);
		data.addContestantEntry("3", contestant3);

		Map<String, Contestant> entries = data.getAllContestantEntries();
		assertTrue(entries.get("1").equals(contestant1));
		assertTrue(entries.get("2").equals(contestant2));
		assertTrue(entries.get("3").equals(contestant3));
		assertEquals(null, entries.get("4"));
	}

	@Test
	public void testAddContestantEntry() {
		Contestant contestant = new Contestant("Karl");
		data.addContestantEntry("1", contestant);
		assertEquals(contestant, data.getContestant("1"));
	}

	@Test
	public void testContestantColumnNames() {
		String[] columns = new String[] {"c1", "c2", "c3"};
		data.setContestantColumnNames(columns);
		assertTrue(columns.equals(data.getContestantColumnNames()));
	}

	@Test
	public void testEquals() {
		DataStructure ds2 = new DataStructure();
		Contestant contestant = new Contestant("Testname");
		contestant.setStartTime(new Time("12.00.00"));
		ds2.addContestantEntry("1", contestant);
		data.addContestantEntry("1", contestant);

		assertTrue(data.equals(ds2));
	}

	@Test
	public void testEqualsNotDatastructure() {
		assertFalse(data.equals(new Scanner(System.in)));
	}

}