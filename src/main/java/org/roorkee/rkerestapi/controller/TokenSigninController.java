package org.roorkee.rkerestapi.controller;

import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.roorkee.rkerestapi.util.RkeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RestController
@RequestMapping("/tokensignin")
@CrossOrigin
public class TokenSigninController {

    private static final String HDR_TOKEN = "token";

    @Value("${google.oauth2.clientId}")
    private String clientId;

    private static final JacksonFactory jacksonFactory = new JacksonFactory();

    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(UrlFetchTransport.getDefaultInstance(), jacksonFactory)
            // Specify the CLIENT_ID of the app that accesses the backend:
            .setAudience(Collections.singletonList(clientId))
            // Or, if multiple clients access the backend:
            //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
            .build();

    @GetMapping
    public ResponseEntity tokenSignIn(HttpServletRequest req){
        if (StringUtils.isEmpty(req.getHeader(HDR_TOKEN))) {
            throw new RkeException(new RuntimeException("Token header missing in request."));
        }
        String idTokenString = req.getHeader(HDR_TOKEN);
        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(idTokenString);
        }
        catch(Exception ex){
            throw new RkeException(ex);
        }

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            // Use or store profile information
            // ...
            return new ResponseEntity(HttpStatus.OK);
        } else {
            throw new RkeException(new RuntimeException("Invalid Token"));
        }
    }

}
