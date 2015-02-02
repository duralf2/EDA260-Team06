package register.logic;

import io.FileWriter;
import io.ReadFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import register.model.DataStructure;
import register.model.Time;

public class Register {
	private DataStructure ds;
	
	public static final File DEFAULT_RESULT_FILE = new File("testfiles/utdata.txt");

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
			PrintWriter pw = new PrintWriter(new BufferedWriter(
					new java.io.FileWriter(file, true)));
//			if (writeHeader(file))
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
}
