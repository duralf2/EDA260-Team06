package sorter.model;

import java.util.HashMap;

public class StageContestant extends AbstractContestant {
	private HashMap<Integer, StageTime> stageTimes;
	private int currentStageNbr;

	public StageContestant() {
		super();
		stageTimes = new HashMap<Integer, StageTime>();
		currentStageNbr = 1;
	}

	public StageContestant(ContestantProperties racerInfo) {
		super(racerInfo);
		stageTimes = new HashMap<Integer, StageTime>();
		currentStageNbr = 1;
	}

	private static class StageTime {
		private Time startTime, finishTime;
		private int multiplier;

		private StageTime(Time startTime, int multiplier) {
			this.startTime = startTime;
			this.multiplier = multiplier;
			finishTime = null;
		}
		
		private void addFinishTime(Time time){
			finishTime = time;
		}
		
		private Time getStageDuration(){
			return Time.getTotalTime(startTime, finishTime).multiply(multiplier);
		}

		private String specifiedToString() {
			StringBuilder sb = new StringBuilder();
			if (startTime == null)
				sb.append(" ");
			else
				sb.append(startTime);
			if (finishTime == null)
				sb.append(" ");
			else
				sb.append(finishTime);
			return sb.toString();
		}

	}

	public void addStartTime(Time time) {
		addStartTime(time, 1);
	}
	
	public void addStartTime(Time time, int multiplier){
		if(stageTimes.containsKey(currentStageNbr))
			throw new IllegalArgumentException("The contestant has to finish the current stage");
//		stageTimes.put(currentStageNbr, new StageTime(startTime, multiplier));
	}
	
	public void addFinishTime(Time time){
		if(!stageTimes.containsKey(currentStageNbr))
			throw new IllegalArgumentException("The contestant has to start the current stage");
		stageTimes.get(currentStageNbr).addFinishTime(time);
		currentStageNbr++;
	}

	@Override
	public int compareTo(AbstractContestant arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Time getTotalTime() {
//		int i = 0;
//		for(StageTime stageTime : stageTimes){
//			
//		}
		return new Time("00.00.00");
	}

	@Override
	protected String specifiedToString(CompetitionType competitionType, boolean useShortFormat) {
		StringBuilder sb = new StringBuilder();
		sb.append(getTotalTime().toString());
		sb.append(";");
		sb.append(super.startTime.toString());
		sb.append(";");
		sb.append(super.finishTime.toString());
		return sb.toString();
	}


}
