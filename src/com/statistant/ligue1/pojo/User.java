package com.statistant.ligue1.pojo;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * POJO class used for user authentification.
 * @author Thomas CHARMES
 */
public class User {

	private String login;
	private String password;
	private Date licenceEndedDate;
	
	public User(String login, String password, Date licenceEndedDate) {
		this.login = login;
		this.password = password;
		this.licenceEndedDate = licenceEndedDate;
	}
	
	public User(String login, String password) {
		this.login = login;
		this.password = password;
		this.licenceEndedDate = DateUtils.addYears(new Date(), 1);
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLicenceEndedDate() {
		return licenceEndedDate;
	}
	public void setLicenceEndedDate(Date licenceEndedDate) {
		this.licenceEndedDate = licenceEndedDate;
	}
	
	public static void main(String[] args) {
		
	}
	
}
