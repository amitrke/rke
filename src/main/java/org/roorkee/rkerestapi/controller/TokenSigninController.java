package org.roorkee.rkerestapi.controller;

import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.roorkee.rkerestapi.dao.UserDao;
import org.roorkee.rkerestapi.entity.User;
import org.roorkee.rkerestapi.service.CacheService;
import org.roorkee.rkerestapi.util.RkeException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@RestController
@RequestMapping("/tokensignin")
@CrossOrigin
public class TokenSigninController implements InitializingBean {

    private static final String HDR_TOKEN = "token";

    @Value("${google.oauth2.clientId}")
    private String clientId;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private UserDao userDao;

    GoogleIdTokenVerifier verifier;

    @Override
    public void afterPropertiesSet() throws Exception {
        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(clientId))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<String> tokenSignIn(HttpServletRequest req){
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
            User user = newUser(payload);
            if (cacheService.get(DigestUtils.md5Hex(idTokenString)) == null){
                user = getDBUser(user);
                cacheService.put(DigestUtils.md5Hex(idTokenString), user.getId());
            }
            else{
                user.setId((Long) cacheService.get(DigestUtils.md5Hex(idTokenString)));
            }

            return new ResponseEntity(new StringResponse(user.getId().toString()), HttpStatus.OK);
        } else {
            throw new RkeException(new RuntimeException("Invalid Token"));
        }
    }

    private User getDBUser(User user){
        List<User> dbUser = userDao.search(user);
        if (dbUser != null && dbUser.size() > 0){
            return dbUser.get(0);
        }
        else{
            Long userId = userDao.save(user);
            return userDao.get(userId);
        }
    }

    private User newUser(GoogleIdToken.Payload payload){
        User user = new User();
        user.setGId(payload.getSubject());
        user.setEmail(payload.getEmail());
        return user;
    }

}
