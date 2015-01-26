package io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

public class ReadFile {

	public static List<String[]> readCSV(File file) throws IOException {
		Reader reader = new FileReader(file);
		CSVReader<String[]> csvParser = CSVReaderBuilder
				.newDefaultReader(reader);
		return csvParser.readAll();
	}

	public static void readNames(File file, DataStructure ds)
			throws IOException {
		List<String[]> data = readCSV(file);

		// Read and remove column names
		readContestantColumns(ds, data.get(0));
		data.remove(0);

		String startNumber, name;
		Contestant contestant;
		for (String[] line : data) {
			startNumber = line[0];
			name = line[1].trim();
			contestant = new Contestant(name);
			ds.addContestantEntry(startNumber, contestant);
		}
	}

	private static void readContestantColumns(DataStructure ds,
			String[] contestantColums) {
		for (int i = 0; i < contestantColums.length; i++) {
			contestantColums[i] = contestantColums[i].trim();
		}
		ds.setContestantColumnNames(contestantColums);
	}

	public static void readStartTime(File file, DataStructure ds)
			throws IOException {
		List<String[]> data = readCSV(file);

		String startNr, time;
		Contestant contestant;
		for (String[] line : data) {
			startNr = line[0];
			time = line[1].trim();
			contestant = getContestant(startNr, ds);
			contestant.setStartTime(new Time(time));
		}
	}

	public static void readFinishTime(File file, DataStructure ds)
			throws IOException {
		List<String[]> data = readCSV(file);

		String startNr, time;
		Contestant contestant;
		//TODO: loop below is almost identical to readStartTime()
		for (String[] line : data) {
			startNr = line[0];
			time = line[1].trim();
			contestant = getContestant(startNr, ds);
			contestant.setFinishTime(new Time(time));
		}
	}
	
	public static void readResult(File file, DataStructure ds)
			throws IOException {
		List<String[]> data = readCSV(file);
		// Remove column names
		data.remove(0);

		String startNumber, name, totalTime, startTime, finishTime;
		Contestant contestant;
		for (String[] line : data) {
			startNumber = line[0];
			name = line[1].trim();
			startTime = line[3].trim();
			finishTime = line[4].trim();
			contestant = new Contestant(name);
			System.out.println(startTime + ";" + finishTime);
			if (!startTime.equals("Start?")) {
				contestant.setStartTime(new Time(startTime));
			}
			if (!finishTime.equals("Slut?")) {
				contestant.setFinishTime(new Time(finishTime));
			}
			ds.addContestantEntry(startNumber, contestant);
		}
	}

	private static Contestant getContestant(String startNr, DataStructure ds) {
		Contestant contestant = ds.getContestant(startNr);
		if (contestant == null) {
			// TODO: Is there a better way? I don't know... Perhaps throw an
			// exception?
			contestant = new Contestant("NAME MISSING");
			ds.addContestantEntry(startNr, contestant);
		}
		return contestant;
	}
}
