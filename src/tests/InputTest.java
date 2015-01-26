package tests;

import static org.junit.Assert.*;
import io.IOHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.DataStructure;

public class InputTest {
	private DataStructure ds;
	FileReader reader;
	
	@Before
	public void setUp() throws Exception {
		ds = new DataStructure();
		reader = new FileReader(new File("testfiles/acceptanstest/acceptanstest3_4/namnfil.txt"));
	}
	
	@After
	public void tearDown() {
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testReadContestantList() {
		IOHandler.readContestantList(reader, ds);
		assertEquals("Anders Asson", ds.getContestant("1").getName());
		assertEquals("Bengt Bsson", ds.getContestant("2").getName());
		assertEquals("Chris Csson", ds.getContestant("3").getName());
		assertEquals("David Dsson", ds.getContestant("4").getName());
		assertEquals("Erik Esson", ds.getContestant("5").getName());
	}
	
	

}
