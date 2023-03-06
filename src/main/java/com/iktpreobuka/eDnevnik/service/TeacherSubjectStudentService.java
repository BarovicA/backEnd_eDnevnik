package com.iktpreobuka.eDnevnik.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.iktpreobuka.eDnevnik.entities.TeacherSubjectGradeEntity;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectStudentRepository;

public interface TeacherSubjectStudentService {

	Boolean isActive(Long id);
	
	
}
