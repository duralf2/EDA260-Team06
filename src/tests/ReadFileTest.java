package tests;

import static org.junit.Assert.assertEquals;
import io.FileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import register.model.AbstractContestant;
import register.model.Configuration;
import register.model.ContestantFactory;
import register.model.ContestantProperties;
import register.model.Database;
import register.model.MarathonContestant;

public class ReadFileTest {
	private Database db;
	private FileReader reader;
	private ContestantFactory factory;
	
	@Before
	public void startUp() throws IOException{
		db = new Database();
		
		Properties properties = new Properties();
		properties.put(Configuration.KEY_RACE_TYPE, Configuration.VALUE_RACE_MARATHON);
		factory = new ContestantFactory(properties);
		reader = new FileReader(factory);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void wrongStructure() throws IOException {
		reader.readFinishTime(new File("testfiles/emptyFileTest"),db);
	}

	
	@Test(expected=FileNotFoundException.class)
	public void nonExistingFile() throws FileNotFoundException, IOException {
		try{
			reader.readFinishTime(new File("asd"),db);
		}catch (IOException e) {
            throw e;
        }
	}
	
	@Test
	public void readStartFile() throws IOException {
		reader.readStartTime(new File("testfiles/acceptanstest/acceptanstest3/starttider.txt"), db);
		AbstractContestant contestant = db.getContestant("1");
		assertEquals("12.00.00",contestant.getStartTime().toString());
	}
	
	@Test
	public void readFinishFile() throws IOException {
		reader.readFinishTime(new File("testfiles/acceptanstest/acceptanstest3/maltider.txt"), db);
		AbstractContestant contestant = db.getContestant("1");
		assertEquals("13.23.34",contestant.getFinishTime().toString());
	}

	@Test
	public void testReadContestantList() throws IOException {
		reader.readNames(new File("testfiles/acceptanstest/acceptanstest3_4/namnfil.txt"), db);
		assertEquals("Anders Asson", db.getContestant("1").getInformation("Namn"));
		assertEquals("Bengt Bsson", db.getContestant("2").getInformation("Namn"));
		assertEquals("Chris Csson", db.getContestant("3").getInformation("Namn"));
		assertEquals("David Dsson", db.getContestant("4").getInformation("Namn"));
		assertEquals("Erik Esson", db.getContestant("5").getInformation("Namn"));
	}
	
	@Test
	public void testReadContestantListWhenContestantsAlreadyExists() throws IOException {
		AbstractContestant contestant = new MarathonContestant(new ContestantProperties(new String[]{}));
		contestant.putInformation("Namn", "Gunnar");
		db.addContestantEntry("1", contestant);
		
		reader.readNames(new File("testfiles/acceptanstest/acceptanstest3_4/namnfil.txt"), db);
		assertEquals("Anders Asson", db.getContestant("1").getInformation("Namn"));
	}
}
