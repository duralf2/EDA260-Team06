package sorter.model;

import java.util.HashMap;

public class StageContestant extends AbstractContestant {
	private HashMap<Integer, Stage> stages;
	private int currentStageNbr;
	private int totalStageTime;

	public StageContestant() {
		super();
		stages = new HashMap<Integer, Stage>();
		currentStageNbr = 1;
	}

	public StageContestant(ContestantProperties racerInfo) {
		super(racerInfo);
		stages = new HashMap<Integer, Stage>();
		currentStageNbr = 1;
	}

	public void addStartTime(Time time) {
		addStartTime(time, 1);
	}
	
	public void addStartTime(Time time, int multiplier){
		if(stages.containsKey(currentStageNbr))
			throw new IllegalArgumentException("The contestant has to finish the current stage");
//		stageTimes.put(currentStageNbr, new StageTime(startTime, multiplier));
	}
	
	public void addFinishTime(Time time){
		if(!stages.containsKey(currentStageNbr))
			throw new IllegalArgumentException("The contestant has to start the current stage");
		stages.get(currentStageNbr).addFinishTime(time);
		currentStageNbr++;
	}
	public int nbrOfStages(){
		return stages.size();
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
	
	/**
	 * An inner class containing a start- and finishtime entry for a stage with a multiplier indicating
	 * how much the time for this stage should be multiplied.
	 */
	private static class Stage {
		private Time startTime, finishTime;
		private int multiplier;

		private Stage(Time startTime, int multiplier) {
			this.startTime = startTime;
			this.multiplier = multiplier;
			finishTime = null;
		}
		private void addStartTime(Time time){
			startTime = time;
		}
		private Time getStartTime(){
			return startTime;
		}
		private void addFinishTime(Time time){
			finishTime = time;
		}
		private Time getFinishTime(){
			return finishTime;
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


}
