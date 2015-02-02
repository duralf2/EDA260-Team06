package tests;

import static org.junit.Assert.assertEquals;
import io.ReadFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import register.model.Contestant;
import register.model.DataStructure;

public class ReadFileTest {
	DataStructure ds;
	
	@Before
	public void startUp(){
		ds = new DataStructure();
	}
	
	@Test(expected=StringIndexOutOfBoundsException.class)
	public void wrongStructure() throws IOException {
		ReadFile.readFinishTime(new File("testfiles/emptyFileTest"),ds);
	}

	
	@Test(expected=FileNotFoundException.class)
	public void nonExistingFile() throws IOException{
		try{
			ReadFile.readFinishTime(new File("asd"),ds);
		}catch (FileNotFoundException e) {
            throw e;
           }
	}
	
	@Test
	public void readStartFile() throws IOException {
		ReadFile.readStartTime(new File("testfiles/acceptanstest/acceptanstest3/starttider.txt"), ds);
		Contestant contestant = ds.getContestant("1");
		assertEquals("12.00.00",contestant.getStartTime().toString());
	}
	
	@Test
	public void readFinishFile() throws IOException {
		ReadFile.readFinishTime(new File("testfiles/acceptanstest/acceptanstest3/maltider.txt"), ds);
		Contestant contestant = ds.getContestant("1");
		assertEquals("13.23.34",contestant.getFinishTime().toString());
	}

	@Test
	public void testReadContestantList() throws IOException {
		ReadFile.readNames(new File("testfiles/acceptanstest/acceptanstest3_4/namnfil.txt"), ds);
		assertEquals("Anders Asson", ds.getContestant("1").getName());
		assertEquals("Bengt Bsson", ds.getContestant("2").getName());
		assertEquals("Chris Csson", ds.getContestant("3").getName());
		assertEquals("David Dsson", ds.getContestant("4").getName());
		assertEquals("Erik Esson", ds.getContestant("5").getName());
	}
	
	@Test
	public void testReadContestantListWhenContestantsAlreadyExists() throws IOException {
		ds.addContestantEntry("1", new Contestant("Gunnar"));
		ReadFile.readNames(new File("testfiles/acceptanstest/acceptanstest3_4/namnfil.txt"), ds);
		assertEquals("Anders Asson", ds.getContestant("1").getName());
	}
	
	@Test
	public void testReadContestantColumnNames() throws IOException {
		ReadFile.readNames(new File("testfiles/acceptanstest/acceptanstest3_4/namnfil.txt"), ds);
		assertEquals("StartNo", ds.getContestantColumnNames()[0]);
		assertEquals("Namn", ds.getContestantColumnNames()[1]);
	}

}
