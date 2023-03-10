package com.iktpreobuka.eDnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.MarkEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;

public interface MarkRepository extends CrudRepository<MarkEntity, Long>{

	List<MarkEntity> findByTeacherSubjectStudent_StudentAndTeacherSubjectStudent_TeacherSubject_Subject(StudentEntity student, SubjectEntity subject);
	
	
	
}
