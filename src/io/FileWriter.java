package io;

import java.io.PrintWriter;
import java.util.*;

import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

/**
 * This class is responsible for writing data to files. The methods of this class
 *  formats the data accordingly before printing it to the <code>PrintWriter</code>
 *  that is passed along with the data.
 */
public class FileWriter {

	/**
	 * Prints the specified database to the specified stream. The data will be
	 *  written in a format that is compatible with the excel file format.
	 * @param pw The <code>PrintWriter</code> to where the data will be written
	 * @param ds The database containing the data to write
	 * @deprecated This method doesn't support laps,
	 *  {@link #writeLapResult(PrintWriter, DataStructure)} does everything this
	 *  method does but also has support for laps!
	 */
	@Deprecated // TODO Deprecated; writeLapResult() does the exact same thing, but has support for laps, this one is unnecessary
	public static void writeResult(PrintWriter pw, DataStructure ds) {
		StringBuilder sb = new StringBuilder();
		Map<String, Contestant> entries = ds.getAllContestantEntries();
		sb.append("StartNr; Namn; TotalTid; Starttider; Måltider\n"); // TODO
																		// how
																		// to
																		// handle
																		// setContestantColumnNames()
																		// in
																		// Datastrucure?
		Contestant contestant;
		for (String startNumber : entries.keySet()) {
			contestant = entries.get(startNumber);
			sb.append(startNumber + "; ");
			sb.append(contestant.getName() + "; ");

			writeTotalTime(contestant, sb);

			if (contestant.startTimeSize() == 0) {
				sb.append("Start?" + "; ");
			} else {
				sb.append(contestant.getStartTime() + "; ");
			}
			if (contestant.finishTimeSize() == 0) {
				sb.append("Slut?");
			} else {
				if (isImpossibleTime(contestant)) {
					sb.append(contestant.getFinishTime() + "; "
							+ "Omöjlig totaltid?");
				} else {
					sb.append(contestant.getFinishTime());
				}
			}
			checkMultipleTimes(contestant, sb);
			sb.append("\n");

		}

		pw.write(sb.toString());
		pw.close();
	}

	/**
	 * Prints the specified database to the specified stream. The data will be
	 *  written in a format that is compatible with the excel file format. If
	 *  there are several laps in the database additional columns are added to 
	 *  display the lap results.
	 * @param pw The <code>PrintWriter</code> to where the data will be written
	 * @param ds The database containing the data to write
	 */
	public static void writeLapResult(PrintWriter pw, DataStructure ds) {
		StringBuilder sb = new StringBuilder();
		Map<String, Contestant> entries = ds.getAllContestantEntries();
		int maxLaps = ds.getMaxLaps();


        sb.append("StartNr;Namn;");
        if(maxLaps > 1) {
            sb.append("#Varv;");
        }
        sb.append("TotalTid;");
        for(int i=1; i <=maxLaps; i++)
            sb.append("Varv" + i + ";");
        sb.append("Starttid;");

        for(int i = 1; i <= maxLaps-1; i++)
            sb.append("Varvning" + i + ";");

        sb.append("Måltid\n");
        Contestant contestant;
        for (String startNumber : entries.keySet()) {
            contestant = entries.get(startNumber);
            sb.append(startNumber + ";");
            sb.append(contestant.getName() + ";");
        

			if (maxLaps > 1) {
				sb.append(contestant.getLapsCompleted()).append(";");
			}
            LinkedList<Time> finishTimes = contestant.getFinishTimes();
            LinkedList<Time> lapTimes = contestant.getLapTimes();
            if(finishTimes.size() > 0) {
                sb.append(Time.getTotalTime(contestant.getStartTime(), contestant.getFinishTime()));
            } else {
                if(lapTimes.size() != 0) {
                    sb.append(Time.getTotalTime(contestant.getStartTime(), lapTimes.getLast()));
                } else {
                    sb.append("--.--.--");
                }
            }
            sb.append(";");

            for(String time : contestant.getLapDurations())
                sb.append(time + ";");
            for(int i=contestant.getLapDurations().size(); i < maxLaps; i++)
                sb.append(" ;");

			if (contestant.startTimeSize() == 0)
				sb.append("Start?;");
			else
				sb.append(contestant.getStartTime() + ";");


            for(Time time : lapTimes)
                sb.append(time.toString() + ";");
            for(int i= lapTimes.size(); i < maxLaps-1; i++)
                sb.append(" ;");

            if (contestant.finishTimeSize() == 0) {
                sb.append("Slut?");
            } else {
                if (isImpossibleTime(contestant)) {
                    sb.append(contestant.getFinishTime() + ";" + "Omöjlig totaltid?");
                } else {
                    sb.append(contestant.getFinishTime());
                }
            }
            checkMultipleTimes(contestant, sb);
            sb.append("\n");
        }

        pw.write(sb.toString());
        pw.close();
    }

	// TODO - implement task 6.3 6.4
	private static void checkMultipleTimes(Contestant contestant,
			StringBuilder sb) {
		checkMultipleTimesStart(contestant, sb);
		checkMultipleTimesFinish(contestant, sb);
	}

	private static void checkMultipleTimesFinish(Contestant contestant,
			StringBuilder sb) {
		if (contestant.finishTimeSize() > 1) {
			sb.append("; " + "Flera måltider?");
			LinkedList<Time> finishTimes = contestant.getFinishTimes();
			Iterator<Time> iterator = finishTimes.iterator();
			iterator.next();
			while (iterator.hasNext()) {
				sb.append(" " + iterator.next());
			}
		}
	}

	private static void checkMultipleTimesStart(Contestant contestant,
			StringBuilder sb) {
		if (contestant.startTimeSize() > 1) {
			sb.append("; " + "Flera starttider?");
			LinkedList<Time> startTimes = contestant.getStartTimes();
			Iterator<Time> iterator = startTimes.iterator();
			iterator.next();
			while (iterator.hasNext()) {
				sb.append(" " + iterator.next());
			}
		}
	}

	private static void writeTotalTime(Contestant contestant, StringBuilder sb) {
		// Getting sizes of lists containing starttimes and finishtimes, if size
		// = 0 time is missing
		if (contestant.startTimeSize() == 0 || contestant.finishTimeSize() == 0) {
			sb.append("--.--.--" + "; ");
		} else {
			sb.append(contestant.getTotalTime() + "; ");
		}
	}

	private static boolean isImpossibleTime(Contestant contestant) {
		boolean impossible;
		try {
			impossible = contestant.startTimeSize() != 0
					&& Integer.parseInt(contestant.getTotalTime().substring(0,
							2)) < 1
					&& Integer.parseInt(contestant.getTotalTime().substring(3,
							5)) <= 15;
		} catch (IllegalArgumentException e) {
			e.printStackTrace(); // negative total time throws the exception.
			return true;
		}
		return impossible;
	}

	public static void writeFinishTimes(PrintWriter pw, DataStructure ds) {
		Map<String, Contestant> entries = ds.getAllContestantEntries();
		StringBuilder sb = new StringBuilder();
		Contestant contestant;
		for (String startNumber : entries.keySet()) {
			contestant = entries.get(startNumber);
			printTimes(contestant.getFinishTimes(), sb, startNumber);
		}
		pw.append(sb.toString());
	}

	private static void printTimes(LinkedList<Time> timeList, StringBuilder sb,
			String startNumber) {
		for (Time time : timeList) {
			sb.append(startNumber.toString() + "; ");
			sb.append(time.toString() + "\n");
		}
	}
}
