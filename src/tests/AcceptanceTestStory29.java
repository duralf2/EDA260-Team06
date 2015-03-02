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

import sorter.Sorter;
import sorter.model.AbstractContestant;
import sorter.model.Configuration;
import sorter.model.ContestantFactory;
import sorter.model.Database;

public class AcceptanceTestStory29 extends AbstractFileComparisonTest {

	private String namesFilepath = "testfiles/acceptanstest/Iteration4/acceptanstest29/namnfil.txt";
	private String startTimesFilepath = "testfiles/acceptanstest/Iteration4/acceptanstest29/starttider.txt";
	private String finishTimesFilepath1 = "testfiles/acceptanstest/Iteration4/acceptanstest29/maltider1.txt";
	private String finishTimesFilepath2 = "testfiles/acceptanstest/Iteration4/acceptanstest29/maltider2.txt";
	private String resultFilepath = "testfiles/acceptanstest/Iteration4/acceptanstest29/resultat.html";
	private String resultSortedFilepath = "testfiles/acceptanstest/Iteration4/acceptanstest29/resultatSorterat.html";
	private File outfile;
	private FileWriter fw;
	private Configuration config;
	
	private FileReader reader;

	@Before
	public void setUp() throws IOException {
		outfile = new File("out.html");
		fw = new FileWriter(outfile);
		config = new Configuration(new File("testfiles/config/lapContestant.ini"));
		reader = new FileReader(new ContestantFactory(config));
		AbstractContestant.setConfiguration(config);
		config.put(Configuration.KEY_START_TIME_LIMIT, "00.59.00");
		config.put(Configuration.KEY_RESULT_FORMAT, Configuration.VALUE_FORMAT_HTML);
		reader = new FileReader(new ContestantFactory(config));
	}

	@After
	public void tearDown() {
		 outfile.delete();
	}

	@Test
	public void testStory29() throws IOException, FileNotFoundException {
		Database db = new Database();
		
		reader.readNames(new File(namesFilepath), db);
		reader.readStartTime(new File(startTimesFilepath), db);
		reader.readFinishTime(new File(finishTimesFilepath1), db);
		reader.readFinishTime(new File(finishTimesFilepath2), db);

		fw.writeResults(config, db, false);

		String printedResult = readFileAsString(outfile);
		String acceptenceResult = readFileAsString(new File(resultFilepath));
		
		assertEquals(acceptenceResult, printedResult);
	}
	

	@Test
	public void testStory29Sorted() throws IOException, FileNotFoundException {
		config.put(Configuration.KEY_RESULT_SORTED, "true");
		config.put(Configuration.KEY_RESULT_FILE_PATH, outfile.getAbsolutePath());
		
		Database db = new Database();
		
		reader.readNames(new File(namesFilepath), db);
		reader.readStartTime(new File(startTimesFilepath), db);
		reader.readFinishTime(new File(finishTimesFilepath1), db);
		reader.readFinishTime(new File(finishTimesFilepath2), db);
		
		Sorter sorter = new Sorter(new Database(), config);
		sorter.sort(new File(namesFilepath), new File[] { new File(startTimesFilepath) }, new File[] { new File(finishTimesFilepath1), new File(finishTimesFilepath2) });

		String printedResult = readFileAsString(outfile);
		String acceptenceResult = readFileAsString(new File(resultSortedFilepath));
		
		assertEquals(acceptenceResult, printedResult);
	}
}
