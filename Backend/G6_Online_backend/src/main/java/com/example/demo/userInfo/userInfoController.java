package com.example.demo.userInfo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is what reads the instructions and communicates with the database
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
		return userRepo.findAll();
	}
	
    /**
     * Will add a new user with given name and password
     * @param name
     * @param password
     * @return confirmation that the addition was completed
     */
    @PostMapping("/newUser")
    public String saveUser(String name, String password) {
    	userInfo u = new userInfo(name, password);
    	userRepo.save(u);
        return "New User \""+ u.getName();
    }
    
    /**
     * Will delete a user given the id number
     * @param id
     * @return confirmation that the user was deleted
     */
    @DeleteMapping("/deleteUser")
    public String delete(int id){
        Optional<userInfo> u = userRepo.findById(id);
        userRepo.deleteById(id);
        return "Deleted user with ID: " + id + ".";
    }
    
    /**
     * Returns the name of a user from a given ID
     * @param id
     * @return
     */
    @GetMapping("/getUserById")
    public String getUserById(Integer id) {
    	Optional<userInfo> u = userRepo.findById(id);
    	userInfo u1 = u.get();
    	return u1.getName();
    }
    
    @GetMapping("/userLogin")
    public Integer userLogin(String user, String pass) {
    	List<userInfo> users = userRepo.findAll();
	    for (userInfo u : users) {
	    	if (u.getName().equals(user)){
	    		if (u.getPassword().equals(pass)){
	    			return u.getId();
	    		}
	    	}
	    }
    	return -1;
    }
}