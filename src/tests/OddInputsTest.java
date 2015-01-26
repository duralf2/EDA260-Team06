package tests;

import static org.junit.Assert.*;
import io.IOHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

public class OddInputsTest {

	private PrintWriter pw;
	private DataStructure ds;
	private File f;
	private Scanner sc;
	
	@Before
	public void setUp() throws FileNotFoundException
	{
		f = new File("output.txt");
		FileOutputStream fos = new FileOutputStream(f);
		pw = new PrintWriter(fos);
		ds = new DataStructure();
		sc = new Scanner(f);
	}
//	
//	@After
//	public void tearDown()
//	{
//		f.delete();
//	}
//	
	@Test
	public void testNoStartTime(){
		Contestant contestant = new Contestant("Göran");
		contestant.setFinishTime(new Time("13.23.34"));
		ds.addContestantEntry("1", contestant);
		IOHandler.writeResult(pw, ds);
		assertEquals("StartNr; Namn; TotalTid; Starttid; Måltid", sc.nextLine());
		assertEquals("1;Göran;--.--.--;Start?;13.23.34", sc.next());
	}
	
	@Test
	public void testNoFinishTime(){
		Contestant contestant = new Contestant("Göran");
		contestant.setStartTime(new Time("13.23.34"));
		ds.addContestantEntry("1", contestant);
		IOHandler.writeResult(pw, ds);
		assertEquals("StartNr; Namn; TotalTid; Starttid; Måltid", sc.nextLine());
		assertEquals("1;Göran;--.--.--;13.23.34;Slut?", sc.next());
	}
	
	@Test
	public void testToFast(){
		Contestant contestant = new Contestant("Göran");
		contestant.setStartTime(new Time("13.23.34"));
		contestant.setFinishTime(new Time("13.30.34"));
		ds.addContestantEntry("1", contestant);
		IOHandler.writeResult(pw, ds);
		assertEquals("StartNr; Namn; TotalTid; Starttid; Måltid", sc.nextLine());
		assertEquals("1;Göran;00.07.00;13.23.34;13.30.34;Omöjlig totaltid?", sc.nextLine());
	}
	
	@Test
	public void testMultipleStart() {
		Contestant contestant = new Contestant("Göran");
		contestant.addStartTime(new Time("13.23.34"));
		contestant.addStartTime(new Time("13.24.35"));
		contestant.setFinishTime(new Time("14.30.34"));
		ds.addContestantEntry("1", contestant);
		IOHandler.writeResult(pw, ds);
		assertEquals("StartNr; Namn; TotalTid; Starttid; Måltid", sc.nextLine());
		assertEquals("1;Göran;01.07.00;13.23.34;14.30.34; Flera starttider? 13.24.35", sc.nextLine());
	}
	
	@Test
	public void testMultipleFinish() {
		Contestant contestant = new Contestant("Göran");
		contestant.addStartTime(new Time("13.23.34"));
		contestant.addFinishTime(new Time("14.30.34"));
		contestant.addFinishTime(new Time("14.31.34"));
		ds.addContestantEntry("1", contestant);
		IOHandler.writeResult(pw, ds);
		assertEquals("StartNr; Namn; TotalTid; Starttid; Måltid", sc.nextLine());
		assertEquals("1;Göran;01.07.00;13.23.34;14.30.34; Flera måltider? 14.31.34", sc.nextLine());
	}
}
	