package com.iktpreobuka.eDnevnik.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.dto.StudentDto;
import com.iktpreobuka.eDnevnik.entities.dto.SubjectDTO;
import com.iktpreobuka.eDnevnik.entities.dto.TeacherEntityDTO;
import com.iktpreobuka.eDnevnik.entities.enums.RoleENUM;
import com.iktpreobuka.eDnevnik.entities.enums.SchoolYear;
import com.iktpreobuka.eDnevnik.entities.enums.Semester;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;
@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	SubjectRepository suRepository;
	
	@Autowired
	TeacherSubjectRepository tsRepository;
	
	@Override
	public boolean isSubjectUnique(String name, SchoolYear schoolYear, Semester semester) {
	    return suRepository.findByNameAndYearAndSemester(name, schoolYear, semester) == null;
	}

	@Override
	public SubjectEntity create(SubjectEntity newSubject) {
		SubjectEntity subject = new SubjectEntity();
		subject.setName(newSubject.getName());
		subject.setYear(newSubject.getYear());
		subject.setSemester(newSubject.getSemester());
		subject.setWeeklyHours(newSubject.getWeeklyHours());
		return suRepository.save(subject);
	}

	@Override
	public Boolean isActive(Long id) {
		if(suRepository.existsById(id)) {
			SubjectEntity sub = suRepository.findById(id).get();
			if(sub.getDeleted().equals(true)) {
				return false;
			}else {
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public SubjectDTO mappSubjectForDto(SubjectEntity subject) {
		SubjectDTO subjectDto = new SubjectDTO();
		subjectDto.setId(subject.getId()); 
		subjectDto.setName(subject.getName());
		subjectDto.setSemester(subject.getSemester());
		subjectDto.setYear(subject.getYear());
		subjectDto.setWeeklyHours(subject.getWeeklyHours());
		List<TeacherSubjectEntity> tsList = tsRepository.findBySubject(subject);
		 List<TeacherEntity> teachers = tsList.stream()
				                        .map(TeacherSubjectEntity::getTeacher)
	                                    .collect(Collectors.toList());
		subjectDto.setTeachers(teachers);
		return subjectDto;
	}

}
