package com.iktpreobuka.eDnevnik.entities.dto;

public class ReportStudentDto {

	private SubjectMarksDto subjectMarkDto;

	public SubjectMarksDto getSubjectMarkDto() {
		return subjectMarkDto;
	}

	public void setSubjectMarkDto(SubjectMarksDto subjectMarkDto) {
		this.subjectMarkDto = subjectMarkDto;
	}

	public ReportStudentDto(SubjectMarksDto subjectMarkDto) {
		this.subjectMarkDto = subjectMarkDto;
	}
	
}
