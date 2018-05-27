package com.dds.securing.database;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DdsUserDetailsService implements UserDetailsService {

	private final static String USER_SELECT_SQL = "SELECT ID,USERNAME,PASSWORD FROM DDS_USER WHERE USERNAME=?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("DDS_USERS"));
		List<DdsUser> users = this.jdbcTemplate.query(USER_SELECT_SQL, new Object[] { username },
				(r, u) -> new DdsUser(authorities, r.getString("USERNAME"), r.getString("PASSWORD"), false, false, true, true));
		if (users.isEmpty()) {
			throw new BadCredentialsException("Username or password is invalid!");
		} else {
			DdsUser user = users.get(0);
			return user;
		}
	}

}
