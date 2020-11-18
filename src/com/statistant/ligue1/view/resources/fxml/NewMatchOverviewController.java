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
import com.statistant.ligue1.pojo.Team;
import com.statistant.ligue1.utils.Ligue1Utils;
import com.statistant.ligue1.view.InitializeWindow;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewMatchOverviewController {

	@FXML
	private TextField journey;
	@FXML
	private TextField homeTeam;
	@FXML
	private TextField score;
	@FXML
	private TextField awayTeam;
	@FXML
	private TextField homeTeamWin;
	@FXML
	private TextField draw;
	@FXML
	private TextField awayTeamWin;
	@FXML
	private TextField homeTeamHasABetterStanding;
	@FXML
	private TextField isAnImportantGameForHomeTeam;
	@FXML
	private TextField isAnImportantGameForAwayTeam;

	public TextField getJourney() {
		return journey;
	}

	public void setJourney(TextField journey) {
		this.journey = journey;
	}

	public TextField getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(TextField homeTeam) {
		this.homeTeam = homeTeam;
	}

	public TextField getScore() {
		return score;
	}

	public void setScore(TextField score) {
		this.score = score;
	}

	public TextField getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(TextField awayTeam) {
		this.awayTeam = awayTeam;
	}

	public TextField getHomeTeamWin() {
		return homeTeamWin;
	}

	public void setHomeTeamWin(TextField homeTeamWin) {
		this.homeTeamWin = homeTeamWin;
	}

	public TextField getDraw() {
		return draw;
	}

	public void setDraw(TextField draw) {
		this.draw = draw;
	}

	public TextField getAwayTeamWin() {
		return awayTeamWin;
	}

	public void setAwayTeamWin(TextField awayTeamWin) {
		this.awayTeamWin = awayTeamWin;
	}

	public TextField getHomeTeamHasABetterStanding() {
		return homeTeamHasABetterStanding;
	}

	public void setHomeTeamHasABetterStanding(TextField homeTeamHasABetterStanding) {
		this.homeTeamHasABetterStanding = homeTeamHasABetterStanding;
	}

	public TextField getIsAnImportantGameForHomeTeam() {
		return isAnImportantGameForHomeTeam;
	}

	public void setIsAnImportantGameForHomeTeam(TextField isAnImportantGameForHomeTeam) {
		this.isAnImportantGameForHomeTeam = isAnImportantGameForHomeTeam;
	}

	public TextField getIsAnImportantGameForAwayTeam() {
		return isAnImportantGameForAwayTeam;
	}

	public void setIsAnImportantGameForAwayTeam(TextField isAnImportantGameForAwayTeam) {
		this.isAnImportantGameForAwayTeam = isAnImportantGameForAwayTeam;
	}

	@FXML
	private void handleValidate() {
		if (matchTermine()) {
			boolean allFieldsOkAfterMatch = checkAllFormFieldsAfterMatch();
			if (allFieldsOkAfterMatch) {
				String team1 = getHomeTeam().getText();
				String team2 = getAwayTeam().getText();
				String resultat = getScore().getText();
				Integer victoireEquipe1 = Integer.parseInt(getHomeTeamWin().getText());
				Integer victoireEquipe2 = Integer.parseInt(getAwayTeamWin().getText());
				Integer nul = Integer.parseInt(getDraw().getText());
				Integer isImportantGameForHomeTeam = Integer.parseInt(getIsAnImportantGameForHomeTeam().getText());
				Integer isImportantGameForAwayTeam = Integer.parseInt(getIsAnImportantGameForAwayTeam().getText());
				Integer homeTeamHasBetterStanding = Integer.parseInt(getHomeTeamHasABetterStanding().getText());
				Integer journee = Integer.parseInt(getJourney().getText());
				Match match = new Match(team1, team2, resultat, victoireEquipe1, nul, victoireEquipe2,
						isImportantGameForHomeTeam, isImportantGameForAwayTeam, homeTeamHasBetterStanding, journee);
				DatabaseConnection.createOrUpdateMatch(match);
				Ligue1Utils.reportInfo("Match " + match.getId() + " créé avec succès.");
				InitializeWindow.alertInfo("Match " + match.getId() + " créé avec succès.");
				InitializeWindow.showMatchOverview();
			}
		}
		else {
			boolean allFieldsOkBeforeMatch = checkAllFormFieldsBeforeMatch();
			if (allFieldsOkBeforeMatch) {
				String team1 = getHomeTeam().getText();
				String team2 = getAwayTeam().getText();
				Integer isImportantGameForHomeTeam = Integer.parseInt(getIsAnImportantGameForHomeTeam().getText());
				Integer isImportantGameForAwayTeam = Integer.parseInt(getIsAnImportantGameForAwayTeam().getText());
				Integer homeTeamHasBetterStanding = Integer.parseInt(getHomeTeamHasABetterStanding().getText());
				Integer journee = Integer.parseInt(getJourney().getText());
				Match match = new Match(team1, team2,
						isImportantGameForHomeTeam, isImportantGameForAwayTeam, homeTeamHasBetterStanding, journee);
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

	private boolean matchTermine() {
		try {
			MatchController.checkOnlyOneResult(Integer.parseInt(getHomeTeamWin().getText()), Integer.parseInt(getAwayTeamWin().getText()), Integer.parseInt(getDraw().getText()));
		} catch (NullResultatException | NumberFormatException | UnhandledResultatException e) {
			return false;
		}
		return true;
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
		String victoireEquipe1 = homeTeamWin.getText();
		String victoireEquipe2 = awayTeamWin.getText();
		String nul = draw.getText();
		try {
			MatchController.checkOnlyOneResult(Integer.parseInt(victoireEquipe1), Integer.parseInt(victoireEquipe2), Integer.parseInt(nul));
		} catch (NullResultatException | NumberFormatException | UnhandledResultatException e) {
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
			getHomeTeamWin().setText("");
			getDraw().setText("");
			getAwayTeamWin().setText("");
			Ligue1Utils.reportError(e.getMessage());
		}
	}

	@FXML
	private void handleTeamsEntered() {
		String team1 = homeTeam.getText();
		String team2 = awayTeam.getText();
		try {
			MatchController.checkTeamsAreOK(team1, team2);
			updateImportantAndStanding(team1, team2);
		} catch (NullTeamException | SameTeamsException e) {
			getHomeTeamHasABetterStanding().setText("");
			getIsAnImportantGameForHomeTeam().setText("");
			getIsAnImportantGameForAwayTeam().setText("");
			Ligue1Utils.reportError(e.getMessage());
		}
	}

	/**
	 * Coche les cases sur le classement, l'importance, et le résultat en fonction
	 * des informations renseignées dans le formulaire (surnoms des équipes, score)
	 * 
	 * @param awayTeam
	 * @param homeTeam
	 * 
	 * @throws NullTeamException
	 */
	public void updateImportantAndStanding(String e1, String e2) throws NullTeamException {

		Team team1 = DatabaseConnection.getTeam(e1);
		Team team2 = DatabaseConnection.getTeam(e2);
		String equipeMieuxClassee = Ligue1Utils.getEquipeMieuxClassee(team1, team2);
		if (!Ligue1Utils.isEmpty(equipeMieuxClassee)) {
			if (equipeMieuxClassee.equals(e1)) {
				getHomeTeamHasABetterStanding().setText("1");
			} else {
				getHomeTeamHasABetterStanding().setText("0");
			}
		}
		boolean matchImportantE1 = Ligue1Utils.getMatchImportant(e1, e2);
		if (matchImportantE1) {
			getIsAnImportantGameForHomeTeam().setText("1");
		} else {
			getIsAnImportantGameForHomeTeam().setText("0");
		}
		boolean matchImportantE2 = Ligue1Utils.getMatchImportant(e2, e1);
		if (matchImportantE2) {
			getIsAnImportantGameForAwayTeam().setText("1");
		} else {
			getIsAnImportantGameForAwayTeam().setText("0");
		}
		InitializeWindow.alertInfo("Les données concernant l'importance du match ont été correctement mises à jour");
	}

	public void updateResultOnFormWithScore(String score) {
		String[] split = score.split("-");
		if (Integer.parseInt(split[0]) > Integer.parseInt(split[1])) {
			getHomeTeamWin().setText("1");
			getDraw().setText("0");
			getAwayTeamWin().setText("0");
		}
		if (Integer.parseInt(split[0]) == Integer.parseInt(split[1])) {
			getHomeTeamWin().setText("0");
			getDraw().setText("1");
			getAwayTeamWin().setText("0");
		}
		if (Integer.parseInt(split[0]) < Integer.parseInt(split[1])) {
			getHomeTeamWin().setText("0");
			getDraw().setText("0");
			getAwayTeamWin().setText("1");
		}
		InitializeWindow.alertInfo("Les informations liées au score ont été correctement mises à jour");
	}
}
