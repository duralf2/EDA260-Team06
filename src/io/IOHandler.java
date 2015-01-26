package io;

import java.io.PrintWriter;
import java.util.Map;

import register.model.DataStructure;
import register.model.Time;

public class IOHandler {

	public static void writeResult(PrintWriter pw, DataStructure ds)
	{
		StringBuilder sb = new StringBuilder();
		Map <String, Time> entries = ds.getAllTimeEntries();
		sb.append("StartNr; TotalTid; Starttid; Måltid\n");
		// loopa genom ds.driver/time
		for (String s: entries.keySet()){
			sb.append(s + "; ");
			sb.append("--.--.--; ");
			sb.append(entries.get(s).getStartTime() + "; ");
			sb.append(entries.get(s).getFinishTime() + '\n');
		}
		pw.write(sb.toString());
		pw.close();
	}
	
	public static void writeStartTimes(PrintWriter pw, DataStructure ds)
	{
		// loopa genom ds.driver/time
		Map <String, Time> entries = ds.getAllTimeEntries();
		StringBuilder sb = new StringBuilder();
		for (String s: entries.keySet()){
			sb.append(s + "; ");
			sb.append(entries.get(s).getStartTime() );
		}
		pw.write(sb.toString());
		pw.close();
	}
	
	public static void writeGoalTimes(PrintWriter pw, DataStructure ds)
	{
		// loopa genom ds.driver/time
		Map <String, Time> entries = ds.getAllTimeEntries();
		StringBuilder sb = new StringBuilder();
		for (String s: entries.keySet()){
			sb.append(s + "; ");
			sb.append(entries.get(s).getFinishTime() );
		}
		pw.write(sb.toString());
		pw.close();
	}
}
