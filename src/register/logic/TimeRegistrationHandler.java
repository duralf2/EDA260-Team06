package register.logic;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observer;

import register.model.ContestantTimes;
import register.model.Time;

public class TimeRegistrationHandler {
	private ContestantTimes times;
	private String lastError;

	public TimeRegistrationHandler(ContestantTimes times) {
		this.times = times;
	}

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
	
	public void observContestantTimes(Observer o) {
		times.addObserver(o);
	}
	
	public Map<String, ArrayList<String>> getAllRegisteredTimes() {
		return times.getTimes();
	}
	
	public boolean isRegistered(String startNumber) {
		return times.isRegistered(startNumber);
	}

}
