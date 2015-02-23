package tests;

import static org.junit.Assert.assertEquals;
import io.FileReader;
import io.FileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sorter.model.AbstractContestant;
import sorter.model.Configuration;
import sorter.model.ContestantFactory;
import sorter.model.Database;

public class AcceptanceTestStory16 extends AbstractFileComparisonTest {

	private String namesFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16/namnfil.txt";
	private String startTimesFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16/starttider.txt";
	private String finishTimesFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16/maltider.txt";
	private String resultFilepath = "testfiles/acceptanstest/Iteration2/acceptanstest16/resultat.txt";
	private File outfile;
	private FileWriter fw;
	private Configuration config;
	
	private FileReader reader;

	@Before
	public void setUp() throws IOException {
		outfile = new File("out.txt");
		fw = new FileWriter(outfile);
		config = new Configuration(new File("testfiles/config/lapContestant.ini"));
		reader = new FileReader(new ContestantFactory(config));
		AbstractContestant.setConfiguration(config);
		config.put(Configuration.KEY_SHORTEST_POSSIBLE_TIME, "00.00.00");
		reader = new FileReader(new ContestantFactory(config));
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

		fw.writeResults(config, db, false);

		String printedResult = readFileAsString(outfile);
		String acceptenceResult = readFileAsString(new File(resultFilepath));
		
		assertEquals(acceptenceResult, printedResult);
	}
}
