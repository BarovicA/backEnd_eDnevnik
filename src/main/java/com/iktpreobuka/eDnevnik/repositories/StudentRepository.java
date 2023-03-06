package com.iktpreobuka.eDnevnik.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Long> {

	boolean existsByUsername(String username);

	

}
