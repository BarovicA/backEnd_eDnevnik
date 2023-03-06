package com.iktpreobuka.eDnevnik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.enums.SchoolYear;
import com.iktpreobuka.eDnevnik.repositories.GradeRepository;

@Service
public class GradeServiceImpl implements GradeService {

	@Autowired
	GradeRepository gradeRepository;
	
	@Override
	public GradeEntity create(GradeEntity newGrade) {
		GradeEntity grade = new GradeEntity();
		grade.setSchoolYear(newGrade.getSchoolYear());
		grade.setUnit(newGrade.getUnit());
		grade.setDeleted(false);
		return gradeRepository.save(grade);
	}
	@Override
	public Boolean isActive(Long id) {
		if(gradeRepository.existsById(id)) {
			GradeEntity grade = gradeRepository.findById(id).get();
			if(grade.getDeleted().equals(true)) {
				return false;
			}else {
				return true;
			}
		}
		return false;
	}
	@Override
	public GradeEntity mappGradeEntity (GradeEntity old, GradeEntity updated) {
		if (!(updated.getSchoolYear() == null)) 
			old.setSchoolYear(updated.getSchoolYear());
		if (!(updated.getUnit() == 0))
			old.setUnit(updated.getUnit());
//		if (!(updated.getTeacherSubjectGrade() == null))
//			old.setTeacherSubjectGrade(updated.getTeacherSubjectGrade());
		return old;
	}
	@Override
	public boolean isGradeUnique(SchoolYear schoolYear, Integer unit) {
	    return gradeRepository.findBySchoolYearAndUnit(schoolYear, unit) == null;
	}
}
