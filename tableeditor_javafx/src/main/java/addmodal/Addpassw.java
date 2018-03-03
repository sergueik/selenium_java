package addmodal;

import interfaces.AddChangePasswAbstr;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.sqlite.SQLiteConfig;
import pojo.Website;
import pswkeeper.Constants;
import sqlite.db;

import java.sql.*;

public class Addpassw implements AddChangePasswAbstr {

	@FXML
	private Button AddButtonAdd;

	@FXML
	private Button AddButtonCancel;

	@FXML
	private TextField txtsite;

	@FXML
	private TextField txtsitelogin;

	@FXML
	private TextField txtsitepass;

	@FXML
	private TextField txtftp;

	@FXML
	private TextField txtftplogin;

	@FXML
	private TextField txtftppass;

	@FXML
	private TextField txtport;

	@FXML
	private TextField txtperson;

	@FXML
	private TextField txtpersonEmail;

	@FXML
	private TextField txtpersonPass;

	@FXML
	private TextField txtpersonPhone;

	@FXML
	private TextField txtdbName;

	@FXML
	private TextField txtdbUser;

	@FXML
	private TextField txtdbPass;

	@FXML
	private TextField txtdbHost;

	@FXML
	private TextField txthostingUrl;

	@FXML
	private TextField txthostingLogin;

	@FXML
	private TextField txthostingPass;

	@FXML
	private TextField txtproviderUrl;

	@FXML
	private TextField txtproviderLogin;

	@FXML
	private TextField txtproviderPass;

	@FXML
	private TextField txtotherUrl;

	@FXML
	private TextField txtotherLogin;

	@FXML
	private TextField txtotherPass;

	@FXML
	private TextField txtnotes;

	private Website website;

	private static Statement stat;
	private static Connection c;

	@Override
	public void AddButtonAdd(ActionEvent actionEvent) {

		website.setSite(txtsite.getText());
		website.setSiteLogin(txtsitelogin.getText());
		website.setSitePass(txtsitepass.getText());
		website.setFtp(txtftp.getText());
		website.setFtpLogin(txtftplogin.getText());
		website.setFtpPass(txtftppass.getText());
		website.setPort(txtport.getText());
		website.setPerson(txtperson.getText());
		website.setPersonEmail(txtpersonEmail.getText());
		website.setPersonPass(txtpersonPass.getText());
		website.setPersonPhone(txtpersonPhone.getText());
		website.setDbName(txtdbName.getText());
		website.setDbUser(txtdbUser.getText());
		website.setDbPass(txtdbPass.getText());
		website.setDbHost(txtdbHost.getText());
		website.setHostingUrl(txthostingUrl.getText());
		website.setHostingLogin(txthostingLogin.getText());
		website.setHostingPass(txthostingPass.getText());
		website.setProviderUrl(txtproviderUrl.getText());
		website.setProviderLogin(txtproviderLogin.getText());
		website.setProviderPass(txtproviderPass.getText());
		website.setOtherUrl(txtotherUrl.getText());
		website.setOtherLogin(txtotherLogin.getText());
		website.setOtherPass(txtotherPass.getText());
		website.setNotes(txtnotes.getText());

		try {
			SQLiteConfig config = new SQLiteConfig();
			c = db.getInstance().getConnection();

			PreparedStatement stat = c.prepareStatement(
					"INSERT INTO data (site, siteLogin, sitePass, ftp, ftpLogin, ftpPass, port, person, personEmail, personPass, personPhone, dbName, dbUser, dbPass, dbHost, hostingUrl, hostingLogin, hostingPass, providerUrl, providerLogin, providerPass, otherUrl, otherLogin, otherPass, notes) VALUES ('"
							+ website.getSite() + "','" + website.getSiteLogin() + "','"
							+ website.getSitePass() + "','" + website.getFtp() + "','"
							+ website.getFtpLogin() + "','" + website.getFtpPass() + "','"
							+ website.getPort() + "','" + website.getPerson() + "','"
							+ website.getPersonEmail() + "','" + website.getPersonPass()
							+ "','" + website.getPersonPhone() + "','" + website.getDbName()
							+ "','" + website.getDbUser() + "','" + website.getDbPass()
							+ "','" + website.getDbHost() + "','" + website.getHostingUrl()
							+ "','" + website.getHostingLogin() + "','"
							+ website.getHostingPass() + "','" + website.getProviderUrl()
							+ "','" + website.getProviderLogin() + "','"
							+ website.getProviderPass() + "','" + website.getOtherUrl()
							+ "','" + website.getOtherLogin() + "','" + website.getOtherPass()
							+ "','" + website.getNotes() + "')");
			stat.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		AddButtonCancel(actionEvent);
	}

	@Override
	public void AddButtonChange(ActionEvent actionEvent) {

	}

	@Override
	public void AddButtonCancel(ActionEvent actionEvent) {
		Node source = (Node) actionEvent.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.hide();
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;

		txtsite.setText(website.getSite());
		txtsitelogin.setText(website.getSiteLogin());
		txtsitepass.setText(website.getSitePass());
		txtftp.setText(website.getFtp());
		txtftplogin.setText(website.getFtpLogin());
		txtftppass.setText(website.getFtpPass());
		txtport.setText(website.getPort());
		txtperson.setText(website.getPerson());
		txtpersonEmail.setText(website.getPersonEmail());
		txtpersonPass.setText(website.getPersonPass());
		txtpersonPhone.setText(website.getPersonPhone());
		txtdbName.setText(website.getDbName());
		txtdbUser.setText(website.getDbUser());
		txtdbPass.setText(website.getDbPass());
		txtdbHost.setText(website.getDbHost());
		txthostingUrl.setText(website.getHostingUrl());
		txthostingLogin.setText(website.getHostingLogin());
		txthostingPass.setText(website.getHostingPass());
		txtproviderUrl.setText(website.getProviderUrl());
		txtproviderLogin.setText(website.getProviderLogin());
		txtproviderPass.setText(website.getProviderPass());
		txtotherUrl.setText(website.getOtherUrl());
		txtotherLogin.setText(website.getOtherLogin());
		txtotherPass.setText(website.getOtherPass());
		txtnotes.setText(website.getNotes());

	}

}