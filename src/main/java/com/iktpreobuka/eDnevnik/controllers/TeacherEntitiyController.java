package com.iktpreobuka.eDnevnik.controllers;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectGradeEntity;
import com.iktpreobuka.eDnevnik.entities.dto.SubjectDTO;
import com.iktpreobuka.eDnevnik.entities.dto.TeacherEntityDTO;
import com.iktpreobuka.eDnevnik.repositories.GradeRepository;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectGradeRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.service.GradeService;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
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
	@Autowired
	TeacherSubjectGradeRepository teacherSubjectGradeRepository;
	
	@Autowired
	GradeRepository gradeRepository;
	@Autowired
	GradeService gradeService;
    
    @InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(teacherCustomValidator);
	}
    
   
    
    // 1. add TeacherEntity
    @Secured("ADMIN")
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
//    @Secured("ADMIN")
//    @GetMapping
//    public ResponseEntity<?> getAllTeachers() {
//        return new ResponseEntity<>(teacherRepository.findAll(), HttpStatus.OK);
//        
//    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllTeachers() {
        // Dohvati trenutni objekt autentikacije
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Provjeri je li korisnik autenticiran
        if (authentication.isAuthenticated()) {
            // Provjeri uloge korisnika
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

            if (isAdmin) {
                // Korisnik je autenticiran i ima ulogu "ADMIN"
                // Izvrši željene radnje
                return new ResponseEntity<>(teacherRepository.findAll(), HttpStatus.OK);
            } else {
                // Korisnik nema ulogu "ADMIN"
                return new ResponseEntity<>("Nemate dozvolu za pristup ovoj putanji", HttpStatus.FORBIDDEN);
            }
        } else {
            // Korisnik nije autenticiran
            return new ResponseEntity<>("Morate biti autenticirani za pristup ovoj putanji", HttpStatus.UNAUTHORIZED);
        }
    }

    // 3. change TeacherEntity
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable Long id) {
        TeacherEntity teacherEntity = teacherRepository.findById(id).orElse(null);
        if (teacherEntity == null) {
            return new ResponseEntity<RESTError>(new RESTError(1 , "Teacher does not exist!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teacherEntity, HttpStatus.OK);
    }

    // 5. delete TeacherEntity (only set TeacherEntity.deleted on true)
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        TeacherEntity existingTeacher = teacherRepository.findById(id).orElse(null);
        if (existingTeacher == null || existingTeacher.getDeleted() == true) {
            return new ResponseEntity<RESTError>(new RESTError(1,"Teacher does not exist!") , HttpStatus.NOT_FOUND);
        }
        existingTeacher.setDeleted(true);
        teacherRepository.save(existingTeacher);
        logger.info("Deleted teacher: " + id);
        return new ResponseEntity<>("Teacher deleted successfully", HttpStatus.OK);
    }
    
    // add subject for teacher
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{teacherId}/subject/{subjectId}")
    public ResponseEntity<?> addSubjectForTeacher(@PathVariable Long teacherId, @PathVariable Long subjectId) {
    	
    	if (!teService.isActive(teacherId)) {
    		return new ResponseEntity<RESTError>(new RESTError(6, "Teacher not found."), HttpStatus.NOT_FOUND);
		}
    	if (!subjectService.isActive(subjectId)) {
    		return new ResponseEntity<RESTError>(new RESTError(2, "Subject not found."), HttpStatus.NOT_FOUND);
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
    	tse.setYear(subjectRepository.findById(subjectId).get().getYear());
    	tse.setSemester(subjectRepository.findById(subjectId).get().getSemester());
    	tse.setDeleted(false);
    	teacherSubjectRepository.save(tse);
    	logger.info("Successfully created teacherSubject with id: " + tse.getId());
    	return new ResponseEntity<>("Successfully added subject for teacher.", HttpStatus.OK);
    }
    
    //delete subject for teacher
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/teacherSubject{id}")
    public ResponseEntity<?> deleteSubjectTeacher(@PathVariable Long id) {
    	TeacherSubjectEntity ts = teacherSubjectRepository.findById(id).orElse(null);
        if (ts == null || ts.getDeleted() == true) {
        	return new ResponseEntity<RESTError>(new RESTError(1,"Teacher does not exist!") , HttpStatus.NOT_FOUND);
        }
    	ts.setDeleted(true);
    	teacherSubjectRepository.save(ts);
        logger.info("Deleted teacherSubject: " + id);
        return new ResponseEntity<>("TeacherSubject deleted successfully", HttpStatus.OK);
    }
    
///api/v1/teachers/mySubjects
    //prikazi sve predmete koje Naastavnik koji je ulogovan predaje.
    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/mySubjects")
    public ResponseEntity<?> getMySubjects(Principal principal){
    	TeacherEntity teacher = teacherRepository.findByUsername(principal.getName());
    	List<SubjectEntity> subjects = new ArrayList<>();
    	subjects = ((List<TeacherSubjectEntity>) teacherSubjectRepository.findByTeacher(teacher))
    			.stream()
				.filter(teacherSubject -> !teacherSubject.getDeleted().equals(true))
				.map(subject -> subject.getSubject())
				.filter(subject -> !subject.getDeleted().equals(true))
				.collect(Collectors.toList());
    	logger.info("This are all subjects for teacher: " + teacher.getUsername() );
    	return new ResponseEntity<List<SubjectEntity>>(subjects, HttpStatus.OK);
  
    }
    //my subject by id
    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/mySubjects/{id}")
    public ResponseEntity<?> getMySubjectById(@PathVariable Long id, Principal principal) {
        TeacherEntity teacher = teacherRepository.findByUsername(principal.getName());

        if (teacherSubjectRepository.existsBySubjectIdAndTeacher(id, teacher)) {
            TeacherSubjectEntity teacherSubject = teacherSubjectRepository.findBySubjectIdAndTeacher(id, teacher).get();
            SubjectEntity subject = teacherSubject.getSubject();
            SubjectDTO subDTO = subjectService.mappSubjectForDto(subject);
            return new ResponseEntity<>(subDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The subject does not exist or is not linked to this teacher.", HttpStatus.NOT_FOUND);
        }
    }
    
  //prikazi sve Razrede gde Naastavnik koji je ulogovan predaje
    @PreAuthorize("hasAuthority('TEACHER')")
	@GetMapping("/myGrades")
	public ResponseEntity<?> getMyGrades(Principal principal) {
		TeacherEntity teacher = teacherRepository.findByUsername(principal.getName());
		List<GradeEntity> grades = new ArrayList<>();
		List<TeacherSubjectEntity> teacherSubjects = teacherSubjectRepository.findByTeacher(teacher);
		for (TeacherSubjectEntity teacherSubject : teacherSubjects) {
			if (!teacherSubject.getDeleted()) {
				List<TeacherSubjectGradeEntity> teacherSubjectGrades = teacherSubjectGradeRepository
						.findByTeacherSubject(teacherSubject);
				for (TeacherSubjectGradeEntity teacherSubjectGrade : teacherSubjectGrades) {
					if (!teacherSubjectGrade.getDeleted()) {
						grades.add(teacherSubjectGrade.getGrade());
					}
				}
			}
		}
		logger.info("Returned all grades where " + teacher.getUsername()+ " teaches." );
		return new ResponseEntity<List<GradeEntity>>(grades, HttpStatus.OK);
	}
    
    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/myGrades/{id}")
    public ResponseEntity<?> getMyGradeById(@PathVariable Long id, Principal principal) {
        TeacherEntity teacher = teacherRepository.findByUsername(principal.getName());
        GradeEntity grade;
        List<TeacherSubjectEntity> teacherSubjects = teacherSubjectRepository.findByTeacher(teacher);
        for (TeacherSubjectEntity teacherSubject : teacherSubjects) {
            if (!teacherSubject.getDeleted()) {
                List<TeacherSubjectGradeEntity> teacherSubjectGrades = teacherSubjectGradeRepository.findByGradeIdAndTeacherSubject(id, teacherSubject);
                for (TeacherSubjectGradeEntity teacherSubjectGrade : teacherSubjectGrades) {
                    if (!teacherSubjectGrade.getDeleted()) {
                        grade = teacherSubjectGrade.getGrade();
                        logger.info("Returned grade with id " + id + " where " + teacher.getUsername()+ " teaches." );
                        return new ResponseEntity<GradeEntity>(grade, HttpStatus.OK);
                    }
                }
            }
        }
        logger.error("Teacher " + teacher.getUsername() + " does not teach in grade with id " + id);
        return new ResponseEntity<String>("Grade not found or teacher does not teach in this grade.", HttpStatus.NOT_FOUND);
    }


    
    //prikazi sve ucenike u razredu 
    @PreAuthorize("hasAuthority('TEACHER')")
	@GetMapping("/studentsInMyGrade/{gradeid}")
	public ResponseEntity<?> getStudentsInGrade(@PathVariable Long gradeid,Principal principal){
		if (gradeService.isActive(gradeid)) {
		GradeEntity grade = gradeRepository.findById(gradeid).get();
		TeacherEntity teacher = teacherRepository.findByUsername(principal.getName());
		if (teacherSubjectGradeService.isTeacherLinkedWithGrade(teacher, grade)) {
			List<StudentEntity> students = gradeService.listAllStudentsInGrade(gradeid);
			logger.info("Returned all students in grade where " + teacher.getUsername()+ " teaches." );
			return new ResponseEntity<List<StudentEntity>>(students, HttpStatus.OK);
		}else {
			return new ResponseEntity<RESTError>(new RESTError(2, "You are not teaching in this grade."), HttpStatus.NOT_FOUND);
		}
		}
		
	return new ResponseEntity<RESTError>(new RESTError(2, "Grade is not active."), HttpStatus.NOT_FOUND);
	}
 
    //"Subjects where you teach:"
    
//    Map<String, Object> response = new HashMap<>();
//    response.put("message", "Subjects where you teach:");
//    response.put("subjects", subjects);
//
//    return ResponseEntity.ok(response);

}


	
	
	
	
	

