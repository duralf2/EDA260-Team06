package tests;

import static org.junit.Assert.*;
import gui.RegistrationStarter;

import org.junit.Before;
import org.junit.Test;

public class GuiTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNoCrashAtStart() {
		new RegistrationStarter();
	}

}
