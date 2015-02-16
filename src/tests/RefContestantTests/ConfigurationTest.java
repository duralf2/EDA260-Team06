package tests.RefContestantTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.model.Configuration;

public class ConfigurationTest {
	
	private File outfile;
	
	private Configuration properties;

	@Before
	public void setUp() throws IOException {
		outfile = new File("testfiles/RacePropertiesTestConfig.txt");
		properties = new Configuration(outfile); // This also creates the default config file
	}

	@After
	public void tearDown() throws Exception {
		outfile.delete();
	}

	@Test
	public void testPrintDefaultConfig() throws FileNotFoundException {
		Scanner scanner = new Scanner(outfile);
		assertEquals("#Enduro config file", scanner.nextLine());
		assertEquals("#", scanner.nextLine());
		assertEquals("#Valid race types:", scanner.nextLine());
		assertEquals("# - " + Configuration.VALUE_RACE_MARATHON, scanner.nextLine());
		assertEquals("# - " + Configuration.VALUE_RACE_LAPS, scanner.nextLine());
		assertEquals("#", scanner.nextLine());
		scanner.nextLine(); // Skip the line with the timestamp
		while (scanner.hasNextLine())
			assertLine(scanner.nextLine());
		scanner.close();
	}
	
	private void assertLine(String line)
	{
		if (!line.contains("="))
			fail("The config line must contain an '=', line: " + line);
		
		if (line.equals("guiOutputFilePath=data/utdata.txt")) return;
		if (line.equals("raceType=marathon")) return;
		if (line.equals("resultFilePath=data/result.txt")) return;
		if (line.equals("nameFilePath=data/namn.txt")) return;
		if (line.equals("minimumRaceDuration=00.00.00")) return;

		fail("Invalid/Unknown config line: " + line);
	}

	
	@Test
	public void testLoadExistingConfig() throws IOException
	{
		// Add new line to existing config
		FileWriter out = new FileWriter(outfile, true);
		out.write("\nTestEntry=TestData");
		out.close();
		
		// Creating it again forces it to read the modified config
		properties = new Configuration(outfile);
		
		assertEquals("TestData", properties.get("TestEntry"));
	}
}
