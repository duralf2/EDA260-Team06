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
	
	
	public FileWriter(String targetPath) {
		this.target = new File(targetPath);
	}
	
	public FileWriter(File file){
		target = file;
	}
	
	public void writeSortedResult(SortedSet<AbstractContestant> contestants, Configuration conf, Database db) throws FileNotFoundException, IOException {
		StringBuilder sb = new StringBuilder();
		
		CompetitionFactory competitionFactory = new CompetitionFactory(conf);
		CompetitionType competition = competitionFactory.createCompetition(db);
		//Write header to file
<<<<<<< HEAD
		
		sb.append(competition.generateHeader());
=======
		printString(competition.print());
>>>>>>> branch 'RefactorBranch' of https://github.com/duralf2/EDA260-Team06.git
	
		for(AbstractContestant contestant : contestants){
			//sb.append(contestant.toString(competition));
			//sb.append("\n");
		}
		
		//printString(sb.toString());
	}
	
	public static void writeResultImproved(PrintWriter pw, Database db){
		
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

