package io;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import register.model.Contestant;
import register.model.DataStructure;

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
			if (contestant.getStartTime() == null
					|| contestant.getFinishTime() == null) {
				sb.append("--.--.--" + ";");
			} else {
				sb.append(contestant.getTotalTime() + ";");
			}
			if (contestant.getStartTime() == null) {
				sb.append("Start?" + ";");
			} else {
				sb.append(contestant.getStartTime() + ";");
			}
			if (contestant.getFinishTime() == null) {
				sb.append("Slut?" + "\n");
			} else {
				if (contestant.getStartTime() != null &&
						Integer.parseInt(contestant.getTotalTime().substring(0, 2)) < 1
						&& Integer.parseInt(contestant.getTotalTime().substring(3, 5)) <= 15) {
					sb.append(contestant.getFinishTime() + ";" + "Omöjlig totaltid?" + "\n");
				} else {
					sb.append(contestant.getFinishTime() + "\n");
				}
			}

		}
		pw.write(sb.toString());
		pw.close();
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

	public static void readContestantList(FileReader reader, DataStructure ds) {
		CSVReader<String[]> csvParser = CSVReaderBuilder
				.newDefaultReader(reader);
		try {
			List<String[]> data = csvParser.readAll();

			Iterator<String[]> iter = data.iterator();
			iter.next();
			while (iter.hasNext()) {
				String[] line = iter.next();
				ds.addContestantEntry(line[0].trim(),
						new Contestant(line[1].trim()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
