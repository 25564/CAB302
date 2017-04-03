package asgn1Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn1Exceptions.TeamException;
import asgn1SoccerCompetition.SoccerTeam;
import asgn1SoccerCompetition.SportsTeamForm;

/**
 * A set of JUnit tests for the asgn1SoccerCompetition.SoccerLeage class
 *
 * @author Alan Woodley
 *
 */
public class SoccerTeamTests {
	@Test
    public void testEntry() { 
		SoccerTeam blankTeam;
		try {
			blankTeam = new SoccerTeam("Blank", "Test");
		    assertEquals("-----", blankTeam.getFormString());
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected=TeamException.class)
    public void BlankNicknameTest() throws TeamException { 
		SoccerTeam SoccerTeam = new SoccerTeam("Blank", "");			
	}

	@Test(expected=TeamException.class)
    public void BlankNameTest() throws TeamException { 
		SoccerTeam SoccerTeam = new SoccerTeam("", "test");			
	}

	@Test
    public void GetNametest() { 
		SoccerTeam blankTeam;
		try {
			blankTeam = new SoccerTeam("TeamOfficialName", "Test");
		    assertEquals("TeamOfficialName", blankTeam.getOfficialName());
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void GetNicknameTest() { 
		SoccerTeam blankTeam;
		try {
			blankTeam = new SoccerTeam("Blank", "TestNickname");
		    assertEquals("TestNickname", blankTeam.getNickName());
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void WinTrackerTest() { 
		SoccerTeam SoccerTeam;
		try {
			SoccerTeam = new SoccerTeam("Blank", "Test");
			
			SoccerTeam.playMatch(3, 1);
			
		    assertEquals("W----", SoccerTeam.getFormString());
		    assertEquals(3, SoccerTeam.getCompetitionPoints());
		    assertEquals(1, SoccerTeam.getMatchesWon());

		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void WLDTest() { 
		SoccerTeam SoccerTeam;
		try {
			SoccerTeam = new SoccerTeam("Blank", "Test");
			
			SoccerTeam.playMatch(3, 1);
			SoccerTeam.playMatch(2, 2);
			SoccerTeam.playMatch(1, 3);

			
		    assertEquals("LDW--", SoccerTeam.getFormString());
		    assertEquals(4, SoccerTeam.getCompetitionPoints());
		    assertEquals(1, SoccerTeam.getMatchesWon());
		    assertEquals(1, SoccerTeam.getMatchesDrawn());
		    assertEquals(1, SoccerTeam.getMatchesLost());
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void SeasonGoalsConcededTest() { 
		SoccerTeam SoccerTeam;
		try {
			SoccerTeam = new SoccerTeam("Blank", "Test");
			
			SoccerTeam.playMatch(3, 1);
			SoccerTeam.playMatch(2, 5);

		    assertEquals(6, SoccerTeam.getGoalsConcededSeason());
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void SeasonGoalsTest() { 
		SoccerTeam SoccerTeam;
		try {
			SoccerTeam = new SoccerTeam("Blank", "Test");
			
			SoccerTeam.playMatch(3, 1);
			SoccerTeam.playMatch(2, 5);

		    assertEquals(5, SoccerTeam.getGoalsScoredSeason());
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void TeamSeasonResetTest() { 
		SoccerTeam SoccerTeam;
		try {
			SoccerTeam = new SoccerTeam("Blank", "Test");
			
			SoccerTeam.playMatch(3, 1);
			SoccerTeam.playMatch(2, 5);
			SoccerTeam.playMatch(2, 5);

			SoccerTeam.resetStats();
			
		    assertEquals(0, SoccerTeam.getCompetitionPoints());
		    assertEquals(0, SoccerTeam.getGoalsConcededSeason());
		    assertEquals(0, SoccerTeam.getMatchesLost());
		    assertEquals(0, SoccerTeam.getMatchesDrawn());
		    assertEquals(0, SoccerTeam.getMatchesWon());
		    assertEquals(0, SoccerTeam.getGoalDifference());
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void ScoreDifference() { 
		SoccerTeam SoccerTeam;
		try {
			SoccerTeam = new SoccerTeam("Blank", "Test");
			
			SoccerTeam.playMatch(3, 1);
			SoccerTeam.playMatch(2, 5);
			SoccerTeam.playMatch(2, 5);
			
		    assertEquals(-4, SoccerTeam.getGoalDifference());
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected=TeamException.class)
    public void ScoreTooHigh() throws TeamException { 
		SoccerTeam SoccerTeam;
		SoccerTeam = new SoccerTeam("Blank", "Test");			
		SoccerTeam.playMatch(21, 1);			
	}
	
	@Test(expected=TeamException.class)
    public void LostByTooMuch() throws TeamException { 
		SoccerTeam SoccerTeam;
		SoccerTeam = new SoccerTeam("Blank", "Test");			
		SoccerTeam.playMatch(1, 21);			
	}
	
	@Test(expected=TeamException.class)
    public void WonByNegativeScore() throws TeamException { 
		SoccerTeam SoccerTeam;
		SoccerTeam = new SoccerTeam("Blank", "Test");			
		SoccerTeam.playMatch(1, -5);			
	}
	
	@Test(expected=TeamException.class)
    public void LostByNegativeScore() throws TeamException { 
		SoccerTeam SoccerTeam;
		SoccerTeam = new SoccerTeam("Blank", "Test");			
		SoccerTeam.playMatch(-11, 1);			
	}	
	
	@Test
    public void CompareTeamAWinsCompetitionPointsTest() { 
		SoccerTeam TeamA;
		SoccerTeam TeamB;

		try {
			TeamA = new SoccerTeam("TeamA", "A");
			TeamB = new SoccerTeam("TeamB", "B");

			TeamA.playMatch(3, 1);
			TeamB.playMatch(1, 3);
			
		    assertEquals(-3, TeamA.compareTo(TeamB));
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void CompareTeamALosesCompetitionPointsTest() { 
		SoccerTeam TeamA;
		SoccerTeam TeamB;

		try {
			TeamA = new SoccerTeam("TeamA", "A");
			TeamB = new SoccerTeam("TeamB", "B");

			TeamA.playMatch(3, 1);
			TeamB.playMatch(6, 1);
			TeamB.playMatch(4, 1);
			
			// Team B won because it has won more games
			
		    assertEquals(3, TeamA.compareTo(TeamB));
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void CompareTeamGoalsDifference() { 
		SoccerTeam TeamA;
		SoccerTeam TeamB;

		try {
			TeamA = new SoccerTeam("TeamA", "A");
			TeamB = new SoccerTeam("TeamB", "B");

			TeamA.playMatch(3, 1);
			TeamB.playMatch(4, 1);
			
			// Team B will win as it has a better goal difference
			
		    assertEquals(1, TeamA.compareTo(TeamB));
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void CompareTeamGoalsDifferenceMultipeMatches() { 
		SoccerTeam TeamA;
		SoccerTeam TeamB;

		try {
			TeamA = new SoccerTeam("TeamA", "A");
			TeamB = new SoccerTeam("TeamB", "B");

			TeamA.playMatch(3, 1);
			TeamB.playMatch(1, 4);
			TeamB.playMatch(3, 2);
			
			// Team A should lose as it has conceeded more points
			
		    assertEquals(-4, TeamA.compareTo(TeamB));
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void CompareTeamsWonCompetePointsLostDifferenceTest() { 
		SoccerTeam TeamA;
		SoccerTeam TeamB;

		try {
			TeamA = new SoccerTeam("TeamA", "A");
			TeamB = new SoccerTeam("TeamB", "B");

			TeamA.playMatch(2, 1);
			TeamA.playMatch(2, 1);
			TeamB.playMatch(1, 1);
			TeamB.playMatch(5, 0);
			
			// Team B has a better total. Bit Team A has more competition points
			
		    assertEquals(-2, TeamA.compareTo(TeamB));
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void CompareToTeamNamesWon() { 
		SoccerTeam TeamA;
		SoccerTeam TeamB;

		try {
			TeamA = new SoccerTeam("TeamA", "A");
			TeamB = new SoccerTeam("TeamB", "B");

			TeamA.playMatch(2, 1);
			TeamB.playMatch(2, 1);
			
			// TeamA is alphabetically before TeamB
			
		    assertEquals(-1, TeamA.compareTo(TeamB));
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
    public void CompareToTeamNamesLost() { 
		SoccerTeam TeamA;
		SoccerTeam TeamB;

		try {
			TeamA = new SoccerTeam("TeamA", "A");
			TeamB = new SoccerTeam("Team", "B");

			TeamA.playMatch(2, 1);
			TeamB.playMatch(2, 1);
			
			// Team is alphabetically before TeamA
			
		    assertEquals(1, TeamA.compareTo(TeamB));
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void CompareTeamsSameScoreAndNameTest() { 
		SoccerTeam TeamA;
		SoccerTeam TeamB;

		try {
			TeamA = new SoccerTeam("TeamA", "A");
			TeamB = new SoccerTeam("TeamA", "B");

			TeamA.playMatch(2, 1);
			TeamB.playMatch(2, 1);
			
			// Teams are identical but the nickname
			
		    assertEquals(0, TeamA.compareTo(TeamB));
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
}
