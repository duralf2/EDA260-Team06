package tests;

import static org.junit.Assert.assertEquals;
import io.FileWriter;
import io.ReadFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
		
		String printedResult = readFileAsString(outfile);
		String acceptenceResult = readFileAsString(new File(resultFilepath));

		assertEquals(acceptenceResult, printedResult);
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


	private String readFileAsString(File file) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));

		String fileContents = "";
		String currentLine = reader.readLine();
		while (currentLine != null) {
			fileContents += currentLine.replace("\\s+", "") + "\n";
			currentLine = reader.readLine();
		}

		reader.close();

		return fileContents;
	}
}
