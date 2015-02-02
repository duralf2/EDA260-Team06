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

import register.model.DataStructure;

public class AcceptanceTestStory16 {

	private String namesFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16NoClasses/namnfil.txt";
	private String startTimesFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16NoClasses/starttider.txt";
	private String finishTimesFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16NoClasses/maltider.txt";
	private String resultFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16NoClasses/resultat.txt";
	private File outfile;

	@Before
	public void setUp() {
		outfile = new File("out.txt");
	}

	@After
	public void tearDown() {
		// outfile.delete();
	}

	@Test
	public void testMergeTimes() throws IOException, FileNotFoundException {
		// Acceptance test 16
		DataStructure ds = new DataStructure();
		ReadFile.readNames(new File(namesFilepath), ds);
		ReadFile.readStartTime(new File(startTimesFilepath), ds);
		ReadFile.readFinishTime(new File(finishTimesFilepath), ds);

		PrintWriter pw = new PrintWriter(outfile);
		FileWriter.writeResult(pw, ds);

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
			fileContents += currentLine.replace("\\s+", "");
			currentLine = reader.readLine();
		}

		reader.close();

		return fileContents;
	}

}
