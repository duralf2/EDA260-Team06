package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class AbstractAcceptanceTest {

	protected String readFileAsString(File file) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));

		String fileContents = "";
		String currentLine = reader.readLine();
		while (currentLine != null) {
			fileContents += currentLine.replace("\\s+", "") + "\n";
			currentLine = reader.readLine();
		}

		reader.close();

		return fileContents;
	}
}
