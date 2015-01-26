package io;

import java.io.PrintWriter;
import java.util.Map;

import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

public class IOHandler {

	public static void writeResult(PrintWriter pw, DataStructure ds)
	{
		StringBuilder sb = new StringBuilder();
		Map <String, Contestant> entries = ds.getAllContestantEntries();
		sb.append("StartNr; TotalTid; Starttid; Måltid\n");
		// loopa genom ds.driver/time
		for (String s: entries.keySet()){
			sb.append(s + "; ");
			sb.append(entries.get(s).getTotalTime() + "; ");
			sb.append(entries.get(s).getStartTime() + "; ");
			sb.append(entries.get(s).getFinishTime() + "\n");
		}
		pw.write(sb.toString());
		pw.close();
	}

	public static void writeStartTimes(PrintWriter pw, DataStructure ds)
	{
		Map <String, Contestant> entries = ds.getAllContestantEntries();

		StringBuilder sb = new StringBuilder();
		for (String s: entries.keySet()){
			sb.append(s + ";");
			sb.append(entries.get(s).getStartTime() + "\n");
		}
		pw.write(sb.toString());
		pw.close();
	}

	public static void writeFinishTimes(PrintWriter pw, DataStructure ds)
	{
		Map <String, Contestant> entries = ds.getAllContestantEntries();
		StringBuilder sb = new StringBuilder();
		for (String s: entries.keySet()){
			sb.append(s + ";");
			sb.append(entries.get(s).getFinishTime() + "\n");
		}
		pw.write(sb.toString());
		pw.close();
	}
}
