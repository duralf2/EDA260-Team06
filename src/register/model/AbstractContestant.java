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
		racerInfo = new RacerInfo(new String[0]);
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
	
	public int startTimeSize() {
		return startTime.size();
	}

	public void addFinishTime(Time time) {
		finishTime.add(time);
	}
	
	public Time getFinishTime(){
		return finishTime.get(0);
	}

	public int finishTimeSize() {
		return finishTime.size();
	}
	
	public String toString(CompetitionType competitionType) {
		StringBuilder sb = new StringBuilder();
		sb.append(racerInfo.toString());
		sb.append(specifiedToString(competitionType));
		return sb.toString();
	}

	protected abstract String specifiedToString(CompetitionType competitionType);
	
	public Time getTotalTime() // Default implementation
	{
		Time startTime = new Time("00.00.00");
		if (startTimeSize() > 0) {
			startTime = getStartTime();
		}
		Time finishTime = new Time("00.00.00");
		if (finishTimeSize() > 0) {
			finishTime = getFinishTime();
		}
		return Time.getTotalTime(startTime, finishTime);
	}
}
