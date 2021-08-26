package com.cardinity.projecttask.dto;

import com.sun.istack.NotNull;

public class UserLoginDto {

	@NotNull
	public String username;

	@NotNull
	public String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
