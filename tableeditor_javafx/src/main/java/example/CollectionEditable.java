package example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.sqlite.SQLiteConfig;

import example.Editpassw.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings("restriction")
public class CollectionEditable implements Editable {

	private ObservableList<Website> passbook = FXCollections.observableArrayList();

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
			System.out.println(number + ") site = " + website.getSite() + "; siteLogin = " + website.getSiteLogin()
					+ "; sitePass = " + website.getSitePass() + "; ftp = " + website.getFtp() + "; ftpLogin = "
					+ website.getFtpLogin() + "; ftpPass = " + website.getFtpPass() + "; port = " + website.getPort()
					+ "; notes = " + website.getNotes());
		}
	}

}
