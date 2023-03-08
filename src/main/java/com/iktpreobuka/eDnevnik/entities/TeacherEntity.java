package com.iktpreobuka.eDnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.eDnevnik.entities.dto.TeacherEntityDTO;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectGradeRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectStudentRepository;

@Entity(name = "Teacher")
@DiscriminatorValue("Teacher")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class TeacherEntity extends UserEntity {

	@JsonIgnore
	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<TeacherSubjectEntity> techerSubject = new ArrayList<>();

	public TeacherEntity() {
		super();
	}

	
	public List<TeacherSubjectEntity> getTecherSubject() {
		return techerSubject;
	}

	public void setTecherSubject(List<TeacherSubjectEntity> techerSubject) {
		this.techerSubject = techerSubject;
	}
	@JsonIgnore
	@Transient
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepository;
	@JsonIgnore
	@Transient
	@Autowired
	private TeacherSubjectGradeRepository teacherSubjectGradeRepository;
	@JsonIgnore
	@Transient
	@Autowired
	private TeacherSubjectStudentRepository teacherSubjectStudentRepository;
	
	@Override
	public void setDeleted(Boolean deleted) {
	    this.deleted = deleted;
	    
	    if (deleted = true) {
	        List<TeacherSubjectEntity> teacherSubjects = teacherSubjectRepository.findByTeacher(this);
	        teacherSubjects.forEach(ts -> ts.setDeleted(true));
	        teacherSubjectRepository.saveAll(teacherSubjects);
	        
	        List<TeacherSubjectStudentEntity> teacherSubjectStudents = teacherSubjectStudentRepository.findByTeacherSubjectTeacher(this);
	        teacherSubjectStudents.forEach(tss -> tss.setDeleted(true));
	        teacherSubjectStudentRepository.saveAll(teacherSubjectStudents);
	        
	        List<TeacherSubjectGradeEntity> teacherSubjectGrades = teacherSubjectGradeRepository.findByTeacherSubjectTeacher(this);
	        teacherSubjectGrades.forEach(tss -> tss.setDeleted(true));
	        teacherSubjectGradeRepository.saveAll(teacherSubjectGrades);
	        
	        
	    }
	}
}
