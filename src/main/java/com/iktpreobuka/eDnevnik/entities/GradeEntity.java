package com.iktpreobuka.eDnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.iktpreobuka.eDnevnik.entities.enums.SchoolYear;


//Klasa Odeljenje u skoli, moze biti vise odeljenja jedne skolske godine(razreda).

@Entity
@Table(uniqueConstraints = @UniqueConstraint (columnNames = {"schoolYear", "unit"}))
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class GradeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column
	private SchoolYear schoolYear;
	
	// broj odeljenja (npr. (SchoolYear)VII, 1)
	
	@Column
	@Min(value = 1, message = "Class number must be grater then 1")
	@Max(value = 4, message = "Class number must me less then 4")
	private Integer unit;
	
	
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherSubjectGrade")
	private TeacherSubjectGradeEntity teacherSubjectGrade;

	
	@JsonIgnore
	@OneToMany(mappedBy = "grade", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<StudentEntity> student = new ArrayList<>();
	
	@JsonIgnore
	private Boolean deleted = Boolean.FALSE;
	
	@Version
	private Integer version;

	public GradeEntity() {
	}

	public Long getId() {
		return id;
	}

	public List<StudentEntity> getStudent() {
		return student;
	}

	public void setStudent(List<StudentEntity> student) {
		this.student = student;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SchoolYear getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(SchoolYear schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public TeacherSubjectGradeEntity getTeacherSubjectGrade() {
		return teacherSubjectGrade;
	}

	public void setTeacherSubjectGrade(TeacherSubjectGradeEntity teacherSubjectGrade) {
		this.teacherSubjectGrade = teacherSubjectGrade;
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
