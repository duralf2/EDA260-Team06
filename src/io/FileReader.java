package io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import register.model.AbstractContestant;
import register.model.ContestantFactory;
import register.model.Database;
import register.model.Time;


/**
 * This class is responsible for reading files formatted in the excel file
 * format and store their contents into a database.
 */
public class FileReader {
	private ContestantFactory cf;

	public FileReader(ContestantFactory cf) {
		this.cf = cf;
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
		List<String[]> data = CSVReader.read(file);

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
		List<String[]> data = CSVReader.read(file);

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
}
