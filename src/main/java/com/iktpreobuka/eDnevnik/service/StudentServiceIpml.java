package com.iktpreobuka.eDnevnik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.dto.StudentDto;
import com.iktpreobuka.eDnevnik.repositories.RoleRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;

@Service
@Primary
public class StudentServiceIpml implements StudentService{
	
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private RoleRepository roleRepo;


	@Override
	public StudentEntity mappNewStudent(StudentDto dto) {
		StudentEntity student = new StudentEntity();
		student.setFirstName(dto.getfirstName());
		student.setLastName(dto.getLastName());
		student.setUsername(dto.getUsername());
		student.setPassword(dto.getPassword());
		student.setRole(roleRepo.findByName("STUDENT"));
		student.setDeleted(false);
		return student;
	}


	@Override
	public Boolean isActive(Long id) {
		if(studentRepository.existsById(id)) {
			StudentEntity student = studentRepository.findById(id).get();
			if(student.getDeleted().equals(true)) {
				return false;
			}else {
				return true;
			}
		}
		return false;
	}

	@Override
	public StudentEntity changeStudentEntity(StudentEntity student, StudentDto dto) {
		if (!(dto.getfirstName() == null)) 
			student.setFirstName(dto.getfirstName());
		if (!(dto.getLastName() == null))
			student.setLastName(dto.getLastName());
		if (!(dto.getUsername() == null))
			student.setUsername(dto.getUsername());
		if (!(dto.getPassword() == null))
			student.setPassword(dto.getPassword());
		student.setDeleted(dto.isDeleted());

		return student;
	}
}
