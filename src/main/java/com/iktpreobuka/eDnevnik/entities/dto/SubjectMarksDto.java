package com.iktpreobuka.eDnevnik.entities.dto;

import java.util.List;

import com.iktpreobuka.eDnevnik.entities.MarkEntity;

public class SubjectMarksDto {

	 
	    private String name;
	    private List<MarkEntity> marks;
	    
	    public SubjectMarksDto(String name, List<MarkEntity> marks) {
	        
	        this.name = name;
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

		public List<MarkEntity> getMarks() {
			return marks;
		}

		public void setMarks(List<MarkEntity> marks) {
			this.marks = marks;
		}

	

	
	
	
	
	
	
	
	
	
}
