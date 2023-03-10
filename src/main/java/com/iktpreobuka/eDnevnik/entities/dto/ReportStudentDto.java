package com.iktpreobuka.eDnevnik.entities.dto;

import java.util.List;

public class ReportStudentDto {

	private String firstName;
	
	private String lastName;
	
	private List<SubjectMarksDto> subjectMarkDto;

	public List<SubjectMarksDto> getSubjectMarkDto() {
		return subjectMarkDto;
	}

	public void setSubjectMarkDto(List<SubjectMarksDto> subjectMarkDto) {
		this.subjectMarkDto = subjectMarkDto;
	}

	public ReportStudentDto() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	
}
