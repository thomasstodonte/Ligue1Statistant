package com.statistant.ligue1.view.resources.fxml;

import java.util.HashSet;
import java.util.Set;

import com.statistant.ligue1.controller.ExpiredMembershipException;
import com.statistant.ligue1.controller.IncoherentArgumentException;
import com.statistant.ligue1.dao.DatabaseConnection;
import com.statistant.ligue1.dao.NullUserException;
import com.statistant.ligue1.pojo.Match;
import com.statistant.ligue1.pojo.User;
import com.statistant.ligue1.utils.AuthentificationUtils;
import com.statistant.ligue1.utils.Ligue1Utils;
import com.statistant.ligue1.view.InitializeWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthentificationOverviewController {
	
	public static String USER_CONNECTED;
	public static String REPORT_PATH;
	public static String JOURNEES_SUBSCRIBED;
	public static String MY_TEAMS;
	public static String SUBSCRIPTION_TYPE;
	public static int NB_REPORTS_LEFT;
	public static int NB_REPORTS_PER_TEAM;
	
	@FXML private TextField login;
	@FXML private PasswordField password;
	
	public TextField getLogin() {
		return login;
	}

	public void setLogin(TextField login) {
		this.login = login;
	}

	public PasswordField getPassword() {
		return password;
	}

	public void setPassword(PasswordField password) {
		this.password = password;
	}

	@FXML
	public void handleConnect() {
		String inputLogin = getLogin().getText();
		String inputPassword = getPassword().getText();
		try {
			AuthentificationUtils.checkAreNotEmpty(inputLogin, inputPassword);
			DatabaseConnection.getUserByLogin(inputLogin);
			User user = DatabaseConnection.getUserByLoginAndPassword(inputLogin, AuthentificationUtils.crypt(inputPassword));
			AuthentificationUtils.checkSubscribtionIsActive(user);
			if (AuthentificationUtils.passwordIsModified(user)) {
				USER_CONNECTED = user.getLogin();
				REPORT_PATH = user.getReportPath();
				JOURNEES_SUBSCRIBED = user.getJourneesSubscribed();
				MY_TEAMS = user.getMyTeams();
				SUBSCRIPTION_TYPE = user.getSubscribtionType();
				NB_REPORTS_LEFT = user.getNbReportsLeft();
				NB_REPORTS_PER_TEAM = user.getNbReportsPerTeam();
				InitializeWindow.showMenuOverview();
			}
			else {
				InitializeWindow.showPasswordModificationOverview(user);
			}
		}
		catch (IncoherentArgumentException | NullUserException | ExpiredMembershipException e) {
			Ligue1Utils.reportError(e.getMessage());
			return;
		}
		
	}

	@FXML
	public void handleForgottenPassword() {
		InitializeWindow.alertInfo("Merci de contacter l'administrateur à l'adresse suivante \"support@statistant.fr\".");
	}

	public static ObservableList<Match> getMatchesOfMyTeams() {
		String teams = AuthentificationOverviewController.MY_TEAMS;
		String[] split = teams.split(";");
		ObservableList<Match> myTeamsMatches = FXCollections.observableArrayList();
		for (String team : split) {
			myTeamsMatches.addAll(DatabaseConnection.getAllMatchesForTeam(team));
		}
		ObservableList<Match> myTeamsMatchesWithoutDoublons = deleteDoublons(myTeamsMatches);
		return myTeamsMatchesWithoutDoublons;
	}

	private static ObservableList<Match> deleteDoublons(ObservableList<Match> myTeamsMatches) {
		ObservableList<Match> myTeamsMatchesWithoutDoublons = FXCollections.observableArrayList();
		Set<Match> set = new HashSet<Match>(myTeamsMatches);
		for (Match match : set) {
			myTeamsMatchesWithoutDoublons.add(match);
		}
		return myTeamsMatchesWithoutDoublons;
	}

	public static ObservableList<Match> getMatchesOfMyJourneys() {
		String journeys = AuthentificationOverviewController.JOURNEES_SUBSCRIBED;
		String[] split = journeys.split(";");
		ObservableList<Match> myJourneysMatches = FXCollections.observableArrayList();
		for (String journey : split) {
			myJourneysMatches.addAll(DatabaseConnection.getAllMatchesForJourney(journey));
		}
		return myJourneysMatches;
	}
	
	

}
