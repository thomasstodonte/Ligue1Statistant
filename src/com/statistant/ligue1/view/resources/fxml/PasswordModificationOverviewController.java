package com.statistant.ligue1.view.resources.fxml;

import com.statistant.ligue1.controller.IncoherentArgumentException;
import com.statistant.ligue1.controller.UnhandledPasswordException;
import com.statistant.ligue1.dao.DatabaseConnection;
import com.statistant.ligue1.dao.NullUserException;
import com.statistant.ligue1.pojo.User;
import com.statistant.ligue1.utils.AuthentificationUtils;
import com.statistant.ligue1.utils.Ligue1Utils;
import com.statistant.ligue1.view.InitializeWindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PasswordModificationOverviewController {
	
	private final StringProperty identifiant = new SimpleStringProperty();
	@FXML private TextField login;
	@FXML private PasswordField newPassword;
	@FXML private PasswordField confirmationPassword;
	
	public String getLogin() {
		return login.getText();
	}

	public void setLogin(String login) {
		this.identifiant.set(login);
		this.login.setText(login);
	}

	public PasswordField getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(PasswordField newPassword) {
		this.newPassword = newPassword;
	}

	public PasswordField getConfirmationPassword() {
		return confirmationPassword;
	}

	public void setConfirmationPassword(PasswordField confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}

	@FXML
	public void handleValidate() {
		String login = getLogin();
		String newPassword = getNewPassword().getText();
		String confirmationPassword = getConfirmationPassword().getText();
		try {
			AuthentificationUtils.checkAreNotEmpty(newPassword, confirmationPassword);
			User user = DatabaseConnection.getUserByLogin(login);
			AuthentificationUtils.checkPasswordMatchRequiredOptions(newPassword);
			AuthentificationUtils.checkPasswordEqualsConfirmation(newPassword, confirmationPassword);
			user.setPassword(AuthentificationUtils.crypt(newPassword));
			user.setPasswordModified(1);
			DatabaseConnection.createOrUpdateUser(user);
			InitializeWindow.alertInfo("Votre mot de passe a été modifié avec succès.");
			InitializeWindow.showMenuOverview();
		}
		catch (IncoherentArgumentException | NullUserException | UnhandledPasswordException e) {
			Ligue1Utils.reportError(e.getMessage());
			return;
		}
		
	}

	public void setUser(User user) {
		identifiant.set(user.getLogin());
		setLogin(user.getLogin());
		login.setText(getLogin());
	}

}
