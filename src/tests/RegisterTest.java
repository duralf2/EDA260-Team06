package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import register.logic.Register;
import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

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
		for (String s : entries.keySet()) {
			int index = Integer.parseInt(s) - 1;
			assertEquals(startTimes[index], entries.get(s).getStartTime());
		}
	}

	@Test
	public void testReadGoalTimes() throws IOException {
		File goal = new File(
				"testfiles/acceptanstest/acceptanstest3/maltider.txt");
		register.readGoalTimes(goal);
		for (String s : entries.keySet()) {
			int index = Integer.parseInt(s) - 1;
			assertEquals(finishTimes[index], entries.get(s).getFinishTime());
		}
	}
	
	@Test
	public void testWriteResultNoSort() throws FileNotFoundException {
		for (int i = 0; i < startTimes.length; i++) {
			Contestant c = new Contestant("TestContestant " + 1);
			c.setStartTime(startTimes[i]);
			c.setFinishTime(finishTimes[i]);
			ds.addContestantEntry(Integer.toString((i + 1)), c);
		}
		File file = new File("testNoSort.txt");
		register.writeResult(file);
		Scanner sc = new Scanner(file);
		sc.nextLine();
		while(sc.hasNext()) {
			String tempIndex = sc.next(); //index + ;
			int index = Integer.parseInt(tempIndex.substring(0, tempIndex.length() - 1)) - 1;
			sc.next(); //--.--.--
			String tempStart = sc.next(); //startTime + ;
			String start = tempStart.substring(0, tempStart.length() - 1);
			String finish = sc.next();
			assertEquals(startTimes[index].toString(), start);
			assertEquals(finishTimes[index].toString(), finish);
		}
		sc.close();
		file.delete();
	}
}
