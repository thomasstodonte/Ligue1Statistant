package com.statistant.ligue1.view.resources.fxml;

import com.statistant.ligue1.controller.StatisticController;
import com.statistant.ligue1.pojo.Confrontation;
import com.statistant.ligue1.utils.Ligue1Utils;
import com.statistant.ligue1.view.InitializeWindow;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class ConfrontationOverviewController {
    
    @FXML
    private void handleMainMenu() {
    	InitializeWindow.showMenuOverview();
    }
    
    @FXML
    private void handleNew() {
    	InitializeWindow.showNewConfrontationWindow();
    }
    
    @FXML
    private void handleModify() {
    	TableView<Confrontation> tableConfrontation = InitializeWindow.getTableConfrontations();
		if (tableConfrontation != null) {
			Confrontation confrontation = tableConfrontation.getSelectionModel().getSelectedItem();
			if (confrontation == null) {
				Ligue1Utils.reportError("Merci de saisir une confrontation à modifier");
				return;
			}
			InitializeWindow.showModifyConfrontationWindow(confrontation);
		}
		else {
			Ligue1Utils.reportError("Erreur à la récupération du tableau des confrontations");
		}
    }
    
    @FXML
    private void handleUpdate() {
    	StatisticController.execute();
    	Ligue1Utils.reportInfo("Mise à jour des statistiques des confrontations OK après clic sur le bouton !");
    	InitializeWindow.alertInfo("Mise à jour des statistiques des confrontations OK après clic sur le bouton !");
    }

}
