package com.iktpreobuka.eDnevnik.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
	//public Optional<UserEntity> findByUsername(String username);

	List<UserEntity> findByIdIn(List<Long> userIds);

	Boolean existsByUsername(String username);
	
	Optional<UserEntity> findByUsername(String username); 

}
