package com.statistant.ligue1.view.resources.fxml;

import com.statistant.ligue1.view.InitializeWindow;

import javafx.fxml.FXML;

public class MenuOverviewController {
    
    @FXML
    private void handleMatchs() {
    	InitializeWindow.showMatchOverview();
    }
    
    @FXML
    private void handleAccount() {
    	String userLogin = AuthentificationOverviewController.getUSER_LOGIN();
    	InitializeWindow.showAccountOverview(userLogin);
    }
    
    @FXML
    private void handleConfrontations() {
    	InitializeWindow.showConfrontationOverview();
    }
    
    @FXML
    private void handleStandings() {
    	InitializeWindow.showStandingOverview();
    }
    
    @FXML
    private void handleResetAllSeason() {
    	InitializeWindow.showAlertResetAllSeason();
    }

}
