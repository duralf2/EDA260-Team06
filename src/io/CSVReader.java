package io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

public class CSVReader {
	/**
	 * Reads all the contents of the specified file and splits them into a list
	 * of string arrays. Each element in the list represents a line in the file,
	 * and each element in the string arrays represents a piece of text,
	 * separated by semicolons. </br> </br> <b>Note:</b> Any whitespace
	 * following/preceding semicolons are left untouched by this method!
	 * Therefore it might be necessary to trim the elements of the string array
	 * after using this method.
	 * 
	 * @param file
	 *            The file to read
	 * @return The contents of the file, formatted as specified above
	 * @throws IOException
	 *             If the file doesn't exist or couldn't be closed
	 */
	public static List<String[]> read(File file) throws IOException {
		Reader reader = new FileReader(file);
		com.googlecode.jcsv.reader.CSVReader<String[]> csvParser = CSVReaderBuilder
				.newDefaultReader(reader);
		List<String[]> result = csvParser.readAll();
		reader.close();
		return result;
	}
}
