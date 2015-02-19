package register.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LapContestant extends AbstractContestant {
	private LinkedList<Time> lapTimes;
	
	public LapContestant(ContestantProperties racerInfo) {
		super(racerInfo);
		lapTimes = new LinkedList<Time>();
	}

	public void addLapTime(Time time) {
		lapTimes.add(time);
		Collections.sort(lapTimes);
	}

	// TODO - javadoc
	@Override
	public int compareTo(AbstractContestant o) {
		if (o instanceof LapContestant) {
			LapContestant lapContestant = (LapContestant) o;
			if (getLapsCompleted() == lapContestant.getLapsCompleted()) {
				return getTotalTime().compareTo(lapContestant.getTotalTime());
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
	protected String specifiedToString(CompetitionType competitionType, boolean useShortFormat) {
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

		if (!useShortFormat) {
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
		}
		else {
			// Remove the ; that was appended earlier 
			sb.deleteCharAt(sb.length() - 1);
		}
		
		return sb.toString();
	}
	
	@Override
	public Time getTotalTime()
	{
		Time startTime = new Time("00.00.00");
		if (startTimeSize() > 0) {
			startTime = getStartTime();
		}
		Time finishTime = startTime;
		if (finishTimeSize() > 0) {
			finishTime = getFinishTime();
		}
		else if (!lapTimes.isEmpty()) {
			finishTime = lapTimes.getLast();
		}
		return Time.getTotalTime(startTime, finishTime);
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
		String data = getConfiguration().getProperty(Configuration.KEY_MINIMUM_RACE_DURATION, "00.00.00");
		Time time = new Time(data);
		return time;
	}

}
