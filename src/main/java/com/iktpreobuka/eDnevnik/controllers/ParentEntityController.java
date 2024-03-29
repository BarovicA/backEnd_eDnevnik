package com.iktpreobuka.eDnevnik.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.dto.ParentDTO;
import com.iktpreobuka.eDnevnik.entities.dto.ReportStudentDto;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.service.ParentService;
import com.iktpreobuka.eDnevnik.service.StudentService;
import com.iktpreobuka.eDnevnik.util.ParentCustomValidator;
import com.iktpreobuka.eDnevnik.util.RESTError;
import com.iktpreobuka.eDnevnik.validation.Validation;

@RestController
@RequestMapping("/api/v1/parents")
public class ParentEntityController {
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
    private ParentRepository parentRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private StudentService studentService;

    @Autowired
    private ParentService parentService;
    
    @Autowired
    private ParentCustomValidator parentCustomValidator;
    
    @InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(parentCustomValidator);
	}
    
   
    
    // 1. add ParentEntity
    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping("/add")
    public ResponseEntity<?> addParent(@Valid @RequestBody ParentDTO newParent, BindingResult result) {
    	if (result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			parentCustomValidator.validate(newParent, result);
		}
    	ParentEntity parent = parentService.mappNewParent(newParent);
        parentRepository.save(parent);
        logger.info("Parent" + parent.getUsername().toString() + "added.");
        return new ResponseEntity<ParentEntity>(parent, HttpStatus.CREATED);
    }
    

    // 2. get all ParentEntity
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllParents() {
        return new ResponseEntity<>(parentRepository.findByDeletedFalse(), HttpStatus.OK);
        
    }

    // 3. change ParentEntity
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateParent(@PathVariable Long id, @Valid @RequestBody ParentDTO dto,
            BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
        } else {
            parentCustomValidator.validate(dto, result);
        }
        ParentEntity existingParent = parentRepository.findById(id).orElse(null);
        if (existingParent == null) {
            return new ResponseEntity<RESTError>(new RESTError(1, "Parent does not exist!"), HttpStatus.NOT_FOUND);
        }
        existingParent = parentService.changeParentEntity(existingParent, dto);
        parentRepository.save(existingParent);
        logger.info("Parent" + existingParent.getUsername().toString() + "updated successfully!");
        return new ResponseEntity<>("Parent updated successfully!", HttpStatus.ACCEPTED);
    }
//
//    // 4. find by id
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getParentById(@PathVariable Long id) {

        if (parentService.isActive(id)) {
        	ParentEntity parentEntity = parentRepository.findById(id).get();
        	return new ResponseEntity<>(parentEntity, HttpStatus.OK);
        }
        return new ResponseEntity<RESTError>(new RESTError(4 , "Parent does not exist!"), HttpStatus.NOT_FOUND);
    }
 // 5. delete ParentEntity (only set ParentEntity.deleted on true)
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteParent(@PathVariable Long id) {
        
        if (parentService.isActive(id)) {
        	ParentEntity existingParent = parentRepository.findById(id).get();
        	existingParent.setDeleted(true);
        	parentRepository.save(existingParent);
        	logger.info("Parent: " + existingParent.getUsername() + " deleted!");
            return new ResponseEntity<>("Parent deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<RESTError>(new RESTError(4,"Parent does not exist!"), HttpStatus.NOT_FOUND);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/changerole/{id}")
    public ResponseEntity<?> TeacherById(@PathVariable Long id) {
    	ParentEntity teacherEntity = parentRepository.findById(id).orElse(null);
        if (teacherEntity == null) {
            return new ResponseEntity<RESTError>(new RESTError(1 , "Teacher does not exist!"), HttpStatus.NOT_FOUND);
        }
        teacherEntity.setRole(null);
        parentRepository.save(teacherEntity);
        return new ResponseEntity<>(teacherEntity, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllMyChildren")
    public ResponseEntity<?> getAllMyChildren(Principal principal) {
    	ParentEntity parent = parentRepository.findByUsername(principal.getName());
    	List<StudentEntity> children = studentRepository.findByParent(parent);
    	List<ReportStudentDto> report = new ArrayList<>();
    	for (StudentEntity student : children) {
			report.add(studentService.makeReportDto(student));
		}
    	
    	return new ResponseEntity<>(report, HttpStatus.OK);
    }
    
    
    
   //promena
}







