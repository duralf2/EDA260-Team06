package register.model;

public abstract class StandardContestant implements Comparable<StandardContestant> {
	private Time startTime, finishTime;
	private RacerInfo racerInfo;
	
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
