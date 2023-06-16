package com.iktpreobuka.eDnevnik.entities.dto;

import java.util.List;

import com.iktpreobuka.eDnevnik.entities.MarkEntity;
import com.iktpreobuka.eDnevnik.entities.enums.SchoolYear;
import com.iktpreobuka.eDnevnik.entities.enums.Semester;

public class SubjectMarksDto {

	 
	    private String name;
	    private SchoolYear year;
	    private Semester semester;
	    private List<MarkEntity> marks;
	    
	    
		public SubjectMarksDto(String name, SchoolYear year, Semester semester, List<MarkEntity> marks) {
			this.name = name;
			this.year = year;
			this.semester = semester;
			this.marks = marks;
		}


		public SubjectMarksDto() {
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
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


		public List<MarkEntity> getMarks() {
			return marks;
		}


		public void setMarks(List<MarkEntity> marks) {
			this.marks = marks;
		}
	    
	    
	    

	
	
	
	
	
	
	
	
	
}
