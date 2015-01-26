package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;
import io.ReadFile;

public class ReadFileTest {
	ReadFile fr;
	DataStructure ds;
	
	@Before
	public void startUp(){
		fr = new ReadFile();
		ds = new DataStructure();
	}
	
	@Test(expected=StringIndexOutOfBoundsException.class)
	public void wrongStructure() throws IOException {
		fr.readFinishTime(new File("testfiles/emptyFileTest"),ds);
	}

	
	@Test(expected=FileNotFoundException.class)
	public void nonExistingFile() throws IOException{
		try{
			fr.readFinishTime(new File("asd"),ds);
		}catch (FileNotFoundException e) {
            throw e;
           }
	}
	
	@Test
	public void readStartFile() throws IOException {
		fr.readStartTime(new File("testfiles/acceptanstest/acceptanstest3/starttider.txt"), ds);
		Contestant contestant = ds.getContestant("1");
		assertEquals("12.00.00",contestant.getStartTime().toString());
	}
	
	@Test
	public void readEndFile() throws IOException {
		fr.readFinishTime(new File("testfiles/acceptanstest/acceptanstest3/maltider.txt"), ds);
		Contestant contestant = ds.getContestant("1");
		assertEquals("13.23.34",contestant.getFinishTime().toString());
	}

}
