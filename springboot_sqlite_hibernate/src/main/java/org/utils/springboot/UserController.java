package org.utils.springboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// http://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html
@RestController
public class UserController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserController.class);

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

	@PostMapping("/updateUser")
	public User updateUser(@RequestBody User targetUser) {
		User user = userRepository.findOne(targetUser.getId());
		if (targetUser.getUserName() != null) {
			user.setUserName(targetUser.getUserName());
		}
		if (targetUser.getPassWord() != null) {
			user.setPassWord(targetUser.getPassWord());
		}
		if (targetUser.getNickName() != null) {
			user.setNickName(targetUser.getNickName());
		}
		if (targetUser.getUserSex() != null) {
			user.setUserSex(targetUser.getUserSex());
		}
		return userRepository.saveAndFlush(user);
	}

	@DeleteMapping("/deleteUser")
	public void deleteUser(Long id) {
		User user = userRepository.findOne(id);
		userRepository.delete(user);
	}

	// TODO: provide proper implementation
	@PostMapping("/addUser")
	public User addUser(@RequestBody User newUser) {
		try {
			return userRepository.saveAndFlush(newUser);
		} catch (Exception e) {
			return newUser;
		}
	}

}
