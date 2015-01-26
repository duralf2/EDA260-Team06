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
		sb.append("StartNr; TotalTid; StartTider; Måltider\n");
		// loopa genom ds.driver/time
		for (String s: entries.keySet()){
			sb.append(s + "; ");
			sb.append("--.--.--; ");
			if(entries.get(s).getStartTime() == null){
				sb.append("Start?" + "; ");
			}else{

			sb.append(entries.get(s).getTotalTime() + "; ");
			}
			if(entries.get(s).getFinishTime() == null){
				sb.append("Slut?" + "\n");
			}else{
			sb.append(entries.get(s).getFinishTime() + "\n");
			}
		}
		pw.write(sb.toString());
		pw.close();
	}

	public static void writeStartTimes(PrintWriter pw, DataStructure ds)
	{
		Map <String, Contestant> entries = ds.getAllContestantEntries();

		StringBuilder sb = new StringBuilder();
		for (String s: entries.keySet()){
			sb.append(s + "; ");
			sb.append(entries.get(s).getStartTime() + "\n");
		}
		pw.write(sb.toString());
		pw.close();
	}

	public static void writeGoalTimes(PrintWriter pw, DataStructure ds)
	{
		// loopa genom ds.driver/time
		Map <String, Contestant> entries = ds.getAllContestantEntries();
		StringBuilder sb = new StringBuilder();
		for (String s: entries.keySet()){
			sb.append(s + "; ");
			sb.append(entries.get(s).getFinishTime() );
		}
		pw.write(sb.toString());
		pw.close();
	}
}
