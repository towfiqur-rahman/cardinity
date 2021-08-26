package com.cardinity.projecttask.security.jwt;

import java.util.List;

public class JwtResponse {

    private String token;
    private String type = "Basic";
    private String username;
    private String password;
    private List<String> roles;

    public JwtResponse(String jwt, String username, String password, List<String> roles) {
//    	this.
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
