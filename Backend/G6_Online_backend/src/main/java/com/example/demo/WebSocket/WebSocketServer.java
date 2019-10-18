package com.example.demo.WebSocket;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.message.Message;
import org.springframework.stereotype.Component;


// come back to
@ServerEndpoint(value = "/websocket/{username}")
@Component
public class WebSocketServer {
	
	// Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();

	
	
	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) /*throws IOException*/ {
//		logger.info("Entered into Open");
//        
//        sessionUsernameMap.put(session, username);
//        usernameSessionMap.put(username, session);
//        
//        String message="User:" + username + " has Joined the Chat";
//        

	}
	@OnMessage
	public void onMessage(Session session, Message message) /*throws IOException*/ {
	// Handle new messages
	}
	@OnClose
	public void onClose(Session session) /*throws IOException*/ {
	// WebSocket connection closes
	}
	@OnError
	public void onError(Session session, Throwable throwable) {
	// Do error handling here
	}
}
