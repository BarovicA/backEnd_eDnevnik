package com.iktpreobuka.eDnevnik.service;

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.dto.SubjectDTO;
import com.iktpreobuka.eDnevnik.entities.enums.SchoolYear;
import com.iktpreobuka.eDnevnik.entities.enums.Semester;

public interface SubjectService {



	Object create(SubjectEntity newSubject);

	boolean isSubjectUnique(String name, SchoolYear schoolYear, Semester semester);

	Boolean isActive(Long id);

	SubjectDTO mappSubjectForDto(SubjectEntity subject);

}
