package register.model;

public class LapContestant extends AbstractContestant{
	
	public LapContestant() {
		super();
	}

	public LapContestant(RacerInfo racerInfo) {
		super(racerInfo);
	}


	@Override
	public int compareTo(AbstractContestant o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void addStartTime(Time time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFinishTime(Time time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Time getTotalTime() {
		return finishTime;
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String specifiedToString() {
		// TODO Auto-generated method stub
		return null;
	}

}
