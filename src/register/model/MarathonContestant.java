package register.model;

public class MarathonContestant extends StandardContestant {

	public MarathonContestant() {
		super();
	}

	public MarathonContestant(RacerInfo racerInfo) {
		super(racerInfo);
	}

	@Override
	public int compareTo(StandardContestant o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addStartTime(Time time) {
		super.startTime = time;
	}

	@Override
	public void addFinishTime(Time time) {
		super.finishTime = time;
	}
	
	@Override
	public Time getTotalTime() {
		return Time.getTotalTime(super.startTime, super.finishTime);
	}

	@Override
	protected String specifiedToString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getTotalTime().toString());
		sb.append(";");
		sb.append(super.startTime.toString());
		sb.append(";");
		sb.append(super.finishTime.toString());
		return sb.toString();
	}


}
