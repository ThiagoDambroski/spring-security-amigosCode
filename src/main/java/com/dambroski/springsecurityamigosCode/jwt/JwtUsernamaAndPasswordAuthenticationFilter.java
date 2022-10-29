package com.dambroski.springsecurityamigosCode.jwt;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtUsernamaAndPasswordAuthenticationFilter  extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;

	public JwtUsernamaAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		
	try {
		UsernamePasswordAuthenticationRequest authenticationRequest = 	new ObjectMapper()
				.readValue(request.getInputStream(), UsernamePasswordAuthenticationRequest.class);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), authenticationRequest.getPassword());
		
		
		return authenticationManager.authenticate(authentication);
		
		
	} catch (IOException e) {
		
		throw new RuntimeException(e);
	} 
	
	
		
	}

}
