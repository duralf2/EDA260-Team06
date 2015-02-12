package register.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time implements Comparable<Time> {
	private int hours;
	private int minutes;
	private int seconds;

	public Time(String time) {
		time = time.trim();
		if (time.charAt(2) != '.' || time.charAt(5) != '.') {
			throw new IllegalArgumentException(time);
		}

		try {
			hours = Integer.parseInt(time.substring(0, 2));
			minutes = Integer.parseInt(time.substring(3, 5));
			seconds = Integer.parseInt(time.substring(6, 8));
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}

		if (minutes >= 60 || seconds >= 60 || hours < 0 || minutes < 0
				|| seconds < 0) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(format(hours));
		sb.append(".");
		sb.append(format(minutes));
		sb.append(".");
		sb.append(format(seconds));
		return sb.toString();
	}

	public Time multiply(int multiplier) {
		int totalSeconds = this.totalSeconds() * multiplier;
		return convertSecondsToTime(totalSeconds);
		
	}

	private Time convertSecondsToTime(int totalSeconds) {
		if (totalSeconds >= 3600 * 24) {
			totalSeconds -= 3600 * 24;
		}

		int hours = totalSeconds / 3600;
		int minutes = (totalSeconds % 3600) / 60;
		int seconds = totalSeconds % 60;

		return new Time(format(hours) + "." + format(minutes) + "."
				+ format(seconds));
	}

	public static Time getTotalTime(Time startTime, Time finishTime)
			throws IllegalArgumentException {
		int totalHours = finishTime.hours - startTime.hours;
		int totalMinutes = finishTime.minutes - startTime.minutes;
		int totalSeconds = finishTime.seconds - startTime.seconds;
		if (totalSeconds < 0) {
			totalSeconds += 60;
			totalMinutes--;
		}
		if (totalMinutes < 0) {
			totalMinutes += 60;
			totalHours--;
		}
		if (totalHours < 0) {
			throw new IllegalArgumentException("Can not use negative time");
		}
		return new Time(format(totalHours) + "." + format(totalMinutes) + "."
				+ format(totalSeconds));
	}

	private static String format(int time) {
		return time < 10 ? "0" + time : Integer.toString(time);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Time) {
			Time t = (Time) obj;
			return t.hours == hours && t.minutes == minutes
					&& t.seconds == seconds;
		}
		return false;
	}

	/**
	 * @return Get the current system time in HH:mm:ss
	 */
	// TODO: check if am/pm
	public static String getCurrentTime() {
		return new SimpleDateFormat("HH.mm.ss").format(new Date().getTime());
	}

	@Override
	public int compareTo(Time t2) {
		return totalSeconds() - t2.totalSeconds();
	}

	private int totalSeconds() {
		return hours * 3600 + minutes * 60 + seconds;
	}

	public Time add(Time time) {
		int timeSeconds = time.totalSeconds();
		int mySeconds = this.totalSeconds();

		int totalSeconds = mySeconds + timeSeconds;

		return convertSecondsToTime(totalSeconds);
	}
}
