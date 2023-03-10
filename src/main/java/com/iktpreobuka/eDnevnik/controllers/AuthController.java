package com.iktpreobuka.eDnevnik.controllers;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.config.JwtTokenProvider;
import com.iktpreobuka.eDnevnik.entities.MarkEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.UserEntity;
import com.iktpreobuka.eDnevnik.entities.dto.MarkDto;
import com.iktpreobuka.eDnevnik.payload.ApiResponse;
import com.iktpreobuka.eDnevnik.repositories.AdminRepository;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.repositories.RoleRepository;
import com.iktpreobuka.eDnevnik.repositories.UserRepository;
import com.iktpreobuka.eDnevnik.security.dto.JwtResponse;
import com.iktpreobuka.eDnevnik.security.dto.LoginRequest;
import com.iktpreobuka.eDnevnik.util.RESTError;
import com.iktpreobuka.eDnevnik.validation.Validation;

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
    
    @Autowired
    private ResourceLoader resourceLoader;
	

    
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

//        // Provera da li postoji korisnik sa zadatim username-om
//        if (userRepository.existsByUsername(loginRequest.getUsername())) {
        	// Dohvatanje korisnika po username-u
        	Optional<UserEntity> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        	if (userOptional.isPresent()) {
        	    UserEntity user = userOptional.get();
        	    
        	    // Provera da li je korisnik označen kao obrisan
        	    if (user.getDeleted()) {
        	        return ResponseEntity
        	                .badRequest()
        	                .body(new ApiResponse(false, "Pristup odbijen. Vaš nalog je obrisan."));
        	    }
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
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "Pogrešni kredencijali. Molimo pokušajte ponovo."));
        }
    }
    
    
    
	//proba bez download
    @GetMapping("/logs")
    public ResponseEntity<String> getLogs() throws IOException {
        Resource resource = resourceLoader.getResource("file:D:/Program Files/StsWorkspaceNew/eDnevnik/logs/spring-boot-logging.log");
        String content = new String(Files.readAllBytes(resource.getFile().toPath()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        logger.info("Success loading logger.");
        return new ResponseEntity<String>(content, headers, HttpStatus.OK);
    }
    
    
    //sredjen download sa try-with-resources blokom
    
    @Secured("ADMIN")
    @GetMapping("/logs/download")
    public void downloadLogs(HttpServletResponse response) {
        try (InputStream inputStream = new FileInputStream("logs/spring-boot-logging.log")) {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"spring-boot-logging.log\"");
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            logger.info("Successfully downloaded log file.");
        } catch (FileNotFoundException e) {
            logger.error("Log file not found!");
            e.printStackTrace();
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } catch (IOException e) {
            logger.error("INTERNAL_SERVER_ERROR");
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
    
    
    
    
    
    
    
//    @Secured("ADMIN")
//    @GetMapping("/logs/download")
//    public void downloadLogs(HttpServletResponse response) throws IOException {
//        try {
//			Resource resource = resourceLoader.getResource("file:D:/Program Files/StsWorkspaceNew/eDnevnik/logs/spring-boot-logging.log");
//			File file = resource.getFile();
//
//			InputStream inputStream = new FileInputStream(file);
//
//			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
//			IOUtils.copy(inputStream, response.getOutputStream());
//			response.flushBuffer();
//			inputStream.close();
//			logger.info("Successfully downloaded log file.");
//		} catch (FileNotFoundException e) {
//			logger.error("Log file not found!");
//			e.printStackTrace();
//			response.setStatus(HttpStatus.NOT_FOUND.value());
//		} catch (IOException e) {
//			logger.error("INTERNAL_SERVER_ERROR");
//			e.printStackTrace();
//		    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//		}
//    }
    

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
