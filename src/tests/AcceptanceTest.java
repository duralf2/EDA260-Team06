package tests;

import io.IOHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;
import junit.framework.TestCase;

public class AcceptanceTest extends TestCase {
	private String startTimesFilepath;
	private String finishTimesFilepath;
	private File outfile;

	@Before
	public void setUp() {
		startTimesFilepath = "testfiles/acceptanstest/acceptanstest3_5/starttider.txt";
		finishTimesFilepath = "testfiles/acceptanstest/acceptanstest3_5/maltider.txt";
		outfile = new File("out.txt");
	}

	@After
	public void tearDown() {
		outfile.delete();
	}

	@Test
	public void testStartTimes() throws IOException, FileNotFoundException {
		DataStructure ds = inputToDataStructure(readCSV(new File(startTimesFilepath)), "start");

		PrintWriter pw = new PrintWriter(outfile);
		IOHandler.writeStartTimes(pw, ds);

		DataStructure outds = inputToDataStructure(readCSV(outfile), "start");
		assertTrue(outds.equals(ds));
	}
	
	
	@Test
	public void testFinishTimes() throws IOException, FileNotFoundException {
		DataStructure ds = inputToDataStructure(readCSV(new File(finishTimesFilepath)), "finish");

		PrintWriter pw = new PrintWriter(outfile);
		IOHandler.writeFinishTimes(pw, ds);

		DataStructure outds = inputToDataStructure(readCSV(outfile), "finish");
		assertTrue(outds.equals(ds));
	}

	@Test
	public void testFileloading() throws IOException, FileNotFoundException {
		File f = new File(startTimesFilepath);
		DataStructure ds = inputToDataStructure(readCSV(f), "start");
		DataStructure outds = inputToDataStructure(readCSV(f), "start");
		assertTrue(outds.equals(ds));
	}

	private List<String[]> readCSV(File file) throws IOException {
		Reader reader = new FileReader(file);
		CSVReader<String[]> csvParser = CSVReaderBuilder
				.newDefaultReader(reader);
		List<String[]> data = csvParser.readAll();
		System.out.println(data.get(data.size()-1)[0] + "; " + data.get(data.size()-1)[1]);
		return data;
	}

	private DataStructure inputToDataStructure(List<String[]> data, String type) {
		DataStructure ds = new DataStructure();
		for (String[] entry : data) {
			Contestant contestant = new Contestant("Testname");
			if(type == "start") {
				contestant.setStartTime(new Time(entry[1]));
			} else if(type == "finish") {
				contestant.setFinishTime(new Time(entry[1]));
			} else {
				throw new IllegalArgumentException("Invalid input type");
			}
			ds.addContestantEntry(entry[0], contestant);
		}
		return ds;
	}
}
