package register.model;

public class StageContestant extends StandardContestant {

	@Override
	public int compareTo(StandardContestant arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
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
	protected String personalToString() {
		// TODO Auto-generated method stub
		return null;
	}

}
