package register.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Contestant {
	private String name;
	private LinkedList<Time> startTime;
	private LinkedList<Time> finishTime;
    private LinkedList<Time> lapTimes;

	public Contestant() {
		this("");
	}

	public Contestant(String name) {
		this.name = name;
		startTime = new LinkedList<Time>();
        lapTimes = new LinkedList<Time>();
		finishTime = new LinkedList<Time>();
	}

	public void addStartTime(Time time) {
		startTime.add(time);
	}

    public void addLapTime(Time time){
        lapTimes.add(time);
    }

    public void addFinishTime(Time time) {
		finishTime.add(time);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Time getStartTime() {
		return startTime.get(0);
	}

	public Time getFinishTime() {
		return finishTime.get(0);
	}

    public List<String> getLapDurations() {
        ArrayList<String> lapDurations = new ArrayList<String>();
        if(lapTimes.size() > 0 && startTime.size() > 0)
            lapDurations.add(Time.getTotalTime(startTime.getFirst(), lapTimes.getFirst()).toString());
        for(int i=0; i < lapTimes.size()-1; i++) {
            lapDurations.add(Time.getTotalTime(lapTimes.get(i), lapTimes.get(i + 1)).toString());
        }
        if(lapTimes.size() > 0 && finishTime.size() > 0)
            lapDurations.add(Time.getTotalTime(lapTimes.getLast(), finishTime.getFirst()).toString());
        return lapDurations;
    }

	public String getTotalTime() {
		return Time.getTotalTime(startTime.get(0), finishTime.get(0)).toString();
	}

    public int getLapsCompleted() {
        return lapTimes.size() + finishTime.size();
    }

	public int startTimeSize() {
		return startTime.size();
	}

	public int finishTimeSize() {
		return finishTime.size();
	}

	public LinkedList<Time> getStartTimes() {
		return startTime;
	}

    public LinkedList<Time> getLapTimes() {
        return lapTimes;
    }

	public LinkedList<Time> getFinishTimes() {
		return finishTime;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Contestant) {
			Contestant otherContestant = (Contestant)obj;
			return otherContestant.name.equalsIgnoreCase(name) &&
					startTime.equals(otherContestant.startTime) &&
                    lapTimes.equals(otherContestant.lapTimes) &&
					finishTime.equals(otherContestant.finishTime);
		}
		return false;
	}
}
