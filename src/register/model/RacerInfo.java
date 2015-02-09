package register.model;

import java.util.HashMap;

public class RacerInfo {
	private HashMap<String, String> information;

	public RacerInfo() {
		information = new HashMap<String, String>();
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
	
	//TODO What order should the information be printed? Header needed,
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for( String racerInfo : information.keySet()) {
			sb.append(information.get(racerInfo));
			sb.append(";");
		}
		return sb.toString();
	}
}
