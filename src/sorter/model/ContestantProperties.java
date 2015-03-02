package sorter.model;

import java.util.HashMap;
/**
 * This class provides storage for variable information about a contestant such as:
 * club, sponsors, vehicle model etc.
 */
public class ContestantProperties {
	private final String[] nameHeader;
	private HashMap<String, String> information;

	public ContestantProperties(String[] nameHeader) {
		information = new HashMap<String, String>();
		this.nameHeader = nameHeader;
	}
	
	/**
	 * Adds an entry to the contestant properties.
	 * @param key a String of the property name
	 * @param value a String representing the property value
	 */
	public void put(String key, String value) {
		information.put(key, value);
	}
	
	/**
	 * Retrieves the value of a contestant property.
	 * @param key the String representation of the name of the property to be returned
	 * @return a String representation of the property value if an entry exists.
	 */
	public String get(String key) {
		String value = information.get(key);
		if (value == null)
			value = "";
		return value;
	}
	
	/**
	 * @return the header data for the contestant properties.
	 * 
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String racerInfo : nameHeader) {
			String currentField = information.get(racerInfo);
			if (currentField != null)
				sb.append(currentField);
			sb.append(";");
		}
		return sb.toString();
	}
}
