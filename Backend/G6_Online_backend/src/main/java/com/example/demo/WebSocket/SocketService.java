package com.example.demo.WebSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import com.example.demo.lobby.lobby;
import com.example.demo.lobby.lobbyRepository;
import com.example.demo.userInfo.userInfo;
import com.example.demo.userInfo.userInfoRepository;

@Service
public class SocketService {

//	private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

	@Autowired
	lobbyRepository lobbyRepo;
	
	@Autowired
	userInfoRepository userRepo;

    public String sendPosGetPlayers(String[] arr) {
	    	Integer[] messageArr = new Integer[4];
	    	for (int i = 0; i < 4; i++) {
	    		messageArr[i] = Integer.parseInt(arr[i]);
	    	}
	    	return sendPosGetPlayers(messageArr[0], messageArr[1], messageArr[2], messageArr[3]);
    }
    
    public String sendPosGetPlayers(Integer lobbyId, Integer playerId, Integer xpos, Integer ypos) {
		String result = "";
		try {
			lobby l = lobbyRepo.findById(lobbyId).get();
			userInfo u = null;
			for (int i = 1; i <= 4; i++) {
				Integer player = l.getPlayer(i);
				if(player == 0) {
					result += "0 0 0 ";
				} else {
					u = userRepo.findById(player).get();
					if (player == playerId){
						u.setXpos(xpos);
						u.setYpos(ypos);
						userRepo.save(u);
					}
					result = result + player + " " + u.getXpos() + " " + u.getYpos() + " ";
				}
			}
			return result;
		} catch (Exception e) {return result + e.toString();}
		
	}
}