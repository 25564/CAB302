package asgn1Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn1Exceptions.TeamException;
import asgn1SoccerCompetition.SoccerTeam;
import asgn1SoccerCompetition.SportsTeamForm;
import asgn1SportsUtils.WLD;

/**
 * A set of JUnit tests for the asgn1SoccerCompetition.SoccerTeamForm class
 *
 * @author Alan Woodley
 *
 */
public class SportsTeamFormTests {
	@Test
    public void testBlankEntry() { 
		SportsTeamForm blankTeamForm = new SportsTeamForm();
		
	    assertEquals("-----", blankTeamForm.toString());
	    assertEquals(0, blankTeamForm.getNumGames());
	}
	
	@Test
    public void testStandard() { 
		SportsTeamForm SportTeamForm = new SportsTeamForm();
		
		SportTeamForm.addResultToForm(WLD.WIN);
		SportTeamForm.addResultToForm(WLD.LOSS);
		SportTeamForm.addResultToForm(WLD.DRAW);
		
	    assertEquals("DLW--", SportTeamForm.toString());
	}
	
	@Test
    public void testMoreThanCap() { 
		SportsTeamForm SportTeamForm = new SportsTeamForm();
		
		SportTeamForm.addResultToForm(WLD.WIN);
		SportTeamForm.addResultToForm(WLD.LOSS);
		SportTeamForm.addResultToForm(WLD.DRAW);
		SportTeamForm.addResultToForm(WLD.WIN);
		SportTeamForm.addResultToForm(WLD.LOSS);
		SportTeamForm.addResultToForm(WLD.DRAW);

	    assertEquals("DLWDL", SportTeamForm.toString());
	    assertEquals(6, SportTeamForm.getNumGames());
	}
	
	@Test
    public void testRestForm() { 
		SportsTeamForm SportTeamForm = new SportsTeamForm();
		
		SportTeamForm.addResultToForm(WLD.WIN);
		SportTeamForm.addResultToForm(WLD.LOSS);
		SportTeamForm.resetForm();
		SportTeamForm.addResultToForm(WLD.WIN);
		SportTeamForm.addResultToForm(WLD.LOSS);
		SportTeamForm.addResultToForm(WLD.DRAW);

	    assertEquals("DLW--", SportTeamForm.toString());
	    assertEquals(3, SportTeamForm.getNumGames());
	}
}
