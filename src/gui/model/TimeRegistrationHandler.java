package gui.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observer;

import sorter.model.Time;

/**
 * This class handles the time registration.
 */
public class TimeRegistrationHandler {
	private ContestantTimes times;
	private String lastError;

	/**
	 * Initializes the times for contestants.
	 * @param times The <code>ContestantTimes</code>.
	 */
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
		} else {
			lastError = "Invalid startnumber, must be a number or *(Mass start).";
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
	
	/**
	 * Pre-register a <code>contestant</code> for current time.
	 */
	public void preRegister() {
		times.preRegister(Time.getCurrentTime());
	}

	/**
	 * Removes a pre-registered <code>contestant</code>.
	 */
	public void removePreRegisteredTime() {
		times.removePreRegisteredTime();
	}
	
	public boolean hasPreRegistered(){
		if(times.getPreRegisteredTime()!=null)
		{
			return true;
		}
		return false;
	}

	/**
	 * Assigns a start number to a pre-registered time.
	 * 
	 * @param startNumber The start number in question.
	 * @return True if successful, otherwise False.
	 */
	public boolean assignPreRegistrationStartNumber(String startNumber) {
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
		return startNumber.matches("[0-9]*");
	}
	
	/**
	 * Return last error.
	 * @return The error.
	 */
	public String getLastError() {
		return lastError;
	}
	
	/**
	 * Adds a Observer to times.
	 * @param o - the Observer
	 */
	public void addObserverToContestantTimes(Observer o) {
		times.addObserver(o);
	}
	
	/**
	 * Returns all registered times.
	 * @return A Map of the times.
	 */
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
