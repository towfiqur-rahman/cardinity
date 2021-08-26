package com.cardinity.projecttask.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cardinity.projecttask.models.User;

public class LoggedInUserDetails {

	public static User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		return (User) principal;
	}

	public static boolean userHasRole(User user, ProjectTaskEnums taskEnum) {
		return user.getAuthorities().iterator().next().getRole().equals(taskEnum.toString());
	}
}
