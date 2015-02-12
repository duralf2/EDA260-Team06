package register.model;

import java.util.LinkedList;

public abstract class AbstractContestant implements
		Comparable<AbstractContestant> {
	protected LinkedList<Time> startTime, finishTime;
	// TODO: Hantera finishTime då denna är null!
	
	protected RacerInfo racerInfo;

	public AbstractContestant() {
		startTime = new LinkedList<Time>();
		finishTime = new LinkedList<Time>();
		racerInfo = new RacerInfo();
	}

	public AbstractContestant(RacerInfo racerInfo) {
		startTime = new LinkedList<Time>();
		finishTime = new LinkedList<Time>();
		this.racerInfo = racerInfo;
	}

	public String getInformation(String key) {
		return racerInfo.get(key);
	}

	public void putInformation(String key, String value) {
		racerInfo.put(key, value);
	}

	public void addStartTime(Time time) {
		startTime.add(time);
	}
	
	public Time getStartTime(){
		return startTime.get(0);
	}

	public void addFinishTime(Time time) {
		finishTime.add(time);
	}
	
	public Time getFinishTime(){
		return finishTime.get(0);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(racerInfo.toString()); //TODO: fixa i rätt ordning, just nu random
		sb.append(specifiedToString()); 
		return sb.toString();
	}

	protected abstract String specifiedToString();

	public abstract Time getTotalTime();

}
