package sorter.model;

import java.util.HashMap;

public class ContestantProperties {
	private final String[] nameHeader;
	private HashMap<String, String> information;

	public ContestantProperties(String[] nameHeader) {
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
	
	// TODO Den här metoden returnerar bara data motsvarande den i headern, toString kanske är fel metod?
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
