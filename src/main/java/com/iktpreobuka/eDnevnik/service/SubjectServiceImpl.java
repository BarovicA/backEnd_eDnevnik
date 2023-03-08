package com.iktpreobuka.eDnevnik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.dto.StudentDto;
import com.iktpreobuka.eDnevnik.entities.enums.SchoolYear;
import com.iktpreobuka.eDnevnik.entities.enums.Semester;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	SubjectRepository suRepository;
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

}
