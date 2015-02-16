package tests;

import static org.junit.Assert.assertEquals;
import io.ReadFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import register.model.AbstractContestant;
import register.model.ContestantProperties;
import register.model.Database;
import register.model.MarathonContestant;

public class ReadFileTest {
	Database db;
	
	@Before
	public void startUp(){
		db = new Database();
	}
	
	@Test(expected=StringIndexOutOfBoundsException.class)
	public void wrongStructure() throws IOException {
		ReadFile.readFinishTime(new File("testfiles/emptyFileTest"),db);
	}

	
	@Test(expected=FileNotFoundException.class)
	public void nonExistingFile() throws FileNotFoundException, IOException {
		try{
			ReadFile.readFinishTime(new File("asd"),db);
		}catch (IOException e) {
            throw e;
        }
	}
	
	@Test
	public void readStartFile() throws IOException {
		ReadFile.readStartTime(new File("testfiles/acceptanstest/acceptanstest3/starttider.txt"), db);
		AbstractContestant contestant = db.getContestant("1");
		assertEquals("12.00.00",contestant.getStartTime().toString());
	}
	
	@Test
	public void readFinishFile() throws IOException {
		ReadFile.readFinishTime(new File("testfiles/acceptanstest/acceptanstest3/maltider.txt"), db);
		AbstractContestant contestant = db.getContestant("1");
		assertEquals("13.23.34",contestant.getFinishTime().toString());
	}

	@Test
	public void testReadContestantList() throws IOException {
		ReadFile.readNames(new File("testfiles/acceptanstest/acceptanstest3_4/namnfil.txt"), db);
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
		
		ReadFile.readNames(new File("testfiles/acceptanstest/acceptanstest3_4/namnfil.txt"), db);
		assertEquals("Anders Asson", db.getContestant("1").getInformation("Namn"));
	}
	
	@Test
	public void testReadContestantColumnNames() throws IOException {
//		ReadFile.readNames(new File("testfiles/acceptanstest/acceptanstest3_4/namnfil.txt"), db);
//		assertEquals("StartNo", db.getContestantColumnNames()[0]);
//		assertEquals("Namn", db.getContestantColumnNames()[1]);
	}

}
