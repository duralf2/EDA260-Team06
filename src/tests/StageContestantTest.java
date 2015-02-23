package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sorter.model.AbstractContestant;
import sorter.model.Configuration;
import sorter.model.ContestantProperties;
import sorter.model.Database;
import sorter.model.StageContestant;
import sorter.model.Time;

public class StageContestantTest {
	private StageContestant stageContestant;
	private ContestantProperties racerInfo;
	private Database db;
	private Configuration config;

	@Before
	public void setUp() throws Exception {
		db = new Database();

		/*File file = new File("testfiles/config/stageContestant.ini"); //OBS! stageContestant finns ännu inte (23/2)
		config = new Configuration(file);
		AbstractContestant.setConfiguration(config);

		racerInfo = new ContestantProperties(new String[] { "StartNr", "Namn" });
		racerInfo.put("StartNr", "1");
		racerInfo.put("Namn", "Lars");

		stageContestant = new StageContestant(racerInfo);
		stageContestant.addStartTime(new Time("00.00.00"));
		stageContestant.addFinishTime(new Time("01.00.01"));

		db.addContestantEntry("1", stageContestant);*/
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
	}

}
