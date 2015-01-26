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
		sb.append("StartNr; Namn; TotalTid; Starttid; Måltid\n");
		Contestant contestant;
		for (String startNumber: entries.keySet()){
			contestant = entries.get(startNumber);
			sb.append(startNumber + ";");
			sb.append(contestant.getName()+";");
			sb.append(contestant.getTotalTime()+";");
			sb.append(contestant.getStartTime() + ";");
			sb.append(contestant.getFinishTime() + "\n");
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
