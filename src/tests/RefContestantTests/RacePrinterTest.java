package tests.RefContestantTests;

import static org.junit.Assert.assertEquals;
import io.RacePrinter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RacePrinterTest {

	private File outfile;

	@Before
	public void setUp() {
		outfile = new File("testfiles/RacePrinterTest.txt");
	}

	@After
	public void tearDown() {
		 outfile.delete();
	}

	@Test
	public void testRacePrinter() throws IOException, FileNotFoundException {

		String expected = "abcd\nedfg";
		
		new RacePrinter(outfile.getAbsolutePath()).print(expected);

		String printedResult = readFileAsString(outfile);
		
		assertEquals(expected, printedResult);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullData() throws IOException
	{
		new RacePrinter(outfile.getAbsolutePath()).print(null);
	}

	private String readFileAsString(File file) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));

		String fileContents = "";
		String currentLine = reader.readLine();
		while (currentLine != null) {
			fileContents += currentLine.replace("\\s+", "") + "\n";
			currentLine = reader.readLine();
		}

		reader.close();

		return fileContents.trim();
	}

}
