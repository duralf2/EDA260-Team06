package register.model;

public class Time {
	private String startTime;
	private String finishTime;

	public Time(String startTime, String finishTime) {
		this.startTime = startTime;
		this.finishTime = finishTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getFinishTime() {
		return finishTime;
	}
}
