package example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.sqlite.SQLiteConfig;

import java.sql.*;

@SuppressWarnings("restriction")

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
	private TextField txtnotes;

	private Website website;

	@SuppressWarnings("unused")
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
		website.setNotes(txtnotes.getText());

		try {
			@SuppressWarnings("unused")
			SQLiteConfig config = new SQLiteConfig();
			c = DB.getInstance().getConnection();

			PreparedStatement stat = c.prepareStatement(
					"INSERT INTO data (site, siteLogin, sitePass, ftp, ftpLogin, ftpPass, port, notes) VALUES ('"
							+ website.getSite() + "','" + website.getSiteLogin() + "','" + website.getSitePass() + "','"
							+ website.getFtp() + "','" + website.getFtpLogin() + "','" + website.getFtpPass() + "','"
							+ website.getPort() + "','" + website.getNotes() + "')");
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
		txtnotes.setText(website.getNotes());
	}

}