package com.dambroski.springsecurityamigosCode.auth;

import java.util.Optional;

public interface ApplicationUserDao {
	
	Optional<ApplicationUser> selectApplicationUserByUsername(String username);

}
