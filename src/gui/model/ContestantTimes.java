package gui.model;


import io.FileReaderGUI;
import io.FileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Set;
import java.util.TreeMap;

public class ContestantTimes extends Observable {
	private Map<String, ArrayList<String>> times;
	private List<String> errorTimes;
	private ArrayList<String> registeredContestants;
	private File nameFile;
	private File timeFile;

	public ContestantTimes(File nameFile, File timeFile) {
		this.nameFile = nameFile;
		this.timeFile = timeFile;
		registeredContestants = new ArrayList<String>();
		times = new TreeMap<String, ArrayList<String>>(new StartNumberComparator());
		errorTimes = new LinkedList<String>();
		readContestantsFromFile();
		readTimesFromFile();
	}

	public void addTime(String startNumber, String time) {
		ArrayList<String> contestantTimes = times.get(startNumber);
		if (contestantTimes == null) {
			contestantTimes = new ArrayList<String>();
			times.put(startNumber, contestantTimes);
		}
		contestantTimes.add(time);

		writeTimesToFile();
		setChanged();
		notifyObservers();
	}

	public void preRegister(String time) {
		ArrayList<String> preRegisteredTime = new ArrayList<String>();
		preRegisteredTime.add(time);
		times.put("Pre-registered time", preRegisteredTime);

		writeTimesToFile();
		setChanged();
		notifyObservers();
	}

	public void removePreRegisteredTime() {
		times.remove("Pre-registered time");

		writeTimesToFile();
		setChanged();
		notifyObservers();
	}

	public String getPreRegisteredTime() {
		if (times.containsKey("Pre-registered time")) {
			ArrayList<String> preRegisteredTime = times.get("Pre-registered time");
			return preRegisteredTime.get(0);
		} else {
			return null;
		}
	}

	public void performMassStart(String time) {
		Set<Entry<String, ArrayList<String>>> entries = times.entrySet();

		for (Entry<String, ArrayList<String>> e : entries) {
			e.getValue().add(time);
		}
		writeTimesToFile();
		setChanged();
		notifyObservers();
	}

	public boolean isRegistered(String startNumber) {
		return registeredContestants.contains(startNumber);
	}

	private void readContestantsFromFile() {
		registeredContestants = FileReaderGUI.readStartNumbers(nameFile);
		for (String startNumber : registeredContestants) {
			times.put(startNumber, new ArrayList<String>());
		}
		setChanged();
		notifyObservers();
	}

	public void readTimesFromFile() {
		errorTimes = FileReaderGUI.readTimesFromFile(timeFile, this);
		writeTimesToFile();
		setChanged();
		notifyObservers();
	}

	public void writeTimesToFile() {
		PrintWriter pw = null;
		Map<String, ArrayList<String>> times = new TreeMap<String, ArrayList<String>>(new StartNumberComparator());
		times.putAll(this.times);
		ArrayList<String> emptyList = new ArrayList<String>();
		for (String str : errorTimes) {
			times.put(str, emptyList);
		}
		try {
			pw = new PrintWriter(timeFile);
			FileWriter.writeTimesToFile(pw, times);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}

	public Map<String, ArrayList<String>> getTimes() {
		return times;
	}
}
