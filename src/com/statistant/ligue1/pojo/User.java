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
	private String reportPath;
	private int passwordModified;
	
	public User(String login, String password, Date licenceEndedDate, String reportPath, int passwordModified) {
		this.login = login;
		this.password = password;
		this.licenceEndedDate = licenceEndedDate;
		this.reportPath = reportPath;
		this.passwordModified = passwordModified;
	}
	
	public User(String login, String password) {
		this.login = login;
		this.password = password;
		this.licenceEndedDate = DateUtils.addYears(new Date(), 1);
		this.reportPath = "C:\\Ligue1";
		this.passwordModified = 0;
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
	public String getReportPath() {
		return reportPath;
	}
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	public int getPasswordModified() {
		return passwordModified;
	}
	public void setPasswordModified(int passwordModified) {
		this.passwordModified = passwordModified;
	}

	public static void main(String[] args) {
		
	}
	
}
