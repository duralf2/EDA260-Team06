package gui.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observer;

import sorter.model.Time;

public class TimeRegistrationHandler {
	private ContestantTimes times;
	private String lastError;

	public TimeRegistrationHandler(ContestantTimes times) {
		this.times = times;
	}

	/**
	 * Register a new time to a given startnumber.
	 * 
	 * @param startNumber The startnumber to register a time to.
	 * @return true if the time were registered otherwise false.
	 */
	public boolean register(String startNumber) {
		boolean validStartNumber = true;
		if (isNumerical(startNumber)) {
			registerContestantTime(startNumber);
		} else if (startNumber.equals("*")) {
			registerMassStart();
		} else if (isPreRegistration(startNumber)) {
			validStartNumber = preRegister(startNumber);
		} else {
			lastError = "Invalid startnumber, must be a number or x.";
			validStartNumber = false;
		}
		return validStartNumber;
	}

	private void registerContestantTime(String startNumber) {
		times.addTime(startNumber, Time.getCurrentTime());
	}

	private void registerMassStart() {
		String time = Time.getCurrentTime();
		times.performMassStart(time);
	}

	private boolean isPreRegistration(String input) {
		return input.startsWith("x") || input.equals("dx");
	}

	private boolean preRegister(String input) {
		boolean validPreRegistration = true;
		if (input.equals("x")) {
			times.preRegister(Time.getCurrentTime());
		} else if (input.equals("dx")) {
			times.removePreRegisteredTime();
		} else if (input.startsWith("x=")) {
			validPreRegistration = assignStartNumberToPreRegistration(input);
		} else {
			validPreRegistration = false;
			lastError = "Invalid preregistration command.";
		}
		return validPreRegistration;
	}

	private boolean assignStartNumberToPreRegistration(String input) {
		String startNumber = input.substring(2);
		if (isNumerical(startNumber)) {
			String preRegisteredTime = times.getPreRegisteredTime();
			if (preRegisteredTime == null) {
				lastError = "No preregistered time set.";
				return false;
			} else {
				times.removePreRegisteredTime();
				times.addTime(startNumber, preRegisteredTime);
				return true;
			}
		} else {
			lastError = "Invalid startnumber, must be a number.";
			return false;
		}
	}

	private boolean isNumerical(String startNumber) {
		return startNumber.matches("[1-9][0-9]*");
	}

	public String getLastError() {
		return lastError;
	}
	
	public void addObserverToContestantTimes(Observer o) {
		times.addObserver(o);
	}
	
	public Map<String, ArrayList<String>> getAllRegisteredTimes() {
		return times.getTimes();
	}
	

	/**
	 * Checks if the start number is registered.
	 * 
	 * @param startNumber The start number to check.
	 * @return True if the start number is registered, otherwise false.
	 */
	public boolean isRegistered(String startNumber) {
		return times.isRegistered(startNumber);
	}
}
