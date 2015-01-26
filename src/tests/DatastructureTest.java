package tests;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.DataStructure;
import register.model.Time;
import static org.junit.Assert.*;

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
	public void testGetAllEntries() {
		
		Time time1 = new Time("10.15.34", "17.65.12");
		Time time2 = new Time("10.15.34", "17.65.12");
		Time time3 = new Time("10.15.34", "17.65.12");
		data.addEntry("1", time1);
		data.addEntry("2", time2);
		data.addEntry("3", time3);
		
		Map<String, Time> entries = data.getAllEntries();
		assertTrue(entries.get("1").equals(time1));
		assertTrue(entries.get("2").equals(time2));
		assertTrue(entries.get("3").equals(time3));
		assertEquals(entries.get("4"), null);
	}
	
	@Test
	public void testAddEntry()
	{
		data.addEntry("1", new Time("10.15.34", "17.65.12"));
	}

}
