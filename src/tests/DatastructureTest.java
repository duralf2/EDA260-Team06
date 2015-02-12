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

	private DataStructure ds;

	@Before
	public void setUp() throws Exception {
		ds = new DataStructure();
	}

	@After
	public void tearDown() {
		ds = null;
	}

	@Test
	public void testGetAllContestantEntries() {
		Contestant contestant1 = new Contestant("Name1");
		Contestant contestant2 = new Contestant("Name2");
		Contestant contestant3 = new Contestant("Name3");
		ds.addContestantEntry("1", contestant1);
		ds.addContestantEntry("2", contestant2);
		ds.addContestantEntry("3", contestant3);

		Map<String, Contestant> entries = ds.getAllContestantEntries();
		assertTrue(entries.get("1").equals(contestant1));
		assertTrue(entries.get("2").equals(contestant2));
		assertTrue(entries.get("3").equals(contestant3));
		assertEquals(null, entries.get("4"));
	}

	@Test
	public void testAddContestantEntry() {
		Contestant contestant = new Contestant("Karl");
		ds.addContestantEntry("1", contestant);
		assertEquals(contestant, ds.getContestant("1"));
	}

	@Test
	public void testContestantColumnNames() {
		String[] columns = new String[] {"c1", "c2", "c3"};
		ds.setContestantColumnNames(columns);
		assertTrue(columns.equals(ds.getContestantColumnNames()));
	}

	@Test
	public void testEquals() {
		DataStructure ds2 = new DataStructure();
		Contestant contestant = new Contestant("Testname");
		contestant.addStartTime(new Time("12.00.00"));
		ds2.addContestantEntry("1", contestant);
		ds.addContestantEntry("1", contestant);

		assertTrue(ds.equals(ds2));
	}

	@Test
	public void testEqualsNotDatastructure() {
		assertFalse(ds.equals(new Scanner(System.in)));
	}
	
	@Test
	public void testClearContestantEntries(){
		ds.addContestantEntry("1", new Contestant("Karl"));
		ds.clearContestantEntries();
		assertEquals(0, ds.getAllContestantEntries().size());
		assertTrue(null == ds.getContestantColumnNames());
	}
}