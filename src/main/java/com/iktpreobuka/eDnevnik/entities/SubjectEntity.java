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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.eDnevnik.entities.enums.SchoolYear;
import com.iktpreobuka.eDnevnik.entities.enums.Semester;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectGradeRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectStudentRepository;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "subject")
public class SubjectEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	@NotNull(message = "Course name must not be provided.")
	@Size(min = 5, max = 30, message = "Course name must be between {min} and {max} characters.")
	private String name;

	@Column
	@NotNull(message = "Weekly hours must not be null.")
	@Min(value = 0, message = "Weekly hours cannot be less than zero.")
	@Max(value = 40, message = "Weekly hours cannot be above 40.")
	private Integer weeklyHours;

	@Column
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Year must not be null.")
	private SchoolYear year;

	@Column
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Semester must not be null.")
	private Semester semester;

	@OneToMany(mappedBy = "subject", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)

	@JsonIgnore
	private List<TeacherSubjectEntity> teacherSubject = new ArrayList<>();

	@Column
	private Boolean deleted;
	
	@JsonIgnore
	@Version
	private Integer version;

	public SubjectEntity() {
		this.deleted = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getWeeklyHours() {
		return weeklyHours;
	}

	public void setWeeklyHours(Integer weeklyHours) {
		this.weeklyHours = weeklyHours;
	}

	public SchoolYear getYear() {
		return year;
	}

	public void setYear(SchoolYear year) {
		this.year = year;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public List<TeacherSubjectEntity> getTeacherSubject() {
		return teacherSubject;
	}

	public void setTeacherSubject(List<TeacherSubjectEntity> teacherSubject) {
		this.teacherSubject = teacherSubject;
	}

	public Boolean getDeleted() {
		return deleted;
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

	
	public void setDeleted(Boolean deleted) {
	    this.deleted = deleted;

	    if (deleted = true) {
	        List<TeacherSubjectEntity> teacherSubjects = teacherSubjectRepository.findBySubject(this);
	        teacherSubjects.forEach(ts -> ts.setDeleted(true));
	        teacherSubjectRepository.saveAll(teacherSubjects);

	        List<TeacherSubjectStudentEntity> teacherSubjectStudents = teacherSubjectStudentRepository.findByTeacherSubjectSubject(this);
	        teacherSubjectStudents.forEach(tss -> tss.setDeleted(true));
	        teacherSubjectStudentRepository.saveAll(teacherSubjectStudents);

	        List<TeacherSubjectGradeEntity> teacherSubjectGrades = teacherSubjectGradeRepository.findByTeacherSubjectSubject(this);
	        teacherSubjectGrades.forEach(tsg -> tsg.setDeleted(true));
	        teacherSubjectGradeRepository.saveAll(teacherSubjectGrades);
	    }
	}

	

}
