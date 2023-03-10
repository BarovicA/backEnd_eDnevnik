package com.iktpreobuka.eDnevnik.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.MarkEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.dto.MarkDto;
import com.iktpreobuka.eDnevnik.entities.enums.MarkEnum;
import com.iktpreobuka.eDnevnik.repositories.MarkRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectStudentRepository;
import com.iktpreobuka.eDnevnik.service.EmailService;
import com.iktpreobuka.eDnevnik.service.MarkService;
import com.iktpreobuka.eDnevnik.service.StudentService;
import com.iktpreobuka.eDnevnik.service.SubjectService;
import com.iktpreobuka.eDnevnik.service.TeacherService;
import com.iktpreobuka.eDnevnik.service.TeacherSubjectService;
import com.iktpreobuka.eDnevnik.util.RESTError;
import com.iktpreobuka.eDnevnik.validation.Validation;

@RestController
@RequestMapping("/api/v1/marks")
public class MarkController {

	@Autowired
	MarkService markservice;
	@Autowired
	TeacherService teacherService;
	@Autowired
	EmailService emailservice;
	@Autowired
	MarkRepository markRepository;
	@Autowired
	SubjectService subjectService;
	@Autowired
	SubjectRepository subjectRepository;
	@Autowired
	StudentService studentService;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	TeacherRepository teacherRepository;
	@Autowired
	TeacherSubjectStudentRepository tssRepository;
	@Autowired
	TeacherSubjectRepository teacherSubjectRepository;
	@Autowired
	TeacherSubjectService teacherSubjectService;

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	// nastavnik dodaje ocenu
	@Secured("TEACHER")
	@PostMapping("/createMarkByTeacher")
	public ResponseEntity<?> createGradeByTeacher(@RequestParam Long studentId, @RequestParam Long subjectId,
			@Valid @RequestBody MarkDto newMark, BindingResult result, Principal principal) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		if (!subjectService.isActive(subjectId)) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Subject not found."), HttpStatus.NOT_FOUND);
		}
		if (!studentService.isActive(studentId)) {
			return new ResponseEntity<RESTError>(new RESTError(4, "Student does not exist!"), HttpStatus.NOT_FOUND);
		}
		StudentEntity student = studentRepository.findById(studentId).get();
		SubjectEntity subject = subjectRepository.findById(subjectId).get();
		TeacherEntity teacher = teacherRepository.findByUsername(principal.getName());

		if (!teacherSubjectService.isActive(teacher, subject)) {
			return new ResponseEntity<RESTError>(new RESTError(5, "Subject with Teacher not found."),
					HttpStatus.NOT_FOUND);
		}
		TeacherSubjectEntity teSu = (TeacherSubjectEntity) teacherSubjectRepository.findBySubjectAndTeacher(subject,
				teacher);
		MarkEntity mark = new MarkEntity();
		mark.setMarkValue(markservice.enumByNo(newMark.getValue()));
		mark.setDate(LocalDate.now());
		mark.setTeacherSubjectStudent(tssRepository.findByTeacherSubjectAndStudent(teSu, student));
		markRepository.save(mark);
		logger.info("Teacher: " + teacher.getUsername() + "added mark: " + newMark.getValue() + "to student: "
				+ student.getUsername() + "in subject: " + subject.getName());
		emailservice.sendMarkEmail(mark);
		return new ResponseEntity<>(mark, HttpStatus.OK);
	}

	@Secured("TEACHER")
	@GetMapping("/getAllMyMarksByTeacher")
	public ResponseEntity<?> getAllMyMarksByTeacher(Principal principal){
	TeacherEntity teacher = teacherRepository.findByUsername(principal.getName());
	
		List<MarkEntity>  all = markRepository.findBySetDeletedFalse()
				.stream().
				filter(mark -> mark.getTeacherSubjectStudent().getTeacherSubject().getTeacher().equals(teacher))
				.collect(Collectors.toList());
		logger.info("Admin got all marks");
		return new ResponseEntity<>(all, HttpStatus.OK);
	}
	
	@Secured("TEACHER")
	@PutMapping("/updateMarkByTeacher")
	public ResponseEntity<?> updateGradeByTeacher(@RequestParam Long markId,
	        @Valid @RequestBody MarkDto updatedMark, BindingResult result, Principal principal) {

	    if (result.hasErrors()) {
	        return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
	    }

	    if (!markservice.isActive(markId)) {
	        return new ResponseEntity<RESTError>(new RESTError(6, "Mark not found."), HttpStatus.NOT_FOUND);
	    }
	    if (!markservice.isItTeachersMark(teacherRepository.findByUsername(principal.getName()).getId(), markId)) {
	    	return new ResponseEntity<RESTError>(new RESTError(6, "Mark is  not yours."), HttpStatus.FORBIDDEN);
	    	
	    }	
	    MarkEntity mark = markRepository.findById(markId).get();
	    mark.setMarkValue(markservice.enumByNo(updatedMark.getValue()));
	    markRepository.save(mark);

	    logger.info("Teacher: " + SecurityContextHolder.getContext().getAuthentication().getName()
	            + " updated mark with id: " + mark.getId() + " to value: " + updatedMark.getValue());

	    //emailservice.sendMarkEmail(mark);

	    return new ResponseEntity<>(mark, HttpStatus.OK);
	}
	
	
	
	@Secured("ADMIN")
	@PostMapping("/createMarkByAdmin")
	public ResponseEntity<?> createGradeByAdmin(@RequestParam Long studentId, @RequestParam Long subjectId,
			@RequestParam Long teacherId, @Valid @RequestBody MarkDto newMark, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}

		if (!subjectService.isActive(subjectId)) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Subject not found."), HttpStatus.NOT_FOUND);
		}

		if (!studentService.isActive(studentId)) {
			return new ResponseEntity<RESTError>(new RESTError(4, "Student does not exist!"), HttpStatus.NOT_FOUND);
		}
		if (!teacherService.isActive(teacherId)) {
			return new ResponseEntity<RESTError>(new RESTError(4, "Teacher does not exist!"), HttpStatus.NOT_FOUND);
		}

		StudentEntity student = studentRepository.findById(studentId).get();
		SubjectEntity subject = subjectRepository.findById(subjectId).get();
		TeacherEntity teacher = teacherRepository.findById(teacherId).get();

		if (!teacherSubjectService.isActive(teacher, subject)) {
			return new ResponseEntity<RESTError>(new RESTError(5, "Subject with Teacher not found."),
					HttpStatus.NOT_FOUND);
		}
		TeacherSubjectEntity teSu = (TeacherSubjectEntity) teacherSubjectRepository.findBySubjectAndTeacher(subject,
				teacher);

		MarkEntity mark = new MarkEntity();
		mark.setMarkValue(markservice.enumByNo(newMark.getValue()));
		mark.setDate(LocalDate.now());
		mark.setTeacherSubjectStudent(tssRepository.findByTeacherSubjectAndStudent(teSu, student));
		markRepository.save(mark);

		logger.info("Admin: " + SecurityContextHolder.getContext().getAuthentication().getName() + " added mark: "
				+ newMark.getValue() + " to student: " + student.getUsername() + " in subject: " + subject.getName());

		//emailservice.sendMarkEmail(mark);

		return new ResponseEntity<>(mark, HttpStatus.OK);
	}
	
	@Secured("ADMIN")
	@PutMapping("/updateMarkByAdmin")
	public ResponseEntity<?> updateGradeByAdmin(@RequestParam Long markId,
	        @Valid @RequestBody MarkDto updatedMark, BindingResult result) {

	    if (result.hasErrors()) {
	        return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
	    }

	    if (!markservice.isActive(markId)) {
	        return new ResponseEntity<RESTError>(new RESTError(6, "Mark not found."), HttpStatus.NOT_FOUND);
	    }
	    MarkEntity mark = markRepository.findById(markId).get();

	    mark.setMarkValue(markservice.enumByNo(updatedMark.getValue()));
	    markRepository.save(mark);

	    logger.info("Admin: " + SecurityContextHolder.getContext().getAuthentication().getName()
	            + " updated mark with id: " + mark.getId() + " to value: " + updatedMark.getValue());

	    //emailservice.sendMarkEmail(mark);

	    return new ResponseEntity<>(mark, HttpStatus.OK);
	}
	
	
	@Secured("ADMIN")
	@GetMapping("/getAllMarksByAdmin")
	public ResponseEntity<?> getAllMarksByAdmin() {
		List<MarkEntity>  all = markRepository.findBySetDeletedFalse();
		logger.info("Admin got all marks");
		return new ResponseEntity<>(all, HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
	
	
}
