package com.statistant.ligue1.view.resources.fxml;

import com.statistant.ligue1.view.InitializeWindow;

import javafx.fxml.FXML;

public class StandingOverviewController {
    
    @FXML
    private void handleMainMenu() {
    	InitializeWindow.showMenuOverview();
    }
    
    @FXML
    private void handleGeneral() {
    	InitializeWindow.showStandingOverview();
    }
    
    @FXML
    private void handleHome() {
    	InitializeWindow.showHomeStandingOverview();
    }
    
    @FXML
    private void handleAway() {
    	InitializeWindow.showAwayStandingOverview();
    }

}
