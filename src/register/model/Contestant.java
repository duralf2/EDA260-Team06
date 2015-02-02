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

	public void addStartTime(Time time) {
		startTime.add(time);
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
		if (obj instanceof Contestant) {
			Contestant otherContestant = (Contestant)obj;
			return otherContestant.name.equalsIgnoreCase(name) &&
					startTime.equals(otherContestant.startTime) &&
					finishTime.equals(otherContestant.finishTime);
		}
		return false;
	}
}
