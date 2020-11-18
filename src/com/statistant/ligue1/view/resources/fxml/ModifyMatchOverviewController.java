package com.statistant.ligue1.view.resources.fxml;

import com.statistant.ligue1.controller.MatchController;
import com.statistant.ligue1.controller.NullMatchParametersException;
import com.statistant.ligue1.controller.NullResultatException;
import com.statistant.ligue1.controller.OutOfBounceJourneyNumberException;
import com.statistant.ligue1.controller.SameTeamsException;
import com.statistant.ligue1.controller.ScoreFormatException;
import com.statistant.ligue1.controller.UnhandledResultatException;
import com.statistant.ligue1.dao.DatabaseConnection;
import com.statistant.ligue1.dao.NullTeamException;
import com.statistant.ligue1.pojo.Match;
import com.statistant.ligue1.utils.Ligue1Utils;
import com.statistant.ligue1.view.InitializeWindow;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ModifyMatchOverviewController {

	private final IntegerProperty journee = new SimpleIntegerProperty();
	@FXML
	private TextField journey;

	private final StringProperty equipeDomicile = new SimpleStringProperty();
	@FXML
	private TextField homeTeam;

	private final StringProperty resultat = new SimpleStringProperty();
	@FXML
	private TextField score;

	private final StringProperty equipeExterieur = new SimpleStringProperty();
	@FXML
	private TextField awayTeam;

	private final IntegerProperty victoireE1 = new SimpleIntegerProperty();
	@FXML
	private TextField homeTeamWin;

	private final IntegerProperty nul = new SimpleIntegerProperty();
	@FXML
	private TextField draw;

	private final IntegerProperty victoireE2 = new SimpleIntegerProperty();
	@FXML
	private TextField awayTeamWin;

	private final IntegerProperty e1MieuxClassee = new SimpleIntegerProperty();
	@FXML
	private TextField homeTeamHasABetterStanding;

	private final IntegerProperty e1Important = new SimpleIntegerProperty();
	@FXML
	private TextField isAnImportantGameForHomeTeam;

	private final IntegerProperty e2Important = new SimpleIntegerProperty();
	@FXML
	private TextField isAnImportantGameForAwayTeam;

	public void setMatch(Match match) {
		setJourney(match.getJourney());
		journey.setText(String.valueOf(getJourney()));
		setHomeTeam(match.getHomeTeamNickname());
		homeTeam.setText(getHomeTeam());
		setAwayTeam(match.getAwayTeamNickname());
		awayTeam.setText(getAwayTeam());
		setScore(match.getScore());
		score.setText(String.valueOf(getScore()));
		setHomeTeamWin(match.getHomeTeamWin());
		homeTeamWin.setText(String.valueOf(getHomeTeamWin()));
		setDraw(match.getDraw());
		draw.setText(String.valueOf(getDraw()));
		setAwayTeamWin(match.getAwayTeamWin());
		awayTeamWin.setText(String.valueOf(getAwayTeamWin()));
		setHomeTeamHasABetterStanding(match.getHomeTeamHasABetterStanding());
		homeTeamHasABetterStanding.setText(String.valueOf(getHomeTeamHasABetterStanding()));
		setIsAnImportantGameForHomeTeam(match.getIsAnImportantGameForHomeTeam());
		isAnImportantGameForHomeTeam.setText(String.valueOf(getIsAnImportantGameForHomeTeam()));
		setIsAnImportantGameForAwayTeam(match.getIsAnImportantGameForAwayTeam());
		isAnImportantGameForAwayTeam.setText(String.valueOf(getIsAnImportantGameForAwayTeam()));
	}

	public Integer getFormJourney() {
		Integer returnInt = 0;
		try {
			returnInt = Integer.parseInt(this.journey.getText());
		}
		catch (NumberFormatException e) {
		}
		return returnInt;
	}
	public Integer getJourney() {
		return this.journee.get();
	}

	public void setJourney(Integer journee) {
		this.journee.set(journee);
		journey.setText(String.valueOf(journee));
	}

	public String getFormHomeTeam() {
		return this.homeTeam.getText();
	}
	public String getHomeTeam() {
		return this.equipeDomicile.get();
	}

	public void setHomeTeam(String string) {
		this.equipeDomicile.set(string);
		homeTeam.setText(String.valueOf(string));
	}

	public String getFormScore() {
		return this.score.getText();
	}
	public String getScore() {
		return this.resultat.get();
	}

	public void setScore(String string) {
		this.resultat.set(string);
		score.setText(String.valueOf(string));
	}

	public String getFormAwayTeam() {
		return this.awayTeam.getText();
	}
	public String getAwayTeam() {
		return this.equipeExterieur.get();
	}

	public void setAwayTeam(String string) {
		this.equipeExterieur.set(string);
		awayTeam.setText(String.valueOf(string));
	}

	public Integer getFormHomeTeamWin() {
		Integer returnInt = 0;
		try {
			returnInt = Integer.parseInt(this.homeTeamWin.getText());
		}
		catch (NumberFormatException e) {
		}
		return returnInt;
	}
	public Integer getHomeTeamWin() {
		return this.victoireE1.get();
	}

	public void setHomeTeamWin(Integer number) {
		this.victoireE1.set(number);
		homeTeamWin.setText(String.valueOf(number));
	}

	public Integer getFormDraw() {
		Integer returnInt = 0;
		try {
			returnInt = Integer.parseInt(this.draw.getText());
		}
		catch (NumberFormatException e) {
		}
		return returnInt;
	}
	public Integer getDraw() {
		return this.nul.get();
	}

	public void setDraw(Integer number) {
		this.nul.set(number);
		draw.setText(String.valueOf(number));
	}

	public Integer getFormAwayTeamWin() {
		Integer returnInt = 0;
		try {
			returnInt = Integer.parseInt(this.awayTeamWin.getText());
		}
		catch (NumberFormatException e) {
		}
		return returnInt;
	}
	public Integer getAwayTeamWin() {
		return this.victoireE2.get();
	}

	public void setAwayTeamWin(Integer number) {
		this.victoireE2.set(number);
		awayTeamWin.setText(String.valueOf(number));
	}

	public Integer getFormHomeTeamHasABetterStanding() {
		Integer returnInt = 0;
		try {
			returnInt = Integer.parseInt(this.homeTeamHasABetterStanding.getText());
		}
		catch (NumberFormatException e) {
		}
		return returnInt;
	}
	public Integer getHomeTeamHasABetterStanding() {
		return this.e1MieuxClassee.get();
	}

	public void setHomeTeamHasABetterStanding(Integer number) {
		this.e1MieuxClassee.set(number);
		homeTeamHasABetterStanding.setText(String.valueOf(number));
	}

	public Integer getFormIsAnImportantGameForHomeTeam() {
		Integer returnInt = 0;
		try {
			returnInt = Integer.parseInt(this.isAnImportantGameForHomeTeam.getText());
		}
		catch (NumberFormatException e) {
		}
		return returnInt;
	}
	public Integer getIsAnImportantGameForHomeTeam() {
		return this.e1Important.get();
	}

	public void setIsAnImportantGameForHomeTeam(Integer number) {
		this.e1Important.set(number);
		isAnImportantGameForHomeTeam.setText(String.valueOf(number));
	}

	public Integer getFormIsAnImportantGameForAwayTeam() {
		Integer returnInt = 0;
		try {
			returnInt = Integer.parseInt(this.isAnImportantGameForAwayTeam.getText());
		}
		catch (NumberFormatException e) {
		}
		return returnInt;
	}
	public Integer getIsAnImportantGameForAwayTeam() {
		return this.e2Important.get();
	}

	public void setIsAnImportantGameForAwayTeam(Integer number) {
		this.e2Important.set(number);
		isAnImportantGameForAwayTeam.setText(String.valueOf(number));
	}

	@FXML
	private void handleValidate() {
		boolean matchTermine = false;
		try {
			matchTermine = matchTermine();
		}
		catch (UnhandledResultatException e) {
			Ligue1Utils.reportError(e.getMessage());
			return;
		}
		if (matchTermine) {
			boolean allFieldsOkAfterMatch = checkAllFormFieldsAfterMatch();
			if (allFieldsOkAfterMatch) {
				String team1 = getFormHomeTeam();
				String team2 = getFormAwayTeam();
				String resultat = getFormScore();
				Integer victoireEquipe1 = getFormHomeTeamWin();
				Integer victoireEquipe2 = getFormAwayTeamWin();
				Integer nul = getFormDraw();
				Integer isImportantGameForHomeTeam = getFormIsAnImportantGameForHomeTeam();
				Integer isImportantGameForAwayTeam = getFormIsAnImportantGameForAwayTeam();
				Integer homeTeamHasBetterStanding = getFormHomeTeamHasABetterStanding();
				Integer journee = getFormJourney();
				Match match = new Match(team1, team2, resultat, victoireEquipe1, nul, victoireEquipe2,
						isImportantGameForHomeTeam, isImportantGameForAwayTeam, homeTeamHasBetterStanding, journee);
				DatabaseConnection.createOrUpdateMatch(match);
				Ligue1Utils.reportInfo("Match " + match.getId() + " créé avec succès.");
				InitializeWindow.alertInfo("Match " + match.getId() + " créé avec succès.");
				InitializeWindow.showMatchOverview();
			}
		} else {
			boolean allFieldsOkBeforeMatch = checkAllFormFieldsBeforeMatch();
			if (allFieldsOkBeforeMatch) {
				String team1 = getFormHomeTeam();
				String team2 = getFormAwayTeam();
				Integer isImportantGameForHomeTeam = getFormIsAnImportantGameForHomeTeam();
				Integer isImportantGameForAwayTeam = getFormIsAnImportantGameForAwayTeam();
				Integer homeTeamHasBetterStanding = getFormHomeTeamHasABetterStanding();
				Integer journee = getFormJourney();
				Match match = new Match(team1, team2, isImportantGameForHomeTeam, isImportantGameForAwayTeam,
						homeTeamHasBetterStanding, journee);
				DatabaseConnection.createOrUpdateMatch(match);
				Ligue1Utils.reportInfo("Match " + match.getId() + " créé avec succès.");
				InitializeWindow.alertInfo("Match " + match.getId() + " créé avec succès.");
				InitializeWindow.showMatchOverview();
			}
		}
	}

	private boolean checkAllFormFieldsBeforeMatch() {
		String team1 = homeTeam.getText();
		String team2 = awayTeam.getText();
		try {
			MatchController.checkTeamsAreOK(team1, team2);
		} catch (NullTeamException | SameTeamsException e) {
			Ligue1Utils.reportError(e.getMessage());
			return false;
		}
		String journee = journey.getText();
		try {
			MatchController.checkJourneyNumber(journee);
		} catch (OutOfBounceJourneyNumberException | NumberFormatException e) {
			Ligue1Utils.reportError(e.getMessage());
			return false;
		}
		String isImportantGameForHomeTeam = isAnImportantGameForHomeTeam.getText();
		String isImportantGameForAwayTeam = isAnImportantGameForAwayTeam.getText();
		String homeTeamHasBetterStanding = homeTeamHasABetterStanding.getText();
		try {
			MatchController.checkMatchParameters(isImportantGameForHomeTeam, isImportantGameForAwayTeam,
					homeTeamHasBetterStanding);
		} catch (NullMatchParametersException e) {
			Ligue1Utils.reportError(e.getMessage());
			return false;
		}
		return true;
	}

	private boolean matchTermine() throws UnhandledResultatException {
		try {
			MatchController.checkOnlyOneResult(getFormHomeTeamWin(), getFormAwayTeamWin(), getFormDraw());
		} catch (NullResultatException e) {
			return false;
		}
		return Ligue1Utils.isScoreWellFormed(getFormScore());
	}

	private boolean checkAllFormFieldsAfterMatch() {
		String team1 = homeTeam.getText();
		String team2 = awayTeam.getText();
		try {
			MatchController.checkTeamsAreOK(team1, team2);
		} catch (NullTeamException | SameTeamsException e) {
			Ligue1Utils.reportError(e.getMessage());
			return false;
		}
		String resultat = score.getText();
		try {
			MatchController.checkScore(resultat);
		} catch (ScoreFormatException e) {
			Ligue1Utils.reportError(e.getMessage());
			return false;
		}
		String journee = journey.getText();
		try {
			MatchController.checkJourneyNumber(journee);
		} catch (OutOfBounceJourneyNumberException | NumberFormatException e) {
			Ligue1Utils.reportError(e.getMessage());
			return false;
		}
		Integer victoireEquipe1 = Integer.parseInt(homeTeamWin.getText());
		Integer victoireEquipe2 = Integer.parseInt(awayTeamWin.getText());
		Integer nul = Integer.parseInt(draw.getText());
		try {
			MatchController.checkOnlyOneResult(victoireEquipe1, victoireEquipe2, nul);
		} catch (NullResultatException | UnhandledResultatException e) {
			Ligue1Utils.reportError(e.getMessage());
			return false;
		}

		String isImportantGameForHomeTeam = isAnImportantGameForHomeTeam.getText();
		String isImportantGameForAwayTeam = isAnImportantGameForAwayTeam.getText();
		String homeTeamHasBetterStanding = homeTeamHasABetterStanding.getText();
		try {
			MatchController.checkMatchParameters(isImportantGameForHomeTeam, isImportantGameForAwayTeam,
					homeTeamHasBetterStanding);
		} catch (NullMatchParametersException e) {
			Ligue1Utils.reportError(e.getMessage());
			return false;
		}
		return true;
	}

	@FXML
	private void handleScoreEntered() {
		String resultat = score.getText();
		try {
			MatchController.checkScore(resultat);
			updateResultOnFormWithScore(resultat);
		} catch (ScoreFormatException e) {
			homeTeamWin.setText("");
			draw.setText("");
			awayTeamWin.setText("");
			Ligue1Utils.reportError(e.getMessage());
		}
	}

	public void updateResultOnFormWithScore(String score) {
		String[] split = score.split("-");
		if (Integer.parseInt(split[0]) > Integer.parseInt(split[1])) {
			homeTeamWin.setText("1");
			draw.setText("0");
			awayTeamWin.setText("0");
		}
		if (Integer.parseInt(split[0]) == Integer.parseInt(split[1])) {
			homeTeamWin.setText("0");
			draw.setText("1");
			awayTeamWin.setText("0");
		}
		if (Integer.parseInt(split[0]) < Integer.parseInt(split[1])) {
			homeTeamWin.setText("0");
			draw.setText("0");
			awayTeamWin.setText("1");
		}
		InitializeWindow.alertInfo("Les informations liées au score ont été correctement mises à jour");
	}

}
