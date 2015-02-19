package register.model;

import java.util.Iterator;

public class MarathonContestant extends AbstractContestant {

	public MarathonContestant(ContestantProperties racerInfo) {
		super(racerInfo);
	}

	@Override
	public int compareTo(AbstractContestant o) {
		if (o instanceof MarathonContestant) {
			return getTotalTime().compareTo(o.getTotalTime());
		}
		throw new IllegalArgumentException(
				"Wrong type of object sent to compareTo()");
	}

	@Override
	protected String specifiedToString(CompetitionType competitionType, boolean useShortFormat) {
		StringBuilder sb = new StringBuilder();
		appendErrorStringTo(sb);
		return sb.toString().trim();
	}

	private void appendErrorStringTo(StringBuilder sb) {

		if (startTime.isEmpty() || finishTime.isEmpty()) {
			sb.append("--.--.--");
		} else {
			sb.append(getTotalTime().toString());
		}

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

		String time = getConfiguration().getProperty(
				Configuration.KEY_MINIMUM_RACE_DURATION, "00.15.00");

		if (!startTime.isEmpty() && !finishTime.isEmpty()) {
			if (getTotalTime().compareTo(new Time(time)) < 0)
				sb.append(";Omöjlig totaltid?");
		}

	}

}
