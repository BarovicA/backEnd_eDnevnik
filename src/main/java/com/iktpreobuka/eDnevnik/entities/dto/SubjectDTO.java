package com.iktpreobuka.eDnevnik.entities.dto;

import java.util.ArrayList;
import java.util.List;

import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.enums.SchoolYear;
import com.iktpreobuka.eDnevnik.entities.enums.Semester;

public class SubjectDTO {
	
	private Long id;
	private String name;
	private Integer weeklyHours;
	private SchoolYear year;
	private Semester semester;
	private List<TeacherEntity> teachers = new ArrayList<>();
	
	
	public SubjectDTO() {
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


	public List<TeacherEntity> getTeachers() {
		return teachers;
	}


	public void setTeachers(List<TeacherEntity> teachers) {
		this.teachers = teachers;
	}
	
	
	
}
