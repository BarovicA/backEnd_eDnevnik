package com.iktpreobuka.eDnevnik.controllers;


import java.security.Principal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.MarkEntity;
import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectStudentEntity;
import com.iktpreobuka.eDnevnik.entities.dto.StudentDto;
import com.iktpreobuka.eDnevnik.entities.dto.SubjectMarksDto;
import com.iktpreobuka.eDnevnik.entities.enums.RoleENUM;
import com.iktpreobuka.eDnevnik.repositories.GradeRepository;
import com.iktpreobuka.eDnevnik.repositories.MarkRepository;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectStudentRepository;
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
	private MarkRepository markRepository;

	@Autowired
	private ParentService parentService;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private StudentService studentService;

	@Autowired
	private StudentCustomValidator studentCustomValidator;
	@Autowired
	TeacherSubjectStudentRepository teacherSubjectStudentRepository;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(studentCustomValidator);
	}
	// 1. add StudentEntity
	@PreAuthorize("hasAuthority('ADMIN')")
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
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public ResponseEntity<?> getAllStudents() {
		List<StudentEntity> students = StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                .filter(student -> !student.getDeleted())
                .collect(Collectors.toList());
	    return new ResponseEntity<>(students, HttpStatus.OK);
	}
	//all Parents children
	
	@CrossOrigin
	@PreAuthorize("hasAuthority('PARENT')")
	@GetMapping("/children")
	public ResponseEntity<?> getMyStudents(Principal principal) {
	    ParentEntity parent = parentRepository.findByUsername(principal.getName());
	    List<StudentEntity> students = parent.getStudent();
	    System.out.println("User roles: " + ((AbstractAuthenticationToken) principal).getAuthorities()); // ispisuje role korisnika
	    return new ResponseEntity<>(students, HttpStatus.OK);
	}
	
	

	
	
	
	// 3. change StudentEntity
	@PreAuthorize("hasAuthority('ADMIN')")
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
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/find/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable Long id) {

	    if (studentService.isActive(id)) {
	    	StudentEntity studentEntity = studentRepository.findById(id).get();
	    	return new ResponseEntity<>(studentEntity, HttpStatus.OK);
	    }
	    return new ResponseEntity<RESTError>(new RESTError(4 , "Student does not exist!"), HttpStatus.NOT_FOUND);
	}
    // 5. delete StudentEntity (only set StudentEntity.deleted on true)
	@PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        
        if (studentService.isActive(id)) {
        	studentService.deleteStudent(id);
            return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<RESTError>(new RESTError(4,"Student does not exist!"), HttpStatus.NOT_FOUND);
    }
    
    // 6. add parent to student
	@PreAuthorize("hasAuthority('ADMIN')")
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
	@PreAuthorize("hasAuthority('ADMIN')")
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
    	//add student to grade and make teacherSubjectStudents entiities with this studdent and teacerSubject
    	// that is connected to him trough grade :)
    	return new ResponseEntity<>(studentService.addStudentToGrade(studentId, gradeId), HttpStatus.OK);
    }
    
	
	// 8. get all students marks
	
	@PreAuthorize("hasAuthority('STUDENT')")
	@GetMapping("/mySubjectsWithMarks")
	public ResponseEntity<?> mySubjectsWithMarks(Principal principal) {
	    // pronalazenje studenta koji ima pristup 
	    StudentEntity student = studentRepository.findByUsername(principal.getName());

	     //pronalazenje svih predmeta koje taj student slusa
	    List<TeacherSubjectStudentEntity> allTSSforStudent = teacherSubjectStudentRepository.findByStudent(student);
	    if (allTSSforStudent.isEmpty()) {
	    	return new ResponseEntity<RESTError>(new RESTError(4,"Student don't listen any classes!"), HttpStatus.NOT_FOUND);
		}
	    //  prolazak kroz sve predmete sto slusa i za svaki pravljenje dto i za svaki nalazenje ocena i stavljanje u dto
	    // napravicu ovu logiku u servis klasi da ga koristim za roditelja i admina samo sa .stream() i .map()
	    allTSSforStudent = teacherSubjectStudentRepository.findByStudent(student);
	    List<SubjectMarksDto> subjectsWithMarkslist = new ArrayList<>();
	    for (TeacherSubjectStudentEntity teacherSubjectStudentEntity : allTSSforStudent) {
	    	
	    	SubjectMarksDto dto = new SubjectMarksDto();
	    	dto.setName(teacherSubjectStudentEntity.getTeacherSubject().getSubject().getName());
	    	dto.setYear(teacherSubjectStudentEntity.getTeacherSubject().getSubject().getYear());
	    	dto.setSemester(teacherSubjectStudentEntity.getTeacherSubject().getSubject().getSemester());
	    	List <MarkEntity> marks = markRepository.findByTeacherSubjectStudent(teacherSubjectStudentEntity);
	    	dto.setMarks(marks);
	    	subjectsWithMarkslist.add(dto);
	    	
		}
	        
	    return new ResponseEntity<>(subjectsWithMarkslist, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/allmMarksForStudentByAdmin")
	public ResponseEntity<?> allmMarksForStudentByAdmin(@RequestParam Long studentId) {
	    // pronalazenje studenta da li aktivan
	    if (!studentService.isActive(studentId)) {
	    	return new ResponseEntity<RESTError>(new RESTError(4,"Student does not exist!"), HttpStatus.NOT_FOUND);
		}
	     //pronalazenje svih predmeta koje taj student slusa
	    StudentEntity student = studentRepository.findById(studentId).get();
	    List<TeacherSubjectStudentEntity> allTSSforStudent = teacherSubjectStudentRepository.findByStudent(student);
	    if (allTSSforStudent.isEmpty()) {
	    	return new ResponseEntity<RESTError>(new RESTError(4,"Student don't listen any classes!"), HttpStatus.NOT_FOUND);
		}
	    List<SubjectMarksDto> subjectsWithMarkslist = studentService.makeSubjectMarksDto(student);
	 
	        logger.info("Admin got all marks for student: "+ studentRepository.findById(studentId).get().getUsername());
	    return new ResponseEntity<>(subjectsWithMarkslist, HttpStatus.OK);
	}
	
	

}
