package com.iktpreobuka.eDnevnik.controllers;

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.dto.TeacherEntityDTO;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.service.SubjectService;
import com.iktpreobuka.eDnevnik.service.TeacherService;
import com.iktpreobuka.eDnevnik.service.TeacherSubjectGradeService;
import com.iktpreobuka.eDnevnik.util.RESTError;
import com.iktpreobuka.eDnevnik.util.TeacherCustomValidator;
import com.iktpreobuka.eDnevnik.validation.Validation;

import org.apache.catalina.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherEntitiyController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherService teService;
    
    @Autowired
    private TeacherCustomValidator teacherCustomValidator;
    
    @Autowired
	private SubjectService subjectService;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	TeacherSubjectRepository teacherSubjectRepository;
	
	@Autowired
	TeacherSubjectGradeService teacherSubjectGradeService;
    
    @InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(teacherCustomValidator);
	}
    
   
    
    // 1. add TeacherEntity
    @PostMapping("/add")
    public ResponseEntity<?> addTeacher(@Valid @RequestBody TeacherEntityDTO newTeacher, BindingResult result) {
    	if (result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			teacherCustomValidator.validate(newTeacher, result);
		}
    	TeacherEntity teacher = teService.mappNewTeacher(newTeacher);
        teacherRepository.save(teacher);
        return new ResponseEntity<TeacherEntity>(teacher, HttpStatus.CREATED);
    }

    // 2. get all TeacherEntity
    @GetMapping
    public ResponseEntity<?> getAllTeachers() {
        return new ResponseEntity<>(teacherRepository.findAll(), HttpStatus.OK);
        
    }

    // 3. change TeacherEntity
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id,@Valid  @RequestBody TeacherEntityDTO dto,
    		BindingResult result) {
    	if (result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			teacherCustomValidator.validate(dto, result);
		}
        TeacherEntity existingTeacher = teacherRepository.findById(id).orElse(null);
        if (existingTeacher == null) {
            return new ResponseEntity<RESTError>(new RESTError(1 , "Teacher does not exist!"), HttpStatus.NOT_FOUND);
        }
        existingTeacher = teService.changeTeacherEntity(existingTeacher, dto);
        teacherRepository.save(existingTeacher);
        return new ResponseEntity<>("Teacher updated successfully!", HttpStatus.ACCEPTED);
    }
//
//    // 4. find by id
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable Long id) {
        TeacherEntity teacherEntity = teacherRepository.findById(id).orElse(null);
        if (teacherEntity == null) {
            return new ResponseEntity<RESTError>(new RESTError(1 , "Teacher does not exist!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teacherEntity, HttpStatus.OK);
    }

    // 5. delete TeacherEntity (only set TeacherEntity.deleted on true)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        TeacherEntity existingTeacher = teacherRepository.findById(id).orElse(null);
        if (existingTeacher == null || existingTeacher.getDeleted() == true) {
            return new ResponseEntity<RESTError>(new RESTError(1,"Teacher does not exist!") , HttpStatus.NOT_FOUND);
        }
        existingTeacher.setDeleted(true);
        teacherRepository.save(existingTeacher);
        return new ResponseEntity<>("Teacher deleted successfully", HttpStatus.OK);
    }
    
    // add subject for teacher
    
    @PostMapping("/{teacherId}/subject/{subjectId}")
    public ResponseEntity<?> addSubjectForTeacher(@PathVariable Long teacherId, @PathVariable Long subjectId) {
    	
    	if (!teService.isActive(teacherId)) {
    		return new ResponseEntity<RESTError>(new RESTError(6, "Teacher not found."), HttpStatus.NOT_FOUND);
		}
    	if (!subjectService.isActive(subjectId)) {
    		return new ResponseEntity<RESTError>(new RESTError(2, "Course not found."), HttpStatus.NOT_FOUND);
		}
    	if ((teacherSubjectRepository.findBySubjectAndTeacher(subjectRepository.findById(subjectId).get(), teacherRepository.findById(teacherId).get()) != null)) {
    		TeacherSubjectEntity tse = (TeacherSubjectEntity) teacherSubjectRepository.findBySubjectAndTeacher(subjectRepository.findById(subjectId).get(), teacherRepository.findById(teacherId).get());
			if (tse.getDeleted() == true) {
				tse.setDeleted(false);
				teacherSubjectRepository.save(tse);
				logger.info("Successfully reactivated subject for teacher: " + tse.getId().toString());
				return new ResponseEntity<>("Successfully reactivated subject for teacher.", HttpStatus.OK);
			}
			else {	
			return new ResponseEntity<>(new RESTError(1, "Teacher with id: " + teacherId + " already teaches subject with id: " 
					+ subjectId), HttpStatus.BAD_REQUEST);
			}
		}
    	TeacherSubjectEntity tse = new TeacherSubjectEntity();
    	tse.setSubject(subjectRepository.findById(subjectId).get());
    	tse.setTeacher(teacherRepository.findById(teacherId).get());
    	tse.setDeleted(false);
    	teacherSubjectRepository.save(tse);
    	logger.info("Successfully created teacherSubject with id: " + tse.getId());
    	return new ResponseEntity<>("Successfully added subject for teacher.", HttpStatus.OK);
    }
    
    
    
    @GetMapping("/changerole/{id}")
    public ResponseEntity<?> TeacherById(@PathVariable Long id) {
        TeacherEntity teacherEntity = teacherRepository.findById(id).orElse(null);
        if (teacherEntity == null) {
            return new ResponseEntity<RESTError>(new RESTError(1 , "Teacher does not exist!"), HttpStatus.NOT_FOUND);
        }
        teacherEntity.setRole(null);
        teacherRepository.save(teacherEntity);
        return new ResponseEntity<>(teacherEntity, HttpStatus.OK);
    }
    
    
    
    
    
    
    
    
    
    
    
}


	
	
	
	
	

