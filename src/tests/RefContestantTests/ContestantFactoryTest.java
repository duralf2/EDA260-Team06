package tests.RefContestantTests;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import register.model.ContestantFactory;
import register.model.Database;
import register.model.LapContestant;
import register.model.MarathonContestant;
import register.model.RaceProperties;
import register.model.AbstractContestant;
import register.model.RacerInfo;

public class ContestantFactoryTest {

	private Properties properties;
	private ContestantFactory factory;
	private Database database;

	@Before
	public void setUp() throws Exception {
		properties = new Properties();
		factory = new ContestantFactory(properties);
		database = new Database();

		properties.put(RaceProperties.KEY_RACE_TYPE,
				RaceProperties.VALUE_RACE_MARATHON);
		properties.put(RaceProperties.KEY_NAME_FILE_PATH,
				"testfiles/FactoryTestNames.txt");
		factory.createRegisteredContestants(database);
	}

	@Test
	public void testCreateRegisteredContestants() throws IOException {
		
		for (Entry<String, AbstractContestant> entry : database.getAllContestantEntries().entrySet())
		{
			assertEquals(MarathonContestant.class, entry.getValue().getClass());
		}

		// TODO Test för att se om contestants headers är correcta 
	}

	@Test
	public void testCreateContestant() {
		RacerInfo info = factory.createRaceInfo();
		info.put("StartNr", "1");
		info.put("Namn", "Bertil");
		AbstractContestant contestant = factory.createContestant();
		assertEquals(MarathonContestant.class, contestant.getClass());

		properties.put(RaceProperties.KEY_RACE_TYPE,
				RaceProperties.VALUE_RACE_LAPS);
		contestant = factory.createContestant(info);
		assertEquals(LapContestant.class, contestant.getClass());
	}
}
