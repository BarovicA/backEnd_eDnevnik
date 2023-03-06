package com.iktpreobuka.eDnevnik.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import com.iktpreobuka.eDnevnik.entities.TeacherEntity;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {

	boolean existsByUsername(String username);

	

}
