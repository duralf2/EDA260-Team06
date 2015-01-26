package io;

import java.io.BufferedReader;
import java.io.File;
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

	public ReadFile() {
	}

	//Template method
	public boolean readStartTime(File file, DataStructure ds) throws IOException {
		try {
			Reader reader = new FileReader(file);
			CSVReader<String[]> csvParser = CSVReaderBuilder
					.newDefaultReader(reader);
			List<String[]> data = csvParser.readAll();
			String startNr, time;
			Contestant contestant;
			for (String[] line : data) {
				startNr = line[0];
				time = line[1];
				time = time.replaceAll("\\s+", "");
				contestant = ds.getContestant(startNr);
				if (contestant == null) {
					contestant = new Contestant("");
					ds.addContestantEntry(startNr, contestant);
				}
				contestant.setStartTime(new Time(time));
			}
		} catch (IOException e) {
			throw e;
		} finally {
		}
		return true;
	}

	//Template method
	public boolean readFinishTime(File file, DataStructure ds) throws IOException {
		try {
			Reader reader = new FileReader(file);
			CSVReader<String[]> csvParser = CSVReaderBuilder
					.newDefaultReader(reader);
			List<String[]> data = csvParser.readAll();
			String startNr, time;
			Contestant contestant;
			for (String[] line : data) {
				startNr = line[0];
				time = line[1];
				time = time.replaceAll("\\s+", "");
				contestant = ds.getContestant(startNr);
				if (contestant == null) {
					contestant = new Contestant("");
					ds.addContestantEntry(startNr, contestant);
				}
				contestant.setFinishTime(new Time(time));
			}
		} catch (IOException e) {
			throw e;
		} finally {
		}
		return true;
	}


}
