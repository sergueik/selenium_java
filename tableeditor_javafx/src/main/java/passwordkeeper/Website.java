package passwordkeeper;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

@SuppressWarnings("restriction")
public class Website {
	// ID
	private SimpleIntegerProperty id = new SimpleIntegerProperty();
	// Website
	private SimpleStringProperty site = new SimpleStringProperty("");
	private SimpleStringProperty siteLogin = new SimpleStringProperty("");
	private SimpleStringProperty sitePass = new SimpleStringProperty("");

	// FTP
	private SimpleStringProperty ftp = new SimpleStringProperty("");
	private SimpleStringProperty ftpLogin = new SimpleStringProperty("");
	private SimpleStringProperty ftpPass = new SimpleStringProperty("");
	private SimpleStringProperty port = new SimpleStringProperty("");

	// Notes
	private SimpleStringProperty notes = new SimpleStringProperty("");

	public Website() {

	};

	public Website(int id, String site, String siteLogin, String sitePass, String ftp, String ftpLogin, String ftpPass,
			String port, String notes) {
		this.id = new SimpleIntegerProperty(id);
		this.site = new SimpleStringProperty(site);
		this.siteLogin = new SimpleStringProperty(siteLogin);
		this.sitePass = new SimpleStringProperty(sitePass);
		this.ftp = new SimpleStringProperty(ftp);
		this.ftpLogin = new SimpleStringProperty(ftpLogin);
		this.ftpPass = new SimpleStringProperty(ftpPass);
		this.port = new SimpleStringProperty(port);
		this.notes = new SimpleStringProperty(notes);
	}

	public int getId() {
		return id.get();
	}

	public SimpleIntegerProperty idProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getSite() {
		return site.get();
	}

	public void setSite(String site) {
		this.site.set(site);
	}

	public String getSiteLogin() {
		return siteLogin.get();
	}

	public void setSiteLogin(String siteLogin) {
		this.siteLogin.set(siteLogin);
	}

	public String getSitePass() {
		return sitePass.get();
	}

	public void setSitePass(String sitePass) {
		this.sitePass.set(sitePass);
	}

	public String getFtp() {
		return ftp.get();
	}

	public void setFtp(String ftp) {
		this.ftp.set(ftp);
	}

	public String getFtpLogin() {
		return ftpLogin.get();
	}

	public void setFtpLogin(String ftpLogin) {
		this.ftpLogin.set(ftpLogin);
	}

	public String getFtpPass() {
		return ftpPass.get();
	}

	public void setFtpPass(String ftpPass) {
		this.ftpPass.set(ftpPass);
	}

	public String getPort() {
		return port.get();
	}

	public void setPort(String port) {
		this.port.set(port);
	}

	public SimpleStringProperty siteProperty() {
		return site;
	}

	public SimpleStringProperty siteLoginProperty() {
		return siteLogin;
	}

	public SimpleStringProperty sitePassProperty() {
		return sitePass;
	}

	public SimpleStringProperty ftpProperty() {
		return ftp;
	}

	public SimpleStringProperty ftpLoginProperty() {
		return ftpLogin;
	}

	public SimpleStringProperty ftpPassProperty() {
		return ftpPass;
	}

	public SimpleStringProperty portProperty() {
		return port;
	}

	public String getNotes() {
		return notes.get();
	}

	public SimpleStringProperty notesProperty() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes.set(notes);
	}

	@Override
	public String toString() {
		return "Website{" + "id=" + id + ", site=" + site + ", siteLogin=" + siteLogin + ", sitePass=" + sitePass
				+ ", ftp=" + ftp + ", ftpLogin=" + ftpLogin + ", ftpPass=" + ftpPass + ", port=" + ", notes=" + notes
				+ '}';
	}
}
