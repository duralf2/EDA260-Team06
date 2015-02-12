package io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import register.model.AbstractContestant;
import register.model.Database;
import register.model.MarathonContestant;
import register.model.Time;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

/**
 * This class is responsible for reading files formatted in the excel file format
 *  and store their contents into a database.
 */
public class ReadFile {

	/**
	 * Reads all the contents of the specified file and splits them into a list
	 *  of string arrays. Each element in the list represents a line in the
	 *  file, and each element in the string arrays represents a piece of text,
	 *  separated by semicolons.
	 * </br>
	 * </br>
	 * <b>Note:</b> Any whitespace following/preceding semicolons are left
	 *  untouched by this method! Therefore it might be necessary to trim the
	 *  elements of the string array after using this method.
	 * @param file The file to read
	 * @return The contents of the file, formatted as specified above
	 * @throws IOException If the file doesn't exist or couldn't be closed
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

		String startNumber, name;
		AbstractContestant contestant;
		for (String[] line : data) {
			startNumber = line[0];
			name = line[1].trim();
			contestant = getContestant(startNumber, db);
			contestant.putInformation("Namn", name);
			db.addContestantEntry(startNumber, contestant);
		}
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
     *  results are put into the specified database, creating new
     *  <code>Contestants</code> if there are none with the correct starting
     *  numbers.
     * @param file The file to load the start times from
	 * @param ds The database to put the start times into
	 * @throws IOException If the file doesn't exist or couldn't be closed
	 */
	public static void readStartTime(File file, Database ds)
			throws IOException {
		List<String[]> data = readCSV(file);

		String startNr, time;
		AbstractContestant contestant;
		for (String[] line : data) {
			startNr = line[0];
			time = line[1].trim();
			contestant = getContestant(startNr, ds);
			contestant.addStartTime(new Time(time));
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
			Time time = new Time(line[1].trim());
			contestant = getContestant(startNr, ds);
			Time startTime = new Time("00.00.00");
			if(!contestant.getStartTimes().isEmpty()){
				startTime = contestant.getStartTime();
			}
            if(time.compareTo(racetime.add(startTime)) <= 0) {
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
	 *  data is put into the specified database. 
	 * @param file The file containing the results
	 * @param ds The database to put the data into
	 * @throws IOException If the file doesn't exist or couldn't be closed
	 * @deprecated This method is getting harder and harder to maintain, and
	 *  it doesn't satisfy the acceptance tests of the customer. A better way to
	 *  perform acceptance tests is required
	 */
	@Deprecated // TODO Deprecated; See the reason in the javadoc
	public static void readResult(File file, Database ds)
			throws IOException {
		List<String[]> data = readCSV(file);
		// Remove column names
		String[] columns = data.remove(0);
        boolean hasLaps = columns[2].trim().equals("#Varv");

        int maxLaps = 0;
        if(hasLaps) {
            for(String[] line : data) {
                int laps = Integer.parseInt(line[2].trim());
                if(laps > maxLaps) maxLaps = laps;
            }
        }

		for (String[] line : data) {
            for(int i=0; i<line.length; i++) {
                line[i] = line[i].trim();
            }
            AbstractContestant contestant = new MarathonContestant();
			String startNumber = line[0];
			contestant.putInformation("Namn", line[1].trim());

            int totalTime_index = hasLaps ? 3 : 2;
            int startTime_index = totalTime_index+maxLaps+1;

			String startTime = line[startTime_index].trim();
            if (!startTime.contains("?") && !startTime.isEmpty())
                contestant.addStartTime(new Time(startTime));

            int laps = hasLaps ? Integer.parseInt(line[2].trim()) : 1;
            for(int i=1; i <= maxLaps; i++) {
                String ts = line[startTime_index+i].trim();
                if(!ts.isEmpty() && !ts.contains("?")) {
                    Time t = new Time(ts);
                    contestant.addLapTime(t);
                }
            }

			String finishTime = line[startTime_index+maxLaps].trim();
            if (!(finishTime.contains("?") || finishTime.isEmpty()))
                contestant.addFinishTime(new Time(finishTime));

			ds.addContestantEntry(startNumber, contestant);
		}
	}

	private static AbstractContestant getContestant(String startNr, Database ds) {
		AbstractContestant contestant = ds.getContestant(startNr);
		if (contestant == null) {
			contestant = new MarathonContestant();
			ds.addContestantEntry(startNr, contestant);
		}
		return contestant;
	}
}
