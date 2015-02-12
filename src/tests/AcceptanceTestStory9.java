package tests;

import static org.junit.Assert.*;
import io.FileWriter;
import io.ReadFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.Database;
import register.model.Time;

public class AcceptanceTestStory9 {

	private String namesFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest9/namnfil.txt";
	private String startTimesFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest9/starttider.txt";
	private String finishTimesFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest9/maltider.txt";
	private String resultFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest9/resultat.txt";
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
	public void testStory9() throws IOException, FileNotFoundException {
		Database db = new Database();
		ReadFile.readNames(new File(namesFilepath), db);
		ReadFile.readStartTime(new File(startTimesFilepath), db);
		ReadFile.readFinishTime(new File(finishTimesFilepath), db, new Time("01.00.00"));

		PrintWriter pw = new PrintWriter(outfile);
		FileWriter.writeLapResult(pw, db);

		String printedResult = readFileAsString(outfile);
		String acceptenceResult = readFileAsString(new File(resultFilepath));
		
		assertEquals(acceptenceResult, printedResult);
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
