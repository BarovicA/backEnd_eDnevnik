package com.iktpreobuka.eDnevnik.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.enums.SchoolYear;
import com.iktpreobuka.eDnevnik.entities.enums.Semester;

public interface SubjectRepository extends CrudRepository<SubjectEntity, Long> {

	Object findByNameAndYearAndSemester(String name, SchoolYear schoolYear, Semester semester);

	List<SubjectEntity> findByNameContainingIgnoreCase(String name); 
	List<SubjectEntity> findByDeletedFalse();
	
	List<SubjectEntity> findByYearAndDeletedFalse(SchoolYear year);
    List<SubjectEntity> findBySemesterAndDeletedFalse(Semester semester);
    List<SubjectEntity> findByNameContainingIgnoreCaseAndDeletedFalse(String name);
    List<SubjectEntity> findByYearAndSemesterAndDeletedFalse(SchoolYear year, Semester semester);
    List<SubjectEntity> findByYearAndNameContainingIgnoreCaseAndDeletedFalse(SchoolYear year, String name);
    List<SubjectEntity> findBySemesterAndNameContainingIgnoreCaseAndDeletedFalse(Semester semester, String name);
    List<SubjectEntity> findByYearAndSemesterAndNameContainingIgnoreCaseAndDeletedFalse(SchoolYear year, Semester semester, String name);
}

	


