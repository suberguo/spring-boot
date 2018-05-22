package com.dds.ldap;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.thymeleaf.util.StringUtils;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${ldap.url:ldap://www.dds.com:389}") private String url;
    @Value("${ldap.domain}:") private String domain;
    @Value("${ldap.userDNPattern:}") private String userDNPattern;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider())
				.userDetailsService(userDetailsService());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests().
			antMatchers("/","/profile/**").authenticated().
			antMatchers("/login**").permitAll().
		and().formLogin().loginPage("/login")
				.failureUrl("/login?error").permitAll().
		and().logout().permitAll();
		http.csrf().disable();
	}

	@Bean
	public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
		System.out.println(url);
		ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider("",
				url);
		provider.setConvertSubErrorCodesToExceptions(true);
		provider.setUseAuthenticationRequestCredentials(true);
		if(!StringUtils.isEmpty(userDNPattern)) {
			provider.setSearchFilter(userDNPattern);
		}
		//provider.setAuthoritiesMapper(new SimpleAuthorityMapper());
		
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Arrays.asList(activeDirectoryLdapAuthenticationProvider()));
	}

}
