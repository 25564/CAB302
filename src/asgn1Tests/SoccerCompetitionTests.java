package asgn1Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn1Exceptions.CompetitionException;
import asgn1Exceptions.LeagueException;
import asgn1Exceptions.TeamException;
import asgn1SoccerCompetition.SoccerCompetition;
import asgn1SoccerCompetition.SoccerLeague;
import asgn1SoccerCompetition.SoccerTeam;

/**
 * A set of JUnit tests for the asgn1SoccerCompetition.SoccerCompetition class
 *
 * @author Alan Woodley
 *
 */
public class SoccerCompetitionTests {
	@Test
	public void testConstructor() throws CompetitionException {
		SoccerCompetition Competition = new SoccerCompetition("League", 1, 1);
		
		SoccerLeague League = Competition.getLeague(0);
		SoccerTeam team = null;
		
		try {
			team = new SoccerTeam("TeamA", "TeamB");
		} catch (TeamException e) {
		    System.err.println("Error while initialisng Team " + e.getMessage());
			e.printStackTrace();
		}
		
		if (team != null) {
			try {
				League.registerTeam(team);
			} catch (LeagueException e) {
			    System.err.println("Error While Registering Team " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		assertEquals(true, Competition.getLeague(0).containsTeam("TeamA"));
		assertEquals(1, Competition.getLeague(0).getRegisteredNumTeams());
	}
	
	 
	@Test (expected = CompetitionException.class)
	public void testGetLeagueThrowExceptionOutOfScope() throws CompetitionException {
		SoccerCompetition Competition = new SoccerCompetition("League", 1, 1);
		
		Competition.getLeague(1);
	}
	
	@Test
	public void testStartingSeasonWithoutTeams() throws CompetitionException {
		SoccerCompetition Competition = new SoccerCompetition("League", 3, 0);
		
		Competition.startSeason();
		
		assertEquals(false, Competition.getLeague(0).isOffSeason());
		assertEquals(false, Competition.getLeague(1).isOffSeason());
		assertEquals(false, Competition.getLeague(2).isOffSeason());
	}
	
	@Test
	public void testStartingSeasonWithTeams() throws CompetitionException {
		SoccerCompetition Competition = new SoccerCompetition("League", 3, 1);
		
		try {
			SoccerTeam Team = new SoccerTeam("Team A", "Team");
			
			Competition.getLeague(0).registerTeam(Team);
			Competition.getLeague(1).registerTeam(Team);
			Competition.getLeague(2).registerTeam(Team);
		} catch (TeamException | LeagueException e) {
			e.printStackTrace();
		}
				
		Competition.startSeason();
		
		assertEquals(false, Competition.getLeague(0).isOffSeason());
		assertEquals(false, Competition.getLeague(1).isOffSeason());
		assertEquals(false, Competition.getLeague(2).isOffSeason());
	}
	
	@Test
	public void testEndingSeasonWithoutTeams() throws CompetitionException {
		SoccerCompetition Competition = new SoccerCompetition("League", 3, 0);
		
		Competition.startSeason();
		Competition.endSeason();

		assertEquals(true, Competition.getLeague(0).isOffSeason());
		assertEquals(true, Competition.getLeague(1).isOffSeason());
		assertEquals(true, Competition.getLeague(2).isOffSeason());
	}
	
	@Test
	public void testEndingSeasonWithTeams() throws CompetitionException {
		SoccerCompetition Competition = new SoccerCompetition("League", 3, 1);
		
		try {
			SoccerTeam Team = new SoccerTeam("Team A", "Team");
			
			Competition.getLeague(0).registerTeam(Team);
			Competition.getLeague(1).registerTeam(Team);
			Competition.getLeague(2).registerTeam(Team);
		} catch (TeamException | LeagueException e) {
			e.printStackTrace();
		}
				
		Competition.startSeason();
		Competition.endSeason();
		
		assertEquals(true, Competition.getLeague(0).isOffSeason());
		assertEquals(true, Competition.getLeague(1).isOffSeason());
		assertEquals(true, Competition.getLeague(2).isOffSeason());
	}
	
	// Gnarly tests below. Not for faint of heart
	@Test
	public void testMovementTwoLeagues() throws CompetitionException {
		SoccerCompetition Competition = new SoccerCompetition("League", 2, 2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Team A", "Team");
			SoccerTeam TeamB = new SoccerTeam("Team B", "Team");
			SoccerTeam TeamC = new SoccerTeam("Team C", "Team");
			SoccerTeam TeamD = new SoccerTeam("Team D", "Team");
			
			Competition.getLeague(0).registerTeam(TeamA);
			Competition.getLeague(0).registerTeam(TeamB);

			Competition.getLeague(1).registerTeam(TeamC);
			Competition.getLeague(1).registerTeam(TeamD);
			
			Competition.startSeason();
			
			Competition.getLeague(0).playMatch("Team A", 9, "Team B", 7);
			Competition.getLeague(1).playMatch("Team C", 9, "Team D", 7);

			Competition.endSeason();
			
			assertEquals(false, Competition.getLeague(0).containsTeam("Team B"));
			assertEquals(true, Competition.getLeague(0).containsTeam("Team C"));
			assertEquals(true, Competition.getLeague(1).containsTeam("Team B"));
			assertEquals(false, Competition.getLeague(1).containsTeam("Team C"));

		} catch (TeamException | LeagueException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMovementThreeLeagues() throws CompetitionException {
		SoccerCompetition Competition = new SoccerCompetition("League", 3, 2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Team A", "Team");
			SoccerTeam TeamB = new SoccerTeam("Team B", "Team");
			SoccerTeam TeamC = new SoccerTeam("Team C", "Team");
			SoccerTeam TeamD = new SoccerTeam("Team D", "Team");
			SoccerTeam TeamE = new SoccerTeam("Team E", "Team");
			SoccerTeam TeamF = new SoccerTeam("Team F", "Team");
			
			Competition.getLeague(0).registerTeam(TeamA);
			Competition.getLeague(0).registerTeam(TeamB);

			Competition.getLeague(1).registerTeam(TeamC);
			Competition.getLeague(1).registerTeam(TeamD);
			
			Competition.getLeague(2).registerTeam(TeamE);
			Competition.getLeague(2).registerTeam(TeamF);
			
			Competition.startSeason();
			
			Competition.getLeague(0).playMatch("Team A", 1, "Team B", 3);
			Competition.getLeague(1).playMatch("Team C", 4, "Team D", 5);
			Competition.getLeague(2).playMatch("Team E", 2, "Team F", 5);
			
			Competition.endSeason();

			assertEquals(true, Competition.getLeague(0).containsTeam("Team B"));
			assertEquals(false, Competition.getLeague(0).containsTeam("Team A"));
			assertEquals(true, Competition.getLeague(0).containsTeam("Team D"));
			assertEquals(false, Competition.getLeague(1).containsTeam("Team C"));
			assertEquals(true, Competition.getLeague(1).containsTeam("Team F"));
			assertEquals(true, Competition.getLeague(2).containsTeam("Team E"));
			assertEquals(true, Competition.getLeague(2).containsTeam("Team C"));
			
		} catch (TeamException | LeagueException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMovementFourLeagues() throws CompetitionException {
		SoccerCompetition Competition = new SoccerCompetition("League", 4, 2);
		
		try {
			SoccerTeam TeamA = new SoccerTeam("Team A", "Team");
			SoccerTeam TeamB = new SoccerTeam("Team B", "Team");
			SoccerTeam TeamC = new SoccerTeam("Team C", "Team");
			SoccerTeam TeamD = new SoccerTeam("Team D", "Team");
			SoccerTeam TeamE = new SoccerTeam("Team E", "Team");
			SoccerTeam TeamF = new SoccerTeam("Team F", "Team");
			SoccerTeam TeamG = new SoccerTeam("Team G", "Team");
			SoccerTeam TeamH = new SoccerTeam("Team H", "Team");
			
			Competition.getLeague(0).registerTeam(TeamA);
			Competition.getLeague(0).registerTeam(TeamB);

			Competition.getLeague(1).registerTeam(TeamC);
			Competition.getLeague(1).registerTeam(TeamD);
			
			Competition.getLeague(2).registerTeam(TeamE);
			Competition.getLeague(2).registerTeam(TeamF);
			
			Competition.getLeague(3).registerTeam(TeamG);
			Competition.getLeague(3).registerTeam(TeamH);
			
			Competition.startSeason();
			
			Competition.getLeague(0).playMatch("Team A", 1, "Team B", 3);
			Competition.getLeague(1).playMatch("Team C", 4, "Team D", 5);
			Competition.getLeague(2).playMatch("Team E", 2, "Team F", 5);
			Competition.getLeague(3).playMatch("Team G", 2, "Team H", 5);
			
			Competition.endSeason();
			
			assertEquals(true, Competition.getLeague(0).containsTeam("Team B"));
			assertEquals(false, Competition.getLeague(0).containsTeam("Team A"));
			assertEquals(true, Competition.getLeague(0).containsTeam("Team D"));
			assertEquals(false, Competition.getLeague(1).containsTeam("Team C"));
			assertEquals(true, Competition.getLeague(1).containsTeam("Team F"));
			assertEquals(false, Competition.getLeague(2).containsTeam("Team E"));
			assertEquals(true, Competition.getLeague(2).containsTeam("Team C"));
			assertEquals(true, Competition.getLeague(2).containsTeam("Team H"));
			assertEquals(true, Competition.getLeague(3).containsTeam("Team E"));
			assertEquals(true, Competition.getLeague(3).containsTeam("Team G"));
			
		} catch (TeamException | LeagueException e) {
			e.printStackTrace();
		}
	}
}

