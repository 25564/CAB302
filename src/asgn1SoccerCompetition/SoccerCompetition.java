/**
 * 
 */
package asgn1SoccerCompetition;

import java.util.ArrayList;
import java.util.HashMap;

import asgn1Exceptions.CompetitionException;
import asgn1Exceptions.LeagueException;
import asgn1Exceptions.TeamException;

/**
 * A class to model a soccer competition. The competition contains one or more number of leagues, 
 * each of which contain a number of teams. Over the course a season matches are played between
 * teams in each league. At the end of the season a premier (top ranked team) and wooden spooner 
 * (bottom ranked team) are declared for each league. If there are multiple leagues then relegation 
 * and promotions occur between the leagues.
 * 
 * @author Alan Woodley
 * @version 1.0
 *
 */
public class SoccerCompetition implements SportsCompetition{
	
	String name;
	protected ArrayList<SoccerLeague> LeagueList;
	
	/**
	 * Creates the model for a new soccer competition with a specific name,
	 * number of leagues and number of teams in each league
	 * 
	 * @param name The name of the competition.
	 * @param numLeagues The number of leagues in the competition.
	 * @param numTeams The number of teams in each league.
	 */
	public SoccerCompetition(String name, int numLeagues, int numTeams){
		this.name = name;
		this.LeagueList = new ArrayList<SoccerLeague>();
		
		for(int i = 0; i < numLeagues; i++) {
			this.LeagueList.add(new SoccerLeague(numTeams));
		}
	}
	
	/**
	 * Retrieves a league with a specific number (indexed from 0). Returns an exception if the 
	 * league number is invalid.
	 * 
	 * @param leagueNum The number of the league to return.
	 * @return A league specified by leagueNum.
	 * @throws CompetitionException if the specified league number is less than 0.
	 *  or equal to or greater than the number of leagues in the competition.
	 */
	public SoccerLeague getLeague(int leagueNum) throws CompetitionException{
		if (this.LeagueList.size() - 1 < leagueNum) {
			throw new CompetitionException("Cannot Retrieve non existant League");
		}
		
		return this.LeagueList.get(leagueNum);
	}
	

	/**
	 * Starts a new soccer season for each league in the competition.
	 */
	public void startSeason() {
		for (SoccerLeague League: this.LeagueList) {
			try {
				League.startNewSeason();
			} catch (LeagueException e) {
			    System.err.println("Caught League Exception when trying to start season: " + e.getMessage());
			}
		}
	}

	
	/** 
	 * Ends the season of each of the leagues in the competition. 
	 * If there is more than one league then it handles promotion
	 * and relegation between the leagues.  
	 * 
	 */
	public void endSeason()  {		
		ArrayList<ArrayList<SoccerTeam>> TransitionLeagueList = new ArrayList<ArrayList<SoccerTeam>>();

		for (SoccerLeague League: this.LeagueList) {	
			TransitionLeagueList.add(new ArrayList<SoccerTeam>());

			try {
				League.endSeason();
			} catch (LeagueException e) {
			    System.err.println("Caught League Exception when trying to end season: " + e.getMessage());
			}
		}
				
		for(int i = 0; i < (this.LeagueList.size()); i++) {	
			try {
				if (this.getLeague(i).getRegisteredNumTeams() > 0) {
					try {
						if (i > 0) {
							SoccerTeam TopTeam = this.getLeague(i).getTopTeam();
							TransitionLeagueList.get(i).add(TopTeam);
						}
					} catch (LeagueException e) {
					    System.err.println("Caught League Exception while isolating top team: " + e.getMessage() + " League: " + i );
					}
					
					try {
						if (i < (this.LeagueList.size() - 1)) {
							SoccerTeam BottomTeam = this.getLeague(i).getBottomTeam();	
							TransitionLeagueList.get(i).add(BottomTeam);
						}
					} catch (LeagueException e) {
					    System.err.println("Caught League Exception while isolating bottom team: " + e.getMessage() + " League: " + i );
					}
				}
			} catch (CompetitionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int LeagueIndex = 0; LeagueIndex < (TransitionLeagueList.size()); LeagueIndex++) {
			try {	
				if (this.getLeague(LeagueIndex).getRegisteredNumTeams() > 0) {
					try {
						if (LeagueIndex < (this.LeagueList.size() - 1)) { // Check not last League
		
							this.getLeague(LeagueIndex).removeTeam(TransitionLeagueList.get(LeagueIndex).get(0));
							this.getLeague(LeagueIndex + 1).removeTeam(TransitionLeagueList.get(LeagueIndex + 1).get(0));
	
							this.getLeague(LeagueIndex).registerTeam(TransitionLeagueList.get(LeagueIndex + 1).get(0));
							this.getLeague(LeagueIndex + 1).registerTeam(TransitionLeagueList.get(LeagueIndex).get(0));
							
							TransitionLeagueList.get(LeagueIndex).remove(0);
							TransitionLeagueList.get(LeagueIndex + 1).remove(0);
						}
					} catch (LeagueException e) {
					    System.err.println("Caught League Exception while re-registering teams in new leagues " + e.getMessage());
						e.printStackTrace();
					}
				}
			} catch (CompetitionException e) {
			    System.err.println("Caught Competition League Exception while re-registering teams in new leagues " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/** 
	 * For each league displays the competition standings.
	 */
	public void displayCompetitionStandings(){
		System.out.println("+++++" + this.name + "+++++");
		
		int i = 0;
		for(SoccerLeague League: this.LeagueList) {
			System.out.println("---- League" + (i +1) + " ----");
			System.out.println("Official Name" +  '\t' +  "Nick Name" + '\t' + "Form" + '\t' +  "Played" + '\t' + "Won" + '\t' + "Lost" + '\t' + "Drawn" + '\t' + "For" + '\t' + "Against" + '\t' + "GlDiff" + '\t' + "Points");
			League.displayLeagueTable();
			i++;
		}
	}
}
