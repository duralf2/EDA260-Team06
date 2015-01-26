package register.model;

public class Driver {
	private String startNumber;
	private String name;

	public Driver(String startNumber, String name) {
		this.startNumber = startNumber;
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
