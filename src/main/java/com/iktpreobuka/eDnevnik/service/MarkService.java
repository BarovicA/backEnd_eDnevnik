package com.iktpreobuka.eDnevnik.service;

import com.iktpreobuka.eDnevnik.entities.MarkEntity;
import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.enums.MarkEnum;

public interface MarkService {

	MarkEnum enumByNo(int i);

	SubjectEntity getSubjectFromMark(MarkEntity mark);

	TeacherEntity getTeacherFromMark(MarkEntity mark);

	StudentEntity getStudentFromMark(MarkEntity mark);

	ParentEntity getStudentsParent(MarkEntity mark);

	Boolean isActive(Long id);

}
