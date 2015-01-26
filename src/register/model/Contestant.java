package register.model;

import java.util.LinkedList;

public class Contestant {
	private String name;
	private LinkedList<Time> startTime;
	private LinkedList<Time> finishTime;

	public Contestant() {
		this("");
	}

	public Contestant(String name) {
		this.name = name;
		startTime = new LinkedList<Time>();
		finishTime = new LinkedList<Time>();
	}

	// TODO Remove - Replaced by addStartTime?
	public void setStartTime(Time time) {
		startTime.add(0, time);
	}

	public void addStartTime(Time time) {
		startTime.add(time);
	}

	// TODO Remove - Replaced by addFinishTime?
	public void setFinishTime(Time time) {
		finishTime.add(0, time);
	}

	public void addFinishTime(Time time) {
		finishTime.add(time);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Time getStartTime() {
		return startTime.get(0);
	}

	public Time getFinishTime() {
		return finishTime.get(0);
	}

	public String getTotalTime() {
		return Time.getTotalTime(startTime.get(0), finishTime.get(0));
	}

	public int startTimeSize() {
		return startTime.size();
	}

	public int finishTimeSize() {
		return finishTime.size();
	}

	public LinkedList<Time> getStartTimes() {
		return startTime;
	}

	public LinkedList<Time> getFinishTimes() {
		return finishTime;
	}

	public boolean equals(Object obj) {
		// TODO: Also use time when doing eq op?
		if (obj instanceof Contestant)
			return ((Contestant) obj).name.equalsIgnoreCase(name);
		return false;
	}
}
