package io;

import gui.model.ContestantTimes;
import gui.model.FormatErrorHandler;
import gui.model.StartNumberComparator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * This class is responsible for reading files formatted in the excel file
 * format for the GUI.
 */
public class FileReaderGUI {
	private static boolean timeErrorFlag = false;
	private static boolean nameErrorFlag = false;
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
				int counter = 2;
				for (iterator.next(); iterator.hasNext();) {
					String number = iterator.next()[0];
					if (!StartNumberComparator.isStartNumber(number)) {
						FormatErrorHandler.addError(FormatErrorHandler.NAME, counter);
					} else {
						startNumbers.add(number);
					}
					counter++;
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
	//TODO: Handle wrong time format
	public static List<String> readTimesFromFile(File timeFile, ContestantTimes times) {
		List<String> errorTimes = new LinkedList<String>();
		try {
			List<String[]> nameFile = CSVReader.read(timeFile);
			for (String[] lines : nameFile) {
				if (!StartNumberComparator.isStartNumber(lines[0])) {
						FormatErrorHandler.addError(FormatErrorHandler.TIME, 0);
					StringBuilder sb = new StringBuilder();
					for (String str : lines) {
						sb.append(str + ";");
					}
					sb.deleteCharAt(sb.length() - 1);
					String s = sb.toString();
					if (!s.contains("#ERROR")) {
						s += "\t\t#ERROR";
					}
					errorTimes.add(s);
				} else {
					String startNumber = lines[0];
					String time = lines[1].trim();
					times.addTime(startNumber, time);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return errorTimes;
	}
}