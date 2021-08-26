package com.cardinity.projecttask.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cardinity.projecttask.models.User;
import com.cardinity.projecttask.models.UserType;
import com.cardinity.projecttask.repositories.UserRepository;
import com.cardinity.projecttask.services.UserService;

@Service
public class UserDetailsServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Username not found");
		}
		return user;
	}

	public User registerUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user = userRepository.save(user);
		return user;
	}

	public User updateUser(User user, Long userId) {
		User existingUser = userRepository.findById(userId).get();
		if (existingUser.getAuthorities() == null && user.getAuthorities() != null) {
			existingUser.setAuthorities(user.getAuthorities());
		}
		else if (existingUser.getAuthorities() != null && user.getAuthorities().size() > 0) {
			Collection<UserType> userAuthorities = new ArrayList<UserType>();
			userAuthorities.add(user.getAuthorities().iterator().next());
			existingUser.setAuthorities(userAuthorities);
		}
		System.out.println(existingUser.getAuthorities().size());
		existingUser.setEnabled(user.isEnabled());
		existingUser = userRepository.save(existingUser);

		return existingUser;
	}
}
