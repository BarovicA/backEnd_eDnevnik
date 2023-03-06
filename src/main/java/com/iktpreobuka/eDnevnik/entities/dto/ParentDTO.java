package com.iktpreobuka.eDnevnik.entities.dto;

import javax.validation.constraints.Email;

public class ParentDTO extends UserEntityDTO {
	
	
	@Email
	private String email;
	

	public ParentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	

}
