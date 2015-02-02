package register.logic;

import io.FileWriter;
import io.ReadFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import register.model.DataStructure;

public class Register {
	private DataStructure ds;

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

	public void appendToFile(File file, String startNumber) {
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(
					new java.io.FileWriter(file, true)));
			pw.println(startNumber
					+ "; "
					+ new SimpleDateFormat("HH:mm:ss").format(new Date()
							.getTime()));
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Används i test
	 */
	public DataStructure getDataStructure() {
		return ds;
	}
}
