package sorter.model;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Abstract superclass of different Contestant types containing information relevant
 * to all contestant types such as times.
 */
public abstract class AbstractContestant implements
		Comparable<AbstractContestant> {
	protected static Configuration config;

	protected LinkedList<Time> startTime, finishTime;
	// TODO: Hantera finishTime då denna är null!

	private String className;
	protected ContestantProperties racerInfo;

	public AbstractContestant() {
		this (new ContestantProperties(new String[0]));
	}

	/**
	 * Default constructor where you pass specific contestant information as an argument.
	 * @param racerInfo the specific contestant information to be added.
	 */
	public AbstractContestant(ContestantProperties racerInfo) {
		startTime = new LinkedList<Time>();
		finishTime = new LinkedList<Time>();
		this.racerInfo = racerInfo;

		className = "";
	}

	/**
	 * Updates the configuration.
	 * @param config the new Configuration object.
	 */
	public static void setConfiguration(Configuration config) {
		AbstractContestant.config = config;
	}

	/**
	 * Returns the Configuration object if it exists, otherwise a new default config will be created.
	 * @return the Configuration object for this contestant
	 */
	public static Configuration getConfiguration() {
		if (config == null) {
			try {
				config = new Configuration();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return config;
	}
	
	/**
	 * Returns the value of a custom property for the contestant.
	 * @param key the String representation of the property name
	 * @return a String representing the value mapped to the specified key if such a mapping exists.
	 */
	public String getInformation(String key) {
		return racerInfo.get(key);
	}
	
	/**
	 * Updates or adds a value for the custom properties of the contestant
	 * @param key the String representation of the property name
	 * @param value a String representation of the new value to be added.
	 */
	public void putInformation(String key, String value) {
		racerInfo.put(key, value);
	}
	
	/**
	 * @param time start time to be added.
	 */
	public void addStartTime(Time time) {
		startTime.add(time);
	}
	
	/**
	 * @return the start time for this contestant
	 */
	public Time getStartTime() {
		return startTime.get(0);
	}
	
	/**
	 * @return the length of the startTime list
	 */
	public int startTimeSize() {
		return startTime.size();
	}
	
	/**
	 * @param time finish time to be added.
	 */
	public void addFinishTime(Time time) {
		finishTime.add(time);
	}
	
	/**
	 * @return the finish time for this contestant.
	 */
	public Time getFinishTime() {
		return finishTime.get(0);
	}
	
	/**
	 * @return the length of the finishTime list.
	 */
	public int finishTimeSize() {
		return finishTime.size();
	}
	
	//TODO: why do we have two different toString methods, would it not be simpler to remove this one?
	/**
	 * This method only delegates to the toString(CompetitionType, boolean) method.
	 * @param competitionType the type of competition this contestant is in
	 * @return a String representation of the Contestant object.
	 */
	public String toString(CompetitionType competitionType) {
		return toString(competitionType, false);
	}
	
	/**
	 * Used for printing to return a String containing various information in the Contestant object.
	 * @param competitionType the type of competition this contestant is in
	 * @param useShortFormat indicates whether or not short format should be used.
	 * @return a String representation of the Contestant object.
	 */
	public String toString(CompetitionType competitionType, boolean useShortFormat) {
		StringBuilder sb = new StringBuilder();
		sb.append(racerInfo.toString());
		sb.append(specifiedToString(competitionType, useShortFormat));
		return sb.toString();
	}
	
	/**
	 * Help method used by the toString method. This method holds the specific implementation
	 * of printing for different types of contestants.
	 * @param competitionType
	 * @param useShortFormat
	 * @return
	 */
	protected abstract String specifiedToString(CompetitionType competitionType, boolean useShortFormat);

	/**
	 * Returns the total time of the race.
	 * @return The total time of the race.
	 */
	public Time getTotalTime()
	{
		Time startTime = new Time("00.00.00");
		if (startTimeSize() > 0) {
			startTime = getStartTime();
		}
		Time finishTime = startTime;
		if (finishTimeSize() > 0) {
			finishTime = getFinishTime();
		}
		
		return Time.getTotalTime(startTime, finishTime);
	}
	
	/**
	 * @param name the new class which this contestant is competing in.
	 */
	public void setClassName(String name) {
		className = name;
	}
	
	/**
	 * @return the class which this contestant is competing in.
	 */
	public String getClassName() {
		return className;
	}
	
	/**
	 * @return true if the contestant has completed the race.
	 */
	public boolean completedRace() {
		 return !startTime.isEmpty() && !finishTime.isEmpty();
	}
}
