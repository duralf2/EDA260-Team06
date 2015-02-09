package tests;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import register.model.ContestantFactory;
import register.model.Database;
import register.model.LapContestant;
import register.model.MarathonContestant;
import register.model.RaceProperties;
import register.model.StandardContestant;

public class ContestantFactoryTest {

	private Properties properties;
	private ContestantFactory factory;
	private Database dataStructure;

	@Before
	public void setUp() throws Exception {
		properties = new Properties();
		factory = new ContestantFactory(properties);
		dataStructure = new Database();

		properties.put(RaceProperties.KEY_RACE_TYPE,
				RaceProperties.VALUE_RACE_MARATHON);
		properties.put(RaceProperties.KEY_NAME_FILE_PATH,
				"testfiles/factoryTestNames.txt");
	}

	@Test
	public void testCreateRegisteredContestants() throws IOException {
		assertTrue(true);
		
//		factory.createRegisteredContestants(dataStructure);
		// TODO
	}

	@Test
	public void testCreateContestant() {
		StandardContestant contestant = factory.createContestant(new String[] {"StartNo", "Namn"}, new String[] { "1", "Bertil" });
		assertEquals(MarathonContestant.class, contestant.getClass());

		properties.put(RaceProperties.KEY_RACE_TYPE,
				RaceProperties.VALUE_RACE_LAPS);
		contestant = factory.createContestant(new String[] {"StartNo", "Namn"}, new String[] { "1", "Bertil" });
		assertEquals(LapContestant.class, contestant.getClass());
	}
}
