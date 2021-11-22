package example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.application.Platform;

import org.sqlite.SQLiteConfig;

import java.sql.*;

@SuppressWarnings("restriction")

public class Editpassw implements AddChangePasswAbstr {

	private Controller controller = GlobalData.getController();

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
		website.setNotes(txtnotes.getText());

		System.err.println("Sqlupdate");
		sqlupdate(website.getId(), website.getSite(), website.getSiteLogin(),
				website.getSitePass(), website.getFtp(), website.getFtpLogin(),
				website.getFtpPass(), website.getPort(), website.getNotes());

		AddButtonCancel(actionEvent);
	}

	@Override
	public void AddButtonCancel(ActionEvent actionEvent) {
		Node source = (Node) actionEvent.getSource();
		Stage changestage = (Stage) source.getScene().getWindow();
		if (controller != null) {
			try {
				System.err
						.println("Completed for: " + controller.changeButton.getText());
				// Update the Label on the JavaFx Application Thread
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						controller.changeButton.setText(controller.changeButton.getText());
					}
				});
			} catch (NullPointerException e) {
				// ignore
			}
		}
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
		txtnotes.setText(website.getNotes());

	}

	public void sqlupdate(int id, String site, String siteLogin, String sitePass,
			String ftp, String ftpLogin, String ftpPass, String port, String notes) {
		String updatesql = "UPDATE data SET site = ? , " + " siteLogin = ? ,"
				+ " sitePass = ?, " + " ftp = ?," + " ftpLogin = ?," + " ftpPass = ?,"
				+ "port = ?" + "WHERE id = ?";
		try {
			@SuppressWarnings("unused")
			SQLiteConfig config = new SQLiteConfig();
			Connection c = DB.getInstance().getConnection();
			PreparedStatement pstmt = c.prepareStatement(updatesql);

			pstmt.setString(1, site);
			pstmt.setString(2, siteLogin);
			pstmt.setString(3, sitePass);
			pstmt.setString(4, ftp);
			pstmt.setString(5, ftpLogin);
			pstmt.setString(6, ftpPass);
			pstmt.setString(7, port);
			// pstmt.setInt(7, port);
			pstmt.setInt(8, id);
			System.err.println(String.format(
					"UPDATE data SET site=%s , " + " siteLogin = %s ,"
							+ " sitePass = %s, " + " ftp = %s," + " ftpLogin = %s,"
							+ " ftpPass = %s," + "port = %s," + "WHERE id = %d",
					site, siteLogin, sitePass, ftp, ftpLogin, ftpPass, port, id));

			// update
			pstmt.executeUpdate();
			System.err.println("Udate: " + pstmt.toString());

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Exception " + ex.toString());
		}

	}

	@FXML
	void initialize() {
		if (controller != null) {
			try {
				System.err.println("Launched by: " + controller.changeButton.getText());
			} catch (NullPointerException e) {
				// ignore
			}
		}
	}
}
