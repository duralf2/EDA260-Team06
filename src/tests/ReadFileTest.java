package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

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
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void wrongStructure() throws IOException {
		fr.read(new File("testfiles/emptyFileTest"),ds);
		
	}
	
//	@Test
//	public void noStartNumber() {
//		
//	}
	
	@Test(expected=FileNotFoundException.class)
	public void nonExistingFile() throws IOException{
		try{
			fr.read(new File("asd"),ds);
		}catch (FileNotFoundException e) {
            throw e;
           }
	}
	
	@Test
	public void workingStructure() throws IOException {
		fr.read(new File("testfiles/validFileTest"), ds);
		fr.toString();
		Map<String,Time> entries= ds.getAllTimeEntries();
		Time entry = entries.get("1");
		String time = entry.getStartTime()+","+entry.getFinishTime();
		assertEquals(time,"12.00.00,13.23.34");
	}

}
