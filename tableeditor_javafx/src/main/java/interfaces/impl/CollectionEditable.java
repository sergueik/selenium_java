package interfaces.impl;

import interfaces.Editable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import editmodal.Editpassw.*;
import org.sqlite.SQLiteConfig;
import pojo.Website;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CollectionEditable implements Editable {

	private ObservableList<Website> passbook = FXCollections
			.observableArrayList();

	@Override
	public void add(Website website) {
		passbook.add(website);
	}

	@Override
	public void update(Website website) {
	}

	@Override
	public void delete(Website website) {
		passbook.remove(website);
	}

	public ObservableList<Website> getPassbook() {
		return passbook;
	}

	public void print() {
		int number = 0;
		System.out.println();
		for (Website website : passbook) {
			number++;
			System.out.println(number + ") site = " + website.getSite()
					+ "; siteLogin = " + website.getSiteLogin() + "; sitePass = "
					+ website.getSitePass() + "; ftp = " + website.getFtp()
					+ "; ftpLogin = " + website.getFtpLogin() + "; ftpPass = "
					+ website.getFtpPass() + "; port = " + website.getPort()
					+ "; person = " + website.getPerson() + "; personEmail = "
					+ website.getPersonEmail() + "; personPass = "
					+ website.getPersonPass() + "; " + "personPhone = "
					+ website.getPersonPhone() + "; dbName = " + website.getDbName()
					+ "; dbUser = " + website.getDbUser() + "; dbPass = "
					+ website.getDbPass() + "; dbHost = " + website.getDbHost()
					+ "; hostingUrl = " + website.getHostingUrl() + "; hostingLogin = "
					+ website.getHostingLogin() + "; hostingPass = "
					+ website.getHostingPass() + "; providerUrl = "
					+ website.getProviderUrl() + "; providerLogin = "
					+ website.getProviderLogin() + "; " + "providerPass = "
					+ website.getProviderPass() + "; otherUrl = " + website.getOtherUrl()
					+ "; otherLogin = " + website.getOtherLogin() + "; otherPass = "
					+ website.getOtherPass() + "; notes = " + website.getNotes());
		}
	}

}
