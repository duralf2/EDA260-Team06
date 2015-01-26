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

import register.model.DataStructure;
import register.model.Time;

public class ReadFile {
	StringBuilder sb;

	public ReadFile() {
		sb = new StringBuilder();
		// br = new BufferedReader();
	}

	public boolean read(File file, DataStructure ds) throws IOException {
//		Reader reader;
		try {
			Reader reader = new FileReader(file);
			CSVReader<String[]> csvParser = CSVReaderBuilder.newDefaultReader(reader);
			List<String[]> data = csvParser.readAll();
			String startNr, startTime, endTime, totalTime;
			
			for (String[] line : data) {
				startNr = line[0];
				totalTime = line[1];
				totalTime =totalTime.replaceAll("\\s+","");
				startTime = line[2];
				startTime =startTime.replaceAll("\\s+","");
				endTime = line[3];
				endTime =endTime.replaceAll("\\s+",""); 
				ds.addTimeEntry(startNr, new Time(startTime, endTime));
//				for(String text : line){
//					sb.append(text);
//				}
			}

		} catch (IOException e) {
			throw e;
		} finally {
//			try {
//				r.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		return true;

	}

//	public String toString() {
//		return sb.toString();
//	}

}
