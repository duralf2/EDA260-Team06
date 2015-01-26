package register.logic;

import io.IOHandler;
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

	public void readStartTimes(File startTimes) throws IOException {
		if(startTimes.isFile()){
			ReadFile.readStartTime(startTimes, ds);
		}
	}

	public void readGoalTimes(File goalTimes) throws IOException {
		if(goalTimes.isFile()){
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
