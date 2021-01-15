package com.statistant.ligue1.view.resources.fxml;

import com.statistant.ligue1.controller.ExpiredMembershipException;
import com.statistant.ligue1.controller.IncoherentArgumentException;
import com.statistant.ligue1.dao.DatabaseConnection;
import com.statistant.ligue1.dao.NullUserException;
import com.statistant.ligue1.pojo.User;
import com.statistant.ligue1.utils.AuthentificationUtils;
import com.statistant.ligue1.utils.Ligue1Utils;
import com.statistant.ligue1.view.InitializeWindow;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthentificationOverviewController {
	
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
			AuthentificationUtils.checkLicenceDateAvailable(user);
			if (AuthentificationUtils.passwordIsModified(user)) {
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
	
	

}
