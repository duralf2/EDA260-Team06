package sorter;

import gui.RegistrationStarter;
import io.FileReader;
import io.FileWriter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

import sorter.model.AbstractContestant;
import sorter.model.Configuration;
import sorter.model.ContestantFactory;
import sorter.model.Database;

/**
 * This class is responsible for loading the data collected during the contests
 * and compile it into sorted result files.
 */
public class SorterSetUp {
	private Database db;
	private Configuration conf;
	private FileWriter fileWriter;
	private Map<String, AbstractContestant> contestants;

	public SorterSetUp(Database db) throws IOException {
		this.db = db;
		conf = new Configuration();
	}

	public SorterSetUp(Database db, Configuration conf) throws IOException {
		this.db = db;
		this.conf = conf;
		
		// Set the working directory of the program to the folder containing the
		// program.
		// If you double-click a jar-file in linux the working directory is set
		// to the user home by default.
		// We want it to be set to the folder of the program, therefore these
		// lines are necessary
		File workingDirectory;
		try {
			workingDirectory = new File(RegistrationStarter.class
					.getProtectionDomain().getCodeSource().getLocation().toURI())
					.getParentFile();
			System.setProperty("user.dir", workingDirectory.getParent());
			
			fileWriter = new FileWriter(new File(workingDirectory,
					conf.getProperty(Configuration.KEY_RESULT_FILE_PATH)));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates a sorted result file for a lap event based on the input files.
	 * 
	 * @param nameFile
	 *            a File object of the file with the contestant name and start
	 *            number mapping
	 * @param startTime
	 *            a File[] object containing the files with all the start times
	 * @param finishTimes
	 *            a File[] containing the files with finish times
	 * @throws IOException
	 *             if any of the files could not be read
	 */
	public void sortLapTimes(File nameFile, File[] startTime, File[] finishTimes)
			throws IOException {
		setUp(nameFile, startTime, finishTimes);

		ArrayList<AbstractContestant> list = new ArrayList<AbstractContestant>();
		for (AbstractContestant c : contestants.values()) {
			list.add(c);
		}
		fileWriter.writeResults(conf, db, false);
	}

	private void setUp(File nameFile, File[] startTimes, File[] finishTimes)
			throws IOException {
		if (!new File("data").isDirectory())
			// Create the data directory if it doesn't exist
			new File("data").mkdir();

		db.clearContestantEntries();

		ContestantFactory factory = new ContestantFactory(conf);

		FileReader read = new FileReader(factory);

		read.readNames(nameFile, db);

		for (int i = 0; i < startTimes.length; i++) {
			read.readStartTime(startTimes[i], db);
		}
		for (int i = 0; i < finishTimes.length; i++) {
			read.readFinishTime(finishTimes[i], db);
		}

		contestants = db.getAllContestantEntries();
	}

	/**
	 * Reads and sorts the collected data. After the data is sorted it is
	 * printed to a results file.
	 * 
	 * @param nameFile
	 *            a File object of the file with the contestant name and start
	 *            number mapping
	 * @param startTime
	 *            a File[] object containing the files with all the start times
	 * @param finishTimes
	 *            a File[] containing the files with finish times
	 * @throws IOException
	 *             If any of the files doesn't exist or couldn't be closed
	 */
	public void sort(File nameFile, File[] startTime, File[] finishTimes)
			throws IOException {
		setUp(nameFile, startTime, finishTimes);
		fileWriter.writeResultList(conf, db, true);
	}
}
