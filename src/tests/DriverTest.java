package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import register.model.Driver;

public class DriverTest {
	private Driver driver;
	private String startNumber;
	private String name;

	@Before
	public void setUp() throws Exception {
		startNumber = "1";
		name = "David";
		driver = new Driver(startNumber,name);
	}

	@Test
	public void testGetName() {
		assertTrue(driver.getName().equals("David"));
	}

}
