package tests;

import static org.junit.Assert.assertEquals;
import io.FileWriter;
import io.ReadFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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
	DataStructure ds;
	@Before
	public void setUp() throws IOException {
		ds = new DataStructure();
		
		File maltider = new File(
				"testfiles/acceptanstest/acceptanstest6/maltider.txt");
		File namn = new File(
				"testfiles/acceptanstest/acceptanstest6/namnfil.txt");
		File starttider = new File(
				"testfiles/acceptanstest/acceptanstest6/starttider.txt");
		
		ReadFile.readNames(namn, ds);
		ReadFile.readStartTime(starttider, ds);
		ReadFile.readFinishTime(maltider, ds);
		
		outfile = new File("out.txt");
	}

	@After
	public void tearDown() {
		//outfile.delete();
	}

	@Test
	public void testMergeTimes() throws IOException, FileNotFoundException {
		// Acceptance test 5
		DataStructure ds = new DataStructure();
		ReadFile.readNames(new File(namesFilepath), ds);
		ReadFile.readStartTime(new File(startTimesFilepath), ds);
		ReadFile.readFinishTime(new File(finishTimesFilepath), ds);

		PrintWriter pw = new PrintWriter(outfile);
		FileWriter.writeResult(pw, ds);

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

	@Test
	public void testFileWriterWriteResult() throws IOException {
		File result = new File("result.txt");
		FileOutputStream fos = new FileOutputStream(result);

		PrintWriter pw2 = new PrintWriter(fos);

		FileWriter.writeResult(pw2, ds);

		Scanner sc2 = new Scanner(result);
		Scanner sc3 = new Scanner(new File(
				"testfiles/acceptanstest/acceptanstest6/resultat.txt"));
		while (sc2.hasNext()) {
			assertEquals(sc3.nextLine(), sc2.nextLine());
		}
	}

	@Test
	public void testFileWriterWriteStartTimes() throws IOException{
		Scanner sc2 = new Scanner(new File(
				"testfiles/acceptanstest/acceptanstest6/starttider.txt"));
		FileOutputStream fos = new FileOutputStream(outfile);
		PrintWriter pw = new PrintWriter(fos);
		
		FileWriter.writeStartTimes(pw, ds);
		
		Scanner sc3 = new Scanner(outfile);
		while (sc2.hasNext()) {
			assertEquals(sc2.nextLine(), sc3.nextLine());
		}
	}
	
	@Test
	public void testFileWriterWriteFinishTimes() throws IOException{
		Scanner sc4 = new Scanner(new File(
				"testfiles/acceptanstest/acceptanstest6/maltider.txt"));
		FileOutputStream fos = new FileOutputStream(outfile);
		PrintWriter pw = new PrintWriter(fos);
		
		FileWriter.writeFinishTimes(pw, ds);
		
		Scanner sc5 = new Scanner(outfile);
		while (sc4.hasNext()) {
			assertEquals(sc4.nextLine(), sc5.nextLine());
		}
	}
}
