package com.dambroski.springsecurityamigosCode.security;

import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dambroski.springsecurityamigosCode.auth.ApplicationUserService;
import com.dambroski.springsecurityamigosCode.jwt.JwtConfig;
import com.dambroski.springsecurityamigosCode.jwt.JwtTokenVerifier;
import com.dambroski.springsecurityamigosCode.jwt.JwtUsernamaAndPasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
	private final SecretKey secretKey;
	private final JwtConfig jwtConfig;
	
	@Autowired
	public SecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService,
			SecretKey secretKey, JwtConfig jwtConfig) {

		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
		this.secretKey = secretKey;
		this.jwtConfig = jwtConfig;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
		//.csrf().csrfTokenRepository(CookieCsrfTokenRepository.wi thHttpOnlyFalse())
		//.and()
		.csrf().disable()
		.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilter(new JwtUsernamaAndPasswordAuthenticationFilter(authenticationManager(),jwtConfig,secretKey))
		.addFilterAfter(new JwtTokenVerifier(secretKey,jwtConfig),JwtUsernamaAndPasswordAuthenticationFilter.class)
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
		.authenticated(); // any request must be authenticated
		/*
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
			*/
	}
	
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() { 
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}
}
