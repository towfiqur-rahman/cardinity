package com.cardinity.projecttask.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cardinity.projecttask.models.User;

public interface UserService extends UserDetailsService {

	User registerUser(User user);

	User updateUser(User user, Long userId);
}
