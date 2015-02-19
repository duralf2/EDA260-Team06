package io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import register.model.Contestant;
import register.model.ContestantTimes;
import register.model.DataStructure;
import register.model.Time;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

/**
 * This class is responsible for reading files formatted in the excel file
 * format and store their contents into a database.
 */
public class ReadFile {

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
	 * @param ds
	 *            The database to put the names into
	 * @throws IOException
	 *             If the file doesn't exist or couldn't be closed
	 */
	public static void readNames(File file, DataStructure ds)
			throws IOException {
		List<String[]> data = readCSV(file);

		// Read and remove column names
		readContestantColumns(ds, data.get(0));
		data.remove(0);

		String startNumberOrClassName, name, className = "";
		Contestant contestant;

		for (String[] line : data) {
			startNumberOrClassName = line[0];
            if (isStartNumber(startNumberOrClassName)) {
                name = line[1].trim();
                contestant = getContestant(startNumberOrClassName, ds);
                contestant.setName(name);
                contestant.setClassName(className);
                ds.addContestantEntry(startNumberOrClassName, contestant);
            } else {
                className = startNumberOrClassName;
            }
		}
	}

    private static boolean isStartNumber(String startNumber) {
        return startNumber.matches("[1-9][0-9]*") || startNumber.equals("x");
    }

	private static void readContestantColumns(DataStructure ds,
			String[] contestantColumns) {
		for (int i = 0; i < contestantColumns.length; i++) {
			contestantColumns[i] = contestantColumns[i].trim();
		}
		ds.setContestantColumnNames(contestantColumns);
	}

	/**
	 * Reads the specified file and interprets it as a list of start times. The
	 * results are put into the specified database, creating new
	 * <code>Contestants</code> if there are none with the correct starting
	 * numbers.
	 * 
	 * @param file
	 *            The file to load the start times from
	 * @param ds
	 *            The database to put the start times into
	 * @throws IOException
	 *             If the file doesn't exist or couldn't be closed
	 */
	public static void readStartTime(File file, DataStructure ds)
			throws IOException {
		List<String[]> data = readCSV(file);

		String startNr, time;
		Contestant contestant;
		for (String[] line : data) {
			startNr = line[0];
			if (line.length > 1) {
				time = line[1].trim();
				contestant = getContestant(startNr, ds);
				if (time.length() > 0 && time != null)
					contestant.addStartTime(new Time(time));
			}
		}
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
	 * @param ds
	 *            The database to put the finish times into
	 * @throws IOException
	 *             If the file doesn't exist or couldn't be closed
	 */
	public static void readFinishTime(File file, DataStructure ds)
			throws IOException {
		readFinishTime(file, ds, new Time("00.00.00"));
	}

	/**
	 * Reads the specified file and interprets it as a list of finish times. The
	 * results are put into the specified database, creating new
	 * <code>Contestants</code> if there are none with the correct starting
	 * numbers. Since finish times are registered once for every lap, only the
	 * finish time recorded after the time limit of the race ran out will be set
	 * as the finish time, the rest of the times will be set as lap times.
	 * 
	 * @param file
	 *            The file to load the finish times from
	 * @param ds
	 *            The database to put the finish times into
	 * @param racetime
	 *            The lenght of the race, determines whether a specific time
	 *            will be interpreted as a finish time or a lap time
	 * @throws IOException
	 *             If the file doesn't exist or couldn't be closed
	 */
	public static void readFinishTime(File file, DataStructure ds, Time racetime)
			throws IOException {
		List<String[]> data = readCSV(file);

		String startNr;
		Contestant contestant;
		// TODO: loop below is almost identical to readStartTime()
		for (String[] line : data) {
			startNr = line[0];
			Time time = new Time("00.00.00");
			if (line[1].trim().length() > 0 && line[1].trim() != null)
				time = new Time(line[1].trim());
			contestant = getContestant(startNr, ds);
			Time startTime = new Time("00.00.00");
			if (!contestant.getStartTimes().isEmpty()) {
				startTime = contestant.getStartTime();
			}
			if (time.compareTo(racetime.add(startTime)) <= 0) {
				// If time is less than racetime, it is a lap
				contestant.addLapTime(time);
			} else {
				// If time is more than racetime, it is a finish time
				contestant.addFinishTime(time);
			}
		}
	}

	/**
	 * Reads the specified file and interprets it as a result file. The loaded
	 * data is put into the specified database.
	 * 
	 * @param file
	 *            The file containing the results
	 * @param ds
	 *            The database to put the data into
	 * @throws IOException
	 *             If the file doesn't exist or couldn't be closed
	 * @deprecated This method is getting harder and harder to maintain, and it
	 *             doesn't satisfy the acceptance tests of the customer. A
	 *             better way to perform acceptance tests is required
	 */
	@Deprecated
	// TODO Deprecated; See the reason in the javadoc
	public static void readResult(File file, DataStructure ds)
			throws IOException {
		List<String[]> data = readCSV(file);
		// Remove column names
		String[] columns = data.remove(0);
		boolean hasLaps = columns[2].trim().equals("#Varv");

		int maxLaps = 0;
		if (hasLaps) {
			for (String[] line : data) {
				int laps = Integer.parseInt(line[2].trim());
				if (laps > maxLaps)
					maxLaps = laps;
			}
		}

		for (String[] line : data) {
			for (int i = 0; i < line.length; i++) {
				line[i] = line[i].trim();
			}
			Contestant contestant = new Contestant();
			String startNumber = line[0];
			contestant.setName(line[1].trim());

			int totalTime_index = hasLaps ? 3 : 2;
			int startTime_index = totalTime_index + maxLaps + 1;

			String startTime = line[startTime_index].trim();
			if (!startTime.contains("?") && !startTime.isEmpty())
				contestant.addStartTime(new Time(startTime));

			int laps = hasLaps ? Integer.parseInt(line[2].trim()) : 1;
			for (int i = 1; i <= maxLaps; i++) {
				String ts = line[startTime_index + i].trim();
				if (!ts.isEmpty() && !ts.contains("?")) {
					Time t = new Time(ts);
					contestant.addLapTime(t);
				}
			}

			String finishTime = line[startTime_index + maxLaps].trim();
			if (!(finishTime.contains("?") || finishTime.isEmpty()))
				contestant.addFinishTime(new Time(finishTime));

			ds.addContestantEntry(startNumber, contestant);
		}
	}

	private static Contestant getContestant(String startNr, DataStructure ds) {
		Contestant contestant = ds.getContestant(startNr);
		if (contestant == null) {
			contestant = new Contestant();
			ds.addContestantEntry(startNr, contestant);
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
			List<String[]> names = ReadFile.readCSV(nameFile);
			if (names.size() > 0) {
				Iterator<String[]> iterator = names.iterator();
				for (iterator.next(); iterator.hasNext();) {
					startNumbers.add(iterator.next()[0]);
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
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
			List<String[]> nameFile = ReadFile.readCSV(timeFile);
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
