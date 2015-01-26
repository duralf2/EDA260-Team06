package register.logic;

import io.IOHandler;
import io.ReadFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;

import register.model.DataStructure;

public class Register {
	private DataStructure ds;
	private ReadFile reader;

	public Register(DataStructure times) {
		this.ds = times;
		reader = new ReadFile();
	}

	public void readStartTimes() throws IOException {
		File startTimes = new File("starttider.txt");
		readStartTimes(startTimes);
	}

	public void readStartTimes(File startTimes) throws IOException {
		reader.readStartTime(startTimes, ds);
	}

	public void readGoalTimes() throws IOException {
		File goalTimes = new File("maltider.txt");
		readGoalTimes(goalTimes);
	}

	public void readGoalTimes(File goalTimes) throws IOException {
		reader.readFinishTime(goalTimes, ds);
	}

	public void writeResult() {
		File result = new File("resultat.txt");
		writeResult(result);
	}

	public void writeResult(File result) {
		try {
			PrintWriter pw = new PrintWriter(result);
			IOHandler.writeResult(pw, ds);
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
