package com.iktpreobuka.eDnevnik.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iktpreobuka.eDnevnik.entities.enums.MarkEnum;

@Entity
public class FinalMark {

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
//	@JoinColumn(name = "teacherSubjectStudent")
//	private TeacherSubjectStudentEntity teacherSubjectStudent;


	@Column
	@JsonIgnore

	private Boolean deleted;

	@Version
	@JsonIgnore

	private Integer version;

	public FinalMark() {
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

//	public TeacherSubjectStudentEntity getTeacherSubjectStudent() {
//		return teacherSubjectStudent;
//	}
//
//	public void setTeacherSubjectStudent(TeacherSubjectStudentEntity teacherSubjectStudent) {
//		this.teacherSubjectStudent = teacherSubjectStudent;
//	}

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
