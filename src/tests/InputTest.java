package tests;

import static org.junit.Assert.assertEquals;
import io.IOHandler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.Contestant;
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
			e.printStackTrace();
		}
	}

	@Test
	public void testReadContestantList() throws IOException {
		IOHandler.readContestantList(reader, ds);
		assertEquals("Anders Asson", ds.getContestant("1").getName());
		assertEquals("Bengt Bsson", ds.getContestant("2").getName());
		assertEquals("Chris Csson", ds.getContestant("3").getName());
		assertEquals("David Dsson", ds.getContestant("4").getName());
		assertEquals("Erik Esson", ds.getContestant("5").getName());
	}
	
	@Test
	public void testReadContestantListWhenContestantsAlreadyExists() throws IOException {
		ds.addContestantEntry("1", new Contestant("Gunnar"));
		IOHandler.readContestantList(reader, ds);
		assertEquals("Anders Asson", ds.getContestant("1").getName());
	}
	
	@Test
	public void testReadContestantColumnNames() throws IOException {
		IOHandler.readContestantList(reader, ds);
		assertEquals("StartNo", ds.getContestantColumnNames()[0]);
		assertEquals("Namn", ds.getContestantColumnNames()[1]);
	}
}
