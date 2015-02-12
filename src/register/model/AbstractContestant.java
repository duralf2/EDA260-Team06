package register.model;

public abstract class AbstractContestant implements Comparable<AbstractContestant> {
	protected Time startTime, finishTime; //TODO: Hantera finishTime då denna är null!
	protected RacerInfo racerInfo;
	
	public AbstractContestant( RacerInfo racerInfo) {
		this.racerInfo = racerInfo;
	}
	
	public void addStartTime(Time time) {
		startTime = time;
	}

	public void addFinishTime(Time time) {
		finishTime = time;
	}
	
	public String toString(CompetitionType competitionType) {
		StringBuilder sb = new StringBuilder();
		sb.append(racerInfo.toString());
		sb.append(specifiedToString(competitionType));
		return sb.toString();
	}
	
	protected abstract String specifiedToString(CompetitionType competitionType); 
	
	public abstract Time getTotalTime();
	
}
