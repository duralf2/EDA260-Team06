package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import register.logic.Register;
import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

public class RegisterTest {
	private DataStructure ds;
	private Register register;
	private Map<String, Contestant> entries;
	private Time[] startTimes;
	private Time[] finishTimes;

	@Before
	public void setUp() {
		ds = new DataStructure();
		register = new Register(ds);
		entries = register.getDataStructure().getAllContestantEntries();
		startTimes = new Time[] { new Time("12.00.00"), new Time("12.01.00"),
				new Time("12.02.00"), new Time("12.03.00"),
				new Time("12.04.00") };
		finishTimes = new Time[] { new Time("13.23.34"), new Time("13.15.16"),
				new Time("13.05.06"), new Time("13.12.07"),
				new Time("13.16.07") };
	}

	@Test
	public void testReadStartTimes() throws IOException {
		File start = new File(
				"testfiles/acceptanstest/acceptanstest3/starttider.txt");
		register.readStartTimes(start);
		for (String startNumber : entries.keySet()) {
			int index = Integer.parseInt(startNumber) - 1;
			assertEquals(startTimes[index], entries.get(startNumber).getStartTime());
		}
	}

	@Test
	public void testReadGoalTimes() throws IOException {
		File goal = new File(
				"testfiles/acceptanstest/acceptanstest3/maltider.txt");
		register.readGoalTimes(goal);
		for (String startNumber : entries.keySet()) {
			int index = Integer.parseInt(startNumber) - 1;
			assertEquals(finishTimes[index], entries.get(startNumber).getFinishTime());
		}
	}

	@Test
	public void testWriteResultNoSort() throws IOException {
		for (int i = 0; i < startTimes.length; i++) {
			Contestant c = new Contestant("TestContestant " + 1);
			c.setStartTime(startTimes[i]);
			c.setFinishTime(finishTimes[i]);
			ds.addContestantEntry(Integer.toString((i + 1)), c);
		}
		File file = new File("testNoSort.txt");
		register.writeResult(file);

		Reader reader = new FileReader(file);
		CSVReader<String[]> csvParser = CSVReaderBuilder
				.newDefaultReader(reader);
		List<String[]> data = csvParser.readAll();

		for (String[] s : data) {
			try {
				int index = Integer.parseInt(s[0]) - 1;
				assertEquals(
						Time.getTotalTime(startTimes[index], finishTimes[index])
								.toString(), s[2]);
				assertEquals(startTimes[index].toString(), s[3]);
				assertEquals(finishTimes[index].toString(), s[4]);
			} catch (NumberFormatException e) {
			}
			;
		}
		reader.close();
		csvParser.close();
		file.delete();
	}
}
