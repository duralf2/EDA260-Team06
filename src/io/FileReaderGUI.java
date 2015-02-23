package io;

import gui.model.ContestantTimes;
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
				for (iterator.next(); iterator.hasNext();) {
					String number = iterator.next()[0];
					if (!StartNumberComparator.isStartNumber(number)) {
//						System.err.println("Format error at line " + counter + " in name file.");
						raiseNotice(false, 0);
					} else {
						startNumbers.add(number);
					
					}
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

	public static List<String> readTimesFromFile(File timeFile, ContestantTimes times) {
		List<String> errorTimes = new LinkedList<String>();
		try {
			List<String[]> nameFile = CSVReader.read(timeFile);
			for (String[] lines : nameFile) {
				if (!StartNumberComparator.isStartNumber(lines[0])) {
//					System.err.println("Format error at line " + counter+" in time file " + lines[0]);
						raiseNotice(true, 0);
					StringBuilder sb = new StringBuilder();
					for (String str : lines) {
						sb.append(str + ";");
					}
					sb.deleteCharAt(sb.length() - 1);
					errorTimes.add(sb.toString());
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

	public static void raiseNotice(boolean isTime, int line) {
		if (!timeErrorFlag && isTime) {
			JOptionPane.showMessageDialog(null, "Format error in time file");
			timeErrorFlag = true;
		} else if (!nameErrorFlag && !isTime) {
			JOptionPane.showMessageDialog(null, "Format error in name file");
			nameErrorFlag = true;
		}
	}
}