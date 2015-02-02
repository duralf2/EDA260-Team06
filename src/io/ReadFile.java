package io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
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
			contestant = ds.getContestant(startNumber);
			if (contestant == null)
				contestant = new Contestant();
			contestant.setName(name);
			ds.addContestantEntry(startNumber, contestant);
		}
	}

	private static void readContestantColumns(DataStructure ds,
			String[] contestantColumns) {
		for (int i = 0; i < contestantColumns.length; i++) {
			contestantColumns[i] = contestantColumns[i].trim();
		}
		ds.setContestantColumnNames(contestantColumns);
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
			contestant.addStartTime(new Time(time));
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
			contestant.addFinishTime(new Time(time));
		}
	}
	
	public static void readResult(File file, DataStructure ds)
			throws IOException {
		List<String[]> data = readCSV(file);
		// Remove column names
		String[] columns = data.remove(0);
        boolean hasLaps = columns[2].trim().equals("#Varv");

        int maxLaps = 0;
        if(hasLaps) {
            for(String[] line : data) {
                int laps = Integer.parseInt(line[2].trim());
                if(laps > maxLaps) maxLaps = laps;
            }
        }

		for (String[] line : data) {
            for(int i=0; i<line.length; i++) {
                line[i] = line[i].trim();
            }
            Contestant contestant = new Contestant();
			String startNumber = line[0];
			contestant.setName(line[1].trim());

            int totalTime_index = hasLaps ? 3 : 2;
            int startTime_index = totalTime_index+maxLaps+1;

			String startTime = line[startTime_index].trim();
            if (!startTime.contains("?") && !startTime.isEmpty())
                contestant.addStartTime(new Time(startTime));

            int laps = hasLaps ? Integer.parseInt(line[2].trim()) : 1;
            for(int i=1; i<maxLaps; i++) {
                String ts = line[startTime_index+i].trim();
                if(!ts.isEmpty()) {
                    Time t = new Time(ts);
                    contestant.addLapTime(t);
                }
            }

			String finishTime = line[startTime_index+maxLaps].trim();
            if (!(finishTime.contains("?") || finishTime.isEmpty()))
                contestant.addFinishTime(new Time(finishTime));

            if(laps != contestant.getLapsCompleted()) {
                System.err.println("laps != laps loaded");
            }

			ds.addContestantEntry(startNumber, contestant);
		}
	}

	private static Contestant getContestant(String startNr, DataStructure ds) {
		Contestant contestant = ds.getContestant(startNr);
		if (contestant == null) {
			// TODO: Is there a better way? I don't know... Perhaps throw an
			// exception?
			contestant = new Contestant("");
			ds.addContestantEntry(startNr, contestant);
		}
		return contestant;
	}
}
