package com.iktpreobuka.eDnevnik.controllers;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.entities.AdminEntity;
import com.iktpreobuka.eDnevnik.entities.enums.RoleENUM;
import com.iktpreobuka.eDnevnik.payload.JwtAuthenticationResponse;
import com.iktpreobuka.eDnevnik.payload.LoginRequest;
import com.iktpreobuka.eDnevnik.repositories.AdminRepository;
import com.iktpreobuka.eDnevnik.repositories.RoleRepository;
import com.iktpreobuka.eDnevnik.repositories.UserRepository;
import com.iktpreobuka.eDnevnik.security.JwtTokenProvider;
import com.iktpreobuka.eDnevnik.security.UserPrincipal;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @Autowired
    AdminRepository adminepository;
	
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
			Authentication authentication = authenticationManager.authenticate(
			        new UsernamePasswordAuthenticationToken(
			                loginRequest.getUsername(),
			                loginRequest.getPassword()
			        )
			);

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = tokenProvider.generateToken(authentication);
			
			UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();		
			List<String> roles = userDetails.getAuthorities().stream()
					.map(item -> item.getAuthority())
					.collect(Collectors.toList());
			logger.info("User: " + loginRequest.getUsername() + "singed in.");
			return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
		} catch (BadCredentialsException ex) {
			logger.error("Wrong credentials during sign in attempt.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong credentials!");
		}
    }
	
	@PostMapping
	public AdminEntity addAdmin() {
		AdminEntity admin = new AdminEntity();
		admin.setFirstName("Admin");
		admin.setLastName("Admin");
		admin.setUsername("admin");
		admin.setPassword("admin");
		admin.setRole(roleRepository.findByName(RoleENUM.ADMIN).get());
		admin.setDeleted(false);
		adminepository.save(admin);
		
		return admin;
		
	}
	
}
