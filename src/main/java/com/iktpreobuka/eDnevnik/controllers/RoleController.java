package com.iktpreobuka.eDnevnik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.entities.RoleEntity;
import com.iktpreobuka.eDnevnik.repositories.RoleRepository;

@RestController
@RequestMapping(path = "/api/v1/project")
public class RoleController {

	
	@Autowired
	private RoleRepository roleRepository;


	@Secured("ADMIN")
	@PostMapping("/roles")
	public RoleEntity createRole(@RequestBody RoleEntity role) {
		return roleRepository.save(role);
	}
	@Secured("ADMIN")
	@DeleteMapping("/{id}")
	public RoleEntity deleteRole(@PathVariable Long id) {
		RoleEntity role = roleRepository.findById(id).get();
		roleRepository.delete(role);
		roleRepository.save(role);
		
		return null;
		
	}
}
