package com.iktpreobuka.eDnevnik.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iktpreobuka.eDnevnik.entities.enums.MarkEnum;

@Entity
public class MarkEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate date;

	@Enumerated
	@Column
	private MarkEnum markValue;

	
//	@JsonManagedReference
//	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
//	@JoinColumn(name = "student")
//	private StudentEntity student;
//	
//	@JsonManagedReference
//	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
//	@JoinColumn(name = "subject")
//	private SubjectEntity subject;

	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherSubjectStudent")
	private TeacherSubjectStudentEntity teacherSubjectStudent;

	@Column
	private Boolean deleted;

	@Version
	private Integer version;

	public MarkEntity() {
		this.deleted = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public MarkEnum getMarkValue() {
		return markValue;
	}

	public void setMarkValue(MarkEnum markValue) {
		this.markValue = markValue;
	}

	public TeacherSubjectStudentEntity getTeacherSubjectStudent() {
		return teacherSubjectStudent;
	}

	public void setTeacherSubjectStudent(TeacherSubjectStudentEntity teacherSubjectStudent) {
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
