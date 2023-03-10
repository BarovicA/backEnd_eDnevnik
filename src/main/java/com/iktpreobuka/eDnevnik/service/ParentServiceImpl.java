package com.iktpreobuka.eDnevnik.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.dto.ParentDTO;
import com.iktpreobuka.eDnevnik.entities.dto.TeacherEntityDTO;
import com.iktpreobuka.eDnevnik.entities.enums.RoleENUM;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.repositories.RoleRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
@Service
public class ParentServiceImpl implements ParentService {
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	private ParentRepository parentRepository;
	
	@Autowired
	private RoleRepository roleRepo;
	
	
	@Override
	public ParentEntity mappNewParent(ParentDTO dto) {
		ParentEntity parent = new ParentEntity();
		parent.setFirstName(dto.getfirstName());
		parent.setLastName(dto.getLastName());
		parent.setEmail(dto.getEmail());
		
		parent.setUsername(dto.getUsername());
		parent.setPassword(dto.getPassword());
		parent.setRole(roleRepo.findByName(RoleENUM.PARENT).get());
		parent.setDeleted(false);
		return parent;
	}
	
	
	@Override
	public Boolean isActive(Long id) {
		if(parentRepository.existsById(id)) {
			ParentEntity parent = parentRepository.findById(id).get();
			if(parent.getDeleted().equals(true)) {
				return false;
			}else {
				return true;
			}
		}
		return false;
	}
	@Override
	public ParentEntity changeParentEntity (ParentEntity parent, ParentDTO dto) {
		if (!(dto.getfirstName() == null)) 
			parent.setFirstName(dto.getfirstName());
		if (!(dto.getLastName() == null))
			parent.setLastName(dto.getLastName());
		if (!(dto.getUsername() == null))
			parent.setUsername(dto.getUsername());
		if (!(dto.getPassword() == null))
			parent.setPassword(dto.getPassword());
		parent.setDeleted(dto.isDeleted());

		return parent;
	}
	@Override
	public List<StudentEntity> getMyStudents(ParentEntity parent){
		List<StudentEntity> students = studentRepository.findByParent(parent);
		return students;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
