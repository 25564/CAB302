package asgn1SoccerCompetition;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import asgn1Exceptions.LeagueException;
import asgn1Exceptions.TeamException;

/**
 * A class to model a soccer league. Matches are played between teams and points awarded for a win,
 * loss or draw. After each match teams are ranked, first by points, then by goal difference and then
 * alphabetically. 
 * 
 * @author Alan Woodley
 * @version 1.0
 *
 */
public class SoccerLeague implements SportsLeague{
	// Specifies the number of team required/limit of teams for the league
	private int requiredTeams;
	// Specifies is the league is in the off season
	private boolean offSeason = true;
	private HashMap<String, SoccerTeam> TeamList;
	private List<SoccerTeam> SortedTeams;
	private boolean SeasonActive = false;
	/**
	 * Generates a model of a soccer team with the specified number of teams. 
	 * A season can not start until that specific number of teams has been added. 
	 * Once that number of teams has been reached no more teams can be added unless
	 * a team is first removed. 
	 * 
	 * @param requiredTeams The number of teams required/limit for the league.
	 */
	public SoccerLeague (int requiredTeams){
		this.requiredTeams = requiredTeams;
		this.TeamList = new HashMap<String, SoccerTeam>();
	}

	/**
	 * Registers a team to the league.
	 * 
	 * @param team Registers a team to play in the league.
	 * @throws LeagueException If the season has already started, if the maximum number of 
	 * teams allowed to register has already been reached or a team with the 
	 * same official name has already been registered.
	 */
	public void registerTeam(SoccerTeam team) throws LeagueException {
		if (containsTeam(team.getOfficialName())) {
			throw new LeagueException(team.getOfficialName() + " is not an available name");
		}
		
		if (this.SeasonActive) {
			throw new LeagueException("Cannot add team while season is active");
		}	
	
		if (getRegisteredNumTeams() >= getRequiredNumTeams()) {
			throw new LeagueException("Too many teams in league");
		}
		
		this.TeamList.put(team.getOfficialName(), team);
	}
	
	/**
	 * Removes a team from the league.
	 * 
	 * @param team The team to remove
	 * @throws LeagueException if the season has not ended or if the team is not registered into the league.
	 */
	public void removeTeam(SoccerTeam team) throws LeagueException{
		if (this.SeasonActive) {
			throw new LeagueException("Cannot remove team while season is active");
		}
		
		if (containsTeam(team.getOfficialName()) == false) {
			throw new LeagueException("Cannot remove non existant team");
		}
		
		this.TeamList.remove(team.getOfficialName());
	}
	
	/** 
	 * Gets the number of teams currently registered to the league
	 * 
	 * @return the current number of teams registered
	 */
	public int getRegisteredNumTeams(){
		return this.TeamList.size();
	}
	
	/**
	 * Gets the number of teams required for the league to begin its 
	 * season which is also the maximum number of teams that can be registered
	 * to a league.

	 * @return The number of teams required by the league/maximum number of teams in the league
	 */
	public int getRequiredNumTeams(){
		return requiredTeams;
	}
	
	/** 
	 * Starts a new season by reverting all statistics for each team to initial values.
	 * 
	 * @throws LeagueException if the number of registered teams does not equal the required number of teams or if the season has already started
	 */
	public void startNewSeason() throws LeagueException{
		if (this.SeasonActive) {
			throw new LeagueException("League is already in season");
		}
		
		if (this.getRegisteredNumTeams() != this.requiredTeams) {
			throw new LeagueException("Not Enough Teams");
		}
		
		for (HashMap.Entry<String, SoccerTeam> entry : this.TeamList.entrySet()) {
		    entry.getValue().resetStats();
		}
		
		this.offSeason = false;
		this.SeasonActive = true;
	}
	

	/**
	 * Ends the season.
	 * 
	 * @throws LeagueException if season has not started
	 */
	public void endSeason() throws LeagueException{
		if (this.SeasonActive == false) {
			throw new LeagueException("League is not in season");
		}
		
		this.offSeason = true;
		this.SeasonActive = false;
	}
	
	/**
	 * Specifies if the league is in the off season (i.e. when matches are not played).
	 * @return True If the league is in its off season, false otherwise.
	 */
	public boolean isOffSeason(){
		return this.offSeason;
	}
	
	
	/**
	 * Returns a team with a specific name.
	 * 
	 * @param name The official name of the team to search for.
	 * @return The team object with the specified official name.
	 * @throws LeagueException if no team has that official name.
	 */
	public SoccerTeam getTeamByOfficalName(String name) throws LeagueException{		
		if (containsTeam(name) == false) {
			throw new LeagueException("League does not have a Team by the name " + name);
		}
		
		return this.TeamList.get(name);
	}
		
	/**
	 * Plays a match in a specified league between two teams with the respective goals. After each match the teams are
	 * resorted.
     *
	 * @param homeTeamName The name of the home team.
	 * @param homeTeamGoals The number of goals scored by the home team.
	 * @param awayTeamName The name of the away team.
	 * @param awayTeamGoals The number of goals scored by the away team.
	 * @throws LeagueException If the season has not started or if both teams have the same official name or if a team does not exist
	 */
	public void playMatch(String homeTeamName, int homeTeamGoals, String awayTeamName, int awayTeamGoals) throws LeagueException{
		if (homeTeamName == awayTeamName) {
			throw new LeagueException("Team cannot play itself");
		}
		
		if (this.SeasonActive == false) {
			throw new LeagueException("Cannot play match prior to season start");
		}
		
		if (this.containsTeam(homeTeamName) == false || this.containsTeam(awayTeamName) == false) {
			throw new LeagueException("Both teams must exist in the same league to play eachother");
		}
		
		try {
			this.TeamList.get(homeTeamName).playMatch(homeTeamGoals, awayTeamGoals);
			this.TeamList.get(awayTeamName).playMatch(awayTeamGoals, homeTeamGoals);
		} catch (TeamException e) {
		    System.err.println("Caught Team Exception when trying to play match: " + e.getMessage());
		}
	}
	
	/**
	 * Displays a ranked list of the teams in the league  to the screen.
	 */
	public void displayLeagueTable(){
		this.sortTeams();
				
		for (SoccerTeam team: this.SortedTeams) {
			team.displayTeamDetails();
		}
	}	
	
	/**
	 * Returns the highest ranked team in the league.
     *
	 * @return The highest ranked team in the league. 
	 * @throws LeagueException if the number of teams is zero or less than the required number of teams.
	 */
	public SoccerTeam getTopTeam() throws LeagueException{
		sortTeams();
		
		if (this.SortedTeams.size() < this.requiredTeams || this.SortedTeams.size() == 0) {
			throw new LeagueException("Not enough teams in league");
		}
		
		return this.SortedTeams.get(0); 
	}

	/**
	 * Returns the lowest ranked team in the league.
     *
	 * @return The lowest ranked team in the league. 
	 * @throws LeagueException if the number of teams is zero or less than the required number of teams.
	 */
	public SoccerTeam getBottomTeam() throws LeagueException{
		sortTeams();
		
		if (this.SortedTeams.size() < this.requiredTeams || this.SortedTeams.size() == 0) {
			throw new LeagueException("Not enough teams in league");
		}
		
		return this.SortedTeams.get(this.SortedTeams.size() - 1); 
	}

	/** 
	 * Sorts the teams in the league.
	 */
    public void sortTeams(){		
    	List<SoccerTeam> SortedTeams = new ArrayList<SoccerTeam>(this.TeamList.values());

        Collections.sort(SortedTeams, new Comparator<SoccerTeam>() {
            public int compare(SoccerTeam Team1, SoccerTeam Team2) {
                return Team1.compareTo(Team2);
            }
        });
        
        this.SortedTeams = SortedTeams;
    }
    
    
    /**
     * Specifies if a team with the given official name is registered to the league.
     * 
     * @param name The name of a team.
     * @return True if the team is registered to the league, false otherwise. 
     */
    public boolean containsTeam(String name){
    	return this.TeamList.containsKey(name);
    }
    
}
