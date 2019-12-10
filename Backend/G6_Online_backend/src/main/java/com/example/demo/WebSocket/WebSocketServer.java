package com.example.demo.WebSocket;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.lobby.lobbyController;

/**
 * 
 * @author Vamsi Krishna Calpakkam
 *
 */
@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocketServer {

	// Store all socket session and their corresponding username.
	private static Map<Session, String> sessionUsernameMap = new HashMap<>();
	private static Map<String, Session> usernameSessionMap = new HashMap<>();

	private final static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

	private static lobbyController lobby;

	@Autowired
	public void setSocketService(SocketService s) {
	}

	@Autowired
	public void setLobbyController(lobbyController l) {
		lobby = l;
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException {
		logger.info("Entered into Open " + username);

		sessionUsernameMap.put(session, username);
		usernameSessionMap.put(username, session);

		String message = "User:" + username + " has Joined the Chat";
		broadcast(message);

	}

	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
		// Handle new messages
		logger.info("Entered into Message: Got Message:" + message);
		String username = sessionUsernameMap.get(session);
		// Handle how to react based on the 4 letter code at the beginning of the
		String code = "";
		try {
			if (!message.contains(":"))
				throw new Exception("Message does not contain ':' identifier");
			code = message.split(":")[0];
			message = message.split(":")[1];
			if (code.equals("UPDATEPOS")) {
				updatePlayerPosition(message);}
			else if (code.equals("JOINLOBBY")) {
				joinLobby(message);}
			else if (code.equals("GETLOBBYINFO")) {
				getLobbyInfo(message, username);
			}
			else {
				sendMessageToParticularUser(username, "Invalid Code: " + code);
			}
		} catch (Exception e) {
			try {
				sendMessageToParticularUser(username, "Invalid message: " + e.toString());
			} catch (Exception sendingErrorToUserExcpetion) {
				logger.info("Unable to send message to user that caused error");
			}
			;
			logger.info("Error: " + e.toString());
		}

		if (message.startsWith("@")) // Direct message to a user using the format "@username <message>"
		{
			String destUsername = message.split(" ")[0].substring(1); // don't do this in your code!
			sendMessageToParticularUser(destUsername, "[DM] " + username + ": " + message);
			sendMessageToParticularUser(username, "[DM] " + username + ": " + message);
		} else // Message to whole chat
		{
//	    	broadcast( + ": " + message);
		}
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");

		String username = sessionUsernameMap.get(session);
		sessionUsernameMap.remove(session);
		usernameSessionMap.remove(username);

		String message = username + " disconnected";
		broadcast(message);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.info("Entered into Error");
	}

	private void sendMessageToParticularUser(String username, String message) {
		try {
			usernameSessionMap.get(username).getBasicRemote().sendText(message);
		} catch (IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("Player id "+username+" is no longer connected. Removing them from lobbies");
			lobby.removePlayerFromLobbies(Integer.parseInt(username));
		}
	}

	private static void broadcast(String message) throws IOException {
		sessionUsernameMap.forEach((session, username) -> {
			synchronized (session) {
				try {
					session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * This method will take a string, split it by spaces and store each value into a new array of Integer
	 */
	private Integer[] toIntArr(String message) {
		String[] messageArr = message.split(" ");
		Integer[] arr = new Integer[messageArr.length];
    	for (int i = 0; i < arr.length; i++) {
    		arr[i] = Integer.parseInt(messageArr[i]);
    	}
    	return arr;
	}
	private void updatePlayerPosition(String message) {
    	try {
    		logger.info("Updating player position");
    		String messageToSend = lobby.sendPosGetPlayers(toIntArr(message)); //updates players positions
    		String[] playersInLobby = lobby.getPlayers(Integer.parseInt(message.split(" ")[0]));  // finds all players in lobby
    		for (String s : playersInLobby) {
    			try {
    				if(!s.equals("0")) sendMessageToParticularUser(s, messageToSend);  //Sends update to each player in the lobby
    			}catch(Exception sendPlayerUpdateException) {logger.info("Player id "+s+" is not in the lobby they updated");}
    		}
    	}catch(Exception e) {logger.info("Error Updating player pos: " +e.toString());};
    }
	private void joinLobby(String message) {
		try {
			logger.info("Updateing lobby with:"+Arrays.toString(toIntArr(message)));
			String messageToSend = lobby.updatePlayer(toIntArr(message)[0],toIntArr(message)[1],toIntArr(message)[2]);
			broadcast(messageToSend);
		}catch(Exception e) {logger.info("Error adding player to lobby: " +e.toString());};
	}
	private void getLobbyInfo(String message, String username) {
		try {
			logger.info("Updateing lobby "+message+" info to user "+username);
//			String messageToSend = lobby.updateLobbyInfo(toIntArr(message)[0],toIntArr(message)[1],toIntArr(message)[2]);
//			sendMessageToParticularUser(username, messageToSend);
		}catch(Exception e) {logger.info("Error updating lobby info: " +e.toString());};
	}
}
