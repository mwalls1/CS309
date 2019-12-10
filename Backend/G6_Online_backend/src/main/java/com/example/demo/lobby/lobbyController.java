package com.example.demo.lobby;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.userInfo.userInfo;
import com.example.demo.userInfo.userInfoRepository;


@RestController
public class lobbyController {
	
	
	@Autowired
	private lobbyRepository lobbyRepo;
	
	@Autowired userInfoRepository userRepo;
	
//	@Autowired
//	private SocketService socketService;
//	
	@GetMapping("getLobbies")
	public List<lobby> getlobbys(){
		try {
			return lobbyRepo.findAll();
		} catch (Exception e) {
			List<lobby> l = null;
			return l;
		}
	}
	
	@GetMapping("getLobbyByID")
	public String getLobbyByID(Integer id) {
		try {
			Optional<lobby> l = lobbyRepo.findById(id);
			lobby l1 = l.get();
			return l1.getPlayer(1) + " " + l1.getPlayer(2) + " " + l1.getPlayer(3) + " " + l1.getPlayer(4);
		} catch (Exception e) {return e.toString();}
	}
	
	@PostMapping("/newLobby")
	public String newLobby(Integer id) {
		try {
			lobby l = new lobby();
			l.setId(id);
			lobbyRepo.save(l);
			return "New Lobby made with id:" + l.getId();
		} catch (Exception e) {return e.toString();}
	}
	
	@PostMapping("/updatePlayer")
	public String updatePlayer(Integer id, Integer player, Integer playerId) {
		try {
			removePlayerFromLobbies(playerId);
			Optional<lobby> l = lobbyRepo.findById(id);
			lobby l1 = l.get();
			switch(player) {
			case(1):
				l1.setPlayer(playerId,1);
				break;
			case(2):
				l1.setPlayer(playerId,2);
				break;
			case(3):
				l1.setPlayer(playerId,3);
				break;
			case(4):
				l1.setPlayer(playerId,4);
			 	break;
			}
			lobbyRepo.save(l1);
			return "Updated player " + player + " to id: " + playerId;
		} catch (Exception e) {return e.toString();}
	}
	
	@PostMapping("/vote")
	public String vote(Integer id, Integer playerId, Integer vote) {
		try {
			lobby l = lobbyRepo.findById(id).get();
			if (l.getPlayer(1) == playerId) l.setPlayerOneVote(vote);
			else if (l.getPlayer(2) == playerId) l.setPlayerTwoVote(vote);
			else if (l.getPlayer(3) == playerId) l.setPlayerThreeVote(vote);
			else if (l.getPlayer(4) == playerId) l.setPlayerFourVote(vote);
			else return "fail";
			lobbyRepo.save(l);
			return "Vote casted";
		} catch (Exception e) {return e.toString();}

	}
	
	@PostMapping("/readyUp")
	public String readyUp(Integer id, Integer playerId) {
		lobby l = lobbyRepo.findById(id).get();
		if(l.getReadyStatus() == null) l.setReadyStatus(1);
		try {
		if (l.getPlayer(1) == playerId) {
			if (l.getReadyStatus()%2 == 0) l.setReadyStatus(l.getReadyStatus()/2);
			else if (l.getReadyStatus()%2 != 0) l.setReadyStatus(l.getReadyStatus()*2);
			lobbyRepo.save(l);
			return "Set player 1 ready status";}
		if (l.getPlayer(2) == playerId) {
			if (l.getReadyStatus()%3 == 0) l.setReadyStatus(l.getReadyStatus()/3);
			else if (l.getReadyStatus()%3 != 0) l.setReadyStatus(l.getReadyStatus()*3);
			lobbyRepo.save(l);
			return "Set player 2 ready status";}
		if (l.getPlayer(3) == playerId) {
			if (l.getReadyStatus()%5 == 0) l.setReadyStatus(l.getReadyStatus()/5);
			else if (l.getReadyStatus()%5 != 0) l.setReadyStatus(l.getReadyStatus()*5);
			lobbyRepo.save(l);
			return "Set player 3 ready status";}
		if (l.getPlayer(4) == playerId) {
			if (l.getReadyStatus()%7 == 0) l.setReadyStatus(l.getReadyStatus()/7);
			else if (l.getReadyStatus()%7 != 0) l.setReadyStatus(l.getReadyStatus()*7);
			lobbyRepo.save(l);
			return "Set player 4 ready status";}
		} catch (Exception e) {return e.toString();}
		lobbyRepo.save(l);
		return "No player updated";
	}
	
	@PostMapping("/readyDown")
	public String readDown(Integer id) {
		try {
			lobby l = lobbyRepo.findById(id).get();
			l.setReadyStatus(1);
			lobbyRepo.save(l);
			return "Lobby ready cleared";
		} catch (Exception e) {return e.toString();}
	}
	
	@PutMapping("/cleanLobbies")
	public String cleanLobbies() {
		try {
			List<lobby> lobbies = lobbyRepo.findAll();
			for (lobby l : lobbies) {
				if ((l.getPlayer(1) == null) || (l.getPlayer(1) == 0)) {
					lobbyRepo.deleteById(l.getId());
				}
			}
			return "Lobbies Cleaned";
		} catch (Exception e) {
			return e.toString();
		}
	}
	
	@GetMapping("/getReadyStatus")
	public String getReadyStatus(Integer id) {
		try {
			return lobbyRepo.findById(id).get().getReadyStatus() + "";
		} catch (Exception e) {return e.toString();}
	}
	
	@GetMapping("/getPlayerVotes")
	public String getPlayerVotes(Integer id) {
		try {
			return lobbyRepo.findById(id).get().getPlayerGameVote();
		} catch (Exception e) {return e.toString();}
	}
	
	@PostMapping("/removePlayerFromLobbies")
	public String removePlayerFromLobbies(Integer playerId) {
		try {
			for (lobby l : lobbyRepo.findAll()) {
				if(l.getReadyStatus() == null) l.setReadyStatus(1);
				try {
					if (l.getPlayer(1) == playerId) l.setPlayer(0,1);
					if (l.getPlayer(2) == playerId) l.setPlayer(0,2);
					if (l.getPlayer(3) == playerId) l.setPlayer(0,3);
					if (l.getPlayer(4) == playerId) l.setPlayer(0,4);
				}catch (Exception e) {return e.toString();}
				lobbyRepo.save(l);
			}
			return "Player Removed";
		} catch (Exception e) {return e.toString();}
	}
	
	public String[] getPlayers(Integer id) {
		try {
			String[] val = new String[4];
			lobby l = lobbyRepo.findById(id).get();
			for(int i = 0; i < 4; i++) {
				val[i] = l.getPlayer(i+1).toString();
			}
			return val;
		} catch (Exception e) {return new String[]{e.toString()};}
	}
	
	/*
	 * take in id xpos ypos return string
	 * 
	 */
	@PostMapping("/sendPosGetPlayers")
	public String sendPosGetPlayers(Integer[] messageArr) {
    	String result = "";
		try {
			lobby l = lobbyRepo.findById(messageArr[0]).get();
			userInfo u = null;
			for (int i = 1; i <= 4; i++) {
				Integer player = l.getPlayer(i);
				if(player == 0) {
					result += "0 0 0 ";
				} else {
					try {
						u = userRepo.findById(player).get();
						if (player == messageArr[1]) {
							u.setXpos(messageArr[2]);
							u.setYpos(messageArr[3]);
							userRepo.save(u);
						}

						result = result + player + " " + u.getXpos() + " " + u.getYpos() + " ";
					}catch(Exception noPosExcpetion) {result = result + "0 0 0 ";}
				}
			}
			return result;
		} catch (Exception e) {return result + e.toString();}
		}
	
//	public String updateLobbyInfo(Integer id) {
//		try {
//			String s = "";
//			s += getLobbyByID(id) + 
//		
//		return null;
//		
//	}
}
