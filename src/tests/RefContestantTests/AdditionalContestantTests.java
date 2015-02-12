package tests.RefContestantTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import register.model.AbstractContestant;
import register.model.CompetitionType;
import register.model.Time;

public class AdditionalContestantTests {
	
	private AbstractContestant contestant;

	@Before
	public void setUp() throws Exception {
		contestant = new MockContestant();
	}

	@Test
	public void testInformationMethods() {
		contestant.putInformation("Namn", "Bertil");
		assertEquals("Bertil", contestant.getInformation("Namn"));
	}

	
	private static class MockContestant extends AbstractContestant
	{
		@Override
		public int compareTo(AbstractContestant o) {
			return 0;
		}

		@Override
		protected String specifiedToString(CompetitionType competitionType) {
			return null;
		}

		@Override
		public Time getTotalTime() {
			return null;
		}
	}
}
