package io;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HTMLParser {

	public static void resultToHTML(File f) throws IOException {
		StringBuilder sb = new StringBuilder();
		List<String[]> read = io.CSVReader.read(f);
	}
}
