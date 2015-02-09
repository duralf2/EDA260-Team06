package register.model;

public abstract class AbstractContestant implements Comparable<AbstractContestant> {
	protected Time startTime, finishTime; //TODO: Hantera finishTime då denna är null!
	protected RacerInfo racerInfo;
	
	public AbstractContestant() {
		racerInfo = new RacerInfo();
	}
	
	public AbstractContestant( RacerInfo racerInfo) {
		this.racerInfo = racerInfo;
	}
	
	public void addStartTime(Time time) {
		startTime = time;
	}

	public void addFinishTime(Time time) {
		finishTime = time;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(racerInfo.toString());
		sb.append(specifiedToString());
		return sb.toString();
	}
	
	protected abstract String specifiedToString(); 
	
	public abstract Time getTotalTime();
	
}
