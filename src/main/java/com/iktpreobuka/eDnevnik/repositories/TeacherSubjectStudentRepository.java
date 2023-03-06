package com.iktpreobuka.eDnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectStudentEntity;
import com.iktpreobuka.eDnevnik.entities.UserEntity;

public interface TeacherSubjectStudentRepository extends CrudRepository<TeacherSubjectStudentEntity, Long> {

	//List<TeacherSubjectStudentEntity> findByTeacherSubjectTeacher(UserEntity userEntity);

	List<TeacherSubjectStudentEntity> findByTeacherSubject(TeacherSubjectEntity TeacherSubject);

	List<TeacherSubjectStudentEntity> findByTeacherSubjectTeacher(TeacherEntity teacherEntity);

	List<TeacherSubjectStudentEntity> findByTeacherSubjectSubject(SubjectEntity subjectEntity);

	List<TeacherSubjectStudentEntity> findByStudent(StudentEntity studentEntity);

}
