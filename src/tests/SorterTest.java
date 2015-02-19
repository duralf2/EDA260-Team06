package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import register.model.AbstractContestant;
import register.model.Configuration;
import register.model.Database;
import sorter.Sorter;

public class SorterTest extends AbstractAcceptanceTest {
	private Sorter sorter;
	private File startTime;
	String nameFile;
	private File[] finishTimes;
	private Configuration conf;
	
	@Before
	public void setUp() throws IOException {
		conf = new Configuration();
		finishTimes = new File[2];
		
	}
	
	@Test
	public void testSortLapRace() throws IOException  {
		
		startTime = new File("testfiles/acceptanstest/Iteration2/acceptanstest10/starttider.txt");
		finishTimes[0] = new File("testfiles/acceptanstest/Iteration2/acceptanstest10/maltider1.txt");
		finishTimes[1] = new File("testfiles/acceptanstest/Iteration2/acceptanstest10/maltider2.txt");
		nameFile = "testfiles/acceptanstest/Iteration2/acceptanstest10/namnfil.txt";	
		
		conf.setProperty(Configuration.KEY_RACE_TYPE, Configuration.VALUE_RACE_LAPS);
		conf.setProperty(Configuration.KEY_MINIMUM_RACE_DURATION, "01.00.00");
		
		Configuration c = AbstractContestant.getConfiguration();
		
		AbstractContestant.setConfiguration(conf);
		
		sorter = new Sorter(new Database(), conf);
		sorter.sortLapTimes(nameFile, startTime, finishTimes);
		
		String s1 = readFileAsString(new File("testfiles/acceptanstest/Iteration2/acceptanstest10/resultat.txt"));
		String s2 = readFileAsString(new File(conf.getProperty(Configuration.KEY_RESULT_FILE_PATH)));
		
		assertEquals(s1,s2);
		
		AbstractContestant.setConfiguration(c);
		
	}
	
	//TODO Finish test
	/*@Test
	public void testSortedResultList() throws IOException {
		
		startTime = new File("testfiles/acceptanstest/Iteration2/acceptanstest18NoClasses/starttider.txt");
		finishTimes[0] = new File("testfiles/acceptanstest/Iteration2/acceptanstest18NoClasses/maltider1.txt");
		finishTimes[1] = new File("testfiles/acceptanstest/Iteration2/acceptanstest18NoClasses/maltider2.txt");
		nameFile = "testfiles/acceptanstest/Iteration2/acceptanstest18NoClasses/namnfil.txt";	
		
		
		conf.setProperty(Configuration.KEY_RACE_TYPE, Configuration.VALUE_RACE_LAPS);
		conf.setProperty(Configuration.KEY_MINIMUM_RACE_DURATION, "01.00.00");
		
		Configuration c = AbstractContestant.getConfiguration();
		
		AbstractContestant.setConfiguration(conf);
		
		sorter = new Sorter(new Database(), conf);
		sorter.sort(nameFile, startTime, finishTimes);
		
		String s1 = readFileAsString(new File("testfiles/acceptanstest/Iteration2/acceptanstest18NoClasses/sortresultat.txt"));
		String s2 = readFileAsString(new File(conf.getProperty(Configuration.KEY_RESULT_FILE_PATH)));
	
		assertEquals(s1,s2);
		
		AbstractContestant.setConfiguration(c);
	}*/
	
	//TODO test marathon race

}
