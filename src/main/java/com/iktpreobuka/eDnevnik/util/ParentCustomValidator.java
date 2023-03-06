package com.iktpreobuka.eDnevnik.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.eDnevnik.entities.dto.ParentDTO;
import com.iktpreobuka.eDnevnik.entities.dto.TeacherEntityDTO;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;

@Component
public class ParentCustomValidator implements Validator {
	
	@Autowired
	ParentRepository parentRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return ParentDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ParentDTO parent = (ParentDTO) target;
		if(!parent.getPassword().equals(parent.getRepeatedPassword())) {
			errors.reject("400", "Passwords must match.");
		}else if(parentRepository.existsByUsername(parent.getUsername())) {
			errors.reject("400", "Username already exists.");
		}
		else if (parentRepository.existsByEmail(parent.getEmail())) {
			errors.reject("400", "Email already exists.");
		}
		
	}
}
