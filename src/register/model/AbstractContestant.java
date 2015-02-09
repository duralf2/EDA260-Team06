package register.model;

public abstract class AbstractContestant implements Comparable<AbstractContestant> {
	protected Time startTime, finishTime;
	protected RacerInfo racerInfo;
	
	public AbstractContestant() {
		racerInfo = new RacerInfo();
	}
	
	public AbstractContestant( RacerInfo racerInfo) {
		this.racerInfo = racerInfo;
	}
	
	public abstract void addStartTime(Time time);
	
	public abstract void addFinishTime(Time time);
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(racerInfo.toString());
		sb.append(specifiedToString());
		return sb.toString();
	}
	
	protected abstract String specifiedToString(); 
	
	public abstract Time getTotalTime();
	
}
