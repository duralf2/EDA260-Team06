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
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTotalTime() {
		return Time.getTotalTime(super.startTime, super.finishTime);
	}


}
