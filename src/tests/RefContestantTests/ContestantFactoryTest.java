package tests.RefContestantTests;

import static org.junit.Assert.assertEquals;
import io.FileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import register.model.AbstractContestant;
import register.model.Configuration;
import register.model.ContestantFactory;
import register.model.ContestantProperties;
import register.model.Database;
import register.model.LapContestant;
import register.model.MarathonContestant;

public class ContestantFactoryTest {

	private Properties properties;
	private ContestantFactory factory;
	private Database database;

	@Before
	public void setUp() throws Exception {
		properties = new Properties();
		factory = new ContestantFactory(properties);
		database = new Database();

		properties.put(Configuration.KEY_RACE_TYPE,
				Configuration.VALUE_RACE_MARATHON);
		properties.put(Configuration.KEY_NAME_FILE_PATH,
				"testfiles/FactoryTestNames.txt");
		FileReader rf = new FileReader(factory);
		rf.readNames(new File((String) properties.get(Configuration.KEY_NAME_FILE_PATH)), database);
//		factory.createRegisteredContestants(database);
	}

	
	@Test
	public void testReadNames(){
			ArrayList<String> contestants = new ArrayList<String>();
		for(Entry<String, AbstractContestant> entry : database.getAllContestantEntries().entrySet()){
			contestants.add(entry.getValue().getInformation("Namn"));
		}
		assertEquals("Anders Asson", contestants.get(0));
		assertEquals("Bengt Bsson", contestants.get(1));
		assertEquals("Chris Csson", contestants.get(2));
		assertEquals("David Dsson", contestants.get(3));
		assertEquals("Erik Esson", contestants.get(4));
	}

	@Test
	public void testCreateContestant() {
		ContestantProperties info = factory.createContestantProperties();
		info.put("StartNr", "1");
		info.put("Namn", "Bertil");
		AbstractContestant contestant = factory.createContestant();
		assertEquals(MarathonContestant.class, contestant.getClass());

		properties.put(Configuration.KEY_RACE_TYPE,
				Configuration.VALUE_RACE_LAPS);
		contestant = factory.createContestant(info);
		assertEquals(LapContestant.class, contestant.getClass());
	}
}
