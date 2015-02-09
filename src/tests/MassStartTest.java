package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Collection;

import io.ReadFile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.logic.Register;
import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

public class MassStartTest {
	private File namefile = new File("testfiles/acceptanstest/Iteration2/acceptanstest11/namnfil.txt");
	private File startfile = new File("testfiles/acceptanstest/Iteration2/acceptanstest11/starttider.txt");
	private File outfile = new File("data/testoutfile.txt");
	
	private DataStructure ds;
	private Register reg;
	@Before
	public void setUp() throws Exception {
		ds = new DataStructure();
		ReadFile.readNames(namefile, ds);
		ReadFile.readStartTime(startfile, ds);
		// Test mass start
		
		reg = new Register(ds);	
	}

	@After
	public void tearDown() {
		outfile.delete();
	}

	@Test
	public void testStart() {
		// Test that all contestants have the same start time
		//reg.performMassStart(outfile);
		Time t = ds.getAllContestantEntries().values().iterator().next().getStartTimes().getLast();
		for(Contestant c : ds.getAllContestantEntries().values()) {
			assertTrue(c.getStartTimes().getLast().equals(t));
		}
	}

	@Test
	public void testRefresh() throws Exception {
		// Test reloading of result file
		ReadFile.readStartTime(startfile, ds);
		Collection<Contestant> coll = ds.getAllContestantEntries().values();
		reg.refreshEntryList();
		assertTrue(coll.equals(ds.getAllContestantEntries().values()));
	}

}
