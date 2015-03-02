package tests;

import static org.junit.Assert.*;
import io.HTMLParser;

import org.junit.Before;
import org.junit.Test;

public class HTMLParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testParsing() {
		HTMLParser parser = new HTMLParser();
		
		String actual = parser.resultToHTML("Klassnamn\n"
				+ "StartNr;Namn; Klubb; Godissort\n"
				+ "0; Bertil;LTH; Lakritskola\n"
				+ "2; Karl;;\n"
				+ "5; StartNr; asd;fff\n"
				+ "Klass2\n"
				+ "Klass3\n"
				+ "StartNr;Namn; Klubb\n"
				+ "3; Kaos; KTH\n"
				+ "4;; BTH");
		
		String expected = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" + "<style> table #header{ font-weight: bold; }</style>\n" +
							"<html> <body> <h3>Klassnamn</h3><table border=\"1\">\n" +
							"<tr><th>Start Number</th><th>Namn</th><th> Klubb</th><th> Godissort</th></tr>\n" +
							"<tr><td>0</td><td> Bertil</td><td>LTH</td><td> Lakritskola</td></tr>\n" +
							"<tr><td>2</td><td> Karl</td><td></td><td></td></tr>\n" + 
							"<tr><td>5</td><td> StartNr</td><td> asd</td><td>fff</td></tr>\n" +
							"</table><h3>Klass2</h3><h3>Klass3</h3><table border=\"1\">\n" +
							"<tr><th>Start Number</th><th>Namn</th><th> Klubb</th></tr>\n" +
							"<tr><td>3</td><td> Kaos</td><td> KTH</td></tr>\n" +
							"<tr><td>4</td><td></td><td> BTH</td></tr>\n" +
							"</table> </body> </html>";
		
		assertEquals(expected, actual);
	}

}
