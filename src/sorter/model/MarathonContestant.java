package sorter.model;

import java.util.Iterator;

/**
 * This class contains the information of a contestant in a marathon event and
 * methods to manipulate this data.
 * @extends AbstractContestant
 */
public class MarathonContestant extends AbstractContestant {

	public MarathonContestant(ContestantProperties racerInfo) {
		super(racerInfo);
	}

	@Override
	public int compareTo(AbstractContestant o) {
		if (o instanceof MarathonContestant) {
			return o.getTotalTime().compareTo(getTotalTime());
		}
		throw new IllegalArgumentException(
				"Wrong type of object sent to compareTo()");
	}

	@Override
	protected String specifiedToString(CompetitionType competitionType, boolean useShortFormat) {
		StringBuilder sb = new StringBuilder();
		if (startTime.isEmpty() || finishTime.isEmpty()) {
			sb.append("--.--.--");
		} else {
			sb.append(getTotalTime().toString());
		}
		
		if (!useShortFormat)
		{
			sb.append(";");
			
			if (!startTime.isEmpty()) {
				sb.append(getStartTime().toString());
			} else {
				sb.append("Start?");
			}
			
			sb.append(";");
			
			if (!finishTime.isEmpty()) {
				sb.append(getFinishTime().toString());
			} else {
				sb.append("Slut?");
			}
		
			if (startTime.size() > 1) {
				sb.append(";Flera starttider? ");
				Iterator<Time> itr = startTime.iterator();
				itr.next(); // Skip first
				while (itr.hasNext()) {
					sb.append(itr.next().toString() + " ");
				}
			}
			
			if (finishTime.size() > 1) {
				sb.append(";Flera måltider? ");
			
				Iterator<Time> itr = finishTime.iterator();
				itr.next(); // Skip first
				while (itr.hasNext()) {
					sb.append(itr.next().toString() + " ");
				}
			}
		}
		
		String time = getConfiguration().getProperty(
				Configuration.KEY_SHORTEST_POSSIBLE_TIME, "00.15.00");
		
		if (!startTime.isEmpty() && !finishTime.isEmpty()) {
			if (getTotalTime().compareTo(new Time(time)) < 0)
				sb.append(";Omöjlig totaltid?");
		}
		return sb.toString().trim();
	}
}
