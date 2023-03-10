package com.iktpreobuka.eDnevnik.service;

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;

public interface TeacherSubjectService {

	Boolean isActive(Long id);

	Boolean isActive(TeacherEntity teacher, SubjectEntity subject);

}
