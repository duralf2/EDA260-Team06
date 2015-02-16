package register.model;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public abstract class AbstractContestant implements
		Comparable<AbstractContestant> {
	protected static Configuration config;

	protected LinkedList<Time> startTime, finishTime;
	// TODO: Hantera finishTime då denna är null!

	private String className;
	protected ContestantProperties racerInfo;

	public AbstractContestant() {
		startTime = new LinkedList<Time>();
		finishTime = new LinkedList<Time>();
		racerInfo = new ContestantProperties(new String[0]);
	}

	public AbstractContestant(ContestantProperties racerInfo) {
		startTime = new LinkedList<Time>();
		finishTime = new LinkedList<Time>();
		this.racerInfo = racerInfo;
	}

	public static void setConfiguration(Configuration config) {
		AbstractContestant.config = config;
	}

	public static Configuration getConfiguration() {
		if (config == null) {
			try {
				config = new Configuration();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return config;
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

	public Time getStartTime() {
		return startTime.get(0);
	}

	public int startTimeSize() {
		return startTime.size();
	}

	public void addFinishTime(Time time) {
		finishTime.add(time);
	}

	public Time getFinishTime() {
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
		Time finishTime = startTime;
		if (finishTimeSize() > 0) {
			finishTime = getFinishTime();
		}
		return Time.getTotalTime(startTime, finishTime);
	}

	public void setClassName(String name) {
		className = name;
	}

	public String getClassName() {
		return className;
	}
	
	@Override
	public int compareTo(AbstractContestant o) {
		return 0;
	}

}
