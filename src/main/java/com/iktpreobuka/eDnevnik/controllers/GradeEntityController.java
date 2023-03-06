package com.iktpreobuka.eDnevnik.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectGradeEntity;
import com.iktpreobuka.eDnevnik.entities.dto.StudentDto;
import com.iktpreobuka.eDnevnik.repositories.GradeRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectGradeRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.service.GradeService;
import com.iktpreobuka.eDnevnik.service.TeacherSubjectService;
import com.iktpreobuka.eDnevnik.util.RESTError;
import com.iktpreobuka.eDnevnik.validation.Validation;

@RestController
@RequestMapping("/api/v1/grades")
public class GradeEntityController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	@Autowired
	TeacherSubjectRepository teSuRepository;
	@Autowired
	TeacherSubjectService teSuService;
	
	@Autowired
	GradeService gradeService;
	
	@Autowired
	TeacherSubjectGradeRepository teacherSubjectGradeRepository;
	
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators();
	}
	
	@Autowired
	GradeRepository gradeRepository;
	

	@PostMapping("/add")
	public ResponseEntity<?> addNewGrade(@RequestBody GradeEntity newGrade, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		if (gradeService.isGradeUnique(newGrade.getSchoolYear(), newGrade.getUnit())) {
	        logger.info("Created grade:" + newGrade.getSchoolYear().toString() + newGrade.getUnit() );
	        return new ResponseEntity<>(gradeService.create(newGrade), HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("The combination of school year and unit already exists.", HttpStatus.CONFLICT);
	    }
	}
	
	
	
	@PutMapping("/upadte/{id}")
	public ResponseEntity<?> updateGrade(@PathVariable Long id, @Valid @RequestBody GradeEntity upadetedGrade, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		GradeEntity existingGrade = gradeRepository.findById(id).orElse(null);
        if (existingGrade == null) {
            return new ResponseEntity<RESTError>(new RESTError(2, "Grade does not exist!"), HttpStatus.NOT_FOUND);
        }
        existingGrade = gradeService.mappGradeEntity(existingGrade, upadetedGrade);
        logger.info("Changed grade with id:" + id);
		return new ResponseEntity<>(gradeRepository.save(existingGrade), HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAll(){
		return new ResponseEntity<>(gradeRepository.findAll(), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/delete/{Id}")
	public ResponseEntity<?> deleteGrade(@PathVariable Long Id){
		if(gradeService.isActive(Id)) {
			GradeEntity grade = gradeRepository.findById(Id).get();
			grade.setDeleted(true);
			gradeRepository.save(grade);
			logger.info("Deleted grade: " + grade.getSchoolYear().toString() + grade.getUnit());
			return new ResponseEntity<>("Grade successfully deleted", HttpStatus.OK);
			
		}
		return new ResponseEntity<>(new RESTError(1, "Grade with id: " + Id + " doesn't exist."), HttpStatus.NOT_FOUND);
	}
	
	// add TecherSubjectCombination to Grade
	
	@PostMapping("/addSubjecttoGrade/{teacherSubjectId}/grade/{gadeId}")
	public ResponseEntity<?> addSubjecttoGrade(@PathVariable Long teacherSubjectId, @PathVariable Long gadeId){
		
		//is ts active
		if (!teSuService.isActive(teacherSubjectId)) {
			return new ResponseEntity<RESTError>(new RESTError(5, "Subject with Teacher not found."), HttpStatus.NOT_FOUND);
		}
		//is grade active
		if (!gradeService.isActive(gadeId)) {
			return new ResponseEntity<RESTError>(new RESTError(5, "Grade not found."), HttpStatus.NOT_FOUND);
		}
		//is tsg (with ts and grade) exist
		if (teacherSubjectGradeRepository.findByteacherSubjectAndGrade(teSuRepository.findById(teacherSubjectId).get(),
				gradeRepository.findById(gadeId).get()) != null) {
			TeacherSubjectGradeEntity teSuGr = teacherSubjectGradeRepository.findByteacherSubjectAndGrade(teSuRepository.findById(teacherSubjectId).get(),
					gradeRepository.findById(gadeId).get());
			  // if does, is it active
			if (teSuGr.getDeleted() == true) {
				// if not, reactivate
				teSuGr.setDeleted(false);
				teacherSubjectGradeRepository.save(teSuGr);
				logger.info("Successfully reactivated techerSubject for grade: " + teSuGr.getId().toString());
				return new ResponseEntity<>("Successfully reactivated TeacherSubject for Grade.", HttpStatus.OK);
			}
			// if yes message that exist
			else {	
				return new ResponseEntity<>(new RESTError(1, "TeacherSubject with id: " + teacherSubjectId + " already teaches grade with id: " 
						+ gadeId), HttpStatus.BAD_REQUEST);
			}
		}
		//not exist make one
		TeacherSubjectGradeEntity teSuGr =  new TeacherSubjectGradeEntity();
		teSuGr.setTeacherSubject(teSuRepository.findById(teacherSubjectId).get());
		teSuGr.setDeleted(false);
		teacherSubjectGradeRepository.save(teSuGr);
		GradeEntity grade = gradeRepository.findById(gadeId).get();
		grade.setTeacherSubjectGrade(teSuGr);
		gradeRepository.save(grade);
		logger.info("Successfully added techerSubject: " + teacherSubjectId + " for grade: " + gadeId);
		return new ResponseEntity<>("Successfully added TeacherSubject for Grade.", HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
