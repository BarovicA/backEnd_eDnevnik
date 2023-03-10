package com.iktpreobuka.eDnevnik.service;

import java.util.List;

import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.dto.StudentDto;
import com.iktpreobuka.eDnevnik.entities.dto.SubjectMarksDto;

public interface StudentService {

	Boolean isActive(Long id);

	StudentEntity changeStudentEntity(StudentEntity student, StudentDto dto);

	StudentEntity mappNewStudent(StudentDto dto);

	StudentEntity addStudentToGrade(Long studentId, Long gradeId);

	List<SubjectMarksDto> makeSubjectMarksDto(StudentEntity student);

}
