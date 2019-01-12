package org.roorkee.rkerestapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

@Service
public class MailService {

    @Value("${app.mail.admin.name}")
    private String adminName;

    @Value("${app.mail.admin.email}")
    private String adminEmail;

    public boolean sendMultipartMail(InternetAddress to, String subject, String textBody, String htmlBody) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(adminEmail, adminName));
            msg.addRecipient(Message.RecipientType.TO, to);
            msg.setSubject(subject);
            msg.setText(textBody);

            Multipart mp = new MimeMultipart();

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlBody, "text/html");
            mp.addBodyPart(htmlPart);

            msg.setContent(mp);

            Transport.send(msg);
            return true;
        } catch (AddressException e) {
            return false;
        } catch (MessagingException e) {
            return false;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

}