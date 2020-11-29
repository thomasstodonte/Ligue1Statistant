package com.statistant.ligue1.view.resources.fxml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.statistant.ligue1.controller.ConfrontationMatchFormatException;
import com.statistant.ligue1.controller.NullConfrontationException;
import com.statistant.ligue1.controller.ScoreFormatException;
import com.statistant.ligue1.controller.StatisticController;
import com.statistant.ligue1.dao.DatabaseConnection;
import com.statistant.ligue1.dao.NullTeamException;
import com.statistant.ligue1.pojo.Confrontation;
import com.statistant.ligue1.pojo.Team;
import com.statistant.ligue1.utils.Ligue1Utils;
import com.statistant.ligue1.view.InitializeWindow;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewConfrontationOverviewController {

	@FXML
	private TextField fxMatch;
	@FXML
	private TextField fxRecent1;
	@FXML
	private TextField fxRecent2;
	@FXML
	private TextField fxRecent3;
	@FXML
	private TextField fxRecent4;
	@FXML
	private TextField fxRecent5;

	public TextField getFxMatch() {
		return fxMatch;
	}

	public void setFxMatch(TextField fxMatch) {
		this.fxMatch = fxMatch;
	}

	public TextField getFxRecent1() {
		return fxRecent1;
	}

	public void setFxRecent1(TextField fxRecent1) {
		this.fxRecent1 = fxRecent1;
	}

	public TextField getFxRecent2() {
		return fxRecent2;
	}

	public void setFxRecent2(TextField fxRecent2) {
		this.fxRecent2 = fxRecent2;
	}

	public TextField getFxRecent3() {
		return fxRecent3;
	}

	public void setFxRecent3(TextField fxRecent3) {
		this.fxRecent3 = fxRecent3;
	}

	public TextField getFxRecent4() {
		return fxRecent4;
	}

	public void setFxRecent4(TextField fxRecent4) {
		this.fxRecent4 = fxRecent4;
	}

	public TextField getFxRecent5() {
		return fxRecent5;
	}

	public void setFxRecent5(TextField fxRecent5) {
		this.fxRecent5 = fxRecent5;
	}

	@FXML
	private void handleValidate() {
		String matchName = getFxMatch().getText();
		try {
			checkMatchIsAllowed(matchName);
		} catch (ConfrontationMatchFormatException e) {
			Ligue1Utils.reportError(e.getMessage());
			return;
		}
		String match = getFxMatch().getText();
		String recent1 = getFxRecent1().getText();
		String recent2 = getFxRecent2().getText();
		String recent3 = getFxRecent3().getText();
		String recent4 = getFxRecent4().getText();
		String recent5 = getFxRecent5().getText();
		try {
			checkAllFieldsAreOK(recent1, recent2, recent3, recent4, recent5);
		} catch (ScoreFormatException e) {
			Ligue1Utils.reportError(e.getMessage());
			return;
		}
		Confrontation confrontation = new Confrontation(match, recent1, recent2, recent3, recent4, recent5);
		DatabaseConnection.createOrUpdateConfrontation(confrontation);
		try {
			StatisticController.setStatistiques(confrontation);
		} catch (NullConfrontationException e) {
			Ligue1Utils.reportError("Erreur à la mise à jour des statistiques du match : "+confrontation.getMatch());
			e.printStackTrace();
		}
		Confrontation reversedConfrontation = Ligue1Utils.getReversedConfrontation(confrontation);
		DatabaseConnection.createOrUpdateConfrontation(reversedConfrontation);
		try {
			StatisticController.setStatistiques(reversedConfrontation);
		} catch (NullConfrontationException e) {
			Ligue1Utils.reportError("Erreur à la mise à jour des statistiques du match : "+reversedConfrontation.getMatch());
			e.printStackTrace();
		}
		InitializeWindow.alertInfo("Confrontations " + confrontation.getMatch() + " et "+ reversedConfrontation.getMatch() +" créées avec succès.");
		InitializeWindow.showConfrontationOverview();
	}

	private void checkAllFieldsAreOK(String recent1, String recent2, String recent3, String recent4, String recent5) throws ScoreFormatException {
		if (! Ligue1Utils.isEmpty(recent1) && ! Ligue1Utils.isScoreWellFormed(recent1)) {
			throw new ScoreFormatException("Le format du score du match le plus récent n'est pas autorisé. Merci de saisir un score au format x-x.");
		}
		if (! Ligue1Utils.isEmpty(recent2) && ! Ligue1Utils.isScoreWellFormed(recent2)) {
			throw new ScoreFormatException("Le format du score du deuxième match le plus récent n'est pas autorisé. Merci de saisir un score au format x-x.");
		}
		if (! Ligue1Utils.isEmpty(recent3) && ! Ligue1Utils.isScoreWellFormed(recent3)) {
			throw new ScoreFormatException("Le format du score du troisième match le plus récent n'est pas autorisé. Merci de saisir un score au format x-x.");
		}
		if (! Ligue1Utils.isEmpty(recent4) && ! Ligue1Utils.isScoreWellFormed(recent4)) {
			throw new ScoreFormatException("Le format du score du quatrième match le plus récent n'est pas autorisé. Merci de saisir un score au format x-x.");
		}
		if (! Ligue1Utils.isEmpty(recent5) && ! Ligue1Utils.isScoreWellFormed(recent5)) {
			throw new ScoreFormatException("Le format du score du cinquième match le plus récent n'est pas autorisé. Merci de saisir un score au format x-x.");
		}
	}

	private static void checkMatchIsAllowed(String matchName) throws ConfrontationMatchFormatException {
		String regex = "^[A-Z0-9]+-[A-Z0-9]+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(matchName);
		if (!m.matches()) {
			throw new ConfrontationMatchFormatException("Erreur dans le format du match. Merci de réitérer la saisie.");
		}
		String[] split = matchName.split("-");
		String homeTeam = split[0];
		String awayTeam = split[1];
		Team t1 = null;
		Team t2 = null;
		try {
			t1 = DatabaseConnection.getTeam(homeTeam);
			t2 = DatabaseConnection.getTeam(awayTeam);
		} catch (NullTeamException e) {
			throw new ConfrontationMatchFormatException("L'une des équipes est nulle");
		}
		if (t1 == null) {
			throw new ConfrontationMatchFormatException(
					"L'équipe 1 n'évolue pas en ligue 1. Merci de réitérer la saisie");
		}
		if (t2 == null) {
			throw new ConfrontationMatchFormatException(
					"L'équipe 2 n'évolue pas en ligue 1. Merci de réitérer la saisie");
		}
		if (t2.getName().equals(t1.getName())) {
			throw new ConfrontationMatchFormatException(
					"Une équipe ne peut pas s'auto-affronter. Merci de réitérer la saisie");
		}
	}
}
