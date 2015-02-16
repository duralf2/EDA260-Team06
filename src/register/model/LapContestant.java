package register.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LapContestant extends AbstractContestant {
	private LinkedList<Time> lapTimes;
	private static Configuration config;
	
	public LapContestant(ContestantProperties racerInfo) {
		super(racerInfo);
		lapTimes = new LinkedList<Time>();
	}

	public void addLapTime(Time time) {
		lapTimes.add(time);
		Collections.sort(lapTimes);
	}

	// TODO - comment
	@Override
	public int compareTo(AbstractContestant o) {
		if (o instanceof LapContestant) {
			LapContestant lapContestant = (LapContestant) o;
			if (getLapsCompleted() == lapContestant.getLapsCompleted()) {
				return lapContestant.getTotalTime().compareTo(getTotalTime());
			}
			return getLapsCompleted() - lapContestant.getLapsCompleted();
		}
		throw new IllegalArgumentException("Wrong object type to CompareTo()");
	}

	public int getLapsCompleted() {
		if (!finishTime.isEmpty())
			return lapTimes.size() + 1;
		else
			return lapTimes.size();
	}

	@Override
	protected String specifiedToString(CompetitionType competitionType) {
		StringBuilder sb = new StringBuilder();
		sb.append(getLapsCompleted());
		sb.append(";");
		sb.append(getTotalTime().toString());
		sb.append(";");

		List<Time> allLapTimes = new ArrayList<Time>(lapTimes);
		allLapTimes.addAll(finishTime);

		Time previousTime = new Time("00.00.00");
		if (!startTime.isEmpty())
			previousTime = getStartTime();
		int maxLaps = ((LapCompetition) competitionType).getMaxLaps();
		for (int i = 0; i < maxLaps; i++) {
			if (allLapTimes.size() > i) {
				sb.append(Time.getTotalTime(previousTime, allLapTimes.get(i))
						.toString());
				previousTime = allLapTimes.get(i);
			}
			sb.append(";");
		}

		if (!startTime.isEmpty())
			sb.append(getStartTime());
		sb.append(";");

		for (int i = 0; i < maxLaps - 1; i++) {
			if (lapTimes.size() > i)
				sb.append(lapTimes.get(i));
			sb.append(";");
		}

		if (!finishTime.isEmpty())
			sb.append(getFinishTime());

		return sb.toString();

	}

	@Override
	public void addFinishTime(Time time) {
		Time raceTime = getRaceTime();
		Time startTime = new Time("00.00.00");
		if (startTimeSize() > 0) {
			startTime = getStartTime();
		}
		
		if (time.compareTo(raceTime.add(startTime)) <= 0) {
			// If time is less than racetime, it is a lap
			addLapTime(time);
		} else {
			// If time is more than racetime, it is a finish time
			super.addFinishTime(time);
		}
	}

	private Time getRaceTime() {
		try {
			if(config==null){
				config = new Configuration();
			}
			String data = config.getProperty(Configuration.KEY_MINIMUM_RACE_DURATION, "00.00.00");
			Time time = new Time(data);
			return time;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Time("00.00.00");
	}
	
	public void setConfiguration(File file){
		try {
			config = new Configuration(file);
			System.out.println(config.getProperty(Configuration.KEY_MINIMUM_RACE_DURATION));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Configuration getConfiguration(){
		return config;
	}
}
