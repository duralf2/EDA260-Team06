package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sorter.SorterMain;
import sorter.model.AbstractContestant;
import sorter.model.Configuration;

public class SorterMainTest extends AbstractFileComparisonTest {
	private Configuration conf;
	
	@Before
	public void setUp() throws IOException {
		conf = new Configuration();
		
	}
	
	
	@Test
	public void testVarvlopptidFulltUnsorted() throws IOException {
		File startTime = new File("testfiles/acceptanstest/Iteration2/varvlopptid-fullt2/startTimes/");
		File finishTime = new File("testfiles/acceptanstest/Iteration2/varvlopptid-fullt2/finishTimes/");
		File nameFile = new File("testfiles/acceptanstest/Iteration2/varvlopptid-fullt2/namnfil.txt");	
		
		conf.setProperty(Configuration.KEY_RACE_TYPE, Configuration.VALUE_RACE_LAPS);
		conf.setProperty(Configuration.KEY_SHORTEST_POSSIBLE_TIME, "00.00.00");
		conf.setProperty(Configuration.KEY_START_TIME_LIMIT, "03.00.00");
		conf.setProperty(Configuration.KEY_RESULT_SORTED, "false");
		
		Configuration c = AbstractContestant.getConfiguration();
		
		AbstractContestant.setConfiguration(conf);
		
		
		SorterMain.noSort(conf, nameFile,  startTime, finishTime, new File(conf.getProperty(Configuration.KEY_RESULT_FILE_PATH)));
		
		String s1 = readFileAsString(new File("testfiles/acceptanstest/Iteration2/varvlopptid-fullt/resultat.txt"));
		String s2 = readFileAsString(new File(conf.getProperty(Configuration.KEY_RESULT_FILE_PATH)));
	
		assertEquals(s1,s2);
		
		AbstractContestant.setConfiguration(c);
	}

}
