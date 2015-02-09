package register.model;

public class MarathonContestant extends AbstractContestant {

	public MarathonContestant() {
		super();
	}

	public MarathonContestant(RacerInfo racerInfo) {
		super(racerInfo);
	}

	@Override
	/*Method compares this object's total time with the specified object if specified is of the instance MarathonContestant.
	 Returns a positive int if this object has a longer time than the specified object, return 0 if equal, and otherwise a negative integer,
	 * */
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
