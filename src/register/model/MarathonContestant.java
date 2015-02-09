package register.model;

public class MarathonContestant extends AbstractContestant {

	public MarathonContestant() {
		super();
	}

	public MarathonContestant(RacerInfo racerInfo) {
		super(racerInfo);
	}

	@Override
	// TODO - comment
	public int compareTo(AbstractContestant o) {
		if( o instanceof MarathonContestant) {
			return getTotalTime().compareTo(o.getTotalTime());
		}
		throw new IllegalArgumentException("Wrong type of object sent to compareTo()");
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
