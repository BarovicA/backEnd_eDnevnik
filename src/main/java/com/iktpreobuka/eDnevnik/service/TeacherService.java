package com.iktpreobuka.eDnevnik.service;

import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.dto.TeacherEntityDTO;

public interface TeacherService {
	
//	public TeacherEntity changeTeacher(Long teacherId, TeacherEntityDTO newTeacher);

	Boolean isActive(Long id);

	TeacherEntity mappNewTeacher(TeacherEntityDTO dto);

	TeacherEntity changeTeacherEntity(TeacherEntity teacher, TeacherEntityDTO dto);
	
}
