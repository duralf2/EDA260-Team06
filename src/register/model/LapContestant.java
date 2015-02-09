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

	/*Method compares this object's total time with the specified object if specified is of the instance LapContestant.
	 Returns a positive int if this object has a longer time than the specified object, return 0 if equal, and otherwise a negative integer,
	 * */
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

	@Override
	public Time getTotalTime() {
		return Time.getTotalTime(super.startTime, super.finishTime);
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

		Time previousTime = super.startTime;
		for (Time lapTime : lapTimes) {
			sb.append(Time.getTotalTime(previousTime, lapTime).toString());
			sb.append(";");
			previousTime = lapTime;
		}

		sb.append(super.startTime);
		sb.append(";");

		for (Time lapTime2 : lapTimes) {
			sb.append(lapTime2.toString());
			sb.append(";");
		}

		sb.append(super.finishTime);

		return sb.toString();

	}

}
