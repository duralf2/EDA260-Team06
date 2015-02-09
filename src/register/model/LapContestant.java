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

	@Override
	public int compareTo(AbstractContestant o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Time getTotalTime() {
		return Time.getTotalTime(super.startTime, super.finishTime);
	}
	
	public int getLapsCompleted() {
		return lapTimes.size()+1;
	}

	@Override
	protected String specifiedToString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getLapsCompleted());
		sb.append(";");
		sb.append(getTotalTime().toString());
		sb.append(";");
		
		Time previousTime = super.startTime;
		for( Time lapTime : lapTimes) {
			sb.append(Time.getTotalTime(previousTime, lapTime).toString());
			sb.append(";");
			previousTime = lapTime;
		}
		
		sb.append(super.startTime);
		sb.append(";");
		
		for( Time lapTime2 : lapTimes) {
			sb.append(lapTime2.toString());
			sb.append(";");
		}
		
		sb.append(super.finishTime);
		
		return sb.toString();
		
	}

}
