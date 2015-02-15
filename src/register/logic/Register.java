package register.logic;

import io.FileWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;

import register.model.AbstractContestant;
import register.model.Database;
import register.model.Time;

public class Register {
	private Database db;
	
	public static final File DEFAULT_RESULT_FILE = new  File("data/utdata.txt");
	public static final File DEFAULT_NAME_FILE   = new File("data/namn.txt");
	
	public Register(Database times) {
		this.db = times;
	}

	public void readStartTimes(File startTimes) throws IOException {
		if (startTimes.isFile()) {
			//ReadFile.readStartTime(startTimes, db);
		}
	}

	public void readGoalTimes(File goalTimes) throws IOException {
		if (goalTimes.isFile()) {
			//ReadFile.readFinishTime(goalTimes, db);
		}
	}
	
	public void readNames(File names) throws IOException {
		if (names.isFile()) {
			//ReadFile.readNames(names, db);
		}
	}

	public void writeResult() {
		File result = new File("resultat.txt");
		writeResult(result);
	}

	public void writeResult(File result) {
		try {
			PrintWriter pw = new PrintWriter(result);
			FileWriter.writeResult(pw, db);
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
	
	public void performMassStart(File targetFile)
	{
		Time startTime = new Time(Time.getCurrentTime());
		for (Entry<String, AbstractContestant> c : db.getAllContestantEntries().entrySet())
		{
			c.getValue().addStartTime(startTime);
			appendToFile(targetFile, c.getKey());
		}
	}

	public Database getDatabase() {
		return db;
	}
	
	public void writeFinishTimes() {
		try {
			PrintWriter pw = new PrintWriter(DEFAULT_RESULT_FILE);
			FileWriter.writeFinishTimes(pw, db);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
		
	public void clear()
	{
		db.getAllContestantEntries().clear();
	}
}
