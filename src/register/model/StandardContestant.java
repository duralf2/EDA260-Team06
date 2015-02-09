package register.model;

public abstract class StandardContestant implements Comparable<StandardContestant> {
	private Time startTime, finishTime;
	private RacerInfo racerInfo;
	
	public StandardContestant() {
		
	}
	
	public abstract void addStartTime(Time time);
	
	public abstract String toString();
}
