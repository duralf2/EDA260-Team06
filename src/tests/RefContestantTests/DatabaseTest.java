package tests.RefContestantTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.Database;
import register.model.MarathonContestant;
import register.model.AbstractContestant;
import register.model.Time;

public class DatabaseTest {

	private Database ds;

	@Before
	public void setUp() throws Exception {
		ds = new Database();
	}

	@After
	public void tearDown() {
		ds = null;
	}

	@Test
	public void testGetAllContestantEntries() {
		AbstractContestant contestant1 = new MarathonContestant(null);
		AbstractContestant contestant2 = new MarathonContestant(null);
		AbstractContestant contestant3 = new MarathonContestant(null);
		ds.addContestantEntry("1", contestant1);
		ds.addContestantEntry("2", contestant2);
		ds.addContestantEntry("3", contestant3);

		Map<String, AbstractContestant> entries = ds.getAllContestantEntries();
		assertTrue(entries.get("1").equals(contestant1));
		assertTrue(entries.get("2").equals(contestant2));
		assertTrue(entries.get("3").equals(contestant3));
		assertEquals(null, entries.get("4"));
	}

	@Test
	public void testAddContestantEntry() {
		AbstractContestant contestant = new MarathonContestant(null);
		ds.addContestantEntry("1", contestant);
		assertEquals(contestant, ds.getContestant("1"));
	}

	@Test
	public void testEquals() {
		Database ds2 = new Database();
		AbstractContestant contestant = new MarathonContestant(null);
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
		ds.addContestantEntry("1", new MarathonContestant(null));
		ds.clearContestantEntries();
		assertEquals(0, ds.getAllContestantEntries().size());
	}

}