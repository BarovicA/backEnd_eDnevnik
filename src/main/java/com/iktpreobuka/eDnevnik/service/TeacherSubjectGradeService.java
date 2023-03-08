package com.iktpreobuka.eDnevnik.service;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectGradeEntity;

public interface TeacherSubjectGradeService {

	

	Boolean isActive(Long id);

	

	boolean isTeacherLinkedWithGrade(TeacherEntity teacher, GradeEntity grade);

}
