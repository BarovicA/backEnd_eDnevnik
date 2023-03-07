package com.iktpreobuka.eDnevnik.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.RoleEntity;
import com.iktpreobuka.eDnevnik.entities.enums.RoleENUM;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

	
	Optional<RoleEntity> findByName(RoleENUM roleName);
	
}
