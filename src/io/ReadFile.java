package io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import register.model.AbstractContestant;
import register.model.ContestantFactory;
import register.model.ContestantTimes;
import register.model.Database;
import register.model.MarathonContestant;
import register.model.Time;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

/**
 * This class is responsible for reading files formatted in the excel file
 * format and store their contents into a database.
 */
public class ReadFile {
	private ContestantFactory cf;

	public ReadFile(ContestantFactory cf) {
		this.cf = cf;
	}

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
	public static List<String[]> readCSV(File file) throws IOException {
		Reader reader = new FileReader(file);
		CSVReader<String[]> csvParser = CSVReaderBuilder
				.newDefaultReader(reader);
		List<String[]> result = csvParser.readAll();
		reader.close();
		return result;
	}

	/**
	 * Reads the contents of the specified file and interprets it as a name
	 * file. All the names that are loaded are put into the specified database,
	 * creating new <code>Contestants</code> if there are none with the correct
	 * starting numbers.
	 * 
	 * @param file
	 *            The file to load the names from
	 * @param db
	 *            The database to put the names into
	 * @throws IOException
	 *             If the file doesn't exist or couldn't be closed
	 */
	public void readNames(File file, Database db) throws IOException {
		List<String[]> data = readCSV(file);

		readContestantColumns(data.get(0));
		data.remove(0);

		String startNumberOrClassName, name, className = "";
		AbstractContestant contestant;
		for (String[] line : data) {
			startNumberOrClassName = line[0];
			if (isStartNumber(startNumberOrClassName)) {
				name = line[1].trim();
				contestant = getContestant(startNumberOrClassName, db);
				contestant.putInformation("Namn", name);
				contestant.setClassName(className);
				db.addContestantEntry(startNumberOrClassName, contestant);
			} else {
				className = startNumberOrClassName;
			}
		}
	}

	private boolean isStartNumber(String startNumber) {
		return startNumber.matches("[1-9][0-9]*") || startNumber.equals("x");
	}

	private void readContestantColumns(String[] contestantColumns) {
		for (int i = 0; i < contestantColumns.length; i++) {
			contestantColumns[i] = contestantColumns[i].trim();
		}
		cf.setContestantColumnNames(contestantColumns);
	}

	/**
	 * Reads the specified file and interprets it as a list of start times. The
	 * results are put into the specified database, creating new
	 * <code>Contestants</code> if there are none with the correct starting
	 * numbers.
	 * 
	 * @param file
	 *            The file to load the start times from
	 * @param db
	 *            The database to put the start times into
	 * @throws IOException
	 *             If the file doesn't exist or couldn't be closed
	 */
	public void readStartTime(File file, Database db) throws IOException {
		readTime(file, db, false);
	}

	/**
	 * Reads the specified file and interprets it as a list of finish times. The
	 * results are put into the specified database, creating new
	 * <code>Contestants</code> if there are none with the correct starting
	 * numbers. All the times loaded will be set as finish times, creating no
	 * lap times.
	 * 
	 * @param file
	 *            The file to load the finish times from
	 * @param db
	 *            The database to put the finish times into
	 * @throws IOException
	 *             If the file doesn't exist or couldn't be closed
	 */
	public void readFinishTime(File file, Database db)
			throws IOException {
		readTime(file, db, true);
	}
	
	private void readTime(File file, Database db, boolean isFinishTime) throws IOException {
		List<String[]> data = readCSV(file);

		for (String[] line : data) {
			String startNr = line[0];
			Time time = new Time("00.00.00");
			if (line[1].trim().length() > 0) {
				time = new Time(line[1].trim());
			}

			AbstractContestant contestant = getContestant(startNr, db);
			if(isFinishTime){
				contestant.addFinishTime(time);
			}else{
				contestant.addStartTime(time);
			}
		}
	}
	
	private AbstractContestant getContestant(String startNr, Database db) {
		AbstractContestant contestant = db.getContestant(startNr);
		if (contestant == null) {
			contestant = cf.createContestant();
			contestant.putInformation("StartNr", startNr);
			db.addContestantEntry(startNr, contestant);
		}
		return contestant;
	}
	

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
			List<String[]> names = readCSV(nameFile);
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
			List<String[]> nameFile = readCSV(timeFile);
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
