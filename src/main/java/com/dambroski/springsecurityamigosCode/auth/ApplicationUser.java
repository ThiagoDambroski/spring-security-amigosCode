package com.dambroski.springsecurityamigosCode.auth;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ApplicationUser implements UserDetails{
	
	
	private final String password;
	private final String username;
	private final List<? extends GrantedAuthority> grantedAuthority;
	private final boolean isAccountNonExpired;
	private final boolean isAccountNonlocked;
	private final boolean isCredentialsNonExpired;
	private final boolean isEnabled;
	

	public ApplicationUser(List<? extends GrantedAuthority> grantedAuthority, String password, String username,
			boolean isAccountNonExpired, boolean isAccountNonlocked, boolean isCredentialsNonExpired,
			boolean isEnabled) {
		this.grantedAuthority = grantedAuthority;
		this.password = password;
		this.username = username;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonlocked = isAccountNonlocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isEnabled = isEnabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return grantedAuthority;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return isAccountNonlocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return isEnabled;
	}

}
