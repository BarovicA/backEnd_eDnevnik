package com.iktpreobuka.eDnevnik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
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
			TeacherSubjectEntity teacherSubject = teacherSubjectRepository.findById(id).orElse(null);
			if(teacherSubject.getDeleted().equals(true) || teacherSubject == null) {
				return false;
			}else {
				return true;
			}
		}
		return false;
	}
	@Override
	public Boolean isActive(TeacherEntity teacher, SubjectEntity subject) {
		if (teacherSubjectRepository.findBySubjectAndTeacher(subject, teacher) == null) {
			return false;
		}
		TeacherSubjectEntity teacherSubject = (TeacherSubjectEntity) teacherSubjectRepository.findBySubjectAndTeacher(subject, teacher);
		if (!teacherSubject.getDeleted()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	
	
}
