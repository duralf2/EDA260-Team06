package io;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

public class FileWriter {

	public static void writeResult(PrintWriter pw, DataStructure ds) {
		StringBuilder sb = new StringBuilder();
		Map<String, Contestant> entries = ds.getAllContestantEntries();
		sb.append("StartNr; Namn; TotalTid; Starttid; Måltid\n");
		Contestant contestant;
		for (String startNumber : entries.keySet()) {
			contestant = entries.get(startNumber);
			sb.append(startNumber + ";");
			sb.append(contestant.getName() + ";");

			writeTotalTime(contestant, sb);

			if (contestant.startTimeSize() == 0) {
				sb.append("Start?" + ";");
			} else {
				sb.append(contestant.getStartTime() + ";");
			}
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
		if (contestant.startTimeSize() == 0 || contestant.finishTimeSize() == 0) {
			sb.append("--.--.--" + ";");
		} else {
			sb.append(contestant.getTotalTime() + ";");
		}
	}

	private static boolean isImpossibleTime(Contestant contestant) {
		boolean impossible;
		try {
			impossible = contestant.startTimeSize() != 0
					&& Integer.parseInt(contestant.getTotalTime().substring(0, 2)) < 1
					&& Integer.parseInt(contestant.getTotalTime().substring(3, 5)) <= 15;
		} catch (IllegalArgumentException e) {
			return true;		// negative total time throws the exception.
		}
		return impossible;
	}

	public static void writeStartTimes(PrintWriter pw, DataStructure ds) {
		Map<String, Contestant> entries = ds.getAllContestantEntries();
		Contestant contestant;
		StringBuilder sb = new StringBuilder();
		for (String s : entries.keySet()) {
			sb.append(s + ";");
			contestant = entries.get(s);
			if(contestant.startTimeSize() == 0)
				sb.append("Start?" + ";");
			else{
			sb.append(contestant.getStartTime());
			checkMultipleTimesStart(contestant,sb);
			}
			sb.append("\n");
		}
		pw.write(sb.toString());
		pw.close();
	}

	public static void writeFinishTimes(PrintWriter pw, DataStructure ds) {
		Map<String, Contestant> entries = ds.getAllContestantEntries();
		StringBuilder sb = new StringBuilder();
		Contestant contestant;
		for (String s : entries.keySet()) {
			sb.append(s + ";");
			contestant = entries.get(s);
			if(contestant.startTimeSize() == 0)
				sb.append("Start?" + ";");
			else{
			sb.append(contestant.getFinishTime());
			checkMultipleTimesStart(contestant,sb);
			}
			sb.append("\n");
		}
		//copy
		Time preRegistered = ds.removePreRegisteredTime();
		if (preRegistered != null) {
			sb.append("\n");
			sb.append("Förregistrerad tid;");
			sb.append(preRegistered.toString());
		}
		
		pw.write(sb.toString());
		pw.close();
	}
}
