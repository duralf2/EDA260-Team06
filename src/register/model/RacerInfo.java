package register.model;

import java.util.HashMap;

public class RacerInfo {
	private final String[] nameHeader;
	private HashMap<String, String> information;

	public RacerInfo(String[] nameHeader) {
		information = new HashMap<String, String>();
		this.nameHeader = nameHeader;
	}

	public void put(String key, String value) {
		information.put(key, value);
	}

	public String get(String key) {
		String value = information.get(key);
		if (value == null)
			value = "";
		return value;
	}
	
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
