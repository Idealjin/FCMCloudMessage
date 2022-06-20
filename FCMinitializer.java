package com.toaping.app.bookapp.comm.service.admin.firebasepush;


import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FCMinitializer {

	  private static final Logger logger = LoggerFactory.getLogger(FCMinitializer.class);
	  private static final String FIREBASE_CONFIG_PATH = "자신의 firebase 프로젝트 비공개키.json";

	  @PostConstruct
	  public void initialize() {
	        try {
	        	logger.info("파이어베이스 초기화");
	            FirebaseOptions options = new FirebaseOptions.Builder()
	                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())).build();
	            if (FirebaseApp.getApps().isEmpty()) {
	                FirebaseApp.initializeApp(options);
	                logger.info("Firebase application has been initialized");
	            }
	        } catch (IOException e) {
	            logger.error(e.getMessage());
	        }
	    }

}
	

