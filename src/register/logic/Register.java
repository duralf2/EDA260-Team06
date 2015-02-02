package register.logic;

import io.FileWriter;
import io.ReadFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import register.model.DataStructure;
import register.model.Time;

public class Register {
	private DataStructure ds;
	
	public static final File DEFAULT_RESULT_FILE = new File("utdata.txt");
	public static final File DEFAULT_NAME_FILE   = new File("namn.txt");
	
	public Register(DataStructure times) {
		this.ds = times;
	}

	public void readStartTimes(File startTimes) throws IOException {
		if (startTimes.isFile()) {
			ReadFile.readStartTime(startTimes, ds);
		}
	}

	public void readGoalTimes(File goalTimes) throws IOException {
		if (goalTimes.isFile()) {
			ReadFile.readFinishTime(goalTimes, ds);
		}
	}
	
	public void readNames(File names) throws IOException {
		if (names.isFile()) {
			ReadFile.readNames(names, ds);
		}
	}

	public void writeResult() {
		File result = new File("resultat.txt");
		writeResult(result);
	}

	public void writeResult(File result) {
		try {
			PrintWriter pw = new PrintWriter(result);
			FileWriter.writeResult(pw, ds);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	//TODO: behandla headers i infilen till GUI
	public void appendToFile(File file, String startNumber) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			PrintWriter pw = new PrintWriter(new BufferedWriter(
					new java.io.FileWriter(file, true)));
//		if (writeHeader(file))
//				pw.println("StartNr; Tid");
			pw.println(startNumber + "; " + Time.getCurrentTime());
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//TODO: fixa \n i "tom" fil.
	private boolean writeHeader(File file) {
		return file.length() <= 2 || !file.exists();
	}

	/*
	 * Används i test
	 */
	public DataStructure getDataStructure() {
		return ds;
	}
	
	public void writeFinishTimes() {
		try {
			PrintWriter pw = new PrintWriter(DEFAULT_RESULT_FILE);
			FileWriter.writeFinishTimes(pw, ds);
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//TODO remove?
	public boolean isPreRegisteredTime() {
		try {
			List<String[]> data = ReadFile.readCSV(DEFAULT_RESULT_FILE);
			for(String[] line: data) {
				if(line[0].equals("x")) {
					data.remove(line);
					PrintWriter pw = new PrintWriter(new BufferedWriter(
							new java.io.FileWriter(DEFAULT_RESULT_FILE)));
					for (String[] t: data) {
						StringBuilder sb = new StringBuilder();
						for (String u: t) {
							sb.append(u).append(';');
						}
						sb.deleteCharAt(sb.length() - 1);
						pw.println(sb.toString());
					}
					pw.close();
					return true;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
		
	public void clear()
	{
		ds.getAllContestantEntries().clear();
	}
}
