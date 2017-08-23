package org.utils.springboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
	
	@GetMapping("/getUsers")
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
    @RequestMapping("/getUser")
    public User getUser(Long id) {
        return userRepository.findOne(id);
    }
    
}