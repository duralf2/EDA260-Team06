package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;

import register.model.AbstractContestant;
import register.model.CompetitionFactory;
import register.model.CompetitionType;
import register.model.Configuration;
import register.model.Database;
import register.model.Time;

/**
 * This class is responsible for writing data to files. The methods of this
 * class formats the data accordingly before printing it to the
 * <code>PrintWriter</code> that is passed along with the data.
 */
public class FileWriter {
	private File target;
	
	public FileWriter(){
		target = null;
	}
	
	public FileWriter(String targetPath) {
		this.target = new File(targetPath);
	}
	
	public void writeSortedResult(SortedSet<AbstractContestant> contestants, Configuration conf, Database db) throws FileNotFoundException, IOException {
		StringBuilder sb = new StringBuilder();
		
		CompetitionFactory competitionFactory = new CompetitionFactory(conf);
		CompetitionType competition = competitionFactory.createCompetition(db);
		//Write header to file
		competition.print(target);
	
		for(AbstractContestant contestant : contestants){
			sb.append(contestant.toString(competition));
		}
		
		printString(sb.toString());
	}
	
	/**
	 * Prints the specified database to the specified stream. The data will be
	 * written in a format that is compatible with the excel file format. This
	 * method is to be used for simple races (marathon races).
	 * 
	 * @param pw
	 *            The <code>PrintWriter</code> to where the data will be written
	 * @param db
	 *            The database containing the data to write
	 */
	public static void writeResult(PrintWriter pw, Database ds) {
//		StringBuilder sb = new StringBuilder();
//		Map<String, Contestant> entries = ds.getAllContestantEntries();
//		sb.append("StartNr; Namn; TotalTid; Starttider; Måltider\n");
//
//		// TODO how to handle setContestantColumnNames() in Datastrucure?
//		
//		Contestant contestant;
//		for (String startNumber : entries.keySet()) {
//			contestant = entries.get(startNumber);
//			sb.append(startNumber + "; ");
//			sb.append(contestant.getName() + "; ");
//
//			writeTotalTime(contestant, sb);
//
//			if (contestant.startTimeSize() == 0) {
//				sb.append("Start?" + "; ");
//			} else {
//				sb.append(contestant.getStartTime() + "; ");
//			}
//			if (contestant.finishTimeSize() == 0) {
//				sb.append("Slut?");
//			} else {
//				if (isImpossibleTime(contestant)) {
//					sb.append(contestant.getFinishTime() + "; "
//							+ "Omöjlig totaltid?");
//				} else {
//					sb.append(contestant.getFinishTime());
//				}
//			}
//			checkMultipleTimes(contestant, sb);
//			sb.append("\n");
//
//		}
//
//		pw.write(sb.toString());
//		pw.close();
	}

	//TODO: Remove when writeResult implemented
	/**
	 * Prints the specified database to the specified stream. The data will be
	 * written in a format that is compatible with the excel file format. This
	 * method is to be used for lap races.
	 * 
	 * @param pw
	 *            The <code>PrintWriter</code> to where the data will be written
	 * @param db
	 *            The database containing the data to write
	 */
	public static void writeLapResult(PrintWriter pw, Database ds) {
//		StringBuilder sb = new StringBuilder();
//		Map<String, Contestant> entries = ds.getAllContestantEntries();
//		int maxLaps = ds.getMaxLaps();
//
//		makeColumnNames(sb, maxLaps);
//
//		List<String> incorrectRegistrations = new ArrayList<String>();
//		for (String startNumber : entries.keySet()) {
//			Contestant contestant = entries.get(startNumber);
//
//			if (contestant.getName().equals(""))
//				incorrectRegistrations.add(startNumber);
//			else {
//				writeContestant(sb, contestant, startNumber, maxLaps);
//			}
//		}
//
//		if (!incorrectRegistrations.isEmpty()) {
//			sb.append("Icke existerande startnummer\n");
//			makeColumnNames(sb, maxLaps);
//			for (String startNumber : incorrectRegistrations) {
//				writeContestant(sb, ds.getContestant(startNumber), startNumber,
//						maxLaps);
//			}
//		}
//
//		pw.write(sb.toString().replaceAll(";", "; ").trim());
//		pw.close();
	}


	//TODO: Remove when implemented toString for contestants
	private static void writeContestant(StringBuilder sb,
			AbstractContestant contestant, String startNumber, int maxLaps) {
//		sb.append(startNumber + ";");
//		sb.append(contestant.getName() + ";");
//
//		sb.append(contestant.getLapsCompleted()).append(";");
//		LinkedList<Time> finishTimes = contestant.getFinishTimes();
//		LinkedList<Time> lapTimes = contestant.getLapTimes();
//		if (finishTimes.size() > 0) {
//			sb.append(Time.getTotalTime(contestant.getStartTime(),
//					contestant.getFinishTime()));
//		} else {
//			if (lapTimes.size() != 0) {
//				sb.append(Time.getTotalTime(contestant.getStartTime(),
//						lapTimes.getLast()));
//			} else {
//				sb.append("--.--.--");
//			}
//		}
//		sb.append(";");
//
//		for (String time : contestant.getLapDurations())
//			sb.append(time + ";");
//		for (int i = contestant.getLapDurations().size(); i < maxLaps; i++)
//			sb.append(";");
//
//		if (contestant.startTimeSize() == 0)
//			sb.append("Start?;");
//		else
//			sb.append(contestant.getStartTime() + ";");
//
//		for (Time time : lapTimes)
//			sb.append(time.toString() + ";");
//		for (int i = lapTimes.size(); i < maxLaps - 1; i++)
//			sb.append(";");
//
//		if (contestant.finishTimeSize() == 0) {
//			// sb.append("Slut?");
//		} else {
//			if (isImpossibleTime(contestant)) {
//				sb.append(contestant.getFinishTime() + ";"
//						+ "Omöjlig totaltid?");
//			} else {
//				sb.append(contestant.getFinishTime());
//			}
//		}
//		checkMultipleTimes(contestant, sb);
//		sb.append("\n");
	}

	private static void checkMultipleTimes(AbstractContestant contestant,
			StringBuilder sb) {
//		checkMultipleTimesStart(contestant, sb);
//		checkMultipleTimesFinish(contestant, sb);
	}

	private static void checkMultipleTimesFinish(AbstractContestant contestant,
			StringBuilder sb) {
//		if (contestant.finishTimeSize() > 1) {
//			sb.append("; " + "Flera måltider?");
//			LinkedList<Time> finishTimes = contestant.getFinishTimes();
//			Iterator<Time> iterator = finishTimes.iterator();
//			iterator.next();
//			while (iterator.hasNext()) {
//				sb.append(" " + iterator.next());
//			}
//		}
	}

	private static void checkMultipleTimesStart(AbstractContestant contestant,
			StringBuilder sb) {
//		if (contestant.startTimeSize() > 1) {
//			sb.append("; " + "Flera starttider?");
//			LinkedList<Time> startTimes = contestant.getStartTimes();
//			Iterator<Time> iterator = startTimes.iterator();
//			iterator.next();
//			while (iterator.hasNext()) {
//				sb.append(" " + iterator.next());
//			}
//		}
	}

	//TODO: Remove since this will be taken care of in the individual classes
	private static void writeTotalTime(AbstractContestant contestant, StringBuilder sb) {
		// Getting sizes of lists containing starttimes and finishtimes, if size
		// = 0 time is missing
//		if (contestant.startTimeSize() == 0 || contestant.finishTimeSize() == 0) {
//			sb.append("--.--.--" + "; ");
//		} else {
//			sb.append(contestant.getTotalTime() + "; ");
//		}
	}

	private static boolean isImpossibleTime(AbstractContestant contestant) {
//		boolean impossible;
//		try {
//			impossible = contestant.startTimeSize() != 0
//					&& Integer.parseInt(contestant.getTotalTime().substring(0,
//							2)) < 1
//					&& Integer.parseInt(contestant.getTotalTime().substring(3,
//							5)) <= 15;
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace(); // negative total time throws the exception.
//			return true;
//		}
//		return impossible;
		return true;
	}

	public static void writeFinishTimes(PrintWriter pw, Database ds) {
//		Map<String, Contestant> entries = ds.getAllContestantEntries();
//		StringBuilder sb = new StringBuilder();
//		Contestant contestant;
//		for (String startNumber : entries.keySet()) {
//			contestant = entries.get(startNumber);
//			printTimes(contestant.getFinishTimes(), sb, startNumber);
//		}
//		pw.append(sb.toString());
	}

	private static void printTimes(LinkedList<Time> timeList, StringBuilder sb,
			String startNumber) {
//		for (Time time : timeList) {
//			sb.append(startNumber.toString() + "; ");
//			sb.append(time.toString() + "\n");
//		}
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
		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		pw.print(sb.toString());
	}
	
	/**
	 * Prints a string to a specified location(for example a file)
	 * @param data
	 * 				String to be printed
	 * @throws IOException
	 */
	public void printString(String data) throws IOException
	{
		if (data != null)
		{
			File parentFile = target.getParentFile();
			if (parentFile != null)
				parentFile.mkdirs();
			java.io.FileWriter out = new java.io.FileWriter(target);
			out.write(data);
			out.close();
		}
		else
		{
			throw new IllegalArgumentException("Can't print null!");
		}
	}
}

