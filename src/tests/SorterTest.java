package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;

import sorter.Sorter;
import sorter.SorterSetUp;
import sorter.model.AbstractContestant;
import sorter.model.Configuration;
import sorter.model.ContestantProperties;
import sorter.model.Database;
import sorter.model.LapContestant;

public class SorterTest extends AbstractFileComparisonTest {
	private SorterSetUp sorter;
	private File startTime;
	private File nameFile;
	private File[] finishTimes;
	private Configuration conf;

	@Before
	public void setUp() throws IOException {
		conf = new Configuration();
		finishTimes = new File[2];

	}

	@Test
	public void testSortLapRace() throws IOException {

		startTime = new File(
				"testfiles/acceptanstest/Iteration2/acceptanstest10/starttider.txt");
		finishTimes[0] = new File(
				"testfiles/acceptanstest/Iteration2/acceptanstest10/maltider1.txt");
		finishTimes[1] = new File(
				"testfiles/acceptanstest/Iteration2/acceptanstest10/maltider2.txt");
		nameFile = new File(
				"testfiles/acceptanstest/Iteration2/acceptanstest10/namnfil.txt");

		conf.setProperty(Configuration.KEY_RACE_TYPE,
				Configuration.VALUE_RACE_LAPS);
		conf.setProperty(Configuration.KEY_SHORTEST_POSSIBLE_TIME, "00.00.00");

		Configuration c = AbstractContestant.getConfiguration();

		AbstractContestant.setConfiguration(conf);

		sorter = new SorterSetUp(new Database(), conf);
		sorter.sortLapTimes(nameFile, new File[] { startTime }, finishTimes);

		String s1 = readFileAsString(new File(
				"testfiles/acceptanstest/Iteration2/acceptanstest10/resultat.txt"));
		String s2 = readFileAsString(new File(
				conf.getProperty(Configuration.KEY_RESULT_FILE_PATH)));

		assertEquals(s1, s2);

		AbstractContestant.setConfiguration(c);

	}

	@Test
	public void testSortedResultList() throws IOException {
		startTime = new File(
				"testfiles/acceptanstest/Iteration2/acceptanstest18NoClasses/starttider.txt");
		finishTimes[0] = new File(
				"testfiles/acceptanstest/Iteration2/acceptanstest18NoClasses/maltider1.txt");
		finishTimes[1] = new File(
				"testfiles/acceptanstest/Iteration2/acceptanstest18NoClasses/maltider2.txt");
		nameFile = new File(
				"testfiles/acceptanstest/Iteration2/acceptanstest18NoClasses/namnfil.txt");

		conf.setProperty(Configuration.KEY_RACE_TYPE,
				Configuration.VALUE_RACE_LAPS);
		conf.setProperty(Configuration.KEY_SHORTEST_POSSIBLE_TIME, "01.00.00");
		conf.setProperty(Configuration.KEY_RESULT_SORTED, "true");

		Configuration c = AbstractContestant.getConfiguration();

		AbstractContestant.setConfiguration(conf);

		sorter = new SorterSetUp(new Database(), conf);
		sorter.sort(nameFile, new File[] { startTime }, finishTimes);

		String s1 = readFileAsString(new File(
				"testfiles/acceptanstest/Iteration2/acceptanstest18NoClasses/sortresultat.txt"));
		String s2 = readFileAsString(new File(
				conf.getProperty(Configuration.KEY_RESULT_FILE_PATH)));

		assertEquals(s1, s2);

		AbstractContestant.setConfiguration(c);
	}

	@Test
	public void testSortedResultListWithClasses() throws IOException {
		System.out.println("test start");
		startTime = new File(
				"testfiles/acceptanstest/Iteration2/acceptanstest18/starttider.txt");
		File startTime2 = new File("testfiles/invalidTimeFile.txt");
		finishTimes[0] = new File(
				"testfiles/acceptanstest/Iteration2/acceptanstest18/maltider1.txt");
		finishTimes[1] = new File(
				"testfiles/acceptanstest/Iteration2/acceptanstest18/maltider2.txt");
		nameFile = new File(
				"testfiles/acceptanstest/Iteration2/acceptanstest18/namnfil.txt");

		conf.setProperty(Configuration.KEY_RACE_TYPE,
				Configuration.VALUE_RACE_LAPS);
		conf.setProperty(Configuration.KEY_SHORTEST_POSSIBLE_TIME, "01.00.00");

		Configuration c = AbstractContestant.getConfiguration();

		AbstractContestant.setConfiguration(conf);

		sorter = new SorterSetUp(new Database(), conf);
		sorter.sort(nameFile, new File[] { startTime, startTime2 }, finishTimes);

		String s1 = readFileAsString(new File(
				"testfiles/acceptanstest/Iteration2/acceptanstest18/sortresultat.txt"));
		s1 += "Icke existerande startnummer\nStartNr; Namn; Klubb; MC; #Varv; TotalTid\n99; ; ; ; 0; --.--.--\n";
		String s2 = readFileAsString(new File(
				conf.getProperty(Configuration.KEY_RESULT_FILE_PATH)));
		assertEquals(s1, s2);

		AbstractContestant.setConfiguration(c);
	}

	@Test
	public void testVarvlopptidFulltSorted() throws IOException {
		finishTimes = new File[1];
		startTime = new File(
				"testfiles/acceptanstest/Iteration2/varvlopptid-fullt/starttider.txt");
		finishTimes[0] = new File(
				"testfiles/acceptanstest/Iteration2/varvlopptid-fullt/maltider.txt");
		nameFile = new File(
				"testfiles/acceptanstest/Iteration2/varvlopptid-fullt/namnfil.txt");

		conf.setProperty(Configuration.KEY_RACE_TYPE,
				Configuration.VALUE_RACE_LAPS);
		conf.setProperty(Configuration.KEY_SHORTEST_POSSIBLE_TIME, "00.00.00");
		conf.setProperty(Configuration.KEY_START_TIME_LIMIT, "03.00.00");
		conf.setProperty(Configuration.KEY_RESULT_SORTED, "true");

		Configuration c = AbstractContestant.getConfiguration();

		AbstractContestant.setConfiguration(conf);

		sorter = new SorterSetUp(new Database(), conf);
		sorter.sort(nameFile, new File[] { startTime }, finishTimes);

		String s1 = readFileAsString(new File(
				"testfiles/acceptanstest/Iteration2/varvlopptid-fullt/sortresultat.txt"));
		String s2 = readFileAsString(new File(
				conf.getProperty(Configuration.KEY_RESULT_FILE_PATH)));

		assertEquals(s1, s2);

		AbstractContestant.setConfiguration(c);
	}
<<<<<<< HEAD
=======
	
		@Test
		public void testSortedResultListWithClasses() throws IOException {
			startTime = new File("testfiles/acceptanstest/Iteration2/acceptanstest18/starttider.txt");
			finishTimes[0] = new File("testfiles/acceptanstest/Iteration2/acceptanstest18/maltider1.txt");
			finishTimes[1] = new File("testfiles/acceptanstest/Iteration2/acceptanstest18/maltider2.txt");
			nameFile = new File("testfiles/acceptanstest/Iteration2/acceptanstest18/namnfil.txt");	
			
			conf.setProperty(Configuration.KEY_RACE_TYPE, Configuration.VALUE_RACE_LAPS);
			conf.setProperty(Configuration.KEY_SHORTEST_POSSIBLE_TIME, "01.00.00");
			conf.setProperty(Configuration.KEY_RESULT_SORTED, "true");
			
			Configuration c = AbstractContestant.getConfiguration();
			
			AbstractContestant.setConfiguration(conf);
			
			sorter = new SorterSetUp(new Database(), conf);
			sorter.sort(nameFile, new File[] { startTime }, finishTimes);
			
			String s1 = readFileAsString(new File("testfiles/acceptanstest/Iteration2/acceptanstest18/sortresultat.txt"));
			String s2 = readFileAsString(new File(conf.getProperty(Configuration.KEY_RESULT_FILE_PATH)));
		
			assertEquals(s1,s2);
			
			AbstractContestant.setConfiguration(c);
		}
		
		@Test
		public void testVarvlopptidFulltSorted() throws IOException {
			finishTimes = new File[1];
			startTime = new File("testfiles/acceptanstest/Iteration2/varvlopptid-fullt/starttider.txt");
			finishTimes[0] = new File("testfiles/acceptanstest/Iteration2/varvlopptid-fullt/maltider.txt");
			nameFile = new File("testfiles/acceptanstest/Iteration2/varvlopptid-fullt/namnfil.txt");	
			
			conf.setProperty(Configuration.KEY_RACE_TYPE, Configuration.VALUE_RACE_LAPS);
			conf.setProperty(Configuration.KEY_SHORTEST_POSSIBLE_TIME, "00.00.00");
			conf.setProperty(Configuration.KEY_START_TIME_LIMIT, "03.00.00");
			conf.setProperty(Configuration.KEY_RESULT_SORTED, "true");
			
			Configuration c = AbstractContestant.getConfiguration();
			
			AbstractContestant.setConfiguration(conf);
			
			sorter = new SorterSetUp(new Database(), conf);
			sorter.sort(nameFile, new File[] { startTime }, finishTimes);
			
			String s1 = readFileAsString(new File("testfiles/acceptanstest/Iteration2/varvlopptid-fullt/sortresultat.txt"));
			String s2 = readFileAsString(new File(conf.getProperty(Configuration.KEY_RESULT_FILE_PATH)));
		
			assertEquals(s1,s2);
			
			AbstractContestant.setConfiguration(c);
		}
		
		
		@Test
		public void testOneLapOverAnHour() throws IOException{
			startTime = new File("testfiles/SorterTestOverAnHour/starttimesOverAnHour.txt");
			
			File[] finishTime = new File[1]; 
			finishTime[0] = new File("testfiles/SorterTestOverAnHour/finishtimesOverAnHour.txt");
			
			nameFile = new File("testfiles/SorterTestOverAnHour/namnfil.txt");	
			
			conf.setProperty(Configuration.KEY_RACE_TYPE, Configuration.VALUE_RACE_LAPS);
			
			Configuration c = AbstractContestant.getConfiguration();
			conf.setProperty(Configuration.KEY_SHORTEST_POSSIBLE_TIME, "01.00.00");
			conf.setProperty(Configuration.KEY_RESULT_SORTED, "true");
			
			AbstractContestant.setConfiguration(conf);
			
			sorter = new SorterSetUp(new Database(), conf);
			sorter.sort(nameFile, new File[] { startTime }, finishTime);
			
			String s1 = readFileAsString(new File("testfiles/SorterTestOverAnHour/resultsOverAnHour.txt"));
			String s2 = readFileAsString(new File(conf.getProperty(Configuration.KEY_RESULT_FILE_PATH)));
		
			assertEquals(s1,s2);
			
			AbstractContestant.setConfiguration(c);	
		}
	
	//TODO add test marathon race : IS DONE
>>>>>>> 48559bd54ff620180b0e549c313ed9db4475015e

	/*
	 * @Test public void testVarvlopptidFulltUnsorted() throws IOException {
	 * finishTimes = new File[1]; startTime = new
	 * File("testfiles/acceptanstest/Iteration2/varvlopptid-fullt/starttider.txt"
	 * ); finishTimes[0] = new
	 * File("testfiles/acceptanstest/Iteration2/varvlopptid-fullt/maltider.txt"
	 * ); nameFile = new
	 * File("testfiles/acceptanstest/Iteration2/varvlopptid-fullt/namnfil.txt");
	 * 
	 * conf.setProperty(Configuration.KEY_RACE_TYPE,
	 * Configuration.VALUE_RACE_LAPS);
	 * conf.setProperty(Configuration.KEY_SHORTEST_POSSIBLE_TIME, "00.00.00");
	 * conf.setProperty(Configuration.KEY_START_TIME_LIMIT, "03.00.00");
	 * conf.setProperty(Configuration.KEY_RESULT_SORTED, "false");
	 * 
	 * Configuration c = AbstractContestant.getConfiguration();
	 * 
	 * AbstractContestant.setConfiguration(conf);
	 * 
	 * sorter = new Sorter(new Database(), conf); sorter.sort(nameFile, new
	 * File[] { startTime }, finishTimes);
	 * 
	 * String s1 = readFileAsString(new
	 * File("testfiles/acceptanstest/Iteration2/varvlopptid-fullt/resultat.txt"
	 * )); String s2 = readFileAsString(new
	 * File(conf.getProperty(Configuration.KEY_RESULT_FILE_PATH)));
	 * 
	 * assertEquals(s1,s2);
	 * 
	 * AbstractContestant.setConfiguration(c); }
	 */
}
