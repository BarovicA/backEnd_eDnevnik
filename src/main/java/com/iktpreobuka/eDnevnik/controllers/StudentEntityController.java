package com.iktpreobuka.eDnevnik.controllers;


import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.hibernate.annotations.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AbstractAuthenticationToken;
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
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.dto.StudentDto;
import com.iktpreobuka.eDnevnik.entities.enums.RoleENUM;
import com.iktpreobuka.eDnevnik.repositories.GradeRepository;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.service.GradeService;
import com.iktpreobuka.eDnevnik.service.ParentService;
import com.iktpreobuka.eDnevnik.service.StudentService;
import com.iktpreobuka.eDnevnik.util.RESTError;
import com.iktpreobuka.eDnevnik.util.StudentCustomValidator;
import com.iktpreobuka.eDnevnik.validation.Validation;

@RestController
@RequestMapping("/api/v1/students")
public class StudentEntityController {
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ParentEntity parentEntity;
	
	@Autowired
	GradeRepository gradeRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ParentRepository parentRepository;

	@Autowired
	private ParentService parentService;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private StudentService studentService;

	@Autowired
	private StudentCustomValidator studentCustomValidator;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(studentCustomValidator);
	}
	// 1. add StudentEntity
	@Secured("ADMIN")
	@PostMapping("/add")
	public ResponseEntity<?> addStudent(@Valid @RequestBody StudentDto newStudent, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			studentCustomValidator.validate(newStudent, result);
		}
		StudentEntity student = studentService.mappNewStudent(newStudent);
	    studentRepository.save(student);
	    return new ResponseEntity<StudentEntity>(student, HttpStatus.CREATED);
	}

	// 2. get all StudentEntity
//	@Secured("ADMIN")
//	@GetMapping
//	public ResponseEntity<?> getAllStudents() {
//	    return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
//	}
	
	@Secured("ADMIN")
	@GetMapping
	public ResponseEntity<?> getAllStudents() {
		List<StudentEntity> students = StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                .filter(student -> !student.getDeleted())
                .collect(Collectors.toList());
	    return new ResponseEntity<>(students, HttpStatus.OK);
	}
	//all Parents children
	
	@CrossOrigin
	@Secured("PARENT")
	@GetMapping("/children")
	public ResponseEntity<?> getMyStudents(Principal principal) {
	    ParentEntity parent = parentRepository.findByUsername(principal.getName());
	    List<StudentEntity> students = parent.getStudent();
	    System.out.println("User roles: " + ((AbstractAuthenticationToken) principal).getAuthorities()); // ispisuje role korisnika
	    return new ResponseEntity<>(students, HttpStatus.OK);
	}
	
	

	
	
	
	// 3. change StudentEntity
	@Secured("ADMIN")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDto dto,
	        BindingResult result) {
	    if (result.hasErrors()) {
	        return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
	    } else {
	        studentCustomValidator.validate(dto, result);
	    }
	    StudentEntity existingStudent = studentRepository.findById(id).orElse(null);
	    if (existingStudent == null) {
	        return new ResponseEntity<RESTError>(new RESTError(1, "Student does not exist!"), HttpStatus.NOT_FOUND);
	    }
	    existingStudent = studentService.changeStudentEntity(existingStudent, dto);
	    studentRepository.save(existingStudent);
	    return new ResponseEntity<>("Student updated successfully!", HttpStatus.ACCEPTED);
	}

	// 4. find by id
	@Secured("ADMIN")
	@GetMapping("/find/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable Long id) {

	    if (studentService.isActive(id)) {
	    	StudentEntity studentEntity = studentRepository.findById(id).get();
	    	return new ResponseEntity<>(studentEntity, HttpStatus.OK);
	    }
	    return new ResponseEntity<RESTError>(new RESTError(4 , "Student does not exist!"), HttpStatus.NOT_FOUND);
	}
    // 5. delete StudentEntity (only set StudentEntity.deleted on true)
	@Secured("ADMIN")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        
        if (studentService.isActive(id)) {
        	StudentEntity existingStudent = studentRepository.findById(id).get();
        	existingStudent.setDeleted(true);
        	studentRepository.save(existingStudent);
            return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<RESTError>(new RESTError(4,"Student does not exist!"), HttpStatus.NOT_FOUND);
    }
    
    // 6. add parent to student
	@Secured("ADMIN")
    @PostMapping("/addParent/{studentId}/parent/{parentId}")
    public ResponseEntity<?> addParentToStudent(@PathVariable Long studentId, @PathVariable Long parentId) {
    	 if (!studentService.isActive(studentId)) {
    		 return new ResponseEntity<RESTError>(new RESTError(4,"Student does not exist!"), HttpStatus.NOT_FOUND);
    	 }
    	 if (!parentService.isActive(parentId)) {
    		 return new ResponseEntity<RESTError>(new RESTError(4,"Parent does not exist!"), HttpStatus.NOT_FOUND);
		}
    	StudentEntity student = studentRepository.findById(studentId).get();
    	ParentEntity parent = parentRepository.findById(parentId).get();
    	student.setParent(parent);
    	logger.info("Parent: " + parent.getUsername() + " added to student: " + student.getUsername());
    	return new ResponseEntity<StudentEntity>(studentRepository.save(student), HttpStatus.OK);
    }
    
    // 7. add student to grade
	@Secured("ADMIN")
    @PostMapping("/addGrade/{studentId}/grade/{gradeId}")
    public ResponseEntity<?> addStudentToGrade(@PathVariable Long studentId, @PathVariable Long gradeId){
    	if (!studentService.isActive(studentId)) {
   		 return new ResponseEntity<RESTError>(new RESTError(4,"Student does not exist!"), HttpStatus.NOT_FOUND);
   	 }
    	if (!gradeService.isActive(gradeId)) {
   		 return new ResponseEntity<RESTError>(new RESTError(4,"Grade does not exist!"), HttpStatus.NOT_FOUND);
		}
    	StudentEntity student = studentRepository.findById(studentId).get();
    	GradeEntity grade = gradeRepository.findById(gradeId).get();
    	if (grade.getTeacherSubjectGrade() == null) {
      		 return new ResponseEntity<RESTError>(new RESTError(4,"Grade does not have assigned Teacher with subject!"), HttpStatus.NOT_FOUND);
		}
    	if (student.getGrade() == grade) {
    		return new ResponseEntity<RESTError>(new RESTError(4,"Student is already in that grade!"), HttpStatus.NOT_FOUND);
		}
    	logger.info("Student: " + student.getUsername() + " added to grade: " + grade.getId());
    	return new ResponseEntity<StudentEntity>(studentService.addStudentToGrade(studentId, gradeId), HttpStatus.OK);
    }
    
    

}
