package com.iktpreobuka.eDnevnik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectGradeEntity;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectGradeRepository;

@Service
public class TeacherSubjectGradeServiceImpl implements  TeacherSubjectGradeService{

	@Autowired
	TeacherSubjectGradeRepository teSuGradeRepository;

	@Override
	public TeacherSubjectGradeEntity connectGradeToTeacherSubject(TeacherSubjectEntity teSu, GradeEntity grade) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
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
	
		
		
		


	
}
