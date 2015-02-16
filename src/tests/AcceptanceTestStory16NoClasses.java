package tests;

import static org.junit.Assert.assertEquals;
import io.FileReader;
import io.FileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.Configuration;
import register.model.ContestantFactory;
import register.model.Database;

public class AcceptanceTestStory16NoClasses extends AbstractAcceptanceTest {

	private String namesFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16NoClasses/namnfil.txt";
	private String startTimesFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16NoClasses/starttider.txt";
	private String finishTimesFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16NoClasses/maltider.txt";
	private String resultFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16NoClasses/resultat.txt";
	private File outfile;
	
	private FileReader reader;

	@Before
	public void setUp() throws IOException {
		outfile = new File("out.txt");
		
		Properties properties = new Properties();
		properties.put(Configuration.KEY_RACE_TYPE, Configuration.VALUE_RACE_LAPS);
		properties.put(Configuration.KEY_MINIMUM_RACE_DURATION, "01.00.00");
		reader = new FileReader(new ContestantFactory(properties));
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
}