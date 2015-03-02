package io;

import gui.model.StartNumberComparator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

	public FileWriter(String targetPath) {
		target = new File(targetPath);
	}

	public FileWriter(File file) {
		target = file;
	}

	/**
	 * Write the result with all specified information.
	 * 
	 * @param config
	 *            The information we have on the contestants.
	 * @param db
	 *            The database for the race
	 * @param useShortFormat
	 *            Whether or not to include the start/lap/finish times as well
	 *            as the total times
	 * @throws IOException
	 */
	public void writeResults(Configuration config, Database db,
			boolean useShortFormat) throws IOException {
		CompetitionFactory competitionFactory = new CompetitionFactory(config);
		CompetitionType competition = competitionFactory.createCompetition(db);
		
		String resultAsCsv = competition.toResultString(useShortFormat);
		boolean printAsHtml = config.getProperty(Configuration.KEY_RESULT_FORMAT).equals(Configuration.VALUE_FORMAT_HTML);
		writeString(printAsHtml ? new HTMLParser().resultToHTML(resultAsCsv) : resultAsCsv);
	}

	/**
	 * Writes a string to the file.
	 * 
	 * @param data
	 *            String to be printed
	 * @throws IOException
	 * @throws IllegalArgumentException If data is <code>null</code>
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
	 * @param config
	 *            The information about competition type, and what informaion we
	 *            have on the contestants.
	 * @param db
	 *            The database for the race.
	 * @param useShortFormat
	 *            Whether or not to include the start/lap/finish times as well
	 *            as the total times
	 * @throws IOException
	 */
	public void writeResultList(Configuration config, Database db, boolean useShortFormat)
			throws IOException {
			CompetitionFactory competitionFactory = new CompetitionFactory(config);
			CompetitionType competition = competitionFactory.createCompetition(db);
			String resultAsCsv;
			if( config.getProperty(Configuration.KEY_RESULT_SORTED).equals("true"))
				resultAsCsv = competition.toResultStringWithPlacement(useShortFormat);
			else
				resultAsCsv = competition.toResultString(useShortFormat);
		
		boolean printAsHtml = config.getProperty(Configuration.KEY_RESULT_FORMAT).equals(Configuration.VALUE_FORMAT_HTML);
		writeString(printAsHtml ? new HTMLParser().resultToHTML(resultAsCsv) : resultAsCsv);
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
			if (entryTimes.size() == 0
					&& !StartNumberComparator.isStartNumber(startNumber)) {
				sb.append(startNumber + "\n");
			}
		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		pw.print(sb.toString());
	}
}
