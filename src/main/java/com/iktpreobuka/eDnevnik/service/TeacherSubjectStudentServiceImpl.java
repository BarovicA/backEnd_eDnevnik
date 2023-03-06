package com.iktpreobuka.eDnevnik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.TeacherSubjectGradeEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectStudentEntity;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectStudentRepository;

@Service
public class TeacherSubjectStudentServiceImpl implements TeacherSubjectStudentService {
	
	@Autowired
	TeacherSubjectStudentRepository teSuSuRepository;

	@Override
	public Boolean isActive(Long id) {
		if(teSuSuRepository.existsById(id)) {
			TeacherSubjectStudentEntity tss = teSuSuRepository.findById(id).get();
			if(tss.getDeleted().equals(true)) {
				return false;
			}else {
				return true;
			}
		}
		return false;
	}
}
