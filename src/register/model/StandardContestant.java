package register.model;

public abstract class StandardContestant implements Comparable<StandardContestant> {
	protected Time startTime, finishTime;
	protected RacerInfo racerInfo;
	
	public StandardContestant() {
	}
	
	public StandardContestant( RacerInfo racerInfo) {
		this.racerInfo = racerInfo;
	}
	
	public abstract void addStartTime(Time time);
	
	public abstract void addFinishTime(Time time);
	
	public abstract String toString();
	
	public abstract void getTotalTime();
	
}
