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
	
	@Test
    public void TestRegisterDuplicateTeam() throws LeagueException { 
		thrown.expect(LeagueException.class);
		thrown.expectMessage("Ducks is not an available name");
		
		SoccerLeague League = new SoccerLeague(2);
		try {
			SoccerTeam TeamA = new SoccerTeam("Ducks", "Birds");
			League.registerTeam(TeamA);		
			League.registerTeam(TeamA);
			
		} catch (TeamException e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void TestRegisterTooManyTeams() throws LeagueException {
		thrown.expect(LeagueException.class);
		thrown.expectMessage("Too many teams in league");
		
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
	
	@Test
    public void AttemptToAddTeamDuringSeason() throws LeagueException { 
		thrown.expect(LeagueException.class);
		thrown.expectMessage("Cannot add team while season is active");
 
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
	
	@Test
    public void TestAttemptToRemoveTeamDuringSeason() throws LeagueException { 
		thrown.expect(LeagueException.class);
		thrown.expectMessage("Cannot remove team while season is active");
 
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
	
	@Test
    public void TestAttemptToRemoveNonExistantTeam() throws LeagueException { 
		thrown.expect(LeagueException.class);
		thrown.expectMessage("Cannot remove non existant team");
 
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
	
	@Test
    public void TestStartingSeason() throws LeagueException {
		thrown.expect(LeagueException.class);
		thrown.expectMessage("Cannot add team while season is active");
		
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
	
	@Test
    public void TestStartingSeasonWhileActive() throws LeagueException {
		thrown.expect(LeagueException.class);
		thrown.expectMessage("League is already in season");
		
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
	
	@Test
    public void TestStartingTeamWithoutEnoughTeams() throws LeagueException {
		thrown.expect(LeagueException.class);
		thrown.expectMessage("Not Enough Teams");
		
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
	
	@Test
    public void TestEndingSeasonThatHasntStarted() throws LeagueException {	
		thrown.expect(LeagueException.class);
		thrown.expectMessage("League is not in season");
		
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
	
	@Test
    public void TestGetTeamByNameThatDoesntExist() throws LeagueException {	
		thrown.expect(LeagueException.class);
		thrown.expectMessage("League does not have a Team by the name FakeTeam");
		
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
	
	@Test
    public void TestTeamPlayingItself() throws LeagueException {	
		thrown.expect(LeagueException.class);
		thrown.expectMessage("Team cannot play itself");
		
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
	
	@Test
    public void TestTryingToPlayGameInOffSeason() throws LeagueException {	
		thrown.expect(LeagueException.class);
		thrown.expectMessage("Cannot play match prior to season start");
		
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
	
	@Test
    public void TestPlayingANonExistantTeam() throws LeagueException {	
		thrown.expect(LeagueException.class);
		thrown.expectMessage("Both teams must exist in the same league to play eachother");
		
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
	
	@Test
    public void TestPlayingBothTeamsNotExisting() throws LeagueException {	
		thrown.expect(LeagueException.class);
		thrown.expectMessage("Both teams must exist in the same league to play eachother");
		
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
	
	@Test
    public void TestGetBottomTeamWithNoTeams() throws LeagueException {	
		thrown.expect(LeagueException.class);
		thrown.expectMessage("Not enough teams in league");
		
		SoccerLeague League = new SoccerLeague(0);
		
		League.startNewSeason();
		League.getBottomTeam();
	}
	
	@Test
    public void TestGetTopTeamWithNoTeams() throws LeagueException {	
		thrown.expect(LeagueException.class);
		thrown.expectMessage("Not enough teams in league");
		
		SoccerLeague League = new SoccerLeague(0);
		
		League.startNewSeason();
		League.getTopTeam();
	}
}

