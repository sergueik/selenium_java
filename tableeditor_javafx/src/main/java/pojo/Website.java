package pojo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Website {
    //ID
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    //Website
    private SimpleStringProperty site = new SimpleStringProperty("");
    private SimpleStringProperty siteLogin = new SimpleStringProperty("");
    private SimpleStringProperty sitePass = new SimpleStringProperty("");

    //FTP
    private SimpleStringProperty ftp = new SimpleStringProperty("");
    private SimpleStringProperty ftpLogin = new SimpleStringProperty("");
    private SimpleStringProperty ftpPass = new SimpleStringProperty("");
    private SimpleStringProperty port = new SimpleStringProperty("");

    //Owner
    private SimpleStringProperty person = new SimpleStringProperty("");
    private SimpleStringProperty personEmail = new SimpleStringProperty("");
    private SimpleStringProperty personPass = new SimpleStringProperty("");
    private SimpleStringProperty personPhone = new SimpleStringProperty("");

    //DB
    private SimpleStringProperty dbName = new SimpleStringProperty("");
    private SimpleStringProperty dbUser = new SimpleStringProperty("");
    private SimpleStringProperty dbPass = new SimpleStringProperty("");
    private SimpleStringProperty dbHost = new SimpleStringProperty("");
    // Hosting
    private SimpleStringProperty hostingUrl = new SimpleStringProperty("");
    private SimpleStringProperty hostingLogin = new SimpleStringProperty("");
    private SimpleStringProperty hostingPass = new SimpleStringProperty("");
    // Domain
    private SimpleStringProperty providerUrl = new SimpleStringProperty("");
    private SimpleStringProperty providerLogin = new SimpleStringProperty("");
    private SimpleStringProperty providerPass = new SimpleStringProperty("");
    //Other Accounts
    private SimpleStringProperty otherUrl = new SimpleStringProperty("");
    private SimpleStringProperty otherLogin = new SimpleStringProperty("");
    private SimpleStringProperty otherPass = new SimpleStringProperty("");

    //Notes
    private SimpleStringProperty notes = new SimpleStringProperty("");

    public Website (){

    };


    public Website(int id, String site, String siteLogin, String sitePass, String ftp, String ftpLogin, String ftpPass, String port, String person, String personEmail, String personPass,
                   String personPhone, String dbName, String dbUser, String dbPass, String dbHost, String hostingUrl, String hostingLogin, String hostingPass, String providerUrl,
                   String providerLogin, String providerPass, String otherUrl, String otherLogin, String otherPass, String notes) {
        this.id = new SimpleIntegerProperty(id);
        this.site = new SimpleStringProperty(site);
        this.siteLogin = new SimpleStringProperty(siteLogin) ;
        this.sitePass = new SimpleStringProperty(sitePass) ;
        this.ftp = new SimpleStringProperty(ftp) ;
        this.ftpLogin = new SimpleStringProperty(ftpLogin) ;
        this.ftpPass = new SimpleStringProperty(ftpPass) ;
        this.port = new SimpleStringProperty(port) ;
        this.person = new SimpleStringProperty(person) ;
        this.personEmail = new SimpleStringProperty(personEmail);
        this.personPass = new SimpleStringProperty(personPass);
        this.personPhone = new SimpleStringProperty(personPhone);
        this.dbName = new SimpleStringProperty(dbName);
        this.dbUser = new SimpleStringProperty(dbUser);
        this.dbPass = new SimpleStringProperty(dbPass);
        this.dbHost = new SimpleStringProperty(dbHost);
        this.hostingUrl = new SimpleStringProperty(hostingUrl);
        this.hostingLogin = new SimpleStringProperty(hostingLogin);
        this.hostingPass = new SimpleStringProperty(hostingPass);
        this.providerUrl = new SimpleStringProperty(providerUrl);
        this.providerLogin = new SimpleStringProperty(providerLogin);
        this.providerPass = new SimpleStringProperty(providerPass);
        this.otherUrl = new SimpleStringProperty(otherUrl);
        this.otherLogin = new SimpleStringProperty(otherLogin);
        this.otherPass = new SimpleStringProperty(otherPass);
        this.notes = new SimpleStringProperty(notes);
    }


    // ??????? ? ???????

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

    public String getSitePass() { return sitePass.get(); }

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

    public void setFtpPass(String ftpPass) { this.ftpPass.set(ftpPass); }

    public String getPort() {
        return port.get();
    }

    public void setPort(String port) {
        this.port.set(port);
    }

    public String getPerson() { return person.get(); }

    public void setPerson(String person) { this.person.set(person); }

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

    public SimpleStringProperty personProperty() {
        return person;
    }

    public String getPersonEmail() {
        return personEmail.get();
    }

    public SimpleStringProperty personEmailProperty() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail.set(personEmail);
    }

    public String getPersonPass() {
        return personPass.get();
    }

    public SimpleStringProperty personPassProperty() {
        return personPass;
    }

    public void setPersonPass(String personPass) {
        this.personPass.set(personPass);
    }

    public String getPersonPhone() {
        return personPhone.get();
    }

    public SimpleStringProperty personPhoneProperty() {
        return personPhone;
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone.set(personPhone);
    }

    public String getDbName() {
        return dbName.get();
    }

    public SimpleStringProperty dbNameProperty() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName.set(dbName);
    }

    public String getDbUser() {
        return dbUser.get();
    }

    public SimpleStringProperty dbUserProperty() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser.set(dbUser);
    }

    public String getDbPass() {
        return dbPass.get();
    }

    public SimpleStringProperty dbPassProperty() {
        return dbPass;
    }

    public void setDbPass(String dbPass) {
        this.dbPass.set(dbPass);
    }

    public String getDbHost() {
        return dbHost.get();
    }

    public SimpleStringProperty dbHostProperty() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost.set(dbHost);
    }

    public String getHostingUrl() {
        return hostingUrl.get();
    }

    public SimpleStringProperty hostingUrlProperty() {
        return hostingUrl;
    }

    public void setHostingUrl(String hostingUrl) {
        this.hostingUrl.set(hostingUrl);
    }

    public String getHostingLogin() {
        return hostingLogin.get();
    }

    public SimpleStringProperty hostingLoginProperty() {
        return hostingLogin;
    }

    public void setHostingLogin(String hostingLogin) {
        this.hostingLogin.set(hostingLogin);
    }

    public String getHostingPass() {
        return hostingPass.get();
    }

    public SimpleStringProperty hostingPassProperty() {
        return hostingPass;
    }

    public void setHostingPass(String hostingPass) {
        this.hostingPass.set(hostingPass);
    }

    public String getProviderUrl() {
        return providerUrl.get();
    }

    public SimpleStringProperty providerUrlProperty() {
        return providerUrl;
    }

    public void setProviderUrl(String providerUrl) {
        this.providerUrl.set(providerUrl);
    }

    public String getProviderLogin() {
        return providerLogin.get();
    }

    public SimpleStringProperty providerLoginProperty() {
        return providerLogin;
    }

    public void setProviderLogin(String providerLogin) {
        this.providerLogin.set(providerLogin);
    }

    public String getProviderPass() {
        return providerPass.get();
    }

    public SimpleStringProperty providerPassProperty() {
        return providerPass;
    }

    public void setProviderPass(String providerPass) {
        this.providerPass.set(providerPass);
    }

    public String getOtherUrl() {
        return otherUrl.get();
    }

    public SimpleStringProperty otherUrlProperty() {
        return otherUrl;
    }

    public void setOtherUrl(String otherUrl) {
        this.otherUrl.set(otherUrl);
    }

    public String getOtherLogin() {
        return otherLogin.get();
    }

    public SimpleStringProperty otherLoginProperty() {
        return otherLogin;
    }

    public void setOtherLogin(String otherLogin) {
        this.otherLogin.set(otherLogin);
    }

    public String getOtherPass() {
        return otherPass.get();
    }

    public SimpleStringProperty otherPassProperty() {
        return otherPass;
    }

    public void setOtherPass(String otherPass) {
        this.otherPass.set(otherPass);
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
        return "Website{" +
                "id=" + id +
                ", site=" + site +
                ", siteLogin=" + siteLogin +
                ", sitePass=" + sitePass +
                ", ftp=" + ftp +
                ", ftpLogin=" + ftpLogin +
                ", ftpPass=" + ftpPass +
                ", port=" + port +
                ", person=" + person +
                ", personEmail=" + personEmail +
                ", personPass=" + personPass +
                ", personPhone=" + personPhone +
                ", dbName=" + dbName +
                ", dbUser=" + dbUser +
                ", dbPass=" + dbPass +
                ", dbHost=" + dbHost +
                ", hostingUrl=" + hostingUrl +
                ", hostingLogin=" + hostingLogin +
                ", hostingPass=" + hostingPass +
                ", providerUrl=" + providerUrl +
                ", providerLogin=" + providerLogin +
                ", providerPass=" + providerPass +
                ", otherUrl=" + otherUrl +
                ", otherLogin=" + otherLogin +
                ", otherPass=" + otherPass +
                ", notes=" + notes +
                '}';
    }
}
