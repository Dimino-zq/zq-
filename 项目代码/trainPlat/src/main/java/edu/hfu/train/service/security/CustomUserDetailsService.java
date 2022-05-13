package edu.hfu.train.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface  CustomUserDetailsService {
	UserDetails loadUserByUsernameAndDomain(String username, String userType) throws UsernameNotFoundException, Exception;
}
