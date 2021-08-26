package com.cardinity.projecttask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cardinity.projecttask.models.User;
import com.cardinity.projecttask.repositories.UserRepository;
import com.cardinity.projecttask.services.UserService;
import com.cardinity.projecttask.utils.LoggedInUserDetails;

@RestController
@RequestMapping("user/")
@ResponseBody
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping(value = "register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		user = userService.registerUser(user);
		return ResponseEntity.accepted().body(user);
	}

	@PostMapping(value = "update/{userId}")
	public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long userId) {
		boolean userExists = userRepository.existsById(userId);
		if (!userExists) {
			return ResponseEntity.badRequest().body("user with id does not exist");
		}
		user.setId(userId);
		user = userService.updateUser(user, userId);
		return ResponseEntity.ok(user);
	}

	@GetMapping(value = "profile")
	public ResponseEntity<User> getUserProfile() {
		return ResponseEntity.ok(LoggedInUserDetails.getLoggedInUser());
	}
}
