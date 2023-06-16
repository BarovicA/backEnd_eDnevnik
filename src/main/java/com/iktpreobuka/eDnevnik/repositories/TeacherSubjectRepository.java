package com.iktpreobuka.eDnevnik.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.UserEntity;

public interface TeacherSubjectRepository extends CrudRepository<TeacherSubjectEntity, Long> {

	
    

	Object findBySubjectAndTeacher(SubjectEntity subject, TeacherEntity teacher);

	List<TeacherSubjectEntity> findByTeacher(UserEntity userEntity);
	
	List<TeacherSubjectEntity> findByTeacher(TeacherEntity teacherEntity);

	List<TeacherSubjectEntity> findBySubject(SubjectEntity subjectEntity);

	boolean existsByIdAndTeacher(Long id, TeacherEntity teacher);

	Optional<TeacherSubjectEntity> findByIdAndTeacher(Long id, TeacherEntity teacher);

	boolean existsBySubjectIdAndTeacher(Long id, TeacherEntity teacher);

	Optional<TeacherSubjectEntity> findBySubjectIdAndTeacher(Long id, TeacherEntity teacher);
}
