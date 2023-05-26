package com.iktpreobuka.eDnevnik.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

	UserDetails loadUserById(Long userId) throws UsernameNotFoundException;

}
