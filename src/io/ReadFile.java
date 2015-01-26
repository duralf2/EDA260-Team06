package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

public class ReadFile {

	public static List<String[]> readCSV(File file) throws IOException {
		Reader reader = new FileReader(file);
		CSVReader<String[]> csvParser = CSVReaderBuilder
				.newDefaultReader(reader);
		return csvParser.readAll();
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
		for (String[] line : data) {
			startNr = line[0];
			time = line[1].trim();
			contestant = getContestant(startNr, ds);
			contestant.setFinishTime(new Time(time));
		}
	}

	private static Contestant getContestant(String startNr, DataStructure ds) {
		Contestant contestant = ds.getContestant(startNr);
		if (contestant == null) {
			// TODO: Is there a better way? I don't know... Perhaps throw an exception?
			contestant = new Contestant("NAME MISSING");
			ds.addContestantEntry(startNr, contestant);
		}
		return contestant;
	}
}
