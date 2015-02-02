package io;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java.util.Map;

import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

public class FileWriter {

	public static void writeResult(PrintWriter pw, DataStructure ds) {
		StringBuilder sb = new StringBuilder();
		Map<String, Contestant> entries = ds.getAllContestantEntries();
		sb.append("StartNr; Namn; TotalTid; Starttider; Måltider\n"); // TODO
																		// how
																		// to
																		// handle
																		// setContestantColumnNames()
																		// in
																		// Datastrucure?
		Contestant contestant;
		for (String startNumber : entries.keySet()) {
			contestant = entries.get(startNumber);
			sb.append(startNumber + "; ");
			sb.append(contestant.getName() + "; ");

			writeTotalTime(contestant, sb);

			if (contestant.startTimeSize() == 0) {
				sb.append("Start?" + "; ");
			} else {
				sb.append(contestant.getStartTime() + "; ");
			}
			if (contestant.finishTimeSize() == 0) {
				sb.append("Slut?");
			} else {
				if (isImpossibleTime(contestant)) {
					sb.append(contestant.getFinishTime() + "; "
							+ "Omöjlig totaltid?");
				} else {
					sb.append(contestant.getFinishTime());
				}
			}
			checkMultipleTimes(contestant, sb);
			sb.append("\n");

		}

		pw.write(sb.toString());
		pw.close();
	}

	public static void writeLapResult(PrintWriter pw, DataStructure ds) {
		StringBuilder sb = new StringBuilder();
		Map<String, Contestant> entries = ds.getAllContestantEntries();
		int maxLaps = ds.getMaxLaps();

		sb.append("StartNr;Namn;");
		if (maxLaps > 1) {
			sb.append("#Varv;");
		}
		sb.append("TotalTid;");
		for (int i = 1; i <= maxLaps; i++)
			sb.append("Varv" + i);
		sb.append("Starttid;");
		// TODO: Varvningstider (absoluta)
		for (int i = 1; i <= maxLaps; i++)
			sb.append("Varvning" + i);
		sb.append("Måltid\n");
		Contestant contestant;
		for (String startNumber : entries.keySet()) {
			contestant = entries.get(startNumber);
			sb.append(startNumber + ";");
			sb.append(contestant.getName() + ";");

			if (maxLaps > 1) {
				sb.append(contestant.getLapsCompleted()).append(";");
			}

			writeTotalTime(contestant, sb);

			if (contestant.startTimeSize() == 0)
				sb.append("Start?;");
			else
				sb.append(contestant.getStartTime() + ";");

			for (String time : contestant.getLapDurations())
				sb.append(time + ";");

			for (int i = contestant.getLapTimes().size(); i <= maxLaps; i++)
				sb.append(" ;");

			if (contestant.finishTimeSize() == 0) {
				sb.append("Slut?");
			} else {
				if (isImpossibleTime(contestant)) {
					sb.append(contestant.getFinishTime() + ";"
							+ "Omöjlig totaltid?");
				} else {
					sb.append(contestant.getFinishTime());
				}
			}
			checkMultipleTimes(contestant, sb);
			sb.append("\n");

		}
		pw.write(sb.toString());
		pw.close();
	}

	// TODO - implement task 6.3 6.4
	private static void checkMultipleTimes(Contestant contestant,
			StringBuilder sb) {
		checkMultipleTimesStart(contestant, sb);
		checkMultipleTimesFinish(contestant, sb);
	}

	private static void checkMultipleTimesFinish(Contestant contestant,
			StringBuilder sb) {
		if (contestant.finishTimeSize() > 1) {
			sb.append("; " + "Flera måltider?");
			LinkedList<Time> finishTimes = contestant.getFinishTimes();
			Iterator<Time> iterator = finishTimes.iterator();
			iterator.next();
			while (iterator.hasNext()) {
				sb.append(" " + iterator.next());
			}
		}
	}

	private static void checkMultipleTimesStart(Contestant contestant,
			StringBuilder sb) {
		if (contestant.startTimeSize() > 1) {
			sb.append("; " + "Flera starttider?");
			LinkedList<Time> startTimes = contestant.getStartTimes();
			Iterator<Time> iterator = startTimes.iterator();
			iterator.next();
			while (iterator.hasNext()) {
				sb.append(" " + iterator.next());
			}
		}
	}

	private static void writeTotalTime(Contestant contestant, StringBuilder sb) {
		// Getting sizes of lists containing starttimes and finishtimes, if size
		// = 0 time is missing
		if (contestant.startTimeSize() == 0 || contestant.finishTimeSize() == 0) {
			sb.append("--.--.--" + "; ");
		} else {
			sb.append(contestant.getTotalTime() + "; ");
		}
	}

	private static boolean isImpossibleTime(Contestant contestant) {
		boolean impossible;
		try {
			impossible = contestant.startTimeSize() != 0
					&& Integer.parseInt(contestant.getTotalTime().substring(0,
							2)) < 1
					&& Integer.parseInt(contestant.getTotalTime().substring(3,
							5)) <= 15;
		} catch (IllegalArgumentException e) {
			return true; // negative total time throws the exception.
		}
		return impossible;
	}

	public static void writeFinishTimes(PrintWriter pw, DataStructure ds) {
		Map<String, Contestant> entries = ds.getAllContestantEntries();
		StringBuilder sb = new StringBuilder();
		Contestant contestant;
		for (String startNumber : entries.keySet()) {
			contestant = entries.get(startNumber);
			printTimes(contestant.getFinishTimes(), sb, startNumber);
		}
		pw.append(sb.toString());
	}

	private static void printTimes(LinkedList<Time> timeList, StringBuilder sb,
			String startNumber) {
		for (Time time : timeList) {
			sb.append(startNumber.toString() + "; ");
			sb.append(time.toString() + "\n");
		}
	}
}