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

import sorter.model.Configuration;

public class ConfigurationTest {
	
	private File outfile;
	
	private Configuration properties;

	@Before
	public void setUp() throws IOException {
		outfile = new File("testfiles/config/RacePropertiesTestConfig.txt");
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
		assertEquals("#Valid result formats:", scanner.nextLine());
		assertEquals("# - " + Configuration.VALUE_FORMAT_CSV, scanner.nextLine());
		assertEquals("# - " + Configuration.VALUE_FORMAT_HTML, scanner.nextLine());
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

		if (line.equals(Configuration.KEY_NAME_FILE_PATH + "=data/namn.txt")) return;
		if (line.equals(Configuration.KEY_START_TIME_FOLDER_PATH + "=data/starttimes/")) return;
		if (line.equals(Configuration.KEY_FINISH_TIME_FOLDER_PATH + "=data/finishtimes/")) return;
		if (line.equals(Configuration.KEY_RESULT_FILE_PATH + "=data/result.txt")) return;
		
		if (line.equals(Configuration.KEY_RACE_TYPE + "=marathon")) return;
		if (line.equals(Configuration.KEY_SHORTEST_POSSIBLE_TIME + "=00.15.00")) return;
		if (line.equals(Configuration.KEY_START_TIME_LIMIT + "=01.00.00")) return;
		
		if (line.equals(Configuration.KEY_RESULT_FORMAT + "=" + Configuration.VALUE_FORMAT_CSV)) return;
		if (line.equals(Configuration.KEY_RESULT_SORTED + "=false")) return;

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
