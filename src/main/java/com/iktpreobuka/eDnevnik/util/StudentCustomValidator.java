package com.iktpreobuka.eDnevnik.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.eDnevnik.entities.dto.StudentDto;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
@Component
public class StudentCustomValidator implements Validator {

	@Autowired
	StudentRepository studentRepo;

	@Override
	public boolean supports(Class<?> clazz) {
	    return StudentDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
	    StudentDto student = (StudentDto) target;
	    if (!student.getPassword().equals(student.getRepeatedPassword())) {
	        errors.reject("400", "Passwords must match.");
	    } else if (studentRepo.existsByUsername(student.getUsername())) {
	        errors.reject("400", "Username already exists.");
	    }
	}

}
