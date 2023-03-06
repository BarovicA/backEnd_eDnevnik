package com.iktpreobuka.eDnevnik.service;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectGradeEntity;

public interface TeacherSubjectGradeService {

	TeacherSubjectGradeEntity connectGradeToTeacherSubject(TeacherSubjectEntity teSu, GradeEntity grade);

	Boolean isActive(Long id);

}
