package com.iktpreobuka.eDnevnik.util;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.eDnevnik.entities.dto.TeacherEntityDTO;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;

@Component
public class TeacherCustomValidator implements Validator {

@Autowired
TeacherRepository teacherRepo;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return TeacherEntityDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		TeacherEntityDTO teacher = (TeacherEntityDTO) target;
		if(!teacher.getPassword().equals(teacher.getRepeatedPassword())) {
			errors.reject("400", "Passwords must match.");
		}else if(teacherRepo.existsByUsername(teacher.getUsername())) {
			errors.reject("400", "Username already exists.");
		}
	}

}
