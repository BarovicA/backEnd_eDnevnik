package com.iktpreobuka.eDnevnik.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.UserEntity;
import com.iktpreobuka.eDnevnik.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	
	@Autowired
    private UserRepository userRepository;
	
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return UserPrincipal.create(userEntity);
    }

}
