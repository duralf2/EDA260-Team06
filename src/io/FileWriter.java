package io;

import gui.model.StartNumberComparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import sorter.model.AbstractContestant;
import sorter.model.CompetitionFactory;
import sorter.model.CompetitionType;
import sorter.model.Configuration;
import sorter.model.Database;

/**
 * This class is responsible for writing data to files. The methods of this
 * class formats the data accordingly before printing it to the
 * <code>PrintWriter</code> that is passed along with the data.
 */
public class FileWriter {
	private File target;

	/**
	 * Constructor for <code>FileRWriter</code>.
	 * 
	 * @param targetPath
	 *            The pathway to find the file where the data will be stored.
	 */
	public FileWriter(String targetPath) {
		target = new File(targetPath);

	}

	/**
	 * Constructor for <code>FileWriter</code>.
	 * 
	 * @param file
	 *            the file where the data will be stored.
	 */
	public FileWriter(File file) {
		target = file;
	}

	/**
	 * Write the result with all specified information sorted according to the
	 * competition type.
	 * 
	 * @param contestants
	 *            The contestants to the race.
	 * @param conf
	 *            The information about competition type, and what informaion we
	 *            have on the contestants.
	 * @param db
	 *            The database for the race.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	//TODO this method is not used - remove?
	public void writeSortedResult(ArrayList<AbstractContestant> contestants,
			Configuration conf, Database db) throws FileNotFoundException,
			IOException {
		StringBuilder sb = new StringBuilder();

		CompetitionFactory competitionFactory = new CompetitionFactory(conf);
		CompetitionType competition = competitionFactory.createCompetition(db);
		
		// Write header to file
		sb.append(competition.generateHeader());
		for (AbstractContestant contestant : contestants) {
			sb.append(contestant.toString(competition));
			sb.append("\n");
		}

		writeString(sb.toString());
	}

	/**
	 * Write the result with all specified information.
	 * 
	 * @param config
	 *            The information we have on the contestants.
	 * @param db
	 *            The database for the race
	 * @param useShortFormat TODO
	 * @throws IOException
	 */
	public void writeResults(Configuration config, Database db, boolean useShortFormat)
			throws IOException {
		CompetitionFactory competitionFactory = new CompetitionFactory(config);
		CompetitionType competition = competitionFactory.createCompetition(db);
		writeString(competition.toResultString(useShortFormat));
	}
	

	/**
	 * Writes a string to the file.
	 * 
	 * @param data
	 *            String to be printed
	 * @throws IOException
	 */
	public void writeString(String data) throws IOException {
		if (data != null) {
			File parentFile = target.getParentFile();
			if (parentFile != null)
				parentFile.mkdirs();
			java.io.FileWriter out = new java.io.FileWriter(target);
			out.write(data);
			out.close();
		} else {
			throw new IllegalArgumentException("Can't print null!");
		}
	}

	/**
	 * Write the result with all specified information with placement in race.
	 * 
	 * @param sortedContestants
	 *            The contestants to the race sorted by their placement in race.
	 * @param conf
	 *            The information about competition type, and what informaion we
	 *            have on the contestants.
	 * @param db
	 *            The database for the race.
	 * @throws IOException
	 */
	public void writeResultList(Configuration config, Database db, boolean useShortFormat)
			throws IOException {
			CompetitionFactory competitionFactory = new CompetitionFactory(config);
			CompetitionType competition = competitionFactory.createCompetition(db);
			writeString(competition.toResultStringWithPlacement(useShortFormat));
	}

	/**
	 * Prints start and finish time files based on the provided map containing
	 * start numbers as keys and arraylists of times in string format (HH.mm.ss)
	 * as values.
	 * 
	 * @param pw
	 *            PrintWriter to use for writing the time file.
	 * @param times
	 *            <StartNumber, ArrayList<Times in string format>>
	 */
	public static void writeTimesToFile(PrintWriter pw,
			Map<String, ArrayList<String>> times) {
		StringBuilder sb = new StringBuilder();
		Set<Entry<String, ArrayList<String>>> timeEntries = times.entrySet();
		for (Entry<String, ArrayList<String>> e : timeEntries) {
			String startNumber = e.getKey();
			ArrayList<String> entryTimes = e.getValue();
			for (String time : entryTimes) {
				sb.append(startNumber + "; " + time + "\n");
			}
			if (entryTimes.size() == 0 && !StartNumberComparator.isStartNumber(startNumber)) {
				sb.append(startNumber + "\n");
			}
		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		pw.print(sb.toString());
	}
}
