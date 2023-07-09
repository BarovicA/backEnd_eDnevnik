package com.iktpreobuka.eDnevnik.service;

import java.util.List;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.enums.SchoolYear;

public interface GradeService {

	GradeEntity create(GradeEntity newGrade);

	GradeEntity mappGradeEntity(GradeEntity old, GradeEntity updated);

	Boolean isActive(Long id);

	boolean isGradeUnique(SchoolYear schoolYear, Integer unit);

	List<StudentEntity> listAllStudentsInGrade(Long gradeId);

	

	void addTeacherSubjectForAllStudentsInGrade(Long gradeId);

	GradeEntity findDeletedGrade(SchoolYear schoolYear, Integer unit);

	GradeEntity findBySchoolYearAndUnit(SchoolYear schoolYear, Integer unit);

}
