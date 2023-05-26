package com.iktpreobuka.eDnevnik.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.config.UserPrincipal;
import com.iktpreobuka.eDnevnik.entities.UserEntity;
import com.iktpreobuka.eDnevnik.repositories.UserRepository;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	
	
	@Override
	public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
	    Optional<UserEntity> userOptional = userRepository.findById(userId);
	    UserEntity user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Korisnik nije pronađen"));
	    return UserPrincipal.create(user);
	}

}
