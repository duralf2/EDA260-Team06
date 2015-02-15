package io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import register.model.AbstractContestant;
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
	 * Reads the contents of the specified file and interprets it as a name file.
	 *  All the names that are loaded are put into the specified database,
	 *  creating new <code>Contestants</code> if there are none with the
	 *  correct starting numbers. 
	 * @param file The file to load the names from
	 * @param db The database to put the names into
	 * @throws IOException If the file doesn't exist or couldn't be closed
	 */
	public static void readNames(File file, Database db)
			throws IOException {
		List<String[]> data = readCSV(file);

		// Read and remove column names
		readContestantColumns(db, data.get(0));
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

    private static boolean isStartNumber(String startNumber) {
        return startNumber.matches("[1-9][0-9]*") || startNumber.equals("x");
    }

	private static void readContestantColumns(Database ds,
			String[] contestantColumns) {
//		for (int i = 0; i < contestantColumns.length; i++) {
//			contestantColumns[i] = contestantColumns[i].trim();
//		}
//		ds.setContestantColumnNames(contestantColumns);
		//TODO: CompetitionType controls columns
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
	public static void readStartTime(File file, Database ds)
			throws IOException {
		List<String[]> data = readCSV(file);

		String startNr, time;
		AbstractContestant contestant;
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
     *  results are put into the specified database, creating new
     *  <code>Contestants</code> if there are none with the correct starting
     *  numbers. All the times loaded will be set as finish times, creating no
     *  lap times.
     * @param file The file to load the finish times from
	 * @param ds The database to put the finish times into
	 * @throws IOException If the file doesn't exist or couldn't be closed
     */
    public static void readFinishTime(File file, Database ds)
            throws IOException {
        readFinishTime(file, ds, new Time("00.00.00"));
    }

    /**
     * Reads the specified file and interprets it as a list of finish times. The
     *  results are put into the specified database, creating new
     *  <code>Contestants</code> if there are none with the correct starting
     *  numbers. Since finish times are registered once for every lap, only the
     *  finish time recorded after the time limit of the race ran out will be
     *  set as the finish time, the rest of the times will be set as lap times.
     * @param file The file to load the finish times from
	 * @param ds The database to put the finish times into
	 * @param racetime The lenght of the race, determines whether a specific
	 *  time will be interpreted as a finish time or a lap time
	 * @throws IOException If the file doesn't exist or couldn't be closed
     */
	public static void readFinishTime(File file, Database ds, Time racetime)
			throws IOException {
		List<String[]> data = readCSV(file);

		String startNr;
		AbstractContestant contestant;
		//TODO: loop below is almost identical to readStartTime()
		for (String[] line : data) {
			startNr = line[0];
			Time time = new Time("00.00.00");
			if (line[1].trim().length() > 0 && line[1].trim() != null)
				time = new Time(line[1].trim());
			contestant = getContestant(startNr, ds);
			Time startTime = new Time("00.00.00");
			
			if(contestant.startTimeSize() > 0){
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

	private static AbstractContestant getContestant(String startNr, Database db) {
		AbstractContestant contestant = db.getContestant(startNr);
		if (contestant == null) {
			contestant = new MarathonContestant();
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
	 * @param timeFile File to read.
	 * @param times ContestantTimes instance to hold the data.
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
