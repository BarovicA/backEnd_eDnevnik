package com.iktpreobuka.eDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.ParentEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Long> {

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

}
