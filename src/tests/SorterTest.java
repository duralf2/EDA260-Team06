package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.*;

import register.model.Database;
import sorter.Sorter;

public class SorterTest {
	private Sorter sorter;
	
	@Before
	public void setUp() throws IOException {
		sorter = new Sorter(new Database());
	}

}
