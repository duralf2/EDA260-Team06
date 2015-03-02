package tests.RefContestantTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sorter.model.AbstractContestant;
import sorter.model.ContestantProperties;
import sorter.model.Database;
import sorter.model.MarathonContestant;
import sorter.model.Time;

public class DatabaseTest {

	private Database db;
	private ContestantProperties prop;
	
	@Before
	public void setUp() throws Exception {
		db = new Database();
		prop = new ContestantProperties(new String[]{});
	}

	@After
	public void tearDown() {
		db = null;
	}

	@Test
	public void testGetAllContestantEntries() {
		AbstractContestant contestant1 = new MarathonContestant(prop);
		AbstractContestant contestant2 = new MarathonContestant(prop);
		AbstractContestant contestant3 = new MarathonContestant(prop);
		db.addContestantEntry("1", contestant1);
		db.addContestantEntry("2", contestant2);
		db.addContestantEntry("3", contestant3);

		Map<String, AbstractContestant> entries = db.getAllContestantEntries();
		assertTrue(entries.get("1").equals(contestant1));
		assertTrue(entries.get("2").equals(contestant2));
		assertTrue(entries.get("3").equals(contestant3));
		assertEquals(null, entries.get("4"));
	}

	@Test
	public void testAddContestantEntry() {
		AbstractContestant contestant = new MarathonContestant(prop);
		db.addContestantEntry("1", contestant);
		assertEquals(contestant, db.getContestant("1"));
	}

	@Test
	public void testRemoveContestantEntry() {
		AbstractContestant contestant = new MarathonContestant(prop);
		db.addContestantEntry("1", contestant);
		assertEquals(contestant, db.removeContestant("1"));
		assertEquals(null, db.getContestant("1"));
	}

	@Test
	public void testEquals() {
		Database ds2 = new Database();
		AbstractContestant contestant = new MarathonContestant(prop);
		contestant.addStartTime(new Time("12.00.00"));
		ds2.addContestantEntry("1", contestant);
		db.addContestantEntry("1", contestant);

		assertTrue(db.equals(ds2));
	}

	@Test
	public void testEqualsNotDatastructure() {
		assertFalse(db.equals(new Scanner(System.in)));
	}
	
	@Test
	public void testClearContestantEntries(){
		db.addContestantEntry("1", new MarathonContestant(prop));
		db.clearContestantEntries();
		assertEquals(0, db.getAllContestantEntries().size());
	}


	@Test
	public void testContestantColumnNames() {
		String[] columns = new String[] {"c1", "c2", "c3"};
		db.setContestantColumnNames(columns);
		assertTrue(columns.equals(db.getContestantColumnNames()));
	}
}