package com.dambroski.springsecurityamigosCode.jwt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;

public class JwtUsernamaAndPasswordAuthenticationFilter  extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;
	private final JwtConfig config;
	private final SecretKey jwtSecretKey;

	public JwtUsernamaAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig config,
			SecretKey jwtSecretKey) {
		this.authenticationManager = authenticationManager;
		this.config = config;
		this.jwtSecretKey = jwtSecretKey;
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
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String token = Jwts.builder()
			.setSubject(authResult.getName())
			.claim("authorities", authResult.getAuthorities())
			.setIssuedAt(new Date())
			.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
			.signWith(jwtSecretKey)
			.compact();
		
		response.addHeader(config.getAuthorizationHeader(),config.getTokenPrefix() + token);
	}

}
