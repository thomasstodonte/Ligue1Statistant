package com.statistant.ligue1.view.resources.fxml;

import java.io.IOException;
import java.util.Collection;

import com.statistant.ligue1.controller.CountMatch;
import com.statistant.ligue1.controller.GenerateStatisticsReport;
import com.statistant.ligue1.controller.InvalidNumberToConvertFromBooleanException;
import com.statistant.ligue1.controller.MatchController;
import com.statistant.ligue1.controller.NullConfrontationException;
import com.statistant.ligue1.controller.NullResourceSelectedException;
import com.statistant.ligue1.controller.NullStatisticException;
import com.statistant.ligue1.controller.SameTeamsException;
import com.statistant.ligue1.dao.DatabaseConnection;
import com.statistant.ligue1.dao.NullMatchException;
import com.statistant.ligue1.dao.NullTeamException;
import com.statistant.ligue1.pojo.Match;
import com.statistant.ligue1.pojo.Statistic;
import com.statistant.ligue1.utils.Ligue1Utils;
import com.statistant.ligue1.view.InitializeWindow;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class MatchOverviewController {

	@FXML
	private void handleMainMenu() {
		InitializeWindow.showMenuOverview();
	}

	@FXML
	private void handleModify() {
		TableView<Match> tableMatch = InitializeWindow.getTableMatchs();
		if (tableMatch != null) {
			Match match = tableMatch.getSelectionModel().getSelectedItem();
			if (match == null) {
				Ligue1Utils.reportError("Merci de saisir un match à modifier");
				return;
			}
			InitializeWindow.showModifyMatchWindow(match);
		} else {
			Ligue1Utils.reportError("Erreur à la récupération du tableau des matchs");
		}
	}

	@FXML
	private void handleNew() {
		InitializeWindow.showNewMatchWindow();
	}

	@FXML
	private void handleCount() {
		int nbMatchsCounted = 0;
		try {
			nbMatchsCounted = CountMatch.execute();
		} catch (NullConfrontationException | NullTeamException e) {
			Ligue1Utils.reportError("Erreur à la comptabilisation des matchs de Ligue 1 : " + e.getMessage());
			e.printStackTrace();
			return;
		}
		if (nbMatchsCounted > 0) {
			DatabaseConnection.saveAllSeason();
			InitializeWindow.alertInfo(
					"La saison a été enregistrée dans le dossier \"C:\\perso\\Ligue1\\sauvegardes\" avec succès.");
		}
		InitializeWindow.showMatchOverview();
	}

	private static Match getSelectedMatch() throws NullResourceSelectedException {
		TableView<Match> tableMatch = InitializeWindow.getTableMatchs();
		Match match = tableMatch.getSelectionModel().getSelectedItem();
		if (match == null) {
			throw new NullResourceSelectedException(
					"Erreur à la récupération du match depuis la table des matchs. Saisir un match.");
		}
		return match;
	}

	private static void generateReportMatch(Match match) {
		String homeTeam = match.getHomeTeamNickname();
		String awayTeam = match.getAwayTeamNickname();

		try {
			MatchController.checkTeamsAreOK(homeTeam, awayTeam);
		} catch (NullTeamException | SameTeamsException e) {
			Ligue1Utils.reportError("Incohérence dans les données du formulaire !");
			e.printStackTrace();
		}

		try {
			String matchName = homeTeam + "-" + awayTeam;
			Statistic stat = null;
			try {
				stat = DatabaseConnection.getStatistic(matchName);
			} catch (NullStatisticException e) {

			}
			if (stat != null) {
				Ligue1Utils.reportInfo("Les statistiques du match " + matchName + " ont récupérées avec succès.");
				GenerateStatisticsReport.generateReport(stat);
				Ligue1Utils.reportInfo("Le rapport du match " + matchName + " a été généré avec succès.");
			}
		} catch (IOException | NullTeamException | NullConfrontationException | NullMatchException
				| InvalidNumberToConvertFromBooleanException | NullStatisticException ex) {
			Ligue1Utils.reportError("Erreur à la génération du rapport de statistiques !" + ex.getMessage());
			ex.printStackTrace();
			return;
		}
	}

	@FXML
	private void handleGenerateReport() {
		Match match = null;
		try {
			match = getSelectedMatch();
		} catch (NullResourceSelectedException e) {
			Ligue1Utils.reportError(e.getMessage());
			return;
		}
		generateReportMatch(match);
		InitializeWindow.showMatchOverview();
	}

	@FXML
	private void handleGenerateAllReports() {
		Collection<Match> allMatches = DatabaseConnection.getAllMatches();
		int count = 0;
		for (Match match : allMatches) {
			if (match.getActiveStatisticsReportGeneration() == 1) {
				generateReportMatch(match);
				count++;
			}
		}
		if (count == 0) {
			InitializeWindow.alertInfo("Tous les rapports ont déjà été générés.");
		}
		InitializeWindow.showMatchOverview();
	}

}
