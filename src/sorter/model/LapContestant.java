package sorter.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class holds information for a contestant participating in a lap event
 * and methods neccessary for adding laptimes, computing total time, getting the number of laps,
 * comparing two contestants, adding finish times
 * and converting this information to a printable String.
 *@extends AbstractContestant
 */
public class LapContestant extends AbstractContestant {
	private LinkedList<Time> lapTimes;
	
	public LapContestant(ContestantProperties racerInfo) {
		super(racerInfo);
		lapTimes = new LinkedList<Time>();
	}
	
	/**
	 * Adds a laptime for this contestant.
	 * @param time a Time object representing the laptime to be added.
	 */
	public void addLapTime(Time time) {
		lapTimes.add(time);
		Collections.sort(lapTimes);
	}

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
	
	/**
	 * Calculates how many laps this contestant has completed.
	 * @return an integer representing the number of completed laps.
	 */
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
		sb.append(formattedTotalTime());
		sb.append(";");

		List<Time> allLapTimes = new ArrayList<Time>(lapTimes);
		allLapTimes.addAll(finishTime);

		Time previousTime = new Time("00.00.00");
		if (!startTime.isEmpty())
			previousTime = getStartTime();
		int maxLaps = ((LapCompetition) competitionType).getMaxLapsByClass(getClassName());
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
			else
				sb.append("Start?");
			sb.append(";");
	
			for (int i = 0; i < maxLaps - 1; i++) {
				if (lapTimes.size() > i)
					sb.append(lapTimes.get(i));
				sb.append(";");
			}

			if (!finishTime.isEmpty())
				sb.append(getFinishTime());
			else
				sb.append("Slut?");
			
			sb.append(generateExtraColumn());
			
		}
		else {
			// Remove the ; that was appended earlier 
			sb.deleteCharAt(sb.length() - 1);
		}
		
		return sb.toString();
	}
	
	private String generateExtraColumn() {
		StringBuilder sb = new StringBuilder();
		if(startTimeSize() > 1) {
			sb.append(";Flera starttider?");
			for (int i = 1; i < startTimeSize(); i++){
				sb.append(" " + startTime.get(i).toString());
			}
		}
		if (finishTime.size() > 1) {
			sb.append(";Flera måltider?");

			for (int i = 1; i < finishTimeSize(); i++){
				sb.append(" " + finishTime.get(i).toString());
			}
		}
		Time minLapTime = new Time(config.getProperty(Configuration.KEY_SHORTEST_POSSIBLE_TIME));
		Time previousTime = new Time("00.00.00");
		if (!startTime.isEmpty())
			previousTime = getStartTime();
		for(int i = 0; i < lapTimes.size(); i++){
			if(Time.getTotalTime(previousTime, lapTimes.get(i)).compareTo(minLapTime) < 0){
				sb.append(";Omöjlig varvtid?");
				break;
			}
			previousTime = lapTimes.get(i);
		}
		return sb.toString();
	}
	
	
	private String formattedTotalTime(){
		if(startTimeSize() > 0 && finishTimeSize() > 0){
			return getTotalTime().toString();
		}
		return "--.--.--";
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
		String data = getConfiguration().getProperty(Configuration.KEY_START_TIME_LIMIT, "00.00.00");
		Time time = new Time(data);
		return time;
	}

}
