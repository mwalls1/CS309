package com.example.demo.userInfo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is for the userInfo table in the database
 * 
 * @author Cole Weitzel
 * @author Taylor Weil
 *
 */

@RestController
public class userInfoController {
	
	@Autowired
	private userInfoRepository userRepo;
	
	/**
	 * Returns all users in the database
	 * @return
	 */
	@GetMapping("/getUsers")
    public List<userInfo> getUsers() {
    	try {
    		return userRepo.findAll();
    	} catch (Exception e) {
    		List<userInfo> u = null;
    		return u;
    	}
	}
	
    /**
     * Add a new user with given name and password
     * @param name
     * @param password
     * @return confirmation that the addition was completed
     */
    @PostMapping("/newUser")
    public String saveUser(String name, String password) {
    	try {
    		userInfo u = new userInfo(name, password);
        	userRepo.save(u);
            return "New User \""+ u.getName();
    	} catch(Exception e) { return e.toString(); }
    }
    
    /**
     * Delete a user given the id number
     * @param id
     * @return confirmation that the user was deleted
     */
    @DeleteMapping("/deleteUser")
    public String delete(int id){
    	try {
            userRepo.deleteById(id);
            return "Deleted user with ID: " + id + ".";
    	} catch(Exception e) { return e.toString(); }
        
    }
    
    /**
     * Returns the name of a user from a given ID
     * @param id
     * @return
     */
    @GetMapping("/getUserById")
    public String getUserById(Integer id) {
    	try {
    		Optional<userInfo> u = userRepo.findById(id);
        	userInfo u1 = u.get();
        	return u1.getName();
    	} catch(Exception e) { return e.toString(); }
    }
    
    /**
     * Determines if the users name and password are correct and returns the userId
     * @param user
     * @param pass
     * @return
     */
    @GetMapping("/userLogin")
    public Integer userLogin(String user, String pass) {
    	try {
    		List<userInfo> users = userRepo.findAll();
    	    for (userInfo u : users) {
    	    	if (u.getName().equals(user)){
    	    		if (u.getPassword().equals(pass)){
    	    			return u.getId();
    	    		}
    	    	}
    	    }
        	return -1;
    	} catch(Exception e) { return -1; }
    }
	
    /**
     * Returns the x and y position seperated by a space
     * @param id
     * @return
     */
	@GetMapping("getPosByID")
	public String getXbyID(Integer id) {
		try {
			Optional<userInfo> p = userRepo.findById(id);
			userInfo p1 = p.get();
			return p1.getXpos().toString() + " " + p1.getYpos().toString();
		} catch(Exception e) { return e.toString(); }
	}
	
	/**
	 * Creates a new player
	 * @param xpos
	 * @param ypos
	 * @return
	 */
	@PostMapping("/newPlayer")
	public String savePlayer(Integer xpos, Integer ypos) {
		// I THINK WE CAN REMOVE THIS METHOD SINCE WE HAVE THE UPDATEPOS METHOD
		userInfo p = new userInfo(xpos, ypos);
		userRepo.save(p);
		return "New player made at " + p.getXpos() + " added to user " + p.getYpos();
	}
	
	/**
	 * Returns the updated x and y position of the given user
	 * @param id
	 * @param xpos
	 * @param ypos
	 * @return
	 */
	@PostMapping("/updatePos")
	public String updatePos(Integer id, Integer xpos, Integer ypos) {
		try {
			userInfo p1 = userRepo.findById(id).get();
			p1.setXpos(xpos);
			p1.setYpos(ypos);
			userRepo.save(p1);
			return p1.getXpos().toString() + " " + p1.getYpos().toString();
		} catch(Exception e) { return e.toString(); }
	}
	
	/**
	 * Adds a friend to the userId with the given friendId
	 * @param userId
	 * @param friendId
	 * @return
	 */
	@PostMapping("/addFriend")
	public String addFriend(Integer userId, Integer friendId) {
		try {
			if(userId == friendId) return "Unfortunately you can't be friends with yourself";
			else if (userId < 0) return "User ID can't be less than zero, try again";
			else if (friendId < 0) return "Friend ID can't be less than zero, try again";
			else if(!checkUserIdNum(userId)) return "User ID not found in database, try again";
			else if(!checkUserIdNum(friendId)) return "Friend ID not found in database, try again";
			
			String friendIdString = friendId.toString();
			userInfo p = userRepo.findById(userId).get();
			String f = p.getFriends();
			if(f == null) {
				p.setFirstFriend(friendId);
				userRepo.save(p);
				return "First friend ID: " + friendId + " added to user: " + userId;
			}
			
			String[] friends = f.split("::");
			
			if(friends.length == 1 && friends[0].equals(friendIdString)) {
				return "You are already friends with this user";
			}
			String friendsWithoutNull = friends[0];
			for(int i = 1; i < friends.length; i++) {
				if(friends[i].equals(friendIdString)) {
					return "You are already friends with this user";
				}
				if(friends[i].equals(null)) continue;
				friendsWithoutNull =  friendsWithoutNull + "::" + friends[i];
			}
			p.setFriends(friendsWithoutNull);
			p.addFriend(friendId);
			userRepo.save(p);
			return "ID: " + friendId + " added to user: " + userId;
			
		} catch(Exception e) { return e.toString(); }
	}
	
	/**
	 * Deletes the friendId from the userId list
	 * @param userId
	 * @param friendId
	 * @return
	 */
	@PostMapping("/deleteFriend")
	public String deleteFriend(Integer userId, Integer friendId) {
		try {
			if (userId < 0) return "User ID can't be less than zero, try again";
			else if (friendId < 0) return "Friend ID can't be less than zero, try again";
			else if(!checkUserIdNum(userId)) return "User ID not found in database, try again";
			else if(!checkUserIdNum(friendId)) return "Friend ID not found in database, try again";
			
			userInfo u = userRepo.findById(userId).get();
			String f = u.getFriends();
			
			if(f == null) return "You have no friends";
			
			String[] arr = f.split("::");
			int arrLen = arr.length;
			
			String friendIdString = friendId.toString();
			String newFriendList = new String();
			boolean found = false;
			for(int i = 0; i < arrLen; i++) {
				if(arr[i].equals(friendIdString)) found = true;
				else newFriendList = newFriendList + arr[i] + "::";
			}
			
			if (!found) return "You are not friends with this user";
			if(found && arrLen == 1) {
				newFriendList = null;
			}
			
			u.setFriends(newFriendList);
			userRepo.save(u);
			return newFriendList;
		} catch(Exception e) { return e.toString(); }
	}
	
	/**
	 * returns a string of all ids of the given user
	 * @param userId
	 * @return
	 */
	@GetMapping("/getFriends")
	public String getFriends(Integer userId) {
		try {
			if(userId == 0) return "Temporary user";
			String f = userRepo.findById(userId).get().getFriends();
			if(f == null) return "You have no friends";
			else return f;
		} catch(Exception e) { return e.toString(); }
	}
	
	/**
	 * returns the names of the friends from the given user
	 * @param userId
	 * @return
	 */
	@GetMapping("/getFriendsNames")
	public String getFriendsNames(Integer userId) {
		try {
			if(userId == 0) return "Temporary user";
			String f = userRepo.findById(userId).get().getFriends();
			if(f == null) return "You have no friends";
			String[] arr = f.split("::");
			String result = "";
			for(int i = 0; i < arr.length; i++) {
				String name = userRepo.findById(Integer.parseInt(arr[i])).get().getName();
				result = result + name + "::" + arr[i] + "::";
			}
			return result;
		} catch(Exception e) { return e.toString(); }
	}
	
	/*
	 * helper method
	 */
	private boolean checkUserIdNum(Integer id) {
		List<userInfo> uL = userRepo.findAll();
		for(userInfo item : uL) {
			if(item.getId() == id) return true;
		}
		return false;
	}
}
