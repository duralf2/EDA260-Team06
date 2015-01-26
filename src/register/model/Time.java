package register.model;

public class Time {
	private int hours;
	private int minutes;
	private int seconds;

	public Time(String time) {
		time = time.trim();
		if (time.charAt(2) != '.' || time.charAt(5) != '.') {
			throw new IllegalArgumentException();
		}
	
		try {
			hours = Integer.parseInt(time.substring(0, 2));
			minutes = Integer.parseInt(time.substring(3, 5));
			seconds = Integer.parseInt(time.substring(6, 8));
		} catch(Exception e) {
			throw new IllegalArgumentException();
		}
		
		if (minutes >= 60 || seconds >= 60 ||
				 hours < 0 || minutes < 0 || seconds < 0) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String toString() {
		return timeToString(hours, minutes, seconds);
	}
	
	public static String getTotalTime(Time startTime, Time finishTime) {
		
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
		return timeToString(totalHours, totalMinutes, totalSeconds);
	}
	
	private static String timeToString(int hours, int minutes, int seconds) {
		StringBuilder sb = new StringBuilder();
		sb.append(format(hours));
		sb.append(".");
		sb.append(format(minutes));
		sb.append(".");
		sb.append(format(seconds));
		return sb.toString();
	}
	
	private static String format(int time) {
		return time < 10? "0" + time : Integer.toString(time);
	}
	
	public boolean equals(Object obj) {
		Time t = (Time)obj;
		return t.hours == hours && t.minutes == minutes && t.seconds == seconds;
	}
}
