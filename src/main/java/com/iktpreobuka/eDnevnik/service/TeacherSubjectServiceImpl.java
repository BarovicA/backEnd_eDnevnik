package com.iktpreobuka.eDnevnik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;

@Service
public class TeacherSubjectServiceImpl implements TeacherSubjectService{

	@Autowired
	TeacherSubjectRepository teacherSubjectRepository;
	
	
	@Override
	public Boolean isActive(Long id) {
		if(teacherSubjectRepository.existsById(id)) {
			TeacherSubjectEntity teacher = teacherSubjectRepository.findById(id).orElse(null);
			if(teacher.getDeleted().equals(true) || teacher == null) {
				return false;
			}else {
				return true;
			}
		}
		return false;
	}
}
