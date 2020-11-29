package com.statistant.ligue1.view;

import java.io.IOException;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Optional;

import com.statistant.ligue1.dao.DatabaseConnection;
import com.statistant.ligue1.pojo.Confrontation;
import com.statistant.ligue1.pojo.Match;
import com.statistant.ligue1.pojo.Team;
import com.statistant.ligue1.utils.Ligue1Utils;
import com.statistant.ligue1.view.resources.fxml.ModifyConfrontationOverviewController;
import com.statistant.ligue1.view.resources.fxml.ModifyMatchOverviewController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InitializeWindow extends Application {

	private static Stage primaryStage;
	private Stage secondStage;
	private static BorderPane rootLayout;
	static TableView<Match> tableMatchs;
	static TableView<Team> tableGeneralStanding;
	static TableView<Team> tableAwayStanding;
	static TableView<Team> tableHomeStanding;
	static TableView<Confrontation> tableConfrontations;

	public Stage getSecondStage() {
		return this.secondStage;
	}

	public void setSecondStage(Stage stage) {
		this.secondStage = stage;
	}

	public static TableView<Match> getTableMatchs() {
		return tableMatchs;
	}

	public static void setTableMatchs(TableView<Match> tableMatchs) {
		InitializeWindow.tableMatchs = tableMatchs;
	}

	public static TableView<Team> getTableGeneralStanding() {
		return tableGeneralStanding;
	}

	public static void setTableGeneralStanding(TableView<Team> tableGeneralStanding) {
		InitializeWindow.tableGeneralStanding = tableGeneralStanding;
	}

	public static TableView<Team> getTableAwayStanding() {
		return tableAwayStanding;
	}

	public static void setTableAwayStanding(TableView<Team> tableAwayStanding) {
		InitializeWindow.tableAwayStanding = tableAwayStanding;
	}

	public static TableView<Team> getTableHomeStanding() {
		return tableHomeStanding;
	}

	public static void setTableHomeStanding(TableView<Team> tableHomeStanding) {
		InitializeWindow.tableHomeStanding = tableHomeStanding;
	}

	public static TableView<Confrontation> getTableConfrontations() {
		return tableConfrontations;
	}

	public static void setTableConfrontations(TableView<Confrontation> tableConfrontations) {
		InitializeWindow.tableConfrontations = tableConfrontations;
	}

	@Override
	public void start(Stage arg0) throws Exception {
		InitializeWindow.primaryStage = arg0;
		InitializeWindow.primaryStage.setTitle("Ligue 1");
		InitializeWindow.primaryStage.setMaximized(true);
		Image image = new Image(InitializeWindow.class.getResourceAsStream("resources/images/Ligue1.png"));
		InitializeWindow.primaryStage.getIcons().add(image);
		initRootLayout();
		showMenuOverview();

	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(InitializeWindow.class.getResource("resources/fxml/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void showMenuOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(InitializeWindow.class.getResource("resources/fxml/MenuOverview.fxml"));
			AnchorPane menuOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(menuOverview);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ObservableList<Match> getMatchs() {
		ObservableList<Match> matchs = FXCollections.observableArrayList();
		Collection<Match> allMatches = DatabaseConnection.getAllMatches();
		matchs.addAll(allMatches);
		return matchs;
	}

	public static ObservableList<Team> getTeams() {
		ObservableList<Team> teams = FXCollections.observableArrayList();
		Collection<Team> allTeams = DatabaseConnection.getAllTeams();
		teams.addAll(allTeams);
		return teams;
	}

	public static ObservableList<Confrontation> getConfrontations() {
		ObservableList<Confrontation> confrontations = FXCollections.observableArrayList();
		Collection<Confrontation> allConfrontations = DatabaseConnection.getAllConfrontations();
		confrontations.addAll(allConfrontations);
		return confrontations;
	}

	@SuppressWarnings("unchecked")
	public static void showMatchOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(InitializeWindow.class.getResource("resources/fxml/MatchOverview.fxml"));
			AnchorPane matchOverview = (AnchorPane) loader.load();
			AnchorPane child = (AnchorPane) matchOverview.getChildren().get(2);

			TableColumn<Match, String> id = new TableColumn<Match, String>("Id");
			id.setCellValueFactory(new PropertyValueFactory<>("id"));
			TableColumn<Match, String> homeTeamNickname = new TableColumn<Match, String>(
					"Surnom de l'équipe à domicile");
			homeTeamNickname.setCellValueFactory(new PropertyValueFactory<>("homeTeamNickname"));
			TableColumn<Match, String> awayTeamNickname = new TableColumn<Match, String>(
					"Surnom de l'équipe à l'extérieur");
			awayTeamNickname.setCellValueFactory(new PropertyValueFactory<>("awayTeamNickname"));
			TableColumn<Match, String> score = new TableColumn<Match, String>("Score");
			score.setCellValueFactory(new PropertyValueFactory<>("score"));
			TableColumn<Match, Integer> homeTeamWin = new TableColumn<Match, Integer>(
					"Victoire de l'équipe à domicile ?");
			homeTeamWin.setCellValueFactory(new PropertyValueFactory<>("homeTeamWin"));
			TableColumn<Match, Integer> draw = new TableColumn<Match, Integer>("Match nul ?");
			draw.setCellValueFactory(new PropertyValueFactory<>("draw"));
			TableColumn<Match, Integer> awayTeamWin = new TableColumn<Match, Integer>(
					"Victoire de l'équipe à l'extérieur ?");
			awayTeamWin.setCellValueFactory(new PropertyValueFactory<>("awayTeamWin"));
			TableColumn<Match, Integer> isAnImportantGameForHomeTeam = new TableColumn<Match, Integer>(
					"Match important pour l'équipe à domicile ?");
			isAnImportantGameForHomeTeam
					.setCellValueFactory(new PropertyValueFactory<>("isAnImportantGameForHomeTeam"));
			TableColumn<Match, Integer> isAnImportantGameForAwayTeam = new TableColumn<Match, Integer>(
					"Match important pour l'équipe à l'extérieur ?");
			isAnImportantGameForAwayTeam
					.setCellValueFactory(new PropertyValueFactory<>("isAnImportantGameForAwayTeam"));
			TableColumn<Match, Integer> homeTeamHasABetterStanding = new TableColumn<Match, Integer>(
					"L'équipe à domicile est-elle mieux classée ?");
			homeTeamHasABetterStanding.setCellValueFactory(new PropertyValueFactory<>("homeTeamHasABetterStanding"));
			TableColumn<Match, Integer> countMatch = new TableColumn<Match, Integer>("Match comptabilisé ?");
			countMatch.setCellValueFactory(new PropertyValueFactory<>("countMatch"));
			TableColumn<Match, Integer> activeStatisticsReportGeneration = new TableColumn<Match, Integer>(
					"Activer la génération du rapport de statistiques du match ?");
			activeStatisticsReportGeneration
					.setCellValueFactory(new PropertyValueFactory<>("activeStatisticsReportGeneration"));
			TableColumn<Match, Integer> journey = new TableColumn<Match, Integer>("Journée");
			journey.setCellValueFactory(new PropertyValueFactory<>("journey"));

			tableMatchs = new TableView<>();
			tableMatchs.setItems(getMatchs());
			tableMatchs.getColumns().addAll(id, journey, countMatch, homeTeamNickname, awayTeamNickname, score, homeTeamWin, draw,
					awayTeamWin, isAnImportantGameForHomeTeam, isAnImportantGameForAwayTeam, homeTeamHasABetterStanding, activeStatisticsReportGeneration);
			journey.setComparator(journey.getComparator().reversed());
			tableMatchs.getSortOrder().add(journey);
			child.getChildren().add(tableMatchs);

			// Set match overview into the center of root layout.
			rootLayout.setCenter(matchOverview);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void showConfrontationOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(InitializeWindow.class.getResource("resources/fxml/ConfrontationOverview.fxml"));
			AnchorPane confrontationOverview = (AnchorPane) loader.load();
			AnchorPane child = (AnchorPane) confrontationOverview.getChildren().get(2);

			TableColumn<Confrontation, String> match = new TableColumn<Confrontation, String>("Match");
			match.setCellValueFactory(new PropertyValueFactory<>("match"));

			TableColumn<Confrontation, String> recent1 = new TableColumn<Confrontation, String>("Récent 1");
			recent1.setCellValueFactory(new PropertyValueFactory<>("recent1"));

			TableColumn<Confrontation, String> recent2 = new TableColumn<Confrontation, String>("Récent 2");
			recent2.setCellValueFactory(new PropertyValueFactory<>("recent2"));

			TableColumn<Confrontation, String> recent3 = new TableColumn<Confrontation, String>("Récent 3");
			recent3.setCellValueFactory(new PropertyValueFactory<>("recent3"));

			TableColumn<Confrontation, String> recent4 = new TableColumn<Confrontation, String>("Récent 4");
			recent4.setCellValueFactory(new PropertyValueFactory<>("recent4"));

			TableColumn<Confrontation, String> recent5 = new TableColumn<Confrontation, String>("Récent 5");
			recent5.setCellValueFactory(new PropertyValueFactory<>("recent5"));

			tableConfrontations = new TableView<>();
			tableConfrontations.setItems(getConfrontations());
			tableConfrontations.getColumns().addAll(match, recent1, recent2, recent3, recent4, recent5);
			tableConfrontations.getSortOrder().add(match);

			child.getChildren().add(tableConfrontations);

			rootLayout.setCenter(confrontationOverview);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void showStandingOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(InitializeWindow.class.getResource("resources/fxml/StandingOverview.fxml"));
			AnchorPane standingOverview = (AnchorPane) loader.load();

			AnchorPane child = (AnchorPane) standingOverview.getChildren().get(2);

			TableColumn<Team, String> standing = new TableColumn<Team, String>("Classement");
			standing.setCellValueFactory(new PropertyValueFactory<>("standing"));

			TableColumn<Team, String> name = new TableColumn<Team, String>("Nom");
			name.setCellValueFactory(new PropertyValueFactory<>("name"));

			TableColumn<Team, String> nbPoints = new TableColumn<Team, String>("Points");
			nbPoints.setCellValueFactory(new PropertyValueFactory<>("nbPoints"));

			TableColumn<Team, Integer> goalAverage = new TableColumn<Team, Integer>("Différence de buts");
			goalAverage.setCellValueFactory(new PropertyValueFactory<>("goalAverage"));

			TableColumn<Team, String> nbMatchesPlayed = new TableColumn<Team, String>("Matchs joués");
			nbMatchesPlayed.setCellValueFactory(new PropertyValueFactory<>("nbMatchesPlayed"));

			TableColumn<Team, Integer> nbWins = new TableColumn<Team, Integer>("Victoires");
			nbWins.setCellValueFactory(new PropertyValueFactory<>("nbWins"));

			TableColumn<Team, Integer> nbDraws = new TableColumn<Team, Integer>("Nuls");
			nbDraws.setCellValueFactory(new PropertyValueFactory<>("nbDraws"));

			TableColumn<Team, Integer> nbLosses = new TableColumn<Team, Integer>("Défaites");
			nbLosses.setCellValueFactory(new PropertyValueFactory<>("nbLosses"));

			TableColumn<Team, Integer> recent1 = new TableColumn<Team, Integer>("recent1");
			recent1.setCellValueFactory(new PropertyValueFactory<>("recent1"));

			TableColumn<Team, Integer> recent2 = new TableColumn<Team, Integer>("recent2");
			recent2.setCellValueFactory(new PropertyValueFactory<>("recent2"));

			TableColumn<Team, Integer> recent3 = new TableColumn<Team, Integer>("recent3");
			recent3.setCellValueFactory(new PropertyValueFactory<>("recent3"));

			TableColumn<Team, Integer> recent4 = new TableColumn<Team, Integer>("recent4");
			recent4.setCellValueFactory(new PropertyValueFactory<>("recent4"));

			TableColumn<Team, Integer> recent5 = new TableColumn<Team, Integer>("recent5");
			recent5.setCellValueFactory(new PropertyValueFactory<>("recent5"));

			tableGeneralStanding = new TableView<>();
			tableGeneralStanding.setItems(getTeams());
			tableGeneralStanding.getColumns().addAll(standing, name, nbPoints, goalAverage, nbMatchesPlayed, nbWins,
					nbDraws, nbLosses, recent1, recent2, recent3, recent4, recent5);
			tableGeneralStanding.getSortOrder().add(standing);
			tableGeneralStanding.setCenterShape(true);

			child.getChildren().add(tableGeneralStanding);

			rootLayout.setCenter(standingOverview);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void showHomeStandingOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(InitializeWindow.class.getResource("resources/fxml/StandingOverview.fxml"));
			AnchorPane homeStandingOverview = (AnchorPane) loader.load();

			AnchorPane child = (AnchorPane) homeStandingOverview.getChildren().get(2);

			TableColumn<Team, String> standing = new TableColumn<Team, String>("Classement");
			standing.setCellValueFactory(new PropertyValueFactory<>("homeStanding"));

			TableColumn<Team, String> name = new TableColumn<Team, String>("Nom");
			name.setCellValueFactory(new PropertyValueFactory<>("name"));

			TableColumn<Team, String> nbPoints = new TableColumn<Team, String>("Points");
			nbPoints.setCellValueFactory(new PropertyValueFactory<>("nbHomePoints"));

			TableColumn<Team, Integer> goalAverage = new TableColumn<Team, Integer>("Différence de buts");
			goalAverage.setCellValueFactory(new PropertyValueFactory<>("homeGoalAverage"));

			TableColumn<Team, String> nbMatchesPlayed = new TableColumn<Team, String>("Matchs joués");
			nbMatchesPlayed.setCellValueFactory(new PropertyValueFactory<>("nbHomeMatchesPlayed"));

			TableColumn<Team, Integer> nbWins = new TableColumn<Team, Integer>("Victoires");
			nbWins.setCellValueFactory(new PropertyValueFactory<>("nbHomeWins"));

			TableColumn<Team, Integer> nbDraws = new TableColumn<Team, Integer>("Nuls");
			nbDraws.setCellValueFactory(new PropertyValueFactory<>("nbHomeDraws"));

			TableColumn<Team, Integer> nbLosses = new TableColumn<Team, Integer>("Défaites");
			nbLosses.setCellValueFactory(new PropertyValueFactory<>("nbHomeLosses"));

			TableColumn<Team, Integer> recent1 = new TableColumn<Team, Integer>("recent1");
			recent1.setCellValueFactory(new PropertyValueFactory<>("homeRecent1"));

			TableColumn<Team, Integer> recent2 = new TableColumn<Team, Integer>("recent2");
			recent2.setCellValueFactory(new PropertyValueFactory<>("homeRecent2"));

			TableColumn<Team, Integer> recent3 = new TableColumn<Team, Integer>("recent3");
			recent3.setCellValueFactory(new PropertyValueFactory<>("homeRecent3"));

			TableColumn<Team, Integer> recent4 = new TableColumn<Team, Integer>("recent4");
			recent4.setCellValueFactory(new PropertyValueFactory<>("homeRecent4"));

			TableColumn<Team, Integer> recent5 = new TableColumn<Team, Integer>("recent5");
			recent5.setCellValueFactory(new PropertyValueFactory<>("homeRecent5"));

			tableHomeStanding = new TableView<>();
			tableHomeStanding.setItems(getTeams());
			tableHomeStanding.getColumns().addAll(standing, name, nbPoints, goalAverage, nbMatchesPlayed, nbWins,
					nbDraws, nbLosses, recent1, recent2, recent3, recent4, recent5);
			tableHomeStanding.getSortOrder().add(standing);
			tableHomeStanding.setCenterShape(true);

			child.getChildren().add(tableHomeStanding);

			rootLayout.setCenter(homeStandingOverview);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void showAwayStandingOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(InitializeWindow.class.getResource("resources/fxml/StandingOverview.fxml"));
			AnchorPane awayStandingOverview = (AnchorPane) loader.load();

			AnchorPane child = (AnchorPane) awayStandingOverview.getChildren().get(2);

			TableColumn<Team, String> standing = new TableColumn<Team, String>("Classement");
			standing.setCellValueFactory(new PropertyValueFactory<>("awayStanding"));

			TableColumn<Team, String> name = new TableColumn<Team, String>("Nom");
			name.setCellValueFactory(new PropertyValueFactory<>("name"));

			TableColumn<Team, String> nbPoints = new TableColumn<Team, String>("Points");
			nbPoints.setCellValueFactory(new PropertyValueFactory<>("nbAwayPoints"));

			TableColumn<Team, Integer> goalAverage = new TableColumn<Team, Integer>("Différence de buts");
			goalAverage.setCellValueFactory(new PropertyValueFactory<>("awayGoalAverage"));

			TableColumn<Team, String> nbMatchesPlayed = new TableColumn<Team, String>("Matchs joués");
			nbMatchesPlayed.setCellValueFactory(new PropertyValueFactory<>("nbAwayMatchesPlayed"));

			TableColumn<Team, Integer> nbWins = new TableColumn<Team, Integer>("Victoires");
			nbWins.setCellValueFactory(new PropertyValueFactory<>("nbAwayWins"));

			TableColumn<Team, Integer> nbDraws = new TableColumn<Team, Integer>("Nuls");
			nbDraws.setCellValueFactory(new PropertyValueFactory<>("nbAwayDraws"));

			TableColumn<Team, Integer> nbLosses = new TableColumn<Team, Integer>("Défaites");
			nbLosses.setCellValueFactory(new PropertyValueFactory<>("nbAwayLosses"));

			TableColumn<Team, Integer> recent1 = new TableColumn<Team, Integer>("recent1");
			recent1.setCellValueFactory(new PropertyValueFactory<>("awayRecent1"));

			TableColumn<Team, Integer> recent2 = new TableColumn<Team, Integer>("recent2");
			recent2.setCellValueFactory(new PropertyValueFactory<>("awayRecent2"));

			TableColumn<Team, Integer> recent3 = new TableColumn<Team, Integer>("recent3");
			recent3.setCellValueFactory(new PropertyValueFactory<>("awayRecent3"));

			TableColumn<Team, Integer> recent4 = new TableColumn<Team, Integer>("recent4");
			recent4.setCellValueFactory(new PropertyValueFactory<>("awayRecent4"));

			TableColumn<Team, Integer> recent5 = new TableColumn<Team, Integer>("recent5");
			recent5.setCellValueFactory(new PropertyValueFactory<>("awayRecent5"));

			tableAwayStanding = new TableView<>();
			tableAwayStanding.setItems(getTeams());
			tableAwayStanding.getColumns().addAll(standing, name, nbPoints, goalAverage, nbMatchesPlayed, nbWins,
					nbDraws, nbLosses, recent1, recent2, recent3, recent4, recent5);
			tableAwayStanding.getSortOrder().add(standing);
			tableAwayStanding.setCenterShape(true);

			child.getChildren().add(tableAwayStanding);

			rootLayout.setCenter(awayStandingOverview);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	// crée une fenêtre d'alerte
	
	public static void alertError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Attention !");
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	public static void alertInfo(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void showAlertResetAllSeason() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Réinitialiser la saison");
		ButtonType save = new ButtonType("Enregistrer les données actuelles");

		// Header Text: null
		alert.setHeaderText(null);
		alert.setContentText("Vous êtes sur le point de réinitialiser totalement la saison ! Attention : Cette opération est irréversible. Etes-vous sûrs ?");
		alert.getButtonTypes().add(save);
		
		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() != null) {
			if (option.get() == ButtonType.OK) {
				Ligue1Utils.resetAllSeason();
				Ligue1Utils.reportInfo("La saison a été réinitialisée avec succès.");
				InitializeWindow.alertInfo("La saison a été réinitialisée avec succès.");
			}
			else if (option.get() == save) {
				DatabaseConnection.saveAllSeason();
				InitializeWindow.alertInfo("La saison a été enregistrée dans le dossier \"C:\\perso\\Ligue1\\sauvegardes\" avec succès.");
			}
			else if (option.get() == ButtonType.CANCEL) {
				Ligue1Utils.reportInfo("La saison de ligue 1 n'a finalement pas été réinitialisée.");
			}
		}

	}

	public static void showNewMatchWindow() {

		AnchorPane newMatchOverview;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(InitializeWindow.class.getResource("resources/fxml/NewMatchOverview.fxml"));
		try {
			newMatchOverview = (AnchorPane) loader.load();
			Scene scene = new Scene(newMatchOverview);
			Stage newWindow = new Stage();
	        newWindow.setTitle("Nouveau match");
	        newWindow.centerOnScreen();
			newWindow.setScene(scene);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(primaryStage);
			newWindow.show();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public static void showModifyMatchWindow(Match match) {
		AnchorPane modifyMatchOverview;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(InitializeWindow.class.getResource("resources/fxml/ModifyMatchOverview.fxml"));
		try {
			modifyMatchOverview = (AnchorPane) loader.load();
			Stage modifyWindow = new Stage();
			modifyWindow.setTitle("Modification match");
			modifyWindow.centerOnScreen();
			modifyWindow.initModality(Modality.WINDOW_MODAL);
			modifyWindow.initOwner(primaryStage);
			Scene scene = new Scene(modifyMatchOverview);
			modifyWindow.setScene(scene);
			ModifyMatchOverviewController controller = loader.getController();
			controller.setMatch(match);
			modifyWindow.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public static void showNewConfrontationWindow() {
		AnchorPane newConfrontationOverview;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(InitializeWindow.class.getResource("resources/fxml/NewConfrontationOverview.fxml"));
		try {
			newConfrontationOverview = (AnchorPane) loader.load();
			Scene scene = new Scene(newConfrontationOverview);
			Stage newWindow = new Stage();
	        newWindow.setTitle("Nouvelle confrontation");
	        newWindow.centerOnScreen();
			newWindow.setScene(scene);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(primaryStage);
			newWindow.show();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}

	public static void showModifyConfrontationWindow(Confrontation confrontation) {
		AnchorPane modifyConfrontationOverview;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(InitializeWindow.class.getResource("resources/fxml/ModifyConfrontationOverview.fxml"));
		try {
			modifyConfrontationOverview = (AnchorPane) loader.load();
			Stage modifyWindow = new Stage();
			modifyWindow.setTitle("Modification confrontation");
			modifyWindow.centerOnScreen();
			modifyWindow.initModality(Modality.WINDOW_MODAL);
			modifyWindow.initOwner(primaryStage);
			Scene scene = new Scene(modifyConfrontationOverview);
			modifyWindow.setScene(scene);
			ModifyConfrontationOverviewController controller = loader.getController();
			controller.setConfrontation(confrontation);
			modifyWindow.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}
}
