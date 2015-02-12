package tests;

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
import register.model.Database;
import register.model.Time;

public class AcceptanceTestStory6 extends TestCase {
	private String namesFilepath = "testfiles/acceptanstest/acceptanstest6/namnfil.txt";
	private String startTimesFilepath = "testfiles/acceptanstest/acceptanstest6/starttider.txt";
	private String finishTimesFilepath = "testfiles/acceptanstest/acceptanstest6/maltider.txt";
	private String resultFilepath = "testfiles/acceptanstest/acceptanstest6/resultat.txt";
	private File outfile;
	Database db;
	@Before
	public void setUp() throws IOException {
		db = new Database();
		
		outfile = new File("out.txt");
	}

	@After
	public void tearDown() {
		outfile.delete();
	}

	@Test
	public void testMergeTimes() throws IOException, FileNotFoundException {
		Database db = new Database();
		ReadFile.readNames(new File(namesFilepath), db);
		ReadFile.readStartTime(new File(startTimesFilepath), db);
		ReadFile.readFinishTime(new File(finishTimesFilepath), db, new Time("13.00.00"));

		PrintWriter pw = new PrintWriter(outfile);
		FileWriter.writeResult(pw, db);

		Database dbOut = new Database();
		ReadFile.readResult(outfile, dbOut);

		Database dsCorrect = new Database();
		ReadFile.readResult(new File(resultFilepath), dsCorrect);

		assertTrue(dbOut.equals(dsCorrect));
	}

	@Test
	public void testFileloading() throws IOException, FileNotFoundException {
		Database db1 = new Database();
		ReadFile.readResult(new File(resultFilepath), db1);

		Database db2 = new Database();
		ReadFile.readResult(new File(resultFilepath), db2);

		assertTrue(db1.equals(db2));
	}

	@Test
	public void testFileWriterWriteResult() throws IOException {
		File result = new File("result.txt");
		FileOutputStream fos = new FileOutputStream(result);

		PrintWriter pw2 = new PrintWriter(fos);

		FileWriter.writeResult(pw2, db);

		Scanner sc2 = new Scanner(result);
		Scanner sc3 = new Scanner(new File(
				"testfiles/acceptanstest/acceptanstest6/resultat.txt"));
		while (sc2.hasNext()) {
			assertEquals(sc3.nextLine(), sc2.nextLine());
		}
		result.delete();
	}
}
