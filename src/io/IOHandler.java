package io;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

public class IOHandler {

	public static void writeResult(PrintWriter pw, DataStructure ds) {
		StringBuilder sb = new StringBuilder();
		Map<String, Contestant> entries = ds.getAllContestantEntries();
		sb.append("StartNr; Namn; TotalTid; Starttid; Måltid\n");
		Contestant contestant;
		for (String startNumber : entries.keySet()) {
			contestant = entries.get(startNumber);
			sb.append(startNumber + ";");
			sb.append(contestant.getName() + ";");

			setTotalTime(contestant, sb);

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
		if (contestant.startTimeSize() > 1) {
			sb.append("; " + "Flera starttider?");
			LinkedList<Time> startTimes = contestant.getStartTimes();
			Iterator<Time> iterator = startTimes.iterator();
			iterator.next();
			while (iterator.hasNext()) {
				sb.append(" " + iterator.next());
			}
		}
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

	private static void setTotalTime(Contestant contestant, StringBuilder sb) {
		if (contestant.startTimeSize() == 0 || contestant.finishTimeSize() == 0) {
			sb.append("--.--.--" + ";");
		} else {
			sb.append(contestant.getTotalTime() + ";");
		}
	}

	private static boolean isImpossibleTime(Contestant contestant) {
		return contestant.startTimeSize() != 0
				&& Integer.parseInt(contestant.getTotalTime().substring(0, 2)) < 1
				&& Integer.parseInt(contestant.getTotalTime().substring(3, 5)) <= 15;
	}

	public static void writeStartTimes(PrintWriter pw, DataStructure ds) {
		Map<String, Contestant> entries = ds.getAllContestantEntries();

		StringBuilder sb = new StringBuilder();
		for (String s : entries.keySet()) {
			sb.append(s + ";");
			sb.append(entries.get(s).getStartTime() + "\n");
		}
		pw.write(sb.toString());
		pw.close();
	}

	public static void writeFinishTimes(PrintWriter pw, DataStructure ds) {
		Map<String, Contestant> entries = ds.getAllContestantEntries();
		StringBuilder sb = new StringBuilder();
		for (String s : entries.keySet()) {
			sb.append(s + ";");
			sb.append(entries.get(s).getFinishTime() + "\n");
		}
		pw.write(sb.toString());
		pw.close();
	}

	public static void readContestantList(FileReader reader, DataStructure ds)
			throws IOException {
		CSVReader<String[]> csvParser = CSVReaderBuilder
				.newDefaultReader(reader);
		List<String[]> data = csvParser.readAll();

		Iterator<String[]> iterator = data.iterator();
		String[] contestantColums = iterator.next();
		readContestantColumns(ds, contestantColums);
		readContestants(ds, iterator);
	}

	private static void readContestantColumns(DataStructure ds,
			String[] contestantColums) {
		for (int i = 0; i < contestantColums.length; i++) {
			contestantColums[i] = contestantColums[i].trim();
		}
		ds.setContestantColumnNames(contestantColums);
	}

	private static void readContestants(DataStructure ds,
			Iterator<String[]> iterator) {
		while (iterator.hasNext()) {
			String[] line = iterator.next();
			String startNumber = line[0].trim();

			Contestant contestant = ds.getContestant(startNumber);
			if (contestant == null)
				contestant = new Contestant();
			contestant.setName(line[1].trim());

			ds.addContestantEntry(startNumber, contestant);
		}
	}
}
