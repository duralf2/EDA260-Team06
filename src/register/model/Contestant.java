package register.model;

public class Contestant {
	private String startNumber;
	private String name;

	public Contestant(String startNumber, String name) {
		this.startNumber = startNumber;
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
