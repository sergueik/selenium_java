package com.wuwei.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 发送可以带附件的邮件
 *
 * @author Wu Wei
 * @date 2017-8-7 16:32:15
 */
public class MailSender {

    private static final String CLASSPATH = MailSender.class.getResource("/").getPath();
    private static final Logger logger = Logger.getLogger(MailSender.class.getName());

    public static void main(String[] args) {
        sendAttachmentMail("D:\\XXX.txt");
    }

    public static void sendAttachmentMail(String filename) {
        Map<String, String> params = readMailConfig();
        if (params == null || params.isEmpty()) {
            return;
        }
        String from = params.get("from");
        String to = params.get("to");
        final String username = params.get("username");
        final String password = params.get("password");
        String host = params.get("host");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            // Set Subject: header field
            message.setSubject("Testing Subject");
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Now set the actual message
            messageBodyPart.setText("This is message body");
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            message.setContent(multipart);
            // Set message send time
            message.setSentDate(new Date());
            // Send message
            Transport.send(message);
            System.out.println("Send mail successfully!");
        } catch (MessagingException e) {
            logger.info(e.getMessage());
        }
    }

    /**
     * 读取邮件配置参数
     *
     * @return
     */
    private static Map<String, String> readMailConfig() {
        StringBuilder fileName = new StringBuilder(CLASSPATH);
        fileName.append("mail.properties");
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(fileName.toString()));
            Map<String, String> params = new HashMap<>();
            prop.entrySet().stream().forEach(entry -> {
                params.put((String) entry.getKey(), (String) entry.getValue());
            });
            return params;
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return null;
    }
}
