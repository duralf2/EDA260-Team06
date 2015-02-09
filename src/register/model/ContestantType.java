package register.model;

public abstract class ContestantType implements Comparable<ContestantType> {
	private RacerInfo racerInfo;
	
	public ContestantType() {
		
	}
	
	public abstract String contestantString();
	
}
