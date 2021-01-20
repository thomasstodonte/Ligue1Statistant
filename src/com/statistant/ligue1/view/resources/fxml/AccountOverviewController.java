package com.statistant.ligue1.view.resources.fxml;

import java.io.File;

import com.statistant.ligue1.dao.DatabaseConnection;
import com.statistant.ligue1.dao.NullUserException;
import com.statistant.ligue1.pojo.User;
import com.statistant.ligue1.utils.Ligue1Utils;
import com.statistant.ligue1.view.InitializeWindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

public class AccountOverviewController {
	
	private final StringProperty identifiant = new SimpleStringProperty();
	@FXML private TextField login;
	
	private final StringProperty adresseMail = new SimpleStringProperty();
	@FXML private TextField email;
	
	private final StringProperty motDePasse = new SimpleStringProperty();
	@FXML private PasswordField password;
	
	private final StringProperty emplacement = new SimpleStringProperty();
	@FXML private TextField reportPath;
	
	private final StringProperty typeAbo = new SimpleStringProperty();
	@FXML private ChoiceBox<String> subscribtionType;
	
	ObservableList<String> abonnements = FXCollections.observableArrayList("EQUIPES","JOURNEES");
	
	private final StringProperty equipes = new SimpleStringProperty();
	@FXML private TextField teams;
	
	private final StringProperty nbRapportsParEquipe = new SimpleStringProperty();
	@FXML private TextField nbReportsPerTeam;
	
	private final StringProperty nbRapportsRestants = new SimpleStringProperty();
	@FXML private TextField nbReportsLeft;
	
	private final StringProperty journees = new SimpleStringProperty();
	@FXML private TextField journeys;
	
	@FXML private Text textNbReportsPerTeam;
	@FXML private Text textNbReportsLeft;
	@FXML private Text textTeams;
	@FXML private Text textJourneys;
	
	public String getLogin() {
		return login.getText();
	}

	public void setLogin(String login) {
		this.identifiant.set(login);
		this.login.setText(login);
	}
	
	public String getEmail() {
		return email.getText();
	}

	public void setEmail(String email) {
		this.adresseMail.set(email);
		this.email.setText(email);
	}

	public String getPassword() {
		return password.getText();
	}

	public void setPassword(String password) {
		this.motDePasse.set(password);
		this.password.setText(password);
	}

	public String getReportPath() {
		return reportPath.getText();
	}

	public void setReportPath(String reportPath) {
		this.emplacement.set(reportPath);
		this.reportPath.setText(reportPath);
		AuthentificationOverviewController.REPORT_PATH = reportPath;
	}

	public String getSubscribtionType() {
		return subscribtionType.getValue().toString();
	}

	public void setSubscribtionType(String subscribtionType) {
		this.typeAbo.set(subscribtionType);
		this.subscribtionType.setValue(subscribtionType);
		AuthentificationOverviewController.SUBSCRIPTION_TYPE = subscribtionType;
	}

	public String getTeams() {
		return teams.getText();
	}

	public void setTeams(String teams) {
		this.teams.setText(teams);
		this.equipes.set(teams);
		AuthentificationOverviewController.MY_TEAMS = teams;
	}

	public Integer getNbReportsPerTeam() {
		return Integer.parseInt(nbReportsPerTeam.getText());
	}

	public void setNbReportsPerTeam(String nbReportsPerTeam) {
		this.nbReportsPerTeam.setText(nbReportsPerTeam);
		this.nbRapportsParEquipe.set(nbReportsPerTeam);
		AuthentificationOverviewController.NB_REPORTS_PER_TEAM = Integer.parseInt(nbReportsPerTeam);
	}

	public Integer getNbReportsLeft() {
		return Integer.parseInt(nbReportsLeft.getText());
	}

	public void setNbReportsLeft(String nbReportsLeft) {
		this.nbReportsLeft.setText(nbReportsLeft);
		this.nbRapportsRestants.set(nbReportsLeft);
		AuthentificationOverviewController.NB_REPORTS_LEFT = Integer.parseInt(nbReportsLeft);
	}

	public String getJourneys() {
		return journeys.getText();
	}

	public void setJourneys(String journeys) {
		this.journeys.setText(journeys);
		this.journees.set(journeys);
		AuthentificationOverviewController.JOURNEES_SUBSCRIBED = journeys;
	}

	@FXML
	public void handleBackToMainMenu() {
		InitializeWindow.showMenuOverview();
	}

	@FXML
	public void handleModifyPassword() {
		User user;
		try {
			user = DatabaseConnection.getUserByLogin(AuthentificationOverviewController.USER_CONNECTED);
			InitializeWindow.showPasswordModificationWithBackButtonOverview(user);
		} catch (NullUserException e) {
			Ligue1Utils.reportError(e.getMessage());
			return;
		}
	}
	
	@FXML
	public void handleModifyReportPath() {
		DirectoryChooser selector = new DirectoryChooser();
		File selectedFile = selector.showDialog(InitializeWindow.primaryStage);
		String reportPath = selectedFile.getAbsolutePath();
		setReportPath(reportPath);
		User user;
		try {
			user = DatabaseConnection.getUserByLogin(AuthentificationOverviewController.USER_CONNECTED);
			user.setReportPath(getReportPath());
			DatabaseConnection.createOrUpdateUser(user);
		} catch (NullUserException e) {
			Ligue1Utils.reportError(e.getMessage());
			return;
		}
	}
	
	@FXML
	private void initialize() {
		subscribtionType.setItems(abonnements);
		String userLogin = AuthentificationOverviewController.USER_CONNECTED;
		try {
			User user = DatabaseConnection.getUserByLogin(userLogin);
			String abo = user.getSubscribtionType();
			subscribtionType.setValue(abo);
			if (abo.equals("EQUIPES")) {
				journeys.setVisible(false);
				textJourneys.setVisible(false);
				nbReportsLeft.setVisible(true);
				textNbReportsLeft.setVisible(true);
				nbReportsPerTeam.setVisible(true);
				textNbReportsPerTeam.setVisible(true);
				teams.setVisible(true);
				textTeams.setVisible(true);				
			}
			if (abo.equals("JOURNEES")) {
				journeys.setVisible(true);
				textJourneys.setVisible(true);
				nbReportsLeft.setVisible(false);
				textNbReportsLeft.setVisible(false);
				nbReportsPerTeam.setVisible(false);
				textNbReportsPerTeam.setVisible(false);
				teams.setVisible(false);
				textTeams.setVisible(false);	
			}
			
		} catch (NullUserException e) {
			Ligue1Utils.reportError(e.getMessage());
			return;
		}
	}

	public void setUser(String userLogin) {
		User user;
		try {
			user = DatabaseConnection.getUserByLogin(userLogin);
		} catch (NullUserException e) {
			Ligue1Utils.reportError(e.getMessage());
			return;
		}
		identifiant.set(user.getLogin());
		setLogin(user.getLogin());
		login.setText(getLogin());
		
		adresseMail.set(user.getEmail());
		setEmail(user.getEmail());
		email.setText(getEmail());
		
		motDePasse.set(user.getPassword());
		setPassword(user.getPassword());
		password.setText(getPassword());
		
		emplacement.set(user.getReportPath());
		setReportPath(user.getReportPath());
		reportPath.setText(getReportPath());
		
		typeAbo.set(user.getSubscribtionType());
		setSubscribtionType(user.getSubscribtionType());
		subscribtionType.setValue(getSubscribtionType());
		
		equipes.set(user.getMyTeams());
		setTeams(user.getMyTeams());
		teams.setText(getTeams());
		
		nbRapportsParEquipe.set(String.valueOf(user.getNbReportsPerTeam()));
		setNbReportsPerTeam(String.valueOf(user.getNbReportsPerTeam()));
		nbReportsPerTeam.setText(String.valueOf(getNbReportsPerTeam()));
		
		nbRapportsRestants.set(String.valueOf(user.getNbReportsLeft()));
		setNbReportsLeft(String.valueOf(user.getNbReportsLeft()));
		nbReportsLeft.setText(String.valueOf(getNbReportsLeft()));
		
		journees.set(user.getJourneesSubscribed());
		setJourneys(user.getJourneesSubscribed());
		journeys.setText(getJourneys());
	}

}
