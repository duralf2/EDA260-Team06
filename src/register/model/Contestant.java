package register.model;

public class Contestant {
	private String name;
	private Time startTime;
	private Time finishTime;

	public Contestant(String name) {
		this.name = name;
	}

	public void setStartTime(Time time) {
		startTime = time;
	}

	public void setFinishTime(Time time) {
		finishTime = time;
	}

	public String getName() {
		return name;
	}

	public Time getStartTime() {
		return startTime;
	}

	public Time getFinishTime() {
		return finishTime;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}