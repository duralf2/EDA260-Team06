package sorter;

import io.FileReader;
import io.FileWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

import sorter.model.AbstractContestant;
import sorter.model.Configuration;
import sorter.model.ContestantFactory;
import sorter.model.Database;

/**
 * This class is responsible for loading the data collected during the contests
 * and compile it into sorted result files.
 */
public class Sorter {
	private Database db;
	private Configuration conf;
	private FileWriter fileWriter;
	private Map<String, AbstractContestant> contestants;

	public Sorter(Database db) throws IOException {
		this.db = db;
		conf = new Configuration();
	}

	public Sorter(Database db, Configuration conf) throws IOException {
		this.db = db;
		this.conf = conf;
		fileWriter = new FileWriter(
				conf.getProperty(Configuration.KEY_RESULT_FILE_PATH));
	}

	public void sortLapTimes(String nameFile, File startTime, File[] finishTimes) throws IOException {
		setUp(nameFile, startTime, finishTimes);
		
		ArrayList<AbstractContestant> list = new ArrayList<AbstractContestant>();
		for( AbstractContestant c : contestants.values()) {
			list.add(c);
		}
		fileWriter.writeSortedResult(list, conf, db);
	}
	
	

	public void setUp(String nameFile, File startTime, File[] finishTimes)
			throws IOException {
		if (!new File("data").isDirectory())
			// Create the data directory if it doesn't exist
			new File("data").mkdir();
		
		db.clearContestantEntries();

		ContestantFactory factory = new ContestantFactory(conf);
		
		FileReader read = new FileReader(factory);

		read.readNames(new File(nameFile), db);
		
		read.readStartTime(startTime, db);
		for (int i = 0; i < finishTimes.length; i++) {
			read.readFinishTime(finishTimes[i], db);
		}

		 contestants = db.getAllContestantEntries();
	}

    /**
	 * Reads and sorts the collected data. After the data is sorted it is
	 * printed to a results file.
	 * 
	 * @param files
	 *            All the files containing finish times
	 * @param nameFile
	 *            TODO
	 * @throws IOException
	 *             If any of the files doesn't exist or couldn't be closed
	 */
	public void sort(String nameFile, File startTime, File[] finishTimes)
			throws IOException {
		
		setUp(nameFile, startTime, finishTimes);


		LinkedList<AbstractContestant> sortedContestants = new LinkedList<AbstractContestant>();
		for (AbstractContestant c : contestants.values()) {
			sortedContestants.add(c);
		}
		Collections.sort(sortedContestants);

		
		fileWriter.writeResultList(sortedContestants, conf, db);
	}
}
