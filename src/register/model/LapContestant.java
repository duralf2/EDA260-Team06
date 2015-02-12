package register.model;

import java.util.LinkedList;

public class LapContestant extends AbstractContestant {
	private LinkedList<Time> lapTimes;

	public LapContestant() {
		super();
		lapTimes = new LinkedList<Time>();
	}

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
		return Time.getTotalTime(getStartTime(), getFinishTime());
	}

	public int getLapsCompleted() {
		return lapTimes.size() + 1;
	}

	@Override
	protected String specifiedToString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getLapsCompleted());
		sb.append(";");
		sb.append(getTotalTime().toString());
		sb.append(";");

		Time previousTime = getStartTime();
		for (Time lapTime : lapTimes) {
			sb.append(Time.getTotalTime(previousTime, lapTime).toString());
			sb.append(";");
			previousTime = lapTime;
		}

		sb.append(getStartTime());
		sb.append(";");

		for (Time lapTime2 : lapTimes) {
			sb.append(lapTime2.toString());
			sb.append(";");
		}

		sb.append(getFinishTime());

		return sb.toString();

	}

}
