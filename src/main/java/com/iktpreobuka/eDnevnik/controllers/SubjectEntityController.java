package com.iktpreobuka.eDnevnik.controllers;

import java.security.Principal;
import java.util.ArrayList;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.dto.SubjectDTO;
import com.iktpreobuka.eDnevnik.entities.enums.SchoolYear;
import com.iktpreobuka.eDnevnik.entities.enums.Semester;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.eDnevnik.service.SubjectService;
import com.iktpreobuka.eDnevnik.util.RESTError;
import com.iktpreobuka.eDnevnik.validation.Validation;

@RestController
@RequestMapping("/api/v1/subjects")
@CrossOrigin(origins = "http://localhost:3000")
public class SubjectEntityController {

private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired StudentRepository studentRepository;
	
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators();
	}
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<?> addNewSubject(@Valid @RequestBody SubjectEntity newSubject, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		if (subjectService.isSubjectUnique(newSubject.getName(), newSubject.getYear(), newSubject.getSemester())) {
	        logger.info("Created subject: " + newSubject.getName());
	        return new ResponseEntity<>(subjectService.create(newSubject), HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("The subject already exists.", HttpStatus.CONFLICT);
	    }
	}
	
	//@Secured("ADMIN")
	@GetMapping
	public Iterable<SubjectEntity> getAllSubjects() {
		return subjectRepository.findAll();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getSubjectById(@PathVariable Long id) {
		if(subjectRepository.existsById(id)) {
			SubjectEntity subject = subjectRepository.findById(id).get();
			SubjectDTO subDTO = subjectService.mappSubjectForDto(subject); 		
			return new ResponseEntity<>(subDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("The subject does not exist.", HttpStatus.NOT_FOUND);
		}
	}
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateSubjectById(@PathVariable Long id, @Valid @RequestBody SubjectEntity updatedSubject, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		if(subjectRepository.existsById(id)) {
			SubjectEntity subject = subjectRepository.findById(id).get();
			subject.setName(updatedSubject.getName());
			subject.setWeeklyHours(updatedSubject.getWeeklyHours());
			subject.setYear(updatedSubject.getYear());
			subject.setSemester(updatedSubject.getSemester());
			subjectRepository.save(subject);
			logger.info("Updated subject with id: " + id);
			return new ResponseEntity<>("The subject is updated.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("The subject does not exist.", HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    logger.info("Logged in user: " + auth.getName());
	    logger.info("Authorities: " + auth.getAuthorities());

	    if (subjectService.isActive(id)) {
	        SubjectEntity subjectToDelete = subjectRepository.findById(id).get();
	        logger.info("Deleted subject with id:" + id);
	        subjectToDelete.setDeleted(true);
	        subjectRepository.save(subjectToDelete);
	        return new ResponseEntity<>("Deleted subject with id:" + id, HttpStatus.OK);
	    }
	    return new ResponseEntity<RESTError>(new RESTError(1, "Grade with id " + id + " not found"),
	            HttpStatus.NOT_FOUND);
	}


	@GetMapping("/search")
	public ResponseEntity<?> searchSubject(
	    @RequestParam(required = false) SchoolYear year, 
	    @RequestParam(required = false) Semester semester, 
	    @RequestParam(required = false) String name) {
	    
	    if(year != null && semester != null && name != null) {
	        return new ResponseEntity<>(subjectRepository.findByYearAndSemesterAndNameContainingIgnoreCaseAndDeletedFalse(year, semester, name), HttpStatus.OK);
	    } else if(year != null && semester != null) {
	        return new ResponseEntity<>(subjectRepository.findByYearAndSemesterAndDeletedFalse(year, semester), HttpStatus.OK);
	    } else if(year != null && name != null) {
	        return new ResponseEntity<>(subjectRepository.findByYearAndNameContainingIgnoreCaseAndDeletedFalse(year, name), HttpStatus.OK);
	    } else if(semester != null && name != null) {
	        return new ResponseEntity<>(subjectRepository.findBySemesterAndNameContainingIgnoreCaseAndDeletedFalse(semester, name), HttpStatus.OK);
	    } else if(year != null) {
	        return new ResponseEntity<>(subjectRepository.findByYearAndDeletedFalse(year), HttpStatus.OK);
	    } else if(semester != null) {
	        return new ResponseEntity<>(subjectRepository.findBySemesterAndDeletedFalse(semester), HttpStatus.OK);
	    } else if(name != null) {
	        return new ResponseEntity<>(subjectRepository.findByNameContainingIgnoreCaseAndDeletedFalse(name), HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(subjectRepository.findByDeletedFalse(), HttpStatus.OK);
	    }
	}	
}



