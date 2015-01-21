package io;

import java.io.PrintWriter;

import register.model.DataStructure;

public class IOHandler {

	public static void writeResult(PrintWriter pw, DataStructure ds)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("StartNr; TotalTid; Starttid; Måltid\n");
		sb.append("1; ");
		sb.append("--.--.--; ");
		sb.append("12.00.00; ");
		sb.append("13.23.34");
		pw.write(sb.toString());
	}
	
	public static void writeStartTimes(PrintWriter pw, DataStructure ds)
	{
		pw.write("1; 12.00.00"); 
	}
	
	public static void writeGoalTimes(PrintWriter pw, DataStructure ds)
	{
		pw.write("1; 13.23.34"); 
	}
	
}
