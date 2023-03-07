package com.iktpreobuka.eDnevnik.entities;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class TeacherSubjectStudentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "student")
	private StudentEntity student;
	

	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherSubject")
	private TeacherSubjectEntity teacherSubject;

	@OneToMany(mappedBy = "teacherSubjectStudent", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<MarkEntity> marks = new ArrayList<>();

//	@JsonBackReference
//	@OneToMany(mappedBy = "teacherSubjectStudent", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
//	private List<FinalMark> finalMarks = new ArrayList<>();

	@Column
	private Boolean deleted;

	@Version
	private Integer version;

	public TeacherSubjectStudentEntity() {
		this.deleted = false;
	}

//	public List<FinalMark> getFinalMarks() {
//		return finalMarks;
//	}
//
//	public void setFinalMarks(List<FinalMark> finalMarks) {
//		this.finalMarks = finalMarks;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public TeacherSubjectEntity getTeacherSubject() {
		return teacherSubject;
	}

	public void setTeacherSubject(TeacherSubjectEntity teacherSubject) {
		this.teacherSubject = teacherSubject;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public List<MarkEntity> getMarks() {
		return marks;
	}

	public void setMarks(List<MarkEntity> marks) {
		this.marks = marks;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
