package com.iktpreobuka.eDnevnik.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectGradeEntity;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectGradeRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;

@Service
public class TeacherSubjectGradeServiceImpl implements  TeacherSubjectGradeService{

	@Autowired
	TeacherSubjectGradeRepository teSuGradeRepository;
	@Autowired
	TeacherSubjectRepository teacherSubjectRepository;
	
	
	@Override
	public Boolean isActive(Long id) {
		if(teSuGradeRepository.existsById(id)) {
			TeacherSubjectGradeEntity tsg = teSuGradeRepository.findById(id).get();
			if(tsg.getDeleted().equals(true)) {
				return false;
			}else {
				return true;
			}
		}
		return false;
	}
	

	@Override
	public boolean isTeacherLinkedWithGrade(TeacherEntity teacher, GradeEntity grade) {
	    List<TeacherSubjectEntity> teacherSubjects = teacherSubjectRepository.findByTeacher(teacher);
	    for (TeacherSubjectEntity teacherSubject : teacherSubjects) {
	        List<TeacherSubjectGradeEntity> teacherSubjectGrades = teSuGradeRepository.findByTeacherSubject(teacherSubject);
	        for (TeacherSubjectGradeEntity teacherSubjectGrade : teacherSubjectGrades) {
	            if (teacherSubjectGrade.getGrade().equals(grade) && !teacherSubjectGrade.getDeleted()) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
		
		


	
}
