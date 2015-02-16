package io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import register.model.ContestantTimes;

public class ReadFileGUI {
	
	/**
	 * Returns an list with start numbers in the specified name file.
	 * 
	 * @param nameFile
	 *            File to read.
	 * @return list with start numbers.
	 */
	public static ArrayList<String> readStartNumbers(File nameFile) {
		ArrayList<String> startNumbers = new ArrayList<String>();
		try {
			List<String[]> names = CSVReader.read(nameFile);
			if (names.size() > 0) {
				Iterator<String[]> iterator = names.iterator();
				for (iterator.next(); iterator.hasNext();) {
					startNumbers.add(iterator.next()[0]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return startNumbers;
	}

	/**
	 * Reads contestant times from the specified file and loads the data into
	 * the provided ContestantTimes instance.
	 * 
	 * @param timeFile
	 *            File to read.
	 * @param times
	 *            ContestantTimes instance to hold the data.
	 */
	public static void readTimesFromFile(File timeFile, ContestantTimes times) {
		try {
			List<String[]> nameFile = CSVReader.read(timeFile);
			for (String[] lines : nameFile) {
				String startNumber = lines[0];
				String time = lines[1].trim();
				times.addTime(startNumber, time);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
