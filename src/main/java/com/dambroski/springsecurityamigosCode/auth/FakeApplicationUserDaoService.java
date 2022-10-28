package com.dambroski.springsecurityamigosCode.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.dambroski.springsecurityamigosCode.security.ApplicationUserRole;
import com.google.common.collect.Lists;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao{
	
	private final PasswordEncoder encoder;
	
	
	@Autowired
	public FakeApplicationUserDaoService(PasswordEncoder encoder) {
		super();
		this.encoder = encoder;
	}

	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		// TODO Auto-generated method stub
		return getApplicationUsers().stream().filter(ApplicationUser -> username.equals(ApplicationUser.getUsername()))
				.findFirst();
	}
	
	private List<ApplicationUser> getApplicationUsers() {
		List<ApplicationUser> applicationUsers = Lists.newArrayList(
				new ApplicationUser("leticia", encoder.encode("linda"),
						ApplicationUserRole.STUDENT.getGrantedAuthorities(), true, true, true, true),
				new ApplicationUser("thiago", encoder.encode("123"),
						ApplicationUserRole.ADMIN.getGrantedAuthorities(), true, true, true, true),
				new ApplicationUser("loide",encoder.encode("123"),
				ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(),true,true,true,true));
		
		return applicationUsers;
	}

}
