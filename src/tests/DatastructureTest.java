package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

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
	public void tearDown(){
		data = null;
	}

	@Test
	public void testGetAllContestantEntries() {
		
		Contestant contestant1 = new Contestant("17.65.12");
		Contestant contestant2 = new Contestant("17.65.12");
		Contestant contestant3 = new Contestant("17.65.12");
		data.addContestantEntry("1", contestant1);
		data.addContestantEntry("2", contestant2);
		data.addContestantEntry("3", contestant3);
		
		Map<String, Contestant> entries = data.getAllContestantEntries();
		assertTrue(entries.get("1").equals(contestant1));
		assertTrue(entries.get("2").equals(contestant2));
		assertTrue(entries.get("3").equals(contestant3));
		assertEquals(entries.get("4"), null);
	}
	
	@Test
	public void testAddContestantEntry()
	{	
		Contestant contestant =  new Contestant("Karl");
		data.addContestantEntry("1", contestant);
		assertEquals(contestant, data.getAllContestantEntries().get("1"));
	}

}