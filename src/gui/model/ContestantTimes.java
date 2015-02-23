package gui.model;


import io.FileReaderGUI;
import io.FileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Set;
import java.util.TreeMap;

/**
 * This class handles time registration logic.
 */
public class ContestantTimes extends Observable {
	private Map<String, ArrayList<String>> times;
	private ArrayList<String> registeredContestants;
	private File nameFile;
	private File timeFile;

	/**
	 * The constructor for <code>ContestantTimes</code>.
	 * 
	 * @param nameFile The file with names.
	 * @param timeFile The file with times.
	 */
	public ContestantTimes(File nameFile, File timeFile) {
		this.nameFile = nameFile;
		this.timeFile = timeFile;
		registeredContestants = new ArrayList<String>();
		times = new TreeMap<String, ArrayList<String>>(new StartNumberComparator());
		readContestantsFromFile();
		readTimesFromFile();
	}

	/**
	 * Adds a time to the start number.
	 * 
	 * @param startNumber The start number.
	 * @param time The time.
	 */
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

	/**
	 * Pre-registers a time.
	 * 
	 * @param time The time.
	 */
	public void preRegister(String time) {
		ArrayList<String> preRegisteredTime = new ArrayList<String>();
		preRegisteredTime.add(time);
		times.put("Pre-registered time", preRegisteredTime);
		writeTimesToFile();
		setChanged();
		notifyObservers();
	}

	/**
	 * Removes a pre-registered time.
	 */
	public void removePreRegisteredTime() {
		times.remove("Pre-registered time");

		writeTimesToFile();
		setChanged();
		notifyObservers();
	}

	/**
	 * Returns pre-registered time.
	 * 
	 * @return The pre-registered time, if no pre-registered time exists returns null.
	 */
	public String getPreRegisteredTime() {
		if (times.containsKey("Pre-registered time")) {
			ArrayList<String> preRegisteredTime = times.get("Pre-registered time");
			return preRegisteredTime.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Performs mass start.
	 * 
	 * @param time The time. 
	 */
	public void performMassStart(String time) {
		Set<Entry<String, ArrayList<String>>> entries = times.entrySet();

		for (Entry<String, ArrayList<String>> e : entries) {
			e.getValue().add(time);
		}
		writeTimesToFile();
		setChanged();
		notifyObservers();
	}

	/**
	 * Checks if the start number is registered.
	 * 
	 * @param startNumber The start number.
	 * @return True if start number is registered, otherwise False.
	 */
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

	/**
	 * Reads times from timeFile.
	 */
	public void readTimesFromFile() {
		FileReaderGUI.readTimesFromFile(timeFile, this);
		setChanged();
		notifyObservers();
	}

	/**
	 * Writes times to timeFile.
	 */
	public void writeTimesToFile() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(timeFile);
			FileWriter.writeTimesToFile(pw, times);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}

	/**
	 * Returns all registered times.
	 * 
	 * @return A Map containing all times.
	 */
	public Map<String, ArrayList<String>> getTimes() {
		return times;
	}
}
