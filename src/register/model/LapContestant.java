package register.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LapContestant extends AbstractContestant {
	private LinkedList<Time> lapTimes;

	public LapContestant(RacerInfo racerInfo) {
		super(racerInfo);
		lapTimes = new LinkedList<Time>();
	}

	public void addLapTime(Time time) {
		lapTimes.add(time);
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

	@Override
	public Time getTotalTime() {
		Time startTime = this.startTime;
		if (startTime == null) {
			startTime = new Time("00.00.00");
		}
		Time finishTime = this.finishTime;
		if (finishTime == null) {
			finishTime = new Time("00.00.00");
		}
		return Time.getTotalTime(startTime, finishTime);
	}

	public int getLapsCompleted() {
		if (finishTime != null)
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
		if (finishTime != null)
		{
			allLapTimes.add(finishTime);
		}

		Time previousTime = super.startTime;
		int maxLaps = ((LapRace) competitionType).getMaxLaps();
		for (int i = 0;i < maxLaps; i++) {
			if (allLapTimes.size() > i) {
				sb.append(Time.getTotalTime(previousTime, allLapTimes.get(i)).toString());
				previousTime = allLapTimes.get(i);
			}
			sb.append(";");
		}
		if (startTime != null)
			sb.append(startTime);
		sb.append(";");
		
		for (int i = 0; i < maxLaps - 1;i++) {
			if (lapTimes.size() > i)
				sb.append(lapTimes.get(i));
			sb.append(";");
		}

		if (finishTime != null)
			sb.append(finishTime);

		return sb.toString();

	}

}
