package register.logic;

import io.FileWriter;
import io.ReadFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import register.model.DataStructure;

public class Register {
	private DataStructure ds;

	public Register(DataStructure times) {
		this.ds = times;
	}

	public void readStartTimes() throws IOException {
		File startTimes = new File("starttider.txt");
		readStartTimes(startTimes);
	}

	public void readStartTimes(File startTimes) throws IOException {
		ReadFile.readStartTime(startTimes, ds);
	}

	public void readGoalTimes() throws IOException {
		File goalTimes = new File("maltider.txt");
		readFinishTimes(goalTimes);
	}

	public void readFinishTimes(File goalTimes) throws IOException {
		ReadFile.readFinishTime(goalTimes, ds);
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

	/*
	 * Används i test
	 */
	public DataStructure getDataStructure() {
		return ds;
	}
}
