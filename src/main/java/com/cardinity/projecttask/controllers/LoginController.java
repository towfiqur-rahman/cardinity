package com.cardinity.projecttask.controllers;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardinity.projecttask.dto.UserLoginDto;
import com.cardinity.projecttask.models.User;
import com.cardinity.projecttask.security.jwt.JwtUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("login")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping
	public ResponseEntity<String> loginUser(@Validated @RequestBody UserLoginDto userLoginDetails) {
    	try {
    		Authentication authentication = authenticationManager.authenticate(
    				new UsernamePasswordAuthenticationToken(userLoginDetails.getUsername(), 
    						userLoginDetails.getPassword()));

    		SecurityContextHolder.getContext().setAuthentication(authentication);
    		String jwt = jwtUtils.generateJwtToken(authentication);
/*    		
    		User userDetails = (User) authentication.getPrincipal();		
    		List<Object> roles = 
    				userDetails.getAuthorities().stream()
    				.map(new Function<GrantedAuthority, Object>() {
						public Object apply(GrantedAuthority item) {
							return item.getAuthority();
						}
					}).collect(Collectors.toList());

    		  String token = Jwts.builder()
                  .setSubject(((User) authentication.getPrincipal()).getUsername())
                  .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                  .signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs".getBytes())

                  .compact();
*/

    		return ResponseEntity.ok("Bearer " + jwt);
	    } catch (BadCredentialsException ex) {
	    	logger.error(ex.getMessage());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	logger.error(e.getMessage());
	    }

        return ResponseEntity.badRequest().body("credentials are invalid");
	}
}
