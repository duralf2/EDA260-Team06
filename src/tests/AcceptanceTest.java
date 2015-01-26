package tests;

import io.IOHandler;
import io.ReadFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.DataStructure;

public class AcceptanceTest extends TestCase {
	private String namesFilepath = "testfiles/acceptanstest/acceptanstest5/namnfil.txt";
	private String startTimesFilepath = "testfiles/acceptanstest/acceptanstest5/starttider.txt";
	private String finishTimesFilepath = "testfiles/acceptanstest/acceptanstest5/maltider.txt";
	private String resultFilepath = "testfiles/acceptanstest/acceptanstest5/resultat.txt";
	private File outfile;

	@Before
	public void setUp() {
		outfile = new File("out.txt");
	}

	@After
	public void tearDown() {
		outfile.delete();
	}
	
	@Test
	public void testMergeTimes() throws IOException, FileNotFoundException {
		// Acceptance test 5
		DataStructure ds = new DataStructure();
		ReadFile.readNames(new File(namesFilepath), ds);
		ReadFile.readStartTime(new File(startTimesFilepath), ds);
		ReadFile.readFinishTime(new File(finishTimesFilepath), ds);
		
		PrintWriter pw = new PrintWriter(outfile);
		IOHandler.writeResult(pw, ds);
		
		DataStructure dsOut = new DataStructure();
		ReadFile.readResult(outfile, dsOut);
		
		DataStructure dsCorrect = new DataStructure();
		ReadFile.readResult(new File(resultFilepath), dsCorrect);
		
		assertTrue(dsOut.equals(dsCorrect));
	}

	@Test
	public void testFileloading() throws IOException, FileNotFoundException {
		DataStructure ds1 = new DataStructure();
		ReadFile.readResult(new File(resultFilepath), ds1);
		
		DataStructure ds2 = new DataStructure();
		ReadFile.readResult(new File(resultFilepath), ds2);
		
		assertTrue(ds1.equals(ds2));
	}
}
