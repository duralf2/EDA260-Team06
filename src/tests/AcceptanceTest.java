package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

import register.model.DataStructure;
import register.model.Time;
import junit.framework.TestCase;

public class AcceptanceTest extends TestCase {
	private String startTimesFilepath;
	private String finishTimesFilepath;

	@Before
	public void setUp() {
		startTimesFilepath = "testfiles/acceptanstest/acceptanstest3_5/starttider.txt";
		finishTimesFilepath = "testfiles/acceptanstest/acceptanstest3_5/maltider.txt";
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testStarttimes() throws IOException, FileNotFoundException {
		Reader reader = new FileReader(startTimesFilepath);
		CSVReader<String[]> csvParser = CSVReaderBuilder
				.newDefaultReader(reader);
		List<String[]> data = csvParser.readAll();

		DataStructure ds = new DataStructure();
		for (String[] entry : data) {
			ds.addTimeEntry(entry[0], new Time(entry[1], null));
		}
		System.out.println(ds.getAllTimeEntries());
	}
}
