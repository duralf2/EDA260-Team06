package tests;

import static org.junit.Assert.*;
import io.ReadFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sorter.Sorter;
import register.model.DataStructure;

public class ResultTest {
	private File[] files; // [0] = starttimes, [1] = finishtimes, [2] =
							// resultFile
	private Sorter sort;

	@Before
	public void setUp() {
		sort = new Sorter(new DataStructure());
		files = new File[] { new File("testfiles/startTimes.txt"),
				new File("testfiles/finishTimes.txt") };
		generateFiles(files);
	}

	@After
	public void tearDown() {
		for (File f : files)
			f.delete();
		File f = new File("testfiles/results.txt");
		if(f.exists())
			f.delete();
	}

	private void generateFiles(File[] files) {
		try {
			PrintWriter pw = new PrintWriter(files[0]);
			pw.write("2; 12.05.50\n");
			pw.write("1; 12.00.01\n");
			pw.write("3; 12.15.33\n");
			pw.write("4; 12.25.37\n");
			pw.write("5; 13.37.37\n");
			pw.close();
			pw = new PrintWriter(files[1]);
			pw.write("1; 14.15.01\n");
			pw.write("2; 15.30.35\n");
			pw.write("3; 14.35.27\n");
			pw.write("4; 14.57.59\n");
			pw.write("5; 15.01.39\n");
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testResult() {
		List<String[]> results = new ArrayList<String[]>();
		try {
			sort.sortTime(files, null);
			results = ReadFile.readCSV(new File("data/results.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//start at 1 to ignore header
		assertEquals("01.24.02", results.get(1)[3].trim());
		assertEquals("02.15.00", results.get(2)[3].trim());
		assertEquals("02.19.54", results.get(3)[3].trim());
		assertEquals("02.32.22", results.get(4)[3].trim());
		assertEquals("03.24.45", results.get(5)[3].trim());
	}

}
