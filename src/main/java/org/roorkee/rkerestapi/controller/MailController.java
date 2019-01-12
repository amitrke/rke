package org.roorkee.rkerestapi.controller;

import org.roorkee.rkerestapi.service.MailService;
import org.roorkee.rkerestapi.util.RkeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

@RestController
@CrossOrigin
public class MailController {

    private static final Logger log = Logger.getLogger(MailController.class.getName());

    @Autowired
    private MailService service;

    @PostMapping(path= "/_ah/mail/*")
    void incoming(HttpServletRequest req){
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            MimeMessage message = new MimeMessage(session, req.getInputStream());
            log.info("Received mail message.");
        } catch (MessagingException e) {
            throw new RkeException(new RuntimeException("MessagingException"));
        }
        catch (IOException ex) {
            throw new RkeException(new RuntimeException("IOException"));
        }
    }

    @PostMapping(path= "/api/mail/out/", consumes = "application/json", produces = "application/json")
    ResponseEntity<StringResponse> outbound(@RequestBody @Valid MessageRequest messageRequest){
        try {
            InternetAddress toAddress = new InternetAddress(messageRequest.getToEmail(), messageRequest.getToName());
            boolean resp = this.service.sendMultipartMail(toAddress,
                    messageRequest.getSubject(), messageRequest.getTextBody(), messageRequest.getHtmlBody());
            StringResponse sr = new StringResponse();
            sr.setResponse(String.valueOf(resp));
            return new ResponseEntity<StringResponse>(sr, HttpStatus.OK);
        }
        catch(UnsupportedEncodingException e) {
            throw new RkeException(new RuntimeException("Unsupported Encoding"));
        }
    }
}
