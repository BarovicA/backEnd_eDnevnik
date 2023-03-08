package com.iktpreobuka.eDnevnik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.dto.TeacherEntityDTO;
import com.iktpreobuka.eDnevnik.entities.enums.RoleENUM;
import com.iktpreobuka.eDnevnik.repositories.RoleRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;
@Service
public class TeacherServiceImpl implements TeacherService{
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public TeacherEntity mappNewTeacher(TeacherEntityDTO dto) {
		TeacherEntity teacher = new TeacherEntity();
		teacher.setFirstName(dto.getfirstName());
		teacher.setLastName(dto.getLastName());
		teacher.setUsername(dto.getUsername());
		teacher.setPassword(dto.getPassword());
		teacher.setRole(roleRepo.findByName(RoleENUM.TEACHER).get());
		return teacher;
	}
	
	
	@Override
	public Boolean isActive(Long id) {
		if(teacherRepository.existsById(id)) {
			TeacherEntity teacher = teacherRepository.findById(id).orElse(null);
			if(teacher.getDeleted().equals(true) || teacher == null) {
				return false;
			}else {
				return true;
			}
		}
		return false;
	}
	@Override
	public TeacherEntity changeTeacherEntity (TeacherEntity teacher, TeacherEntityDTO dto) {
		if (!(dto.getfirstName() == null)) 
		teacher.setFirstName(dto.getfirstName());
		if (!(dto.getLastName() == null))
		teacher.setLastName(dto.getLastName());
		if (!(dto.getUsername() == null))
		teacher.setUsername(dto.getUsername());
		if (!(dto.getPassword() == null))
		teacher.setPassword(dto.getPassword());

		return teacher;
	}
	
	
	
	
	

}
