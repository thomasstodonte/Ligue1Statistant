package com.statistant.ligue1.view.resources.fxml;

import java.io.IOException;

import com.statistant.ligue1.controller.CountMatch;
import com.statistant.ligue1.controller.GenerateStatisticsReport;
import com.statistant.ligue1.controller.InvalidNumberToConvertFromBooleanException;
import com.statistant.ligue1.controller.MatchController;
import com.statistant.ligue1.controller.NullConfrontationException;
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
		}
		else {
			Ligue1Utils.reportError("Erreur à la récupération du tableau des matchs");
		}
	}

	@FXML
	private void handleNew() {
		InitializeWindow.showNewMatchWindow();
	}

	@FXML
	private void handleCount() {
		try {
			CountMatch.execute();
		} catch (NullConfrontationException | NullTeamException e) {
			Ligue1Utils.reportError("Erreur à la comptabilisation des matchs de Ligue 1 : "+e.getMessage());
			e.printStackTrace();
			return;
		}
		InitializeWindow.showMatchOverview();
	}

	@FXML
	private void handleGenerateReport() {
		TableView<Match> tableMatch = InitializeWindow.getTableMatchs();
		if (tableMatch != null) {
			Match match = tableMatch.getSelectionModel().getSelectedItem();
			if (match == null) {
				Ligue1Utils.reportError("Erreur à la récupération du match depuis la table des matchs. Saisir un match.");
				return;
			}
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
				boolean isReversed = false;
				try {
					stat = DatabaseConnection.getStatistic(matchName);
				}
				catch (NullStatisticException e) {
					
				}
				if (stat == null) {
					matchName = awayTeam + "-" + homeTeam;
					stat = DatabaseConnection.getStatistic(matchName);
					isReversed = true;
				}
				if (stat != null) {
					Ligue1Utils.reportInfo("Les statistiques du match " + matchName + " ont récupérées avec succès.");
					GenerateStatisticsReport.generateReport(stat, isReversed);
					Ligue1Utils.reportInfo("Le rapport du match " + matchName + " a été généré avec succès.");
				}
			} catch (IOException | NullTeamException | NullConfrontationException | NullMatchException | InvalidNumberToConvertFromBooleanException | NullStatisticException ex) {
				Ligue1Utils.reportError("Erreur à la génération du rapport de statistiques !"+ex.getMessage());
				ex.printStackTrace();
				return;
			}
		}
	}

}
