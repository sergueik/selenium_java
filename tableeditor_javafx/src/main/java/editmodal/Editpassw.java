package editmodal;

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

public class Editpassw implements AddChangePasswAbstr {

	@FXML
	private Button AddButtonChange;

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

	@Override
	public void AddButtonAdd(ActionEvent actionEvent) {

	}

	@Override
	public void AddButtonChange(ActionEvent actionEvent) {

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

		System.err.println("Sqlupdate");
		sqlupdate(website.getId(), website.getSite(), website.getSiteLogin(),
				website.getSitePass(), "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "");

		AddButtonCancel(actionEvent);
	}

	@Override
	public void AddButtonCancel(ActionEvent actionEvent) {
		Node source = (Node) actionEvent.getSource();
		Stage changestage = (Stage) source.getScene().getWindow();
		changestage.hide();
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

	public void sqlupdate(int id, String site, String siteLogin,
			String sitePass, String ftp, String ftpLogin, String ftpPass, String port,
			String person, String personEmail, String personPass, String personPhone,
			String dbName, String dbUser, String dbPass, String dbHost,
			String hostingUrl, String hostingLogin, String hostingPass,
			String providerUrl, String providerLogin, String providerPass,
			String otherUrl, String otherLogin, String otherPass, String notes) {
		/*
		String updatesql = "UPDATE data SET site=? , " + " siteLogin = ? ,"
				+ " sitePass = ? ," + " ftp = ?," + " ftpLogin = ?," + " ftpPass = ?,"
				+ "port = ?," + "person = ?," + "personEmail = ?," + "personPass = ?,"
				+ "personPhone = ?," + "dbName = ?," + "dbUser = ?," + "dbPass = ?,"
				+ "dbHost = ?," + "hostingUrl = ?," + "hostingLogin = ?,"
				+ "hostingPass = ?," + "providerUrl = ?," + "providerLogin = ?,"
				+ "providerPass = ?," + "otherUrl = ?," + "otherLogin = ?,"
				+ "otherPass = ?," + "notes = ? " + "WHERE id = ?";
				*/
		String updatesql = "UPDATE data SET site=? , " + " siteLogin = ? ,"
				+ " sitePass = ? " + "WHERE id = ?";
		try {
			SQLiteConfig config = new SQLiteConfig();
			Connection c = db.getInstance().getConnection();
			PreparedStatement pstmt = c.prepareStatement(updatesql);

			pstmt.setString(1, site);
			pstmt.setString(2, siteLogin);
			pstmt.setString(3, sitePass);
			pstmt.setInt(4, id);
			System.err
					.println(String.format(
							"UPDATE data SET site=%s , " + " siteLogin = %s ,"
									+ " sitePass = %s " + "WHERE id = %d",
							site, siteLogin, sitePass, id));

			/*
			pstmt.setString(4, ftp);
			pstmt.setString(5, ftpLogin);
			pstmt.setString(6, ftpPass);
			pstmt.setString(7, port);
			pstmt.setString(8, person);
			pstmt.setString(9, personEmail);
			pstmt.setString(10, personPass);
			pstmt.setString(11, personPhone);
			pstmt.setString(12, dbName);
			pstmt.setString(13, dbUser);
			pstmt.setString(14, dbPass);
			pstmt.setString(15, dbHost);
			pstmt.setString(16, hostingUrl);
			pstmt.setString(17, hostingLogin);
			pstmt.setString(18, hostingPass);
			pstmt.setString(19, providerUrl);
			pstmt.setString(20, providerLogin);
			pstmt.setString(21, providerPass);
			pstmt.setString(22, otherUrl);
			pstmt.setString(23, otherLogin);
			pstmt.setString(24, otherPass);
			pstmt.setString(25, notes);
			*/
			// update
			pstmt.executeUpdate();
			System.err.println("Udate: " + pstmt.toString());

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Exception " + ex.toString());
		}

	}
}
