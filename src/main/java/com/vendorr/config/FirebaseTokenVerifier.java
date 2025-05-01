

package com.vendorr.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import org.springframework.core.io.Resource;

@Component
@RequiredArgsConstructor
public class FirebaseTokenVerifier {

    private final ResourceLoader resourceLoader;

    @Value("${firebase.credentials-file}")
    private String credentialsFilePath;

    private FirebaseApp firebaseApp;

    @PostConstruct
    public void init() throws IOException {
        Resource resource = resourceLoader.getResource(credentialsFilePath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        this.firebaseApp = FirebaseApp.initializeApp(options);
    }

    public FirebaseToken verify(String idToken) throws FirebaseAuthException {
        return FirebaseAuth.getInstance(firebaseApp).verifyIdToken(idToken);
    }
}
