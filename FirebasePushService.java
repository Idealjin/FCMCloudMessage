package com.toaping.app.bookapp.comm.service.admin.firebasepush;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class FirebasePushService{
	
	  public void sendToToken(String token) {
		  int memberIdx = jwtProviderService.getMemberIdx();
	        AdminUtil.checkAdmin(memberIdx);
	        
		    // [START send_to_token
		  List<Message> messages = tokenList.stream().map(token -> Message.builder()
	                .putData("time", LocalDateTime.now().toString())
	                .setNotification(Notification.builder()
	                		.setTitle("제목")
	                		.setBody("내용")
	                		.build())
	                .setToken(token)
	                .build()).collect(Collectors.toList()); 

		  	//알림 발송
		  	BatchResponse response;
		  	try {
		  		
		  	response = FirebaseMessaging.getInstance().sendAll(messages);
		    
		    //응답처리
		    if(response.getFailureCount() > 0) {
		    	List<SendResponse> responses = response.getResponses();
		    	List<String> failedTokens = new ArrayList<>();
		    	
		    	for(int i = 0; i < responses.size(); i++) {
		    		if(!responses.get(i).isSuccessful()) {
		    			failedTokens.add(tokenList.get(i));
		    			}
		    		}
		    	log.error("List of tokens are not valid FCM token : " + failedTokens);
		    	}
		    } catch(FirebaseMessagingException e) {
		    	log.error("cannot send to memberList push message. error info : ()", e.getMessage());
		    }
		    // [END send_to_token]
		    log.info("Push(Token) 발송!");
		  }


	
}