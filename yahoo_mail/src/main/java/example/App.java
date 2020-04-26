package example;

import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class App {
	private static final String host = "smtp.mail.yahoo.com";
	private final static Properties properties = System.getProperties();
	private static boolean debug = false;
	private static CommandLineParser commandLineParser;

	static {
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
	}
	private static MimeMessage message = null;

	public static void main(String[] args) {

		commandLineParser = new CommandLineParser();
		commandLineParser.saveFlagValue("to");
		commandLineParser.saveFlagValue("from");
		commandLineParser.saveFlagValue("password");

		commandLineParser.parse(args);

		if (commandLineParser.hasFlag("debug")) {
			debug = true;
		}
		final String to = commandLineParser.getFlagValue("to");
		if (to == null) {
			System.err.println("Missing required argument: to");
			return;
		}

		final String from = commandLineParser.getFlagValue("from");
		if (from == null) {
			System.err.println("Missing required argument: from");
			return;
		}

		final String password = commandLineParser.getFlagValue("password");
		if (password == null) {
			System.err.println("Missing required argument: password");
			return;
		}

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(to.replace("@.*$", ""), password);
			}
		});
		if (debug) {
			session.setDebug(true);
		}
		try {
			message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("This is the Subject Line!");
			message.setText("This is actual message");

			System.err.println("sending...");
			Transport.send(message);
			System.err.println("Sent message successfully....");
		} catch (AuthenticationFailedException e) {
			System.err.println("Invalid credentials");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
