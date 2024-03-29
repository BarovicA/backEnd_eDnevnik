package com.iktpreobuka.eDnevnik.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.enums.SchoolYear;

public interface GradeRepository extends CrudRepository<GradeEntity, Long> {

	//Object findBySchoolYearAndUnit(SchoolYear schoolYear, Integer unit);
	
	List<GradeEntity> findBySchoolYearAndDeletedFalse(SchoolYear schoolYear);
	List<GradeEntity> findByUnitAndDeletedFalse(Integer unit);
	List<GradeEntity> findBySchoolYearAndUnitAndDeletedFalse(SchoolYear schoolYear, Integer unit);

	Optional<GradeEntity> findBySchoolYearAndUnitAndDeletedIsTrue(SchoolYear schoolYear, Integer unit);

	GradeEntity findBySchoolYearAndUnit(SchoolYear schoolYear, Integer unit);
}
