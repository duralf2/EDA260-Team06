package register.model;

public class MarathonContestant extends AbstractContestant {

	public MarathonContestant() {
		super();
	}

	public MarathonContestant(RacerInfo racerInfo) {
		super(racerInfo);
	}

	@Override
	/*Method compares this object's total time with the specified object.
	 Returns -1 if the compared object has a smaller total time than this object.
	 Returns 1 if opposite.
	 Return 0 if equal.
	 * */
	public int compareTo(AbstractContestant o) {
		if( o instanceof MarathonContestant) {
			int diff = getTotalTime().compareTo(o.getTotalTime());
			if(diff > 0)
				return -1;
			else if(diff < 0)
				return 1;
			else
				return 0;
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
