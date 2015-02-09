package register.logic;

import io.FileWriter;
import io.ReadFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map.Entry;

import register.gui.RegistrationGUI;
import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

public class Register {
	private DataStructure ds;
	
	public static final File DEFAULT_RESULT_FILE = new  File("data/utdata.txt");
	public static final File DEFAULT_NAME_FILE   = new File("data/namn.txt");
	
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
	
	public void performMassStart(File targetFile)
	{
		Time startTime = new Time(Time.getCurrentTime());
		for (Entry<String, Contestant> c : ds.getAllContestantEntries().entrySet())
		{
			c.getValue().addStartTime(startTime);
			appendToFile(targetFile, c.getKey());
		}
	}

	public DataStructure getDataStructure() {
		return ds;
	}
	
	public void writeFinishTimes() {
		try {
			PrintWriter pw = new PrintWriter(DEFAULT_RESULT_FILE);
			FileWriter.writeFinishTimes(pw, ds);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
		
	public void clear()
	{
		ds.getAllContestantEntries().clear();
	}

	public void refreshEntryList() throws IOException {
		// TODO RegGui; Add the new time directly to the datastructure instead of clearing it and reading it all from a file
		getDataStructure().clearContestantEntries();
		readGoalTimes(Register.DEFAULT_RESULT_FILE);
		readNames(Register.DEFAULT_NAME_FILE);
	}
}
