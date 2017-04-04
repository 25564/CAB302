package asgn1Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import asgn1Exceptions.LeagueException;
import asgn1Exceptions.TeamException;
import asgn1SoccerCompetition.SoccerLeague;
import asgn1SoccerCompetition.SoccerTeam;


/**
 * A set of JUnit tests for the asgn1SoccerCompetition.SoccerLeage class
 *
 * @author Alan Woodley
 *
 */

public class SoccerLeagueTests {
    @Rule
    public ExpectedException thrown= ExpectedException.none();
    
    public void testConstructor() {
		SoccerLeague LeagueA = new SoccerLeague(24);
		SoccerLeague LeagueB = new SoccerLeague(9);
		
		assertEquals(24, LeagueA.getRequiredNumTeams());
		assertEquals(9, LeagueB.getRequiredNumTeams());		
	}
    
	@Test 
    public void TestRegisterTeam() { 
		SoccerLeague League = new SoccerLeague(2);
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			League.registerTeam(TeamA);
		
		    assertEquals(1, League.getRegisteredNumTeams());
		    assertEquals(true, League.containsTeam("Ducks"));
		} catch (LeagueException | TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = LeagueException.class)
    public void TestRegisterDuplicateTeam() throws LeagueException { 
		
		SoccerLeague League = new SoccerLeague(2);
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			League.registerTeam(TeamA);		
			League.registerTeam(TeamA);
			
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = LeagueException.class)
    public void TestRegisterTooManyTeams() throws LeagueException {
	
		SoccerLeague League = new SoccerLeague(2);
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Geese", "Birds");
			SoccerTeam TeamC = new SoccerTeam("Roosters", "Birds");
			
			League.registerTeam(TeamA);		
			League.registerTeam(TeamB);			
			League.registerTeam(TeamC);
			
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = LeagueException.class)
    public void AttemptToAddTeamDuringSeason() throws LeagueException { 

		SoccerLeague League = new SoccerLeague(2);
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Geese", "Birds");
			SoccerTeam TeamC = new SoccerTeam("Roosters", "Birds");
			
			League.registerTeam(TeamA);		
			League.registerTeam(TeamB);		
			
			League.startNewSeason();
			
			League.registerTeam(TeamC);
			
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void TestRemoveTeam() throws LeagueException { 
		SoccerLeague League = new SoccerLeague(2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Geese", "Birds");
			
			League.registerTeam(TeamA);		
			League.registerTeam(TeamB);		
					
		    assertEquals(2, League.getRegisteredNumTeams());
		    assertEquals(true, League.containsTeam("Geese"));
			
			League.removeTeam(TeamB);
			
		    assertEquals(1, League.getRegisteredNumTeams());
		    assertEquals(false, League.containsTeam("Geese"));
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = LeagueException.class)
    public void TestAttemptToRemoveTeamDuringSeason() throws LeagueException { 
	
		SoccerLeague League = new SoccerLeague(2);
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Geese", "Birds");
			
			League.registerTeam(TeamA);		
			League.registerTeam(TeamB);		
			
			League.startNewSeason();
			
			League.removeTeam(TeamB);
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = LeagueException.class)
    public void TestAttemptToRemoveNonExistantTeam() throws LeagueException { 
		SoccerLeague League = new SoccerLeague(2);
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Geese", "Birds");
			SoccerTeam TeamC = new SoccerTeam("Roosters", "Birds");
			
			League.registerTeam(TeamA);		
			League.registerTeam(TeamB);		
				
			League.removeTeam(TeamC);		
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void TestContainsTeam() throws LeagueException {
		SoccerLeague League = new SoccerLeague(2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			
			League.registerTeam(TeamA);		

		    assertEquals(true, League.containsTeam("Ducks"));
		    assertEquals(false, League.containsTeam("Geese"));
		    assertEquals(false, League.containsTeam("Roosters"));
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void TestGetRegisteredTeamCount() throws LeagueException {
		SoccerLeague League = new SoccerLeague(3);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Geese", "Birds");
			SoccerTeam TeamC = new SoccerTeam("Roosters", "Birds");
			
			League.registerTeam(TeamA);		
			League.registerTeam(TeamB);		
			League.registerTeam(TeamC);	
			
		    assertEquals(3, League.getRegisteredNumTeams());

		    League.removeTeam(TeamC);
		    
		    assertEquals(2, League.getRegisteredNumTeams());
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void TestRequiredTeams() throws LeagueException {
		SoccerLeague LeagueA = new SoccerLeague(3);
		SoccerLeague LeagueB = new SoccerLeague(226);
		SoccerLeague LeagueC = new SoccerLeague(18);
		
	    assertEquals(3, LeagueA.getRequiredNumTeams());
	    assertEquals(226, LeagueB.getRequiredNumTeams());
	    assertEquals(18, LeagueC.getRequiredNumTeams());
	}
	
	@Test (expected = LeagueException.class)
    public void TestStartingSeason() throws LeagueException {

		SoccerLeague League = new SoccerLeague(3);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Geese", "Birds");
			SoccerTeam TeamC = new SoccerTeam("Roosters", "Birds");
			SoccerTeam TeamD = new SoccerTeam("Chickens", "Birds");
			
			League.registerTeam(TeamA);		
			League.registerTeam(TeamB);		
			League.registerTeam(TeamC);	
			
			League.startNewSeason();
			
		    assertEquals(false, League.isOffSeason());
		    
		    League.registerTeam(TeamD);		    
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = LeagueException.class)
    public void TestStartingSeasonWhileActive() throws LeagueException {
		SoccerLeague League = new SoccerLeague(2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Geese", "Birds");
			
			League.registerTeam(TeamA);		
			League.registerTeam(TeamB);		
			
			League.startNewSeason();
			
		    assertEquals(false, League.isOffSeason());
		    
		    League.startNewSeason();
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = LeagueException.class)
    public void TestStartingTeamWithoutEnoughTeams() throws LeagueException {
	
		SoccerLeague League = new SoccerLeague(2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			
			League.registerTeam(TeamA);		
			
		    assertEquals(1, League.getRegisteredNumTeams());
		
		    League.startNewSeason();
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void TestStartSeasonClearStats() throws LeagueException {		
		SoccerLeague League = new SoccerLeague(2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Roosters", "Birds");
			
			League.registerTeam(TeamA);					
			League.registerTeam(TeamB);		
					
		    League.startNewSeason();
		    
		    League.playMatch("Ducks", 3, "Roosters", 6);
		    
		    SoccerTeam Season1 = League.getTeamByOfficalName("Roosters");
		    assertEquals(6, Season1.getGoalsScoredSeason());
		    
		    League.endSeason();
		    League.startNewSeason();
		    
		    SoccerTeam Season2 = League.getTeamByOfficalName("Roosters");
		    assertEquals(0, Season2.getGoalsScoredSeason());
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void TestEndingSeason() throws LeagueException {		
		SoccerLeague League = new SoccerLeague(2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Roosters", "Birds");
			
			League.registerTeam(TeamA);					
			League.registerTeam(TeamB);		
					
		    League.startNewSeason();
		    
		    assertEquals(false, League.isOffSeason());
		    
		    League.endSeason();
		    
		    assertEquals(true, League.isOffSeason());
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = LeagueException.class)
    public void TestEndingSeasonThatHasntStarted() throws LeagueException {	

		SoccerLeague League = new SoccerLeague(2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Roosters", "Birds");
			
			League.registerTeam(TeamA);					
			League.registerTeam(TeamB);		
							    
		    assertEquals(true, League.isOffSeason());
		    
		    League.endSeason();
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void TestGetTeamByName() throws LeagueException {
		SoccerLeague League = new SoccerLeague(2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Geese");
			SoccerTeam TeamB = new SoccerTeam("Roosters", "Birds");
			
			League.registerTeam(TeamA);					
			League.registerTeam(TeamB);		
							    
		    assertEquals("Geese", League.getTeamByOfficalName("Ducks").getNickName());		    
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = LeagueException.class)
    public void TestGetTeamByNameThatDoesntExist() throws LeagueException {	

		SoccerLeague League = new SoccerLeague(2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Roosters", "Birds");
			
			League.registerTeam(TeamA);					
			League.registerTeam(TeamB);		
							    		    
		    League.getTeamByOfficalName("FakeTeam");
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void TestPlayMatch() throws LeagueException {		
		SoccerLeague League = new SoccerLeague(2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Roosters", "Birds");
			
			League.registerTeam(TeamA);					
			League.registerTeam(TeamB);		
					
		    League.startNewSeason();
		    
		    League.playMatch("Ducks", 3, "Roosters", 6);
		    
		    SoccerTeam Season1 = League.getTeamByOfficalName("Roosters");
		    assertEquals(6, Season1.getGoalsScoredSeason());
		    assertEquals(3, Season1.getGoalsConcededSeason());
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = LeagueException.class)
    public void TestTeamPlayingItself() throws LeagueException {	
		
		SoccerLeague League = new SoccerLeague(2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Roosters", "Birds");
			
			League.registerTeam(TeamA);					
			League.registerTeam(TeamB);		
					
		    League.startNewSeason();
		    
		    League.playMatch("Ducks", 3, "Ducks", 6);
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = LeagueException.class)
    public void TestTryingToPlayGameInOffSeason() throws LeagueException {	
		
		SoccerLeague League = new SoccerLeague(2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Roosters", "Birds");
			
			League.registerTeam(TeamA);					
			League.registerTeam(TeamB);		
							    
		    League.playMatch("Ducks", 3, "Roosters", 6);
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = LeagueException.class)
    public void TestPlayingANonExistantTeam() throws LeagueException {	
		
		SoccerLeague League = new SoccerLeague(2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Roosters", "Birds");
			
			League.registerTeam(TeamA);					
			League.registerTeam(TeamB);	
			
			League.startNewSeason();
							    
		    League.playMatch("Ducks", 3, "Derps", 6);
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = LeagueException.class)
    public void TestPlayingBothTeamsNotExisting() throws LeagueException {	
		
		SoccerLeague League = new SoccerLeague(2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Roosters", "Birds");
			
			League.registerTeam(TeamA);					
			League.registerTeam(TeamB);	
			
			League.startNewSeason();
							    
		    League.playMatch("Foos", 3, "Derps", 6);
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void TestSortingLeague() throws LeagueException {			
		SoccerLeague League = new SoccerLeague(3);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			SoccerTeam TeamB = new SoccerTeam("Roosters", "Birds");
			SoccerTeam TeamC = new SoccerTeam("Geese", "Birds");
			
			League.registerTeam(TeamA);					
			League.registerTeam(TeamB);	
			League.registerTeam(TeamC);	
			
			League.startNewSeason();
			
			/*  So Roosters has won a game so they are on top and the other two are tied
			 *  on a Draw, however this means the Ducks are on the bottom because they 
			 *  have conceded more points 
			 * */
							    
		    League.playMatch("Ducks", 3, "Roosters", 6);
		    League.playMatch("Ducks", 8, "Geese", 8);

		    assertEquals("Ducks", League.getBottomTeam().getOfficialName());
		    assertEquals("Roosters", League.getTopTeam().getOfficialName());
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = LeagueException.class)
	public void TestGetTopTeamWithNoTeamsOffSeason() throws LeagueException {
		
		SoccerLeague League = new SoccerLeague(2);
		League.getTopTeam();
	}
	
	@Test (expected = LeagueException.class)
	public void TestGetBottomTeamWithNoTeamsOffSeason() throws LeagueException {
	
		SoccerLeague League = new SoccerLeague(2);
		League.getBottomTeam();
	}
	
	@Test (expected = LeagueException.class)
    public void TestGetBottomTeamWithNoTeamsOnSeason() throws LeagueException {	
	
		SoccerLeague League = new SoccerLeague(0);
		
		League.startNewSeason();
		League.getBottomTeam();
	}
	
	@Test (expected = LeagueException.class)
    public void TestGetTopTeamWithNoTeamsOnSeason() throws LeagueException {	
		
		SoccerLeague League = new SoccerLeague(0);
		
		League.startNewSeason();
		League.getTopTeam();
	}
}

