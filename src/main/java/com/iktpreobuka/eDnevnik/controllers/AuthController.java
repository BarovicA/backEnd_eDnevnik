package com.iktpreobuka.eDnevnik.controllers;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.config.JwtTokenProvider;
import com.iktpreobuka.eDnevnik.config.UserPrincipal;
import com.iktpreobuka.eDnevnik.entities.AdminEntity;
import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.UserEntity;
import com.iktpreobuka.eDnevnik.entities.enums.RoleENUM;
import com.iktpreobuka.eDnevnik.payload.JwtAuthenticationResponse;
import com.iktpreobuka.eDnevnik.repositories.AdminRepository;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.repositories.RoleRepository;
import com.iktpreobuka.eDnevnik.repositories.UserRepository;
import com.iktpreobuka.eDnevnik.security.dto.JwtResponse;
import com.iktpreobuka.eDnevnik.security.dto.LoginRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	@Autowired
    AuthenticationManager authenticationManager;
	

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ParentRepository parentRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AdminRepository adminepository;
	
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Provjera vjerodajnica korisnika
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Postavljanje autentifikacije u SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generiranje JWT tokena
        String jwt = tokenProvider.generateToken(authentication);

        // Dohvat informacija o prijavljenom korisniku
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        logger.info("Ulogovan korisnik: " + loginRequest.getUsername());
        // Vraćanje odgovora s generiranim tokenom i informacijama o korisniku
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }
	
//	@PostMapping
//	public AdminEntity addAdmin() {
//		AdminEntity admin = new AdminEntity();
//		admin.setFirstName("Admin");
//		admin.setLastName("Admin");
//		admin.setUsername("admin");
//		admin.setPassword("admin");
//		admin.setRole(roleRepository.findByName(RoleENUM.ADMIN).get());
//		admin.setDeleted(false);
//		adminepository.save(admin);
//		
//		return admin;
//		
//	}

//	@PostMapping("/{id}")
//	public UserEntity changerole(@PathVariable Long id) {
//		UserEntity user= userRepository.findById(id).get();
//		user.setRole(roleRepository.findByName(RoleENUM.TEACHER).get());
//		userRepository.save(user);
//		return user;
//	}
	

}
