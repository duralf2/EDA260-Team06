package tests;

import static org.junit.Assert.*;
import io.ReadFile;

import java.io.File;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.logic.Register;
import register.model.AbstractContestant;
import register.model.Contestant;
import register.model.Database;
import register.model.Time;

public class MassStartTest {
	private File namefile = new File("testfiles/acceptanstest/Iteration2/acceptanstest11/namnfil.txt");
	private File startfile = new File("testfiles/acceptanstest/Iteration2/acceptanstest11/starttider.txt");
	private File outfile = new File("data/testoutfile.txt");
	
	private Database db;
	private Register reg;
	@Before
	public void setUp() throws Exception {
		db = new Database();
		ReadFile.readNames(namefile, db);
		ReadFile.readStartTime(startfile, db);
		
		reg = new Register(db);	
	}

	@After
	public void tearDown() {
		outfile.delete();
	}

	@Test
	public void testStart() {
		// Test that all contestants have the same start time
		//reg.performMassStart(outfile);
		Time t = db.getAllContestantEntries().values().iterator().next().getStartTimes().getLast();
		for(AbstractContestant c : db.getAllContestantEntries().values()) {
			assertTrue(c.getStartTimes().getLast().equals(t));
		}
	}
}
