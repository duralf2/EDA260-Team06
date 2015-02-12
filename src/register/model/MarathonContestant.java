package register.model;

public class MarathonContestant extends AbstractContestant {
	
	public MarathonContestant() { }

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
		return Time.getTotalTime(getStartTime(), getFinishTime());
	}

	@Override
	protected String specifiedToString(CompetitionType competitionType) {
		StringBuilder sb = new StringBuilder();
		sb.append(getTotalTime().toString());
		sb.append(";");
		sb.append(getStartTime().toString());
		sb.append(";");
		sb.append(getFinishTime().toString());
		return sb.toString();
	}


}
