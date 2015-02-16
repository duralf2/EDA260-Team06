package tests;

import static org.junit.Assert.assertEquals;
import io.FileWriter;
import io.ReadFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.Configuration;
import register.model.ContestantFactory;
import register.model.Database;

public class AcceptanceTestStory16NoClasses {

	private String namesFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16NoClasses/namnfil.txt";
	private String startTimesFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16NoClasses/starttider.txt";
	private String finishTimesFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16NoClasses/maltider.txt";
	private String resultFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16NoClasses/resultat.txt";
	private File outfile;
	
	private ReadFile reader;

	@Before
	public void setUp() throws IOException {
		outfile = new File("out.txt");
		
		Properties properties = new Properties();
		properties.put(Configuration.KEY_RACE_TYPE, Configuration.VALUE_RACE_LAPS);
		properties.put(Configuration.KEY_LAPRACE_DURATION, "01.00.00");
		reader = new ReadFile(new ContestantFactory(properties));
	}

	@After
	public void tearDown() {
		 outfile.delete();
	}

	@Test
	public void testStory16() throws IOException, FileNotFoundException {
		Database db = new Database();
		
		reader.readNames(new File(namesFilepath), db);
		reader.readStartTime(new File(startTimesFilepath), db);
		reader.readFinishTime(new File(finishTimesFilepath), db);

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
