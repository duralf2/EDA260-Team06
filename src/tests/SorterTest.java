package tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.*;

import register.model.Configuration;
import register.model.Database;
import sorter.Sorter;

public class SorterTest {
	private Sorter sorter;
	private File startTime;
	String nameFile;
	private File[] finishTimes = {new File("testfiles/acceptanstest/Iteration2/acceptanstest10/maltider1.txt"),
            new File("testfiles/acceptanstest/Iteration2/acceptanstest10/maltider2.txt")};
	private Configuration conf;
	
	@Before
	public void setUp() throws IOException {
		conf = new Configuration();
		
		startTime = new File("testfiles/acceptanstest/Iteration2/acceptanstest10/starttider.txt");
		nameFile = "testfiles/acceptanstest/Iteration2/acceptanstest10/namnfil.txt";
	}
	
	@Test
	public void testSortLapRace() throws IOException  {
		conf.setProperty(Configuration.KEY_RACE_TYPE, Configuration.VALUE_RACE_LAPS);
		conf.put(Configuration.KEY_LAPRACE_DURATION, "01.00.00");
		
		sorter = new Sorter(new Database(), conf);
		sorter.sort(nameFile, startTime, finishTimes);
		
		String s1 = readFileAsString(new File("testfiles/acceptanstest/Iteration2/acceptanstest10/resultat.txt"));
		String s2 = readFileAsString(new File(conf.getProperty(Configuration.KEY_RESULT_FILE_PATH)));
		
		assertEquals(s1,s2);
		
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

		return fileContents.trim();
	}

}
