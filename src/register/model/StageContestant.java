package register.model;

public class StageContestant extends AbstractContestant {

	public StageContestant(RacerInfo racerInfo) {
		super(racerInfo);
	}


	@Override
	public int compareTo(AbstractContestant arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Time getTotalTime() {
		return finishTime;
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String specifiedToString(CompetitionType competitionType) {
		// TODO Auto-generated method stub
		return null;
	}

}
