package com.iktpreobuka.eDnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectGradeEntity;

public interface TeacherSubjectGradeRepository extends CrudRepository<TeacherSubjectGradeEntity, Long> {

	
	TeacherSubjectGradeEntity findByteacherSubjectAndGrade(TeacherSubjectEntity techSub, GradeEntity grade);

	List<TeacherSubjectGradeEntity> findByTeacherSubjectTeacher(TeacherEntity teacherEntity);

	List<TeacherSubjectGradeEntity> findByTeacherSubjectSubject(SubjectEntity subjectEntity);

	


	
}

