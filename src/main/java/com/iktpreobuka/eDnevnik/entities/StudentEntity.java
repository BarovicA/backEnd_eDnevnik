package com.iktpreobuka.eDnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectGradeRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectStudentRepository;

@Entity(name = "Student")
@DiscriminatorValue("Student")
public class StudentEntity extends UserEntity {

	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	private ParentEntity parent;
	
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "grade")
	private GradeEntity grade;

	@OneToMany(mappedBy = "student", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<TeacherSubjectStudentEntity> teacherSubjectStudent;
	
	
	
	
//	@OneToMany(mappedBy = "student", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
//	@JsonBackReference
//	private List<MarkEntity> marks;
//	

	
	
	public StudentEntity() {
		super();
		this.deleted = false;
		
	}

	public List<TeacherSubjectStudentEntity> getTeacherSubjectStudent() {
		return teacherSubjectStudent;
	}

	public void setTeacherSubjectStudent(List<TeacherSubjectStudentEntity> teacherSubjectStudent) {
		this.teacherSubjectStudent = teacherSubjectStudent;
	}

	public TeacherSubjectStudentRepository getTeacherSubjectStudentRepository() {
		return teacherSubjectStudentRepository;
	}

	public void setTeacherSubjectStudentRepository(TeacherSubjectStudentRepository teacherSubjectStudentRepository) {
		this.teacherSubjectStudentRepository = teacherSubjectStudentRepository;
	}

	public TeacherSubjectGradeRepository getTeacherSubjectGradeRepository() {
		return teacherSubjectGradeRepository;
	}

	public void setTeacherSubjectGradeRepository(TeacherSubjectGradeRepository teacherSubjectGradeRepository) {
		this.teacherSubjectGradeRepository = teacherSubjectGradeRepository;
	}

	public ParentEntity getParent() {
		return parent;
	}

	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}

	public GradeEntity getGrade() {
		return grade;
	}

	public void setGrade(GradeEntity grade) {
		this.grade = grade;
	}

//	public List<MarkEntity> getMarks() {
//		return marks;
//	}
//
//	public void setMarks(List<MarkEntity> marks) {
//		this.marks = marks;
//	}
//	
	
	@JsonIgnore
	@Transient
	@Autowired
	private TeacherSubjectStudentRepository teacherSubjectStudentRepository;
	@JsonIgnore
	@Transient
	@Autowired
	private TeacherSubjectGradeRepository teacherSubjectGradeRepository;
	
	
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
		if (deleted) {
		    List<TeacherSubjectStudentEntity> teacherSubjectStudents = teacherSubjectStudentRepository.findByStudent(this);
		    teacherSubjectStudents.forEach(tss -> tss.setDeleted(true));
		    teacherSubjectStudentRepository.saveAll(teacherSubjectStudents);
	}
	}
}