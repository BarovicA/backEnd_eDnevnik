package com.iktpreobuka.eDnevnik.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "teacher_subject")
public class TeacherSubjectEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher")
	private TeacherEntity teacher;

	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "subject")
	private SubjectEntity subject;

	@JsonIgnore
	@OneToMany(mappedBy = "teacherSubject", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<TeacherSubjectGradeEntity> teacherSubjectGrade = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "teacherSubject", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<TeacherSubjectStudentEntity> teacherSubjectStudent = new ArrayList<>();

	@Column
	private Boolean deleted;

	@Version
	private Integer version;

	public TeacherSubjectEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TeacherEntity getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}

	public SubjectEntity getSubject() {
		return subject;
	}

	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}

	public List<TeacherSubjectGradeEntity> getTeacherSubjectGrade() {
		return teacherSubjectGrade;
	}

	public void setTeacherSubjectGrade(List<TeacherSubjectGradeEntity> teacherSubjectGrade) {
		this.teacherSubjectGrade = teacherSubjectGrade;
	}

	public List<TeacherSubjectStudentEntity> getTeacherSubjectStudent() {
		return teacherSubjectStudent;
	}

	public void setTeacherSubjectStudent(List<TeacherSubjectStudentEntity> teacherSubjectStudent) {
		this.teacherSubjectStudent = teacherSubjectStudent;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
