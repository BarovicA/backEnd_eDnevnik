package com.iktpreobuka.eDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.enums.SchoolYear;

public interface GradeRepository extends CrudRepository<GradeEntity, Long> {

	Object findBySchoolYearAndUnit(SchoolYear schoolYear, Integer unit);

}
