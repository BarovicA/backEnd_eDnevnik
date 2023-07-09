package com.iktpreobuka.eDnevnik.service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectGradeEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectStudentEntity;
import com.iktpreobuka.eDnevnik.entities.enums.SchoolYear;
import com.iktpreobuka.eDnevnik.repositories.GradeRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectGradeRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectStudentRepository;

@Service
public class GradeServiceImpl implements GradeService {

	@Autowired
	GradeRepository gradeRepository;
	@Autowired
	TeacherSubjectGradeRepository techerSubjectGradeRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	TeacherSubjectStudentRepository teacherSubjectStudentRepository;
	
	@Override
	public GradeEntity create(GradeEntity newGrade) {
		GradeEntity grade = new GradeEntity();
		grade.setSchoolYear(newGrade.getSchoolYear());
		grade.setUnit(newGrade.getUnit());
		
		return gradeRepository.save(grade);
	}
	@Override
	public GradeEntity findDeletedGrade(SchoolYear schoolYear, Integer unit) {
	    return gradeRepository.findBySchoolYearAndUnitAndDeletedIsTrue(schoolYear, unit).orElse(null);
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
	
	@Override
	public GradeEntity findBySchoolYearAndUnit(SchoolYear schoolYear, Integer unit) {
	    return gradeRepository.findBySchoolYearAndUnit(schoolYear, unit);
	}
	
	@Override
	public List<StudentEntity> listAllStudentsInGrade(Long gradeId){
		GradeEntity grade = gradeRepository.findById(gradeId).get();
		List<StudentEntity> students = (studentRepository.findAllByGrade(grade))
										.stream()
										.filter(student -> !student.getDeleted().equals(true))
										.collect(Collectors.toList());
		
		return students;
	}
	
	// prolazak kroz sve studente u razredu i dodavanje njih u TeacherSubjectStudent ako vec nisu
	// jer Za svaki TeacherSubjectGrade mora da postoji,
	// onoliko TeacherSubjectStudent koliko ima studenta u tom razredu
	
	@Override
	public void addTeacherSubjectForAllStudentsInGrade(Long gradeId) {
		List<StudentEntity> students = listAllStudentsInGrade(gradeId);
		GradeEntity grade = gradeRepository.findById(gradeId).get();
		
		List<TeacherSubjectGradeEntity> listTSG = techerSubjectGradeRepository.findAllByGrade(grade);
		List<TeacherSubjectEntity> listTS = listTSG.stream()
				                                    .map(tsg -> tsg.getTeacherSubject())
		                                            .collect(Collectors.toList());  										
		
		TeacherSubjectStudentEntity TSS = new TeacherSubjectStudentEntity();
		for (TeacherSubjectEntity teacherSubjectEntity : listTS) {
			         for (StudentEntity studentEntity : students) {
						if (teacherSubjectStudentRepository.findByTeacherSubjectAndStudent(teacherSubjectEntity, studentEntity) == null) {
							TSS.setTeacherSubject(teacherSubjectEntity);
							TSS.setStudent(studentEntity);
							teacherSubjectStudentRepository.save(TSS);
						}
						if (teacherSubjectStudentRepository.findByTeacherSubjectAndStudent(teacherSubjectEntity, studentEntity).getDeleted()) {
							teacherSubjectStudentRepository.findByTeacherSubjectAndStudent(teacherSubjectEntity, studentEntity).setDeleted(false);
						}
					}
		}
		
	}

	
	
	
}
