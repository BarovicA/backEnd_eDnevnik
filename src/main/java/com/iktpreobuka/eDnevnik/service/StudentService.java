package com.iktpreobuka.eDnevnik.service;

import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.dto.StudentDto;

public interface StudentService {

	Boolean isActive(Long id);

	StudentEntity changeStudentEntity(StudentEntity student, StudentDto dto);

	StudentEntity mappNewStudent(StudentDto dto);

	StudentEntity addStudentToGrade(Long studentId, Long gradeId);

}
