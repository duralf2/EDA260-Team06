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

import register.model.AbstractContestant;
import register.model.Configuration;
import register.model.ContestantFactory;
import register.model.Database;

public class AcceptanceTestStory6 extends AbstractAcceptanceTest {
	private String namesFilepath = "testfiles/acceptanstest/acceptanstest6/namnfil.txt";
	private String startTimesFilepath = "testfiles/acceptanstest/acceptanstest6/starttider.txt";
	private String finishTimesFilepath = "testfiles/acceptanstest/acceptanstest6/maltider.txt";
	private String resultFilepath = "testfiles/acceptanstest/acceptanstest6/resultat.txt";
	
	private File outfile;
	private FileReader reader;
	private FileWriter fw;
	private Configuration config;
	
	@Before
	public void setUp() throws IOException {
		outfile = new File("out.txt");
		fw = new FileWriter(outfile);

		config = new Configuration(new File("testfiles/config/marathonContestant.ini"));
		reader = new FileReader(new ContestantFactory(config));
		AbstractContestant.setConfiguration(config);
	}

	@After
	public void tearDown() throws IOException {
		outfile.delete();
		AbstractContestant.setConfiguration(new Configuration());
	}

	@Test
	public void testStory6() throws IOException, FileNotFoundException {
		Database db = new Database();
		reader.readNames(new File(namesFilepath), db);
		reader.readStartTime(new File(startTimesFilepath), db);
		reader.readFinishTime(new File(finishTimesFilepath), db);
		
		fw.writeResults(config, db);
		String printedResult = readFileAsString(outfile);
		String acceptenceResult = readFileAsString(new File(resultFilepath));

		assertEquals(acceptenceResult, printedResult);
	}
}
