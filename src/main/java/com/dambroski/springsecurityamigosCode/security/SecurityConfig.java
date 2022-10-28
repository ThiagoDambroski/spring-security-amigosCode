package com.dambroski.springsecurityamigosCode.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public SecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
		//.csrf().csrfTokenRepository(CookieCsrfTokenRepository.wi thHttpOnlyFalse())
		//.and()
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/", "index", "/css/*","/js/*").permitAll()
		.antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
		/*
		.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
		.antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
		.antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
		.antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(),ApplicationUserRole.ADMINTRAINEE.name())
		*/
		.anyRequest()
		.authenticated() // any request must be authenticated
		.and()
		.formLogin()
			.loginPage("/login")
			.permitAll()
			.defaultSuccessUrl("/courses",true)
		.and()
		.rememberMe()
			.tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
			.key("somethingverysecured")
		.and()
		.logout()
			.logoutUrl("/logout")
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
			.clearAuthentication(true)
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID","remember-me")
			.logoutSuccessUrl("/login");
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
	UserDetails leticiaUser = User.builder()
			.username("leticia")
			.password(passwordEncoder.encode("linda"))
			//.roles(ApplicationUserRole.STUDENT.name()) //ROLE_STUDENT
			.authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
			.build();
	
	UserDetails thiagoUser = User.builder()
			.username("thiago")
			.password(passwordEncoder.encode("123"))
			//.roles(ApplicationUserRole.ADMIN.name())
			.authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
			.build();
	
	UserDetails loideUser = User.builder()
			.username("loide")
			.password(passwordEncoder.encode("123"))
			//.roles(ApplicationUserRole.ADMINTRAINEE.name())
			.authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
			.build();
	
	return new InMemoryUserDetailsManager(leticiaUser,thiagoUser,loideUser);
	}
}
